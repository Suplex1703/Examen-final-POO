package com.empresa.abrasivos.mvc.model;

public class Cliente {
    private String identificacion;
    private String razonSocial;
    private String direccion;
    private String estado;


    public Cliente(String identificacion, String razonSocial, String direccion, String estado) {
        this.identificacion = identificacion;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.estado = estado;
    }

    public Cliente(int identificacion2, String razonSocial2, String direccion2, String estado2) {
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEstado() {
        return estado;
    }

}
