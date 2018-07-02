package model;

public class Imangen {
    private int id,siniestro_id;
    private String path,nombre;

    public Imangen() {
    }

    public Imangen(String path) {
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSiniestro_id() {
        return siniestro_id;
    }

    public void setSiniestro_id(int siniestro_id) {
        this.siniestro_id = siniestro_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
