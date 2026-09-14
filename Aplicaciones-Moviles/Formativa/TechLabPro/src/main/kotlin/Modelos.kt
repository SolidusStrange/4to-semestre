import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

sealed class EstadoPuesto {
    data object Disponible : EstadoPuesto()
    data class EnUso(val equipo: Equipo) : EstadoPuesto()
    data class EnProceso(val motivo: String, val equipo: Equipo? = null) : EstadoPuesto()
    data class FueraDeServicio(val motivo: String) : EstadoPuesto()
}

enum class TipoUsuario {
    ESTUDIANTE,
    MENSUAL,
    DOCENTE,
}

open class Equipo(
    val codigo: String,
    val marcaModelo: String,
    val usuario: TipoUsuario,
    val fechaIngreso: LocalDateTime = LocalDateTime.now(),
) {
    open fun calcularCostoBase(minutosUso: Int): Double {
        throw UnsupportedOperationException("Cada tipo de equipo debe definir su tarifa")
    }

    open fun categoria(): String = "Equipo"

    open fun detalle(): String = "${categoria()} $codigo - $marcaModelo"

    fun fechaIngresoFormateada(): String =
        fechaIngreso.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))
}

class Laptop(
    codigo: String,
    marcaModelo: String,
    usuario: TipoUsuario,
) : Equipo(codigo, marcaModelo, usuario) {
    override fun calcularCostoBase(minutosUso: Int): Double {
        val costo = 2000.0 * minutosUso / 60.0
        return if (usuario == TipoUsuario.MENSUAL) costo * 0.80 else costo
    }

    override fun categoria(): String = "Laptop"
}

class Desktop(
    codigo: String,
    marcaModelo: String,
    usuario: TipoUsuario,
) : Equipo(codigo, marcaModelo, usuario) {
    override fun calcularCostoBase(minutosUso: Int): Double {
        if (minutosUso < 30) return 0.0
        return 1500.0 * minutosUso / 60.0
    }

    override fun categoria(): String = "Desktop"
}

class EstacionGrafica(
    codigo: String,
    marcaModelo: String,
    usuario: TipoUsuario,
    val gpuDedicada: Boolean,
) : Equipo(codigo, marcaModelo, usuario) {
    override fun calcularCostoBase(minutosUso: Int): Double {
        val costo = 4000.0 * minutosUso / 60.0
        return if (gpuDedicada) costo * 1.30 else costo
    }

    override fun categoria(): String = "Estacion grafica"

    override fun detalle(): String =
        "${categoria()} $codigo - $marcaModelo - GPU dedicada: ${if (gpuDedicada) "si" else "no"}"
}

data class Ticket(
    val numero: Int,
    val codigoEquipo: String,
    val tipoEquipo: String,
    val tipoUsuario: TipoUsuario,
    val minutosUso: Int,
    val montoPagado: Double,
)

data class SalidaPendiente(
    val numeroPuesto: Int,
    val equipo: Equipo,
)
