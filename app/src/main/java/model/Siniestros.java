package model;

public class Siniestros {

    private int id;

    public Siniestros(int id) {
        this.id = id;
    }

    public Siniestros() {

    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    private int userId;
    private double lat,lon;
    private String estado,fecha,hora;

    public Siniestros(int id, int userId, double lat, double lon, String estado, String fecha, String hora) {
        this.id = id;
        this.userId = userId;
        this.lat = lat;
        this.lon = lon;
        this.estado = estado;
        this.fecha = fecha;
        this.hora = hora;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
