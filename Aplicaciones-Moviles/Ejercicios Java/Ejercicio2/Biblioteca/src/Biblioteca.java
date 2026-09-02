import java.util.ArrayList;

public class Biblioteca {

    ArrayList<MaterialBiblioteca> biblioteca = new ArrayList<>();
    // ArrayList<MaterialBiblioteca> materialPrestado = new ArrayList<>();


    // Agregar materiales
    public void agregarMateriales(MaterialBiblioteca materialBiblioteca){
        biblioteca.add(materialBiblioteca);
        System.out.println("Agregado exitosamente.");
    }

    // Listar materiales
    public void listarMateriales(){
        if(biblioteca.isEmpty()) {
            System.out.println("La lista está vacia.");
        }else{
            for (MaterialBiblioteca materialBiblioteca: biblioteca){
                System.out.println(materialBiblioteca);
            }
        }
    }

    // Buscar un material por su codigo
    public MaterialBiblioteca buscarMaterialPorCodigo(String codigoBuscado){

        for (MaterialBiblioteca materialBiblioteca: biblioteca){
            if(materialBiblioteca.getCodigo().equals(codigoBuscado)){
                return materialBiblioteca;
            }
        }
        return null;
    }


    // Prestar un material
    public MaterialBiblioteca prestarMaterial(String codigoBuscado){

        // Busca el objeto y lo guarda en prestado                                                                                                                     aterial
        MaterialBiblioteca material = buscarMaterialPorCodigo(codigoBuscado);

        if (material == null){
            System.out.println("El material buscado no se encuentra.");
            return null;
        }

        if (material.getDisponible().equals("Prestado")){
            System.out.println("El material ya está prestado.");
            return null;
        }

        // materialPrestado.add(material);
        // biblioteca.remove(material);
        material.prestar(); // Para hacerlo tuvimos que hacer que la clase abstracta implementara el metodo
        System.out.println("Material prestado correctamente.");
        return material;
    }

    public MaterialBiblioteca devolverMaterial(String codigoBuscado){

        // Buscamos el objeto y lo guardamos en material
        MaterialBiblioteca material = buscarMaterialPorCodigo(codigoBuscado);

        if (material == null){
            System.out.println("Revise el código. El material no se encuentra en la biblioteca.");
            return null;
        }

        if (material.getDisponible().equals("Disponible")){
            System.out.println("El material está disponible. Revise el código.");
            return null;
        }

        material.devolver();
        System.out.println("Material devuelto correctamente");
        return material;
    }

    public void listarDisponibles(){
        boolean hayDisponibles = false;

        for (MaterialBiblioteca materialBiblioteca: biblioteca){
            if (materialBiblioteca.getDisponible().equals("Disponible")) {
                System.out.println(materialBiblioteca);
                hayDisponibles = true;
            }
            if (!hayDisponibles){
                System.out.println("No hay libros disponibles.");
            }
        }
    }
}
