package com.empresa.abrasivos;

import java.util.List;

public class CotizacionCompra {
    private Cliente cliente;
    private String fechaOrden;
    private List<Producto> productos;
    private double descuento;
    private String estado;
    private int listaPrecios;


    public CotizacionCompra(Cliente cliente, String fechaOrden, List<Producto> productos, double descuento, String estado) {
        this.cliente = cliente;
        this.fechaOrden = fechaOrden;
        this.productos = productos;
        this.descuento = descuento;
        this.estado = estado;
    }

    public double calcularMontoTotal() {
        double montoTotal = 0.0;

        // Iterar a través de la lista de productos y sumar el precio total de cada producto
        for (Producto producto : productos) {
            montoTotal += producto.getPrecioTotal();
        }

        // Aplicar descuento si es mayor que 0
        if (descuento > 0) {
            montoTotal -= (montoTotal * descuento) / 100;
        }

        // Agregar lógica para calcular el IVA del 19% (Impuesto al Valor Agregado) si es necesario
        double iva = 0.19;  // Tasa de IVA del 19%
        montoTotal += montoTotal * iva;

        return montoTotal;
    }

    public void aplicarDescuento() {
        double descuentoPorcentaje = 0.0;

        // Determinar el descuento según la lista de precios seleccionada
        switch (listaPrecios) {
            case 1:
                descuentoPorcentaje = 5.0;
                break;
            case 2:
                descuentoPorcentaje = 10.0;
                break;
            case 3:
                descuentoPorcentaje = 15.0;
                break;
            // Puedes agregar más casos si es necesario
            default:
                descuentoPorcentaje = 0.0; 
                break;
        }

        // Aplicar descuento al campo descuento de la CotizacionCompra
        this.descuento = descuentoPorcentaje;
    }

    public String getFechaOrden() {
        return fechaOrden;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public String getEstado() {
        return estado;
    }

}
