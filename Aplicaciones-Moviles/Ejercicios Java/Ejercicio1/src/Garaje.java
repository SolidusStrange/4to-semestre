import java.util.ArrayList;

public class Garaje {

    // Declaramos una Array de tipo Auto llamado garaje. Y le asignamos, una lista vacia para trabajarla

    // Mejor práctica
    private ArrayList<Vehiculo> garaje;

    public Garaje(){
        garaje = new ArrayList<>();
    }

    // Un metodo
    // private ArrayList<Vehiculo> garaje = new ArrayList<>();

    // Metodos
    public void agregarVehiculo(Vehiculo vehiculo){
        garaje.add(vehiculo);
    }

    public void listarVehiculo(){
        if (garaje.isEmpty()){
            System.out.println("La lista de vehículos está vacia. Por favor, agregue uno.");

        }else{

            for (Vehiculo vehiculo : garaje){
                System.out.println(vehiculo);
            }
        }
    }

}
