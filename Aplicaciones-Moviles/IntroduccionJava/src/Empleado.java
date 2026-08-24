public abstract class Empleado extends Usuario implements Pagable {
    private double sueldoBase;
    private String cargo;

    public Empleado(String rut, double sueldoBase, String cargo) {
        super(rut);
        this.sueldoBase = sueldoBase;
        this.cargo = cargo;
    }

    public double getSueldoBase() {
        return sueldoBase;
    }

    public void setSueldoBase(double sueldoBase) {
        this.sueldoBase = sueldoBase;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    // Metodo abstracto propio de esta clase abstracta
    abstract String tipoContrato();



}
