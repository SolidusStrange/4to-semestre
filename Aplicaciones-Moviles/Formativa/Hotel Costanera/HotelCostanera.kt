import java.time.LocalDateTime
import kotlin.math.roundToLong

/** Contiene el catalogo, el historial del turno, las operaciones y las consultas de negocio. */
class HotelCostanera(
    private val capacidad: Int = CAPACIDAD_PREDETERMINADA
) {
    private val habitaciones = mutableListOf<Habitacion>()
    private val historialTickets = mutableListOf<Ticket>()
    private val recaudacionPorTipo = TipoHabitacion.entries
        .associateWith { 0.0 }
        .toMutableMap()

    private var recaudacionTotal = 0.0
    private var siguienteNumeroTicket = 1

    fun registrarHabitacion(habitacion: Habitacion): ResultadoOperacion<Unit> {
        if (!CODIGO_HABITACION.matches(habitacion.codigo)) {
            return ResultadoOperacion.Error(
                "Codigo invalido: ${habitacion.codigo}. Debe tener una letra entre A y F y dos digitos."
            )
        }
        if (habitacion.tarifaBase <= 0.0) {
            return ResultadoOperacion.Error("Error de datos: la tarifa base debe ser mayor que cero.")
        }
        if (habitaciones.any { it.codigo == habitacion.codigo }) {
            return ResultadoOperacion.Error("Ya existe una habitacion registrada con el codigo ${habitacion.codigo}.")
        }
        if (habitaciones.size >= capacidad) {
            return ResultadoOperacion.Error("Hotel sin capacidad: el catalogo ya tiene $capacidad habitaciones.")
        }

        habitaciones.add(habitacion)
        return ResultadoOperacion.Exito(Unit, "Habitacion ${habitacion.codigo} registrada correctamente.")
    }

    fun marcarFueraDeServicio(codigo: String, motivo: String): ResultadoOperacion<Unit> {
        val habitacion = buscarHabitacion(codigo) ?: return ResultadoOperacion.Error(
            "Huesped no encontrado: no existe una habitacion registrada con el codigo $codigo."
        )

        if (habitacion.estado !is EstadoHabitacion.Libre) {
            return ResultadoOperacion.Error(
                "No se puede inhabilitar ${habitacion.codigo} porque no esta libre."
            )
        }

        habitacion.cambiarEstado(EstadoHabitacion.FueraDeServicio(motivo))
        return ResultadoOperacion.Exito(Unit, "Habitacion ${habitacion.codigo} fuera de servicio: $motivo")
    }

    /** Busca la primera habitacion libre, la deja EnProceso y confirma el check-in tras tres segundos. */
    suspend fun registrarCheckIn(huesped: Huesped): ResultadoOperacion<Habitacion> {
        val habitacion = habitaciones.firstOrNull { it.estado is EstadoHabitacion.Libre }
            ?: return ResultadoOperacion.Error("Hotel sin capacidad: no hay habitaciones libres para el check-in.")

        habitacion.cambiarEstado(EstadoHabitacion.EnProceso("Registrando check-in"))
        kotlinx.coroutines.delay(DEMORA_CHECK_IN_MS)

        val estadia = RegistroEstadia(huesped, LocalDateTime.now())
        habitacion.cambiarEstado(EstadoHabitacion.Ocupada(estadia))
        return ResultadoOperacion.Exito(
            habitacion,
            "Check-in confirmado para ${huesped.nombre} en la habitacion ${habitacion.codigo}."
        )
    }

    /**
     * Conserva la estadia si ocurre un error al calcular la tarifa. Asi la habitacion no queda
     * bloqueada en EnProceso ni se pierde el huesped asignado.
     */
    suspend fun registrarCheckOut(codigo: String, noches: Int): ResultadoOperacion<Ticket> {
        if (!CODIGO_HABITACION.matches(codigo)) {
            return ResultadoOperacion.Error("Codigo invalido: $codigo.")
        }

        val habitacion = buscarHabitacion(codigo)
            ?: return ResultadoOperacion.Error(
                "Huesped no encontrado: no existe una habitacion registrada con el codigo $codigo."
            )

        val estadoInicial = habitacion.estado
        if (estadoInicial !is EstadoHabitacion.Ocupada) {
            return ResultadoOperacion.Error(mensajeCheckOutNoPermitido(habitacion.codigo, estadoInicial))
        }

        habitacion.cambiarEstado(EstadoHabitacion.EnProceso("Calculando tarifa final"))
        kotlinx.coroutines.delay(DEMORA_CHECK_OUT_MS)

        return try {
            val montoFinal = habitacion.calcularMontoFinal(noches, estadoInicial.estadia.huesped.tipo)
            val ticket = Ticket(
                numero = siguienteNumeroTicket++,
                huesped = estadoInicial.estadia.huesped,
                tipoHabitacion = habitacion.tipo,
                codigoHabitacion = habitacion.codigo,
                noches = noches,
                montoPagado = montoFinal
            )

            historialTickets.add(ticket)
            recaudacionTotal += montoFinal
            recaudacionPorTipo[habitacion.tipo] = recaudacionPorTipo.getValue(habitacion.tipo) + montoFinal
            habitacion.cambiarEstado(EstadoHabitacion.Libre)

            ResultadoOperacion.Exito(
                ticket,
                "Check-out confirmado. Ticket #${ticket.numero} emitido para ${ticket.huesped.nombre}."
            )
        } catch (error: TarifaInvalidaException) {
            habitacion.cambiarEstado(estadoInicial)
            ResultadoOperacion.Error("Resultado de tarifa invalido: ${error.message}")
        }
    }

    fun cantidadHabitacionesDisponibles(): Int = habitaciones.count {
        it.estado is EstadoHabitacion.Libre
    }

    fun huespedesFrecuentesDelTurno(): List<Huesped> = historialTickets
        .map { it.huesped }
        .filter { it.tipo == TipoHuesped.FRECUENTE }

    fun ingresoPromedioPorHuesped(): Double = if (historialTickets.isEmpty()) {
        0.0
    } else {
        recaudacionTotal / historialTickets.size
    }

    fun codigosConCheckOut(): List<String> = historialTickets.map { it.codigoHabitacion }

    fun huespedConMasNoches(): Ticket? = historialTickets.maxByOrNull { it.noches }

    fun tipoConMayorRecaudacion(): TipoHabitacion? = if (historialTickets.isEmpty()) {
        null
    } else {
        recaudacionPorTipo.maxByOrNull { it.value }?.key
    }

    fun generarReporteCierre(): String = buildString {
        appendLine("\n=== REPORTE DE CIERRE - HOTEL COSTANERA ===")
        if (historialTickets.isEmpty()) {
            appendLine("No hubo huespedes atendidos durante el turno.")
        } else {
            historialTickets.forEach { ticket ->
                appendLine(
                    "Ticket #${ticket.numero} | ${ticket.huesped.nombre} | " +
                        "${ticket.tipoHabitacion.etiqueta} | ${ticket.codigoHabitacion} | " +
                        "${ticket.noches} noche(s) | ${formatearPesos(ticket.montoPagado)}"
                )
            }
        }

        val huespedConMayorEstadia = huespedConMasNoches()
        appendLine("Total recaudado: ${formatearPesos(recaudacionTotal)}")
        appendLine("Huespedes atendidos: ${historialTickets.size}")
        appendLine("Ingreso promedio: ${formatearPesos(ingresoPromedioPorHuesped())}")
        appendLine(
            "Tipo con mayor recaudacion: " +
                (tipoConMayorRecaudacion()?.etiqueta ?: "Sin datos de check-out")
        )
        appendLine("Habitaciones disponibles al cierre: ${cantidadHabitacionesDisponibles()}")
        appendLine("Huespedes frecuentes: ${huespedesFrecuentesDelTurno().joinToString { it.nombre }}")
        appendLine("Codigos con check-out: ${codigosConCheckOut().joinToString()}")
        appendLine(
            "Estadia mas larga: " +
                (huespedConMayorEstadia?.let { "${it.huesped.nombre} (${it.noches} noches)" }
                    ?: "Sin datos")
        )
    }

    private fun buscarHabitacion(codigo: String): Habitacion? = habitaciones.find { it.codigo == codigo }

    private fun mensajeCheckOutNoPermitido(codigo: String, estado: EstadoHabitacion): String = when (estado) {
        EstadoHabitacion.Libre -> "No se puede hacer check-out de $codigo porque esta libre."
        is EstadoHabitacion.EnProceso ->
            "No se puede hacer check-out de $codigo: esta en proceso (${estado.motivo})."
        is EstadoHabitacion.FueraDeServicio ->
            "No se puede hacer check-out de $codigo: esta fuera de servicio (${estado.motivo})."
        is EstadoHabitacion.Ocupada -> "No se pudo iniciar el check-out de $codigo."
    }

    private fun formatearPesos(monto: Double): String = "${'$'}${monto.roundToLong()}"

    companion object {
        const val CAPACIDAD_PREDETERMINADA = 12
        const val DEMORA_CHECK_IN_MS = 3_000L
        const val DEMORA_CHECK_OUT_MS = 6_500L
        val CODIGO_HABITACION = Regex("[A-F]\\d{2}")
    }
}

