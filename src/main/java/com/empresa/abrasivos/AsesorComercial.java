package com.empresa.abrasivos;

import java.util.ArrayList;
import java.util.List;

public class AsesorComercial {
    private List<Cliente> clientes;
    private List<CotizacionCompra> cotizaciones;

    public AsesorComercial() {
        this.clientes = new ArrayList<>();
        this.cotizaciones = new ArrayList<>();
    }

    public void registrarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public void generarCotizacion(Cliente cliente, String fechaOrden, List<Producto> productos, double descuento, String estado) {
        CotizacionCompra cotizacion = new CotizacionCompra(cliente, fechaOrden, productos, descuento, estado);
        cotizacion.aplicarDescuento();
        cotizaciones.add(cotizacion);
    }

    public List<CotizacionCompra> getCotizaciones() {
        return cotizaciones;
    }
}
