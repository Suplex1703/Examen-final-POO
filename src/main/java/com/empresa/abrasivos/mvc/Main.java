package com.empresa.abrasivos.mvc;

import java.util.ArrayList;
import java.util.List;

import com.empresa.abrasivos.mvc.model.*;
import com.empresa.abrasivos.mvc.model.view.PortalListadoCotizacion;

public class Main {
    public static void main(String[] args) {
        // Crear instancias del modelo y la vista
        AsesorComercial asesor = new AsesorComercial();
        PortalListadoCotizacion portal = new PortalListadoCotizacion(asesor.getCotizaciones(), asesor.getClientes(), asesor);

        // Generar cotizaciones y registrar clientes (puedes personalizar según tus necesidades)
        Cliente cliente1 = new Cliente("123", "Cliente 1", "Dirección 1", "Activo");
        asesor.registrarCliente(cliente1);

        // Generar cotización
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Tela de Esmeril", 10.0, 5));
        asesor.generarCotizacion(cliente1, "2023-12-01", productos, 0.1, "Pendiente");

        // Listar cotizaciones y clientes usando la vista
        portal.listarCotizacionesPorFecha("2023-12-01");
        portal.listarCotizacionesPorIdentificacion("123");
        portal.listarCotizacionesPorRazonSocial("Cliente 1");

        portal.mostrarInterfazGrafica();
    }
}