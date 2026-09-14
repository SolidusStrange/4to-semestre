import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

private const val DEMORA_ENTRADA_MS = 3_000L
private const val DEMORA_SALIDA_MS = 6_500L

suspend fun registrarIngreso(sistema: TechLabProSistema, equipo: Equipo): Boolean {
    return try {
        val puesto = sistema.iniciarEntrada(equipo)
        println("Puesto $puesto en proceso: registrando ${equipo.detalle()}.")
        delay(DEMORA_ENTRADA_MS)
        sistema.confirmarEntrada(puesto, equipo)
        println("Puesto $puesto: ingreso confirmado para ${equipo.codigo}.")
        true
    } catch (error: ReglaNegocioException) {
        mostrarError(error)
        false
    }
}

// Para emitir el ticket cuando seatodo valido
suspend fun registrarSalida(sistema: TechLabProSistema, codigo: String, minutosUso: Int): Boolean {
    var salidaPendiente: SalidaPendiente? = null
    return try {
        salidaPendiente = sistema.iniciarSalida(codigo)
        val salida = requireNotNull(salidaPendiente)
        println("Puesto ${salida.numeroPuesto} en proceso: calculando tarifa para ${salida.equipo.codigo}.")
        delay(DEMORA_SALIDA_MS)

        val monto = sistema.calcularMontoFinal(salida.equipo, minutosUso)
        val cobroSinCosto = salida.equipo is Desktop && minutosUso < 30
        validarMontoFinal(monto, cobroSinCosto)

        val ticket = sistema.finalizarSalida(salida, minutosUso, monto)
        val mensajeCobro = if (cobroSinCosto) "sin cobro por uso inferior a 30 minutos" else formatearMonto(monto)
        println("Puesto ${salida.numeroPuesto}: salida confirmada. Ticket #${ticket.numero}, $mensajeCobro.")
        true
    } catch (error: ReglaNegocioException) {
        salidaPendiente?.let { sistema.cancelarSalida(it) }
        mostrarError(error)
        false
    }
}

private fun mostrarError(error: Exception) {
    println("[ERROR] ${error.message ?: "Ocurrio un error controlado."}")
}

// demostracion solicitada
private suspend fun ejecutarDemostracion(sistema: TechLabProSistema) {
    val casos = listOf(
        Laptop("LB12PC", "Dell XPS 15", TipoUsuario.MENSUAL) to 75,
        Laptop("HP99ZA", "HP Pavilion 15", TipoUsuario.ESTUDIANTE) to 180,
        Desktop("DS22TO", "iMac 24", TipoUsuario.ESTUDIANTE) to 25,
        EstacionGrafica("EG44RG", "Mac Pro", TipoUsuario.DOCENTE, gpuDedicada = true) to 120,
        EstacionGrafica("EG77RG", "PC Workstation", TipoUsuario.ESTUDIANTE, gpuDedicada = false) to 45,
    )

    println("\n== Prueba de error controlado ==")
    registrarIngreso(sistema, Laptop("123ABC", "Codigo invalido", TipoUsuario.ESTUDIANTE))

    println("\n== Ingresos asincronos ==")
    coroutineScope {
        casos.map { (equipo, _) -> async { registrarIngreso(sistema, equipo) } }.awaitAll()
    }
    sistema.imprimirEstadoPuestos()

    println("\n== Salidas asincronas ==")
    coroutineScope {
        casos.map { (equipo, minutos) -> async { registrarSalida(sistema, equipo.codigo, minutos) } }.awaitAll()
    }
    sistema.imprimirConsultasNegocio()
    sistema.imprimirReporteCierre()
}

private fun mostrarMenu() {
    println(
        """===== TECHLAB PRO =====
        1. Registrar ingreso de equipo
        2. Registrar salida de equipo
        3. Ver estado de puestos
        4. Ver consultas de negocio
        5. Ver reporte de cierre
        6. Deshabilitar puesto
        7. Habilitar puesto
        8. Ejecutar demostracion
        0. Salir
        """.trimIndent(),
    )
}

private suspend fun ejecutarMenu(sistema: TechLabProSistema) {
    while (true) {
        mostrarMenu()
        when (leerTexto("Selecciona una opcion: ")) {
            "1" -> leerEquipoDesdeConsola()?.let { registrarIngreso(sistema, it) }
            "2" -> {
                val codigo = leerTexto("Codigo del equipo: ")
                val minutos = leerEntero("Minutos de uso: ") ?: continue
                registrarSalida(sistema, codigo, minutos)
            }

            "3" -> sistema.imprimirEstadoPuestos()
            "4" -> sistema.imprimirConsultasNegocio()
            "5" -> sistema.imprimirReporteCierre()
            "6" -> cambiarEstadoPuesto(sistema, deshabilitar = true)
            "7" -> cambiarEstadoPuesto(sistema, deshabilitar = false)
            "8" -> ejecutarDemostracion(sistema)
            "0" -> {
                println("Turno finalizado.")
                return
            }

            else -> println("[ERROR] Opcion no valida.")
        }
    }
}

private fun leerEquipoDesdeConsola(): Equipo? {
    println("Tipo de equipo: 1) Laptop  2) Desktop  3) Estacion grafica")
    val tipo = leerTexto("Tipo: ")
    val codigo = leerTexto("Codigo: ")
    val marcaModelo = leerTexto("Marca y modelo: ")
    val usuario = leerTipoUsuario() ?: return null

    return when (tipo) {
        "1" -> Laptop(codigo, marcaModelo, usuario)
        "2" -> Desktop(codigo, marcaModelo, usuario)
        "3" -> {
            val gpuDedicada = leerTexto("¿Tiene GPU dedicada? (s/n): ").equals("s", ignoreCase = true)
            EstacionGrafica(codigo, marcaModelo, usuario, gpuDedicada)
        }

        else -> {
            println("[ERROR] Debes escoger 1, 2 o 3.")
            null
        }
    }
}

private fun leerTipoUsuario(): TipoUsuario? {
    println("Tipo de usuario: 1) Estudiante  2) Mensual  3) Docente")
    return when (leerTexto("Usuario: ")) {
        "1" -> TipoUsuario.ESTUDIANTE
        "2" -> TipoUsuario.MENSUAL
        "3" -> TipoUsuario.DOCENTE
        else -> {
            println("[ERROR] Tipo de usuario no valido.")
            null
        }
    }
}

private fun cambiarEstadoPuesto(sistema: TechLabProSistema, deshabilitar: Boolean) {
    val numero = leerEntero("Numero de puesto: ") ?: return
    try {
        if (deshabilitar) {
            val motivo = leerTexto("Motivo: ")
            sistema.deshabilitarPuesto(numero, motivo)
            println("Puesto $numero deshabilitado.")
        } else {
            sistema.habilitarPuesto(numero)
            println("Puesto $numero habilitado.")
        }
    } catch (error: ReglaNegocioException) {
        mostrarError(error)
    }
}

private fun leerTexto(mensaje: String): String {
    print(mensaje)
    return readlnOrNull()?.trim().orEmpty()
}

private fun leerEntero(mensaje: String): Int? = leerTexto(mensaje).toIntOrNull().also {
    if (it == null) println("[ERROR] Debes ingresar un numero entero.")
}

fun main(args: Array<String>) = runBlocking {
    val sistema = TechLabProSistema()
    if (args.contains("--demo")) {
        ejecutarDemostracion(sistema)
    } else {
        ejecutarMenu(sistema)
    }
}
