public class EmpleadoComun extends Empleado{
    public EmpleadoComun(String rut, double sueldoBase, String cargo) {
        super(rut, sueldoBase, cargo);
    }

    @Override
    public double calcularSueldo() {
        return getSueldoBase();
    }

    @Override
    public String tipoContrato() {
        return "Indefinido";
    }

    @Override
    public String toString() {
        return "RUT: " + getRut()
            + ", Sueldo base: " + getSueldoBase()
            + ", Cargo: " + getCargo()
            + ", Tipo de Contrato: " + tipoContrato();
    }
}

