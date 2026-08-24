public class EmpleadoComun extends Empleado{
    public EmpleadoComun(String rut, double sueldoBase, String cargo) {
        super(rut, sueldoBase, cargo);
    }

    @Override
    public double calcularSueldo() {
        return getSueldoBase();
    }

    @Override
    String tipoContrato() {
        return "Indefinido";
    }
}
