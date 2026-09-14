import java.util.Locale

class TechLabProSistema(
    private val nombre: String = "TechLab Pro",
    private val capacidad: Int = 10,
) {
    private val puestos: MutableList<EstadoPuesto> = MutableList(capacidad) { EstadoPuesto.Disponible }
    private val historial: MutableList<Ticket> = mutableListOf()
    private var recaudacionTotal = 0.0
    private var siguienteTicket = 1

    init {
        require(capacidad > 0) { "La capacidad debe ser mayor que cero." }
    }

    fun iniciarEntrada(equipo: Equipo): Int {
        validarCodigoEquipo(equipo.codigo)
        if (codigoEstaActivo(equipo.codigo)) {
            throw OperacionNoPermitidaException("Ya existe un equipo activo con codigo ${equipo.codigo}.")
        }

        val indice = puestos.indexOfFirst { it is EstadoPuesto.Disponible }
        if (indice == -1) {
            throw OperacionNoPermitidaException("$nombre no tiene puestos disponibles.")
        }

        puestos[indice] = EstadoPuesto.EnProceso("registrando entrada con el sensor", equipo)
        return indice + 1
    }

    fun confirmarEntrada(numeroPuesto: Int, equipo: Equipo) {
        val indice = indicePuesto(numeroPuesto)
        when (val estado = puestos[indice]) {
            is EstadoPuesto.EnProceso -> {
                if (estado.equipo?.codigo != equipo.codigo) {
                    throw OperacionNoPermitidaException("El puesto $numeroPuesto esta procesando otra operacion.")
                }
                puestos[indice] = EstadoPuesto.EnUso(equipo)
            }

            EstadoPuesto.Disponible -> throw OperacionNoPermitidaException("El puesto $numeroPuesto no tiene una entrada en proceso.")
            is EstadoPuesto.EnUso -> throw OperacionNoPermitidaException("El puesto $numeroPuesto ya esta ocupado.")
            is EstadoPuesto.FueraDeServicio -> throw OperacionNoPermitidaException("El puesto $numeroPuesto esta fuera de servicio: ${estado.motivo}")
        }
    }

    fun iniciarSalida(codigo: String): SalidaPendiente {
        val indice = puestos.indexOfFirst {
            it is EstadoPuesto.EnUso && it.equipo.codigo.equals(codigo, ignoreCase = true)
        }
        if (indice == -1) {
            throw OperacionNoPermitidaException("No hay un equipo activo con codigo '$codigo'.")
        }

        val equipo = (puestos[indice] as EstadoPuesto.EnUso).equipo
        puestos[indice] = EstadoPuesto.EnProceso("calculando tarifa de salida", equipo)
        return SalidaPendiente(indice + 1, equipo)
    }

    fun cancelarSalida(salida: SalidaPendiente) {
        val indice = indicePuesto(salida.numeroPuesto)
        val estado = puestos[indice]
        if (estado is EstadoPuesto.EnProceso && estado.equipo?.codigo == salida.equipo.codigo) {
            puestos[indice] = EstadoPuesto.EnUso(salida.equipo)
        }
    }

    fun finalizarSalida(salida: SalidaPendiente, minutosUso: Int, monto: Double): Ticket {
        val indice = indicePuesto(salida.numeroPuesto)
        val estado = puestos[indice]
        if (estado !is EstadoPuesto.EnProceso || estado.equipo?.codigo != salida.equipo.codigo) {
            throw OperacionNoPermitidaException("El puesto ${salida.numeroPuesto} ya no esta procesando la salida solicitada.")
        }

        val ticket = Ticket(
            numero = siguienteTicket++,
            codigoEquipo = salida.equipo.codigo,
            tipoEquipo = salida.equipo.categoria(),
            tipoUsuario = salida.equipo.usuario,
            minutosUso = minutosUso,
            montoPagado = monto,
        )
        historial.add(ticket)
        recaudacionTotal += monto
        puestos[indice] = EstadoPuesto.Disponible
        return ticket
    }

    // Calcular costo equipo mas el iva y descuento por ser docente
    fun calcularMontoFinal(equipo: Equipo, minutosUso: Int): Double {
        validarMinutosUso(minutosUso)
        val costoConIva = equipo.calcularCostoBase(minutosUso) * 1.19
        return if (equipo.usuario == TipoUsuario.DOCENTE) costoConIva * 0.50 else costoConIva
    }

    fun puestosDisponibles(): Int = puestos.count { it is EstadoPuesto.Disponible }

    fun equiposMensualesAtendidos(): List<Ticket> =
        historial.filter { it.tipoUsuario == TipoUsuario.MENSUAL }

    fun ingresoPromedio(): Double =
        if (historial.isEmpty()) 0.0 else historial.sumOf { it.montoPagado } / historial.size

    fun codigosFinalizados(): List<String> = historial.map { it.codigoEquipo }

    fun equipoConMasTiempo(): Ticket? = historial.maxByOrNull { it.minutosUso }

    fun ingresosPorTipo(): Map<String, Double> =
        historial.groupBy { it.tipoEquipo }
            .mapValues { (_, tickets) -> tickets.sumOf { it.montoPagado } }

    fun tipoConMasIngresos(): String? =
        ingresosPorTipo().maxByOrNull { it.value }?.key

    fun totalRecaudado(): Double = recaudacionTotal

    fun cantidadAtendidos(): Int = historial.size

    fun deshabilitarPuesto(numeroPuesto: Int, motivo: String) {
        val indice = indicePuesto(numeroPuesto)
        when (val estado = puestos[indice]) {
            EstadoPuesto.Disponible -> puestos[indice] = EstadoPuesto.FueraDeServicio(motivo)
            is EstadoPuesto.EnUso -> throw OperacionNoPermitidaException("No se puede deshabilitar el puesto $numeroPuesto: esta ocupado por ${estado.equipo.codigo}.")
            is EstadoPuesto.EnProceso -> throw OperacionNoPermitidaException("No se puede deshabilitar el puesto $numeroPuesto: ${estado.motivo}.")
            is EstadoPuesto.FueraDeServicio -> throw OperacionNoPermitidaException("El puesto $numeroPuesto ya esta fuera de servicio.")
        }
    }

    fun habilitarPuesto(numeroPuesto: Int) {
        val indice = indicePuesto(numeroPuesto)
        when (val estado = puestos[indice]) {
            is EstadoPuesto.FueraDeServicio -> puestos[indice] = EstadoPuesto.Disponible
            EstadoPuesto.Disponible -> throw OperacionNoPermitidaException("El puesto $numeroPuesto ya esta disponible.")
            is EstadoPuesto.EnUso -> throw OperacionNoPermitidaException("El puesto $numeroPuesto esta ocupado por ${estado.equipo.codigo}.")
            is EstadoPuesto.EnProceso -> throw OperacionNoPermitidaException("El puesto $numeroPuesto esta en proceso: ${estado.motivo}.")
        }
    }

    fun imprimirEstadoPuestos() {
        println("\n--- Estado de puestos ---")
        puestos.forEachIndexed { indice, estado ->
            val descripcion = when (estado) {
                EstadoPuesto.Disponible -> "Disponible"
                is EstadoPuesto.EnUso -> "En uso - ${estado.equipo.detalle()}"
                is EstadoPuesto.EnProceso -> "En proceso - ${estado.motivo}"
                is EstadoPuesto.FueraDeServicio -> "Fuera de servicio - ${estado.motivo}"
            }
            println("Puesto ${indice + 1}: $descripcion")
        }
    }

    fun imprimirConsultasNegocio() {
        println("\n--- Consultas de negocio ---")
        println("Puestos disponibles: ${puestosDisponibles()}")
        println("Equipos de usuario mensual: ${equiposMensualesAtendidos().map { it.codigoEquipo }.ifEmpty { listOf("ninguno") }.joinToString()}")
        println("Ingreso promedio: ${formatearMonto(ingresoPromedio())}")
        println("Codigos finalizados: ${codigosFinalizados().ifEmpty { listOf("ninguno") }.joinToString()}")
        val mayorTiempo = equipoConMasTiempo()
        println("Equipo con mas tiempo: ${mayorTiempo?.let { "${it.codigoEquipo} (${it.minutosUso} min)" } ?: "ninguno"}")
    }

    fun imprimirReporteCierre() {
        println("\n===== REPORTE DE CIERRE: $nombre =====")
        if (historial.isEmpty()) {
            println("No se registraron equipos finalizados durante el turno.")
        } else {
            historial.forEach { ticket ->
                println(
                    "Ticket #${ticket.numero} | ${ticket.tipoEquipo} | ${ticket.codigoEquipo} | " +
                        "${ticket.minutosUso} min | ${formatearMonto(ticket.montoPagado)}",
                )
            }
        }
        println("----------------------------------------")
        println("Total recaudado: ${formatearMonto(totalRecaudado())}")
        println("Equipos atendidos: ${cantidadAtendidos()}")
        println("Ingreso promedio: ${formatearMonto(ingresoPromedio())}")
        println("Tipo con mas ingresos: ${tipoConMasIngresos() ?: "N/A"}")
        println("Puestos disponibles al cierre: ${puestosDisponibles()}")
        println("Recaudacion por tipo: ${ingresosPorTipo().entries.joinToString { "${it.key}=${formatearMonto(it.value)}" }.ifEmpty { "sin cobros" }}")
    }

    private fun codigoEstaActivo(codigo: String): Boolean = puestos.any { estado ->
        when (estado) {
            is EstadoPuesto.EnUso -> estado.equipo.codigo.equals(codigo, ignoreCase = true)
            is EstadoPuesto.EnProceso -> estado.equipo?.codigo?.equals(codigo, ignoreCase = true) == true
            EstadoPuesto.Disponible, is EstadoPuesto.FueraDeServicio -> false
        }
    }

    private fun indicePuesto(numeroPuesto: Int): Int {
        if (numeroPuesto !in 1..capacidad) {
            throw DatosInvalidosException("El puesto debe estar entre 1 y $capacidad.")
        }
        return numeroPuesto - 1
    }
}

fun formatearMonto(monto: Double): String = String.format(Locale.forLanguageTag("es-CL"), "\$%,.0f", monto)
