package com.empresa.abrasivos;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear instancias de AsesorComercial y PortalListadoCotizacion
        AsesorComercial asesor = new AsesorComercial();
        PortalListadoCotizacion portal = new PortalListadoCotizacion(asesor.getCotizaciones());

        // Generar cotizaciones y registrar clientes 
        Cliente cliente1 = new Cliente("123", "Cliente 1", "Dirección 1", "Activo");
        asesor.registrarCliente(cliente1);

        // Generar cotización
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto("Tela de Esmeril", 10.0, 5));
        asesor.generarCotizacion(cliente1, "2023-12-01", productos, 0.1, "Pendiente");

        // Listar cotizaciones por fecha
        portal.listarCotizacionesPorFecha("2023-12-01");

        // Listar cotizaciones por identificación
        portal.listarCotizacionesPorIdentificacion("123");

        // Listar cotizaciones por razón social
        portal.listarCotizacionesPorRazonSocial("Cliente 1");
    }
}
