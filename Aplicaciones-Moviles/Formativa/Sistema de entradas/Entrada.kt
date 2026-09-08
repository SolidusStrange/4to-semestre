/**
 * Clase base para cualquier tipo de entrada del evento.
 * En Kotlin las clases son finales por defecto; por eso se usa open para permitir herencia.
 */
open class Entrada(
    val id: Int,
    val precio: Double
) {
    open fun mostrarDetalle(): String =
        "Entrada #$id | Precio: ${'$'}${"%.0f".format(precio)}"
}

class EntradaGeneral(
    id: Int,
    precio: Double,
    val sector: String
) : Entrada(id, precio) {
    override fun mostrarDetalle(): String =
        "[GENERAL] ${super.mostrarDetalle()} | Sector: $sector"
}

class EntradaVIP(
    id: Int,
    precio: Double,
    val beneficiosExtra: List<String>
) : Entrada(id, precio) {
    override fun mostrarDetalle(): String =
        "[VIP] ${super.mostrarDetalle()} | Beneficios: ${beneficiosExtra.joinToString()}"
}

