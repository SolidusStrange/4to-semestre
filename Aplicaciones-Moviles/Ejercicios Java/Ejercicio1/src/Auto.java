public class Auto extends Vehiculo implements Arrancable {
    private int numeroPuertas;

    public Auto(String marca, String modelo, int anio, int numeroPuertas) {
        super(marca, modelo, anio);
        this.numeroPuertas = numeroPuertas;
    }

    @Override
    public String tipoVehiculo() {
        return "Automovil";
    }

    @Override
    public double calcularValor() {
        return 5000000;
    }

    @Override
    public void arrancar() {
    }

    @Override
    public String toString() {
        return "Marca: " + getMarca() +
                " Modelo: " + getModelo() +
                " Año: " + getAnio() +
                " Numero de puertas: " + numeroPuertas +
                " Tipo Vehículo: " + tipoVehiculo() +
                " Valor: " + calcularValor();
    }
}
