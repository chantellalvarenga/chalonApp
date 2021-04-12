package com.example.chalonapp.data.model;

public class Cita {
    int id;

    public Cita(int id, int id_cliente, int id_tratamiento, String fecha, String estado, String hora) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_tratamiento = id_tratamiento;
        this.fecha = fecha;
        this.estado = estado;
        this.hora = hora;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    int id_cliente;

    public int getId_tratamiento() {
        return id_tratamiento;
    }

    public void setId_tratamiento(int id_tratamiento) {
        this.id_tratamiento = id_tratamiento;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id_tratamiento;

    public String getFecha() {
        return fecha;
    }

    public int getId() {
        return id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    String fecha;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    String estado;

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    String hora;

}
