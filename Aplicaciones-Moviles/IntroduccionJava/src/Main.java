import java.util.Scanner;

public static void main(String[] args) {
    // Crear un objeto escaner
    Scanner sc = new Scanner(System.in);
    GestorEmpleados gestor = new GestorEmpleados();

    int opcion;
    do {
        // Mostrar menú
        System.out.println("Seleccione la opción: ");
        System.out.println("1. Agregar empleado");
        System.out.println("2. Listar empleados");
        System.out.println("3. Salir");

        // Leer el input del usuario, en este caso un int
        opcion = sc.nextInt();



        switch (opcion) {
            case 1:
                System.out.println("Qué tipo de empleado desea agregar?");
                System.out.println("1. Empleado común");
                System.out.println("2. Empleado ventas");

                int tipo = sc.nextInt();

                Empleado empleado;
                if (tipo == 1) {

                    System.out.println("Ingrese RUT: ");
                    String rut = sc.next();

                    System.out.println("Ingrese sueldo base: ");
                    double sueldoBase = sc.nextDouble();

                    System.out.println("Ingrese cargo: ");
                    String cargo = sc.next();

                    empleado = new EmpleadoComun(rut, sueldoBase, cargo);

                } else if (tipo == 2) {
                    System.out.println("Ingrese RUT: ");
                    String rut = sc.next();

                    System.out.println("Ingrese sueldo base: ");
                    double sueldoBase = sc.nextDouble();

                    System.out.println("Ingrese cargo: ");
                    String cargo = sc.next();

                    System.out.println("Ingrese comisión: ");
                    double comision = sc.nextDouble();

                    System.out.println("Ingrese meta de comisión");
                    double metaComision = sc.nextDouble();

                    empleado = new EmpleadoVentas(rut, sueldoBase, cargo, comision, metaComision);

                } else {
                    System.out.println("Opcion no válida");
                    break;
                }

                gestor.agregarEmpleados(empleado);
                break;
            case 2:
                // Listar empleados
                gestor.listarEmpleados();
                break;
            case 3:
                System.out.println("Finalizando proceso de inscripción");
                break;
        }
    } while (opcion != 3);


}
