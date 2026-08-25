import java.util.ArrayList;

public class GestorEmpleados {
    private ArrayList<Empleado> empleados;

    // Inicializarlo
    public GestorEmpleados(){
        empleados = new ArrayList<>();
    }

    public void agregarEmpleados(Empleado empleado){
        empleados.add(empleado);
    }

    public void listarEmpleados(){
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados");
        }else{

            for (Empleado empleado : empleados){
                System.out.println(empleado);
            }
        }
    }
}
