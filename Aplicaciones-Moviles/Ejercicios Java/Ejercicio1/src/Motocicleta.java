public class Motocicleta extends Vehiculo implements Arrancable{
    private int cilindrada;

    public Motocicleta(String marca, String modelo, int anio, int cilindrada) {
        super(marca, modelo, anio);
        this.cilindrada = cilindrada;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    @Override
    public String tipoVehiculo() {
        return "Motocicleta";
    }

    @Override
    public double calcularValor() {
        return 2000000;
    }

    @Override
    public void arrancar() {
    }

    @Override
    public String toString() {
        return "Marca: " + getMarca() +
                " Modelo: " + getModelo() +
                " Año: " + getAnio() +
                " Cilindrada: " + cilindrada +
                " Tipo Vehículo: " + tipoVehiculo() +
                " Valor: " + calcularValor();
    }
}
