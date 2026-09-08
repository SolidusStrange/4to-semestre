import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val entradas: List<Entrada> = listOf(
        EntradaGeneral(id = 101, precio = 15_000.0, sector = "Cancha"),
        EntradaVIP(
            id = 202,
            precio = 45_000.0,
            beneficiosExtra = listOf("Acceso preferente", "Zona lounge", "Bebida de cortesia")
        ),
        EntradaGeneral(id = 303, precio = 18_000.0, sector = "Platea"),
        EntradaVIP(
            id = 404,
            precio = 50_000.0,
            beneficiosExtra = listOf("Meet and greet", "Estacionamiento")
        )
    )

    println("=== DETALLE DE ENTRADAS ===")
    entradas.forEach { entrada -> println(entrada.mostrarDetalle()) }

    val ingresoTotal = entradas.sumOf { entrada -> entrada.precio }
    val entradasVIP = entradas.filterIsInstance<EntradaVIP>()
    println("\nIngreso total: ${'$'}${"%.0f".format(ingresoTotal)}")
    println("Cantidad de entradas VIP vendidas: ${entradasVIP.size}")

    println("\n=== VALIDACION ASINCRONA ===")
    val idABuscar = 202
    mostrarResultadoValidacion(EstadoValidacion.Validando)

    val resultado = validarEntrada(idABuscar, entradas)
    mostrarResultadoValidacion(resultado)
}
