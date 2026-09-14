open class ReglaNegocioException(mensaje: String) : Exception(mensaje)

class CodigoInvalidoException(codigo: String) :
    ReglaNegocioException("El codigo '$codigo' debe tener dos letras, dos digitos y dos letras (ejemplo: LB12PC).")

class DatosInvalidosException(mensaje: String) : ReglaNegocioException(mensaje)

class OperacionNoPermitidaException(mensaje: String) : ReglaNegocioException(mensaje)

// Validar que el codigo cumpla con el patron dos letras, dos numeros y dos letras
fun validarCodigoEquipo(codigo: String) {
    val formatoCodigo = Regex("^[A-Za-z]{2}[0-9]{2}[A-Za-z]{2}$")
    if (!formatoCodigo.matches(codigo)) throw CodigoInvalidoException(codigo)
}



fun validarMinutosUso(minutosUso: Int) {
    if (minutosUso <= 0) {
        throw DatosInvalidosException("Los minutos de uso deben ser mayores que cero.")
    }
}

// Validar el monto que

fun validarMontoFinal(monto: Double, cobroSinCostoPermitido: Boolean) {
    if (monto < 0 || (monto == 0.0 && !cobroSinCostoPermitido)) {
        throw DatosInvalidosException("La tarifa calculada ($monto) no es valida para esta operacion.")
    }
}
