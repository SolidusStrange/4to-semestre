public class Revista extends MaterialBiblioteca implements Prestable{
    private int numeroEdicion;

    public Revista(String titulo, String codigo, int anioPublicacion, String disponible, int numeroEdicion) {
        super(titulo, codigo, anioPublicacion, disponible);
        this.numeroEdicion = numeroEdicion;
    }

    public int getNumeroEdicion() {
        return numeroEdicion;
    }

    public void setNumeroEdicion(int numeroEdicion) {
        this.numeroEdicion = numeroEdicion;
    }

    @Override
    public void prestar() {
        setDisponible("Prestado");
    }

    @Override
    public void devolver() {
        setDisponible("Disponible");
    }

    @Override
    public String tipoMaterial() {
        return "Papel couché";
    }

    @Override
    public String toString() {
        return "Titulo: " + getTitulo() +
                " Numero de edición: " + numeroEdicion +
                " Codigo: " + getCodigo() +
                " Año publicación: " + getAnioPublicacion() +
                " Disponible: " + getDisponible();
    }
}


