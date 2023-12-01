package com.empresa.abrasivos;

import java.util.List;

public class PortalListadoCotizacion {
    private List<CotizacionCompra> cotizaciones;

    public PortalListadoCotizacion(List<CotizacionCompra> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    public void listarCotizacionesPorFecha(String fecha) {
        System.out.println("Listado de Cotizaciones para la fecha " + fecha);
        for (CotizacionCompra cotizacion : cotizaciones) {
            if (cotizacion.getFechaOrden().equals(fecha)) {
                imprimirDetalleCotizacion(cotizacion);
            }
        }
    }

    public void listarCotizacionesPorIdentificacion(String identificacion) {
        System.out.println("Listado de Cotizaciones para la identificación " + identificacion);
        for (CotizacionCompra cotizacion : cotizaciones) {
            if (cotizacion.getCliente() instanceof Cliente) {
                Cliente cliente = (Cliente) cotizacion.getCliente(); // Casting a Cliente
                if (cliente.getIdentificacion() != null && cliente.getIdentificacion().equals(identificacion)) {
                    imprimirDetalleCotizacion(cotizacion);
                }
            }
        }
    }
    

    public void listarCotizacionesPorRazonSocial(String razonSocial) {
        System.out.println("Listado de Cotizaciones para la razón social " + razonSocial);
        for (CotizacionCompra cotizacion : cotizaciones) {
            if (cotizacion.getCliente() instanceof Cliente) {
                Cliente cliente = (Cliente) cotizacion.getCliente(); // Casting a Cliente
                if (cliente.getRazonSocial() != null && cliente.getRazonSocial().equals(razonSocial)) {
                    imprimirDetalleCotizacion(cotizacion);
                }
            }
        }
    }
    

    private void imprimirDetalleCotizacion(CotizacionCompra cotizacion) {
        System.out.println("Fecha de Orden: " + cotizacion.getFechaOrden());
    
        // Comprobar si el objeto devuelto por getCliente() es una instancia de Cliente
        if (cotizacion.getCliente() instanceof Cliente) {
            Cliente cliente = (Cliente) cotizacion.getCliente(); // Casting a Cliente
            System.out.println("Cliente: " + cliente.getRazonSocial());
        } else {
            System.out.println("Cliente no válido");
        }
    
        System.out.println("Monto Total: " + cotizacion.calcularMontoTotal());
    
        // Verificar si el método getEstado() está definido en CotizacionCompra
        if (cotizacion instanceof CotizacionCompra) {
            System.out.println("Estado: " + cotizacion.getEstado());
        } else {
            System.out.println("Estado no válido");
        }
    
        System.out.println("-----");
    }
    
    
}