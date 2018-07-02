package model;

import java.io.Serializable;

public class UserSiniestro implements Serializable {
    private int id;
    private String nombre;
    private String estado;
    private String pathImagen;

    public UserSiniestro() {
    }

    public UserSiniestro(int id, String nombre, String estado, String pathImagen) {

        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.pathImagen = pathImagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }
}
