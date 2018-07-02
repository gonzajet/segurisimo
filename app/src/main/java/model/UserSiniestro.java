package model;

import java.io.Serializable;

public class UserSiniestro implements Serializable {
    private int id,user_id;
    private double lat,lon;
    private String estado,fecha,hora;

    private String name;
    private String email;
    private String direccion;
    private String patente;
    private String telefono;
    private String poliza;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPoliza() {
        return poliza;
    }

    public void setPoliza(String poliza) {
        this.poliza = poliza;
    }

    public UserSiniestro(int id, int user_id, double lat, double lon, String estado, String fecha, String hora, int id1, String name, String email, String direccion, String patente, String telefono, String poliza) {

        this.id = id;
        this.user_id = user_id;
        this.lat = lat;
        this.lon = lon;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
        this.id = id1;
        this.name = name;
        this.email = email;
        this.direccion = direccion;
        this.patente = patente;
        this.telefono = telefono;
        this.poliza = poliza;
    }

    public UserSiniestro() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
