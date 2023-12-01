package com.empresa.abrasivos;

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

    // Otros getters y setters
}
