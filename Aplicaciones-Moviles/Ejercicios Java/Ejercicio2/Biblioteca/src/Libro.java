public class Libro extends MaterialBiblioteca implements Prestable {
    private String autor;

    public Libro(String titulo, String codigo, int anioPublicacion, String disponible, String autor) {
        super(titulo, codigo, anioPublicacion, disponible);
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
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
        return ("Papel offset");
    }

    @Override
    public String toString() {
        return "Titulo: " + getTitulo() +
                " Autor: " + autor +
                " Codigo: " + getCodigo() +
                " Año publicación: " + getAnioPublicacion() +
                " Disponible: " + getDisponible();
    }
}
