import kotlinx.coroutines.runBlocking
import kotlin.math.roundToLong

fun main() = runBlocking {
    val hotel = HotelCostanera()
    registrarCatalogoInicial(hotel)

    println("=== CHECK-IN ASINCRONO ===")
    val huespedes = listOf(
        Huesped("Carla Reyes", TipoHuesped.FRECUENTE),
        Huesped("Jorge Peña", TipoHuesped.REGULAR),
        Huesped("Marta Soto", TipoHuesped.REGULAR),
        Huesped("Diego Fuentes", TipoHuesped.MOVILIDAD_REDUCIDA),
        Huesped("Valentina Ruiz", TipoHuesped.REGULAR)
    )
    huespedes.forEach { huesped ->
        imprimirResultado(hotel.registrarCheckIn(huesped)) { habitacion ->
            detalleHabitacion(habitacion)
        }
    }

    println("\n=== CHECK-OUT ASINCRONO ===")
    val nochesPorCodigo = linkedMapOf(
        "A05" to 3,
        "B12" to 2,
        "B03" to 1,
        "C01" to 4,
        "C02" to 2
    )
    nochesPorCodigo.forEach { (codigo, noches) ->
        imprimirResultado(hotel.registrarCheckOut(codigo, noches)) { ticket ->
            "${ticket.codigoHabitacion}: ${ticket.noches} noche(s), monto ${formatearMonto(ticket.montoPagado)}."
        }
    }

    print(hotel.generarReporteCierre())

    println("\n=== PRUEBAS DE ERRORES CONTROLADOS ===")
    imprimirResultado(hotel.registrarHabitacion(HabitacionIndividual("9XZ"))) { "" }
    imprimirResultado(hotel.registrarCheckOut("F99", 1)) { "" }
    imprimirResultado(TipoHuesped.desdeTexto("Corporativo")) { it.etiqueta }
}

private fun registrarCatalogoInicial(hotel: HotelCostanera) {
    val habitaciones: List<Habitacion> = listOf(
        HabitacionIndividual("A05"),
        HabitacionDoble("B12"),
        HabitacionDoble("B03"),
        HabitacionSuite("C01", vistaAlMar = true),
        HabitacionSuite("C02", vistaAlMar = false),
        HabitacionIndividual("A01"),
        HabitacionIndividual("A02"),
        HabitacionIndividual("A03"),
        HabitacionDoble("B01"),
        HabitacionDoble("B02"),
        HabitacionSuite("C03", vistaAlMar = true),
        HabitacionSuite("C04", vistaAlMar = false)
    )

    habitaciones.forEach { habitacion ->
        imprimirResultado(hotel.registrarHabitacion(habitacion)) { "" }
    }
}

private fun <T> imprimirResultado(
    resultado: ResultadoOperacion<T>,
    detalle: (T) -> String
) {
    when (resultado) {
        is ResultadoOperacion.Exito -> {
            val textoDetalle = detalle(resultado.valor)
            println("OK: ${resultado.mensaje}${if (textoDetalle.isBlank()) "" else " $textoDetalle"}")
        }
        is ResultadoOperacion.Error -> println("ERROR: ${resultado.mensaje}")
    }
}

private fun detalleHabitacion(habitacion: Habitacion): String = when (habitacion) {
    is HabitacionIndividual -> "Tipo asignado: Individual."
    is HabitacionDoble -> "Tipo asignado: Doble."
    is HabitacionSuite -> "Tipo asignado: Suite. ${habitacion.detalleVista()}."
    else -> "Tipo asignado: ${habitacion.tipo.etiqueta}."
}

private fun formatearMonto(monto: Double): String = "${'$'}${monto.roundToLong()}"
