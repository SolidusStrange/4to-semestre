import java.util.Scanner;

public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Vehiculo vehiculo;
    Garaje garaje = new Garaje();

    int opcion;

    do {
        System.out.println("Bienvenido a la automotora: ");
        System.out.println("Elija una opción: ");
        System.out.println("1. Agregar auto");
        System.out.println("2. Agregar motocicleta");
        System.out.println("3. Listar vehículos");
        System.out.println("4. Salir");

        opcion = sc.nextInt();
        switch (opcion){
            case 1:

                System.out.println("Ingrese la marca del automóvil: ");
                String marca = sc.next();

                System.out.println("Ingrese el modelo del automóvil: ");
                String modelo = sc.next();

                System.out.println("Ingrese el año del automóvil: ");
                int anio = sc.nextInt();

                System.out.println("Ingrese el número de puertas del automóvil: ");
                int numeroPuertas = sc.nextInt();

                vehiculo = new Auto(marca, modelo, anio, numeroPuertas);

                garaje.agregarVehiculo(vehiculo);
                break;

            case 2:
                System.out.println("Ingrese la marca del motocicleta: ");
                marca = sc.next();

                System.out.println("Ingrese el modelo del motocicleta: ");
                modelo = sc.next();

                System.out.println("Ingrese el año del motocicleta: ");
                anio = sc.nextInt();

                System.out.println("Ingrese cilindrada de la motocicleta: ");
                int cilindrada = sc.nextInt();

                vehiculo = new Motocicleta(marca, modelo, anio, cilindrada);

                garaje.agregarVehiculo(vehiculo);
                break;

            case 3:
                garaje.listarVehiculo();
                break;

            case 4:
                System.out.println("Saliendo del sistema.");
                break;

            default:
                System.out.println("Opcion no válida");
                break;
        }

    } while (opcion != 4);

}
