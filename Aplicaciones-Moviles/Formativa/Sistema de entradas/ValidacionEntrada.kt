import kotlinx.coroutines.delay

/**
 * Simula la validacion remota de una entrada.
 * delay no bloquea el hilo: suspende la corrutina durante dos segundos.
 */
suspend fun validarEntrada(id: Int, entradas: List<Entrada>): EstadoValidacion {
    delay(2_000)

    val entradaEncontrada = entradas.find { it.id == id }
    return if (entradaEncontrada != null) {
        EstadoValidacion.Valida(entradaEncontrada)
    } else {
        EstadoValidacion.NoValida("No existe una entrada con el id $id.")
    }
}

fun mostrarResultadoValidacion(estado: EstadoValidacion) {
    when (estado) {
        EstadoValidacion.Validando -> println("Validando entrada...")
        is EstadoValidacion.Valida -> println("Entrada valida: ${estado.entrada.mostrarDetalle()}")
        is EstadoValidacion.NoValida -> println("Entrada no valida: ${estado.mensaje}")
    }
}

