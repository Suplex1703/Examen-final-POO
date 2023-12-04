package com.empresa.abrasivos.mvc.model; 


public class Producto {
    private String nombre;
    private double precio;
    private int cantidad;

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;   
        this.cantidad = cantidad;
    }

    public Producto(String nombre2, int precio2, double d) {
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecioTotal() {
        return precio * cantidad;
    }

    public String getPrecioUnitario() {
        return null;
    }

    public String getCantidad() {
        return null;
    }

    public double getPrecio() {
        return 0;
    }
}
