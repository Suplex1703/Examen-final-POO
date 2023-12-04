// CotizacionController.java
package com.empresa.abrasivos.mvc.controller;

import java.util.ArrayList;
import java.util.List;

import com.empresa.abrasivos.mvc.model.AsesorComercial;
import com.empresa.abrasivos.mvc.model.Cliente;
import com.empresa.abrasivos.mvc.model.Producto;

public class CotizacionController {
    private AsesorComercial asesor;
    
    public CotizacionController(AsesorComercial asesor) {
        this.asesor = asesor;
    }

    public void generarCotizacion(String identificacion, String fechaOrden, String nombreProducto, double precio, int cantidad, double descuento, String estado) {
        Cliente cliente = new Cliente(identificacion, "Razón Social", "Dirección", "Activo");
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto(nombreProducto, precio, cantidad));
        asesor.generarCotizacion(cliente, fechaOrden, productos, descuento, estado);
    }
    
}
