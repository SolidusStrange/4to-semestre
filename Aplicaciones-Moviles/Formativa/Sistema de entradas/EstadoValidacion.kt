/** Representa todos los estados posibles de la validacion de una entrada. */
sealed class EstadoValidacion {
    object Validando : EstadoValidacion()
    data class Valida(val entrada: Entrada) : EstadoValidacion()
    data class NoValida(val mensaje: String) : EstadoValidacion()
}

