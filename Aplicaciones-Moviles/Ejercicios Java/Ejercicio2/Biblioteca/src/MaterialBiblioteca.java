public abstract class MaterialBiblioteca implements Prestable {
    private String titulo;
    private String codigo;
    private int anioPublicacion;
    private String disponible;

    public MaterialBiblioteca(String titulo, String codigo, int anioPublicacion, String disponible) {
        this.titulo = titulo;
        this.codigo = codigo;
        this.anioPublicacion = anioPublicacion;
        this.disponible = disponible;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public String getDisponible() {
        return disponible;
    }

    public void setDisponible(String disponible) {
        this.disponible = disponible;
    }

    // Metodos


    public String tipoMaterial(){
        return "";
    };

}
