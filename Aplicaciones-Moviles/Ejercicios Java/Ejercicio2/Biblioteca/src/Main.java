import java.util.Scanner;

public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    Biblioteca gestorBiblioteca = new Biblioteca();

    int opcion;

    do {

        System.out.println("Bienvenido a la biblioteca. Elija su opción: ");
        System.out.println("1. Agregar libro");
        System.out.println("2. Agregar revista");
        System.out.println("3. Listar todos");
        System.out.println("4. Busca por código");
        System.out.println("5. Prestar material");
        System.out.println("6. Devolver material");
        System.out.println("7. Listar disponibles");
        System.out.println("8. Salir");

        opcion = sc.nextInt();

        switch (opcion){
            case 1:
                System.out.println("Ingrese el título del libro: ");
                String titulo = sc.next();

                System.out.println("Ingrese el codigo del libro: ");
                String codigo = sc.next();

                System.out.println("Ingrese año de publicación: ");
                int anioPublicacion = sc.nextInt();

                String disponible = "Disponible";

                System.out.println("Ingrese autor: ");
                String autor = sc.next();


                MaterialBiblioteca materialBiblioteca = new Libro(titulo, codigo, anioPublicacion, disponible, autor);
                gestorBiblioteca.agregarMateriales(materialBiblioteca);

                break;
            case 2:
                System.out.println("Ingrese el título del revista: ");
                titulo = sc.next();

                System.out.println("Ingrese el codigo del revista: ");
                codigo = sc.next();

                System.out.println("Ingrese año de publicación: ");
                anioPublicacion = sc.nextInt();

                disponible = "Disponible";

                System.out.println("Ingrese el numero de edición: ");
                int numeroEdicion = sc.nextInt();

                materialBiblioteca = new Revista(titulo, codigo, anioPublicacion, disponible, numeroEdicion);
                gestorBiblioteca.agregarMateriales(materialBiblioteca);

                break;
            case 3:
                gestorBiblioteca.listarMateriales();
                break;
            case 4:
                System.out.println("Ingrese el codigo a buscar: ");
                String codigoBuscado = sc.next();
                gestorBiblioteca.buscarMaterialPorCodigo(codigoBuscado);
                break;
            case 5:
                System.out.println("Ingrese el codigo del material para prestar: ");
                codigoBuscado = sc.next();
                gestorBiblioteca.prestarMaterial(codigoBuscado);
                break;
            case 6:
                System.out.println("Ingrese el código del material para devolver");
                codigoBuscado = sc.next();
                gestorBiblioteca.devolverMaterial(codigoBuscado);
                break;
            case 7:
                gestorBiblioteca.listarDisponibles();
                break;
            case 8:
                System.out.println("Salir");
                break;
        }

    } while (opcion != 8);



}
