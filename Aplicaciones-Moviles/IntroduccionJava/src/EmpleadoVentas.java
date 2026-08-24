public class EmpleadoVentas extends Empleado {
    private double comision;
    private double metaComision;

    public EmpleadoVentas(String rut, double sueldoBase, String cargo, double comision, double metaComision) {
        super(rut, sueldoBase, cargo);
        this.comision = comision;
        this.metaComision = metaComision;
    }

    public double getComision() {
        return comision;
    }

    public void setComision(double comision) {
        this.comision = comision;
    }

    public double getMetaComision() {
        return metaComision;
    }

    public void setMetaComision(double metaComision) {
        this.metaComision = metaComision;
    }

    @Override
    public double calcularSueldo() {
        return getSueldoBase() + comision;
    }

    @Override
    String tipoContrato() {
        return "Por comisión";
    }
}
