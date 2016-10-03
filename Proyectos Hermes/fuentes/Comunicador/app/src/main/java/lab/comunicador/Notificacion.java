package lab.comunicador;

public class Notificacion {

    private int id;
    private String nombreNinio;
    private String contenido;
    private String categoria;

    public Notificacion(){}

    public Notificacion(int id, String nombreNinio, String contenido , String categoria) {
        this.id = id;
        this.nombreNinio = nombreNinio;
        this.categoria = categoria;
        this.contenido = contenido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreNinio() {
        return nombreNinio;
    }

    public void setNombreNinio(String nombreNinio) {
        this.nombreNinio = nombreNinio;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
