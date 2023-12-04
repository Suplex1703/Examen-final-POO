package com.empresa.abrasivos.mvc.model.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

import com.empresa.abrasivos.mvc.model.*;

public class PortalListadoCotizacion {
    private List<CotizacionCompra> cotizaciones;
    private List<Cliente> clientes;
    private AsesorComercial asesor;
    private JFrame cotizacionFrame;

    public PortalListadoCotizacion(List<CotizacionCompra> cotizaciones, List<Cliente> clientes, AsesorComercial asesor) {
        this.cotizaciones = cotizaciones;
        this.clientes = clientes;
        this.asesor = asesor;
        this.cotizacionFrame = null;
    }

    public void mostrarInterfazGrafica() {
        // Verificar si ya hay un marco abierto y cerrarlo
        for (Window window : JFrame.getWindows()) {
            if (window instanceof JFrame) {
                JFrame existingFrame = (JFrame) window;
                if (existingFrame.getTitle().equals("Listado de Cotizaciones y Clientes")) {
                    existingFrame.dispose();
                }
            }
        }

        // Crear pestañas
        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña de Cotizaciones
        JPanel cotizacionesPanel = new JPanel(new BorderLayout());
        DefaultTableModel cotizacionesModel = new DefaultTableModel();
        cotizacionesModel.addColumn("Fecha de Orden");
        cotizacionesModel.addColumn("Cliente");
        cotizacionesModel.addColumn("Monto Total");
        cotizacionesModel.addColumn("Estado");
        JTable cotizacionesTable = new JTable(cotizacionesModel);

        for (CotizacionCompra cotizacion : cotizaciones) {
            cotizacionesModel.addRow(new Object[]{
                    cotizacion.getFechaOrden(),
                    ((Cliente) cotizacion.getCliente()).getRazonSocial(),
                    cotizacion.calcularMontoTotal(),
                    cotizacion.getEstado()
            });
        }

        JScrollPane cotizacionesScrollPane = new JScrollPane(cotizacionesTable);
        cotizacionesPanel.add(cotizacionesScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Cotizaciones", cotizacionesPanel);

        // Pestaña de Clientes
        JPanel clientesPanel = new JPanel(new BorderLayout());
        DefaultTableModel clientesModel = new DefaultTableModel();
        clientesModel.addColumn("Identificación");
        clientesModel.addColumn("Razón Social");
        clientesModel.addColumn("Dirección");
        clientesModel.addColumn("Estado");
        JTable clientesTable = new JTable(clientesModel);

        for (Cliente cliente : clientes) {
            clientesModel.addRow(new Object[]{
                    cliente.getIdentificacion(),
                    cliente.getRazonSocial(),
                    cliente.getDireccion(),
                    cliente.getEstado()
            });
        }

        JScrollPane clientesScrollPane = new JScrollPane(clientesTable);
        clientesPanel.add(clientesScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Clientes", clientesPanel);

        // Botón para mostrar formulario de cliente
        JButton agregarClienteButton = new JButton("Agregar Cliente");
        agregarClienteButton.addActionListener(e -> mostrarFormularioCliente());
        clientesPanel.add(agregarClienteButton, BorderLayout.SOUTH);

        // Botón para generar cotización
        JButton generarCotizacionButton = new JButton("Generar Cotización");
        generarCotizacionButton.addActionListener(e -> mostrarFormularioCotizacion());
        cotizacionesPanel.add(generarCotizacionButton, BorderLayout.SOUTH);

        JFrame frame = new JFrame("Listado de Cotizaciones y Clientes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void mostrarFormularioCliente() {
        // Validar identificación numérica
        String identificacionInput = JOptionPane.showInputDialog("Ingrese la identificación del cliente:");
        if (!esNumero(identificacionInput)) {
            JOptionPane.showMessageDialog(null, "La identificación debe ser numérica.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Salir de la función si la identificación no es numérica
        }
        int identificacion = Integer.parseInt(identificacionInput);

        // Validar razonSocial como string
        String razonSocial = JOptionPane.showInputDialog("Ingrese la razón social del cliente:");
        if (!esTexto(razonSocial)) {
            JOptionPane.showMessageDialog(null, "La razón social debe ser un texto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Salir de la función si la razón social no es un texto
        }

        // Validar dirección permitiendo cualquier caracter
        String direccion = JOptionPane.showInputDialog("Ingrese la dirección del cliente:");

        // Validar estado como string
        String estado = JOptionPane.showInputDialog("Ingrese el estado del cliente:");
        if (!esTexto(estado)) {
            JOptionPane.showMessageDialog(null, "El estado debe ser un texto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;  // Salir de la función si el estado no es un texto
        }

        Cliente nuevoCliente = new Cliente(String.valueOf(identificacion), razonSocial, direccion, estado);
        asesor.registrarCliente(nuevoCliente);

        JOptionPane.showMessageDialog(null, "Cliente registrado exitosamente.");

        mostrarInterfazGrafica();
    }

    public void mostrarFormularioCotizacion() {
        // Obtener cliente seleccionado
        Cliente clienteSeleccionado = obtenerClienteSeleccionado();

        if (clienteSeleccionado != null) {
            // Crear instancia de cotización
            CotizacionCompra cotizacion = crearCotizacion(clienteSeleccionado);

            // Mostrar detalles de la cotización en una ventana aparte
            mostrarDetallesCotizacion(cotizacion);
        } else {
            JOptionPane.showMessageDialog(null, "No hay clientes disponibles para generar una cotización.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Cliente obtenerClienteSeleccionado() {
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay clientes disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    
        String[] opcionesClientes = new String[clientes.size()];
        for (int i = 0; i < clientes.size(); i++) {
            opcionesClientes[i] = clientes.get(i).getRazonSocial();
        }
    
        String clienteSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione un cliente:",
                "Seleccionar Cliente", JOptionPane.QUESTION_MESSAGE, null, opcionesClientes, opcionesClientes[0]);
    
        if (clienteSeleccionado != null) {
            // Obtener el cliente correspondiente al nombre seleccionado
            for (Cliente cliente : clientes) {
                if (cliente.getRazonSocial().equals(clienteSeleccionado)) {
                    return cliente;
                }
            }
        }
    
        return null;
    }
    

    private CotizacionCompra crearCotizacion(Cliente cliente) {
        // Obtener detalles de la cotización
        String fechaOrden = JOptionPane.showInputDialog("Ingrese la fecha de la cotización (formato YYYY-MM-DD):");
        if (!esFechaValida(fechaOrden)) {
            JOptionPane.showMessageDialog(null, "Fecha no válida. Utilice el formato YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    
        // Puedes agregar más detalles según tus necesidades, como la selección de productos, etc.
        List<Producto> productos = seleccionarProductos();
    
        // Calcular el monto total
        double montoTotal = calcularMontoTotal(productos);
    
        // Estado inicial "Pendiente"
        String estado = "Pendiente";
    
        return new CotizacionCompra(cliente, fechaOrden, productos, montoTotal, estado);
    }
    
    private boolean esFechaValida(String fecha) {
        // Verificar el formato de la fecha (YYYY-MM-DD)
        if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
    
        // Obtener valores de año, mes y día
        int año = Integer.parseInt(fecha.substring(0, 4));
        int mes = Integer.parseInt(fecha.substring(5, 7));
        int dia = Integer.parseInt(fecha.substring(8, 10));
    
        // Verificar rangos de año, mes y día
        return año >= 1900 && año <= 9999 &&
               mes >= 1 && mes <= 12 &&
               dia >= 1 && dia <= obtenerUltimoDiaDelMes(año, mes);
    }
    
    private int obtenerUltimoDiaDelMes(int año, int mes) {
        if (mes == 2) {
            // Ajustar para febrero y años bisiestos
            if ((año % 4 == 0 && año % 100 != 0) || (año % 400 == 0)) {
                return 29;
            } else {
                return 28;
            }
        } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
            return 30;
        } else {
            return 31;
        }
    }
    
    private List<Producto> seleccionarProductos() {
        // Lista de productos disponibles (puedes adaptarla según tus productos reales)
        List<Producto> productosDisponibles = Arrays.asList(
                new Producto("Producto1", 10.0, 0),
                new Producto("Producto2", 15.0, 0),
                new Producto("Producto3", 20.0, 0)
        );

        // Array de nombres de productos para el cuadro de diálogo de selección
        String[] nombresProductos = new String[productosDisponibles.size()];
        for (int i = 0; i < productosDisponibles.size(); i++) {
            nombresProductos[i] = productosDisponibles.get(i).getNombre();
        }

        // Mostrar el cuadro de diálogo de selección
        Object[] seleccionados = (Object[]) JOptionPane.showInputDialog(null,
                "Seleccione los productos:",
                "Selección de Productos",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombresProductos,
                null);

        // Verificar si el usuario hizo una selección
        if (seleccionados != null) {
            // Crear la lista de productos seleccionados
            List<Producto> productosSeleccionados = Arrays.asList(
                    productosDisponibles.get(Arrays.asList(nombresProductos).indexOf(seleccionados[0])),
                    productosDisponibles.get(Arrays.asList(nombresProductos).indexOf(seleccionados[1]))
                    // ... puedes continuar según la cantidad de productos permitidos
            );
            return productosSeleccionados;
        } else {
            // El usuario canceló la selección
            return List.of();
        }
    }
    
    private double calcularMontoTotal(List<Producto> productos) {
        double montoTotal = 0.0;
    
        // Iterar sobre la lista de productos y sumar los precios
        for (Producto producto : productos) {
            montoTotal += producto.getPrecio();
        }
    
        return montoTotal;
    }

    private void mostrarDetallesCotizacion(CotizacionCompra cotizacion) {
        // Cerrar la ventana de cotización si ya está abierta
        if (cotizacionFrame != null) {
            cotizacionFrame.dispose();
        }
    
        // Crear una nueva ventana para mostrar la cotización
        cotizacionFrame = new JFrame("Detalles de Cotización");
        cotizacionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cotizacionFrame.setLayout(new BorderLayout());
    
        // Crear panel para mostrar detalles de la cotización
        JPanel cotizacionPanel = new JPanel();
        cotizacionPanel.setLayout(new BoxLayout(cotizacionPanel, BoxLayout.Y_AXIS));
    
        // Agregar detalles de la cotización al panel
        cotizacionPanel.add(new JLabel("Fecha de Orden: " + cotizacion.getFechaOrden()));
    
        if (cotizacion.getCliente() instanceof Cliente) {
            Cliente cliente = (Cliente) cotizacion.getCliente();
            cotizacionPanel.add(new JLabel("Cliente: " + cliente.getRazonSocial()));
        } else {
            cotizacionPanel.add(new JLabel("Cliente no válido"));
        }
    
        // Agregar el monto total al panel
        cotizacionPanel.add(new JLabel("Monto Total: " + cotizacion.calcularMontoTotal()));
    
        if (cotizacion instanceof CotizacionCompra) {
            cotizacionPanel.add(new JLabel("Estado: " + cotizacion.getEstado()));
        } else {
            cotizacionPanel.add(new JLabel("Estado no válido"));
        }
    
        // Mostrar la ventana con los detalles de la cotización
        mostrarVentanaDetallesCotizacion(cotizacionPanel);
    }
    
    // Método para mostrar la ventana con detalles de cotización
    private void mostrarVentanaDetallesCotizacion(JPanel cotizacionPanel) {
        // Crear la ventana para mostrar los detalles de la cotización
        JFrame detallesFrame = new JFrame("Detalles de Cotización");
        detallesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        detallesFrame.setLayout(new BorderLayout());
    
        // Agregar el panel con los detalles al frame
        detallesFrame.add(cotizacionPanel, BorderLayout.CENTER);
    
        // Configurar propiedades del frame
        detallesFrame.setSize(400, 300);
        detallesFrame.setLocationRelativeTo(null);
        detallesFrame.setVisible(true);
    }
    

    // ... otros métodos existentes


    private boolean esNumero(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean esTexto(String str) {
        return str.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+");
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
                Cliente cliente = (Cliente) cotizacion.getCliente();
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
                Cliente cliente = (Cliente) cotizacion.getCliente();
                if (cliente.getRazonSocial() != null && cliente.getRazonSocial().equals(razonSocial)) {
                    imprimirDetalleCotizacion(cotizacion);
                }
            }
        }
    }

    private void imprimirDetalleCotizacion(CotizacionCompra cotizacion) {
        System.out.println("Fecha de Orden: " + cotizacion.getFechaOrden());

        if (cotizacion.getCliente() instanceof Cliente) {
            Cliente cliente = (Cliente) cotizacion.getCliente();
            System.out.println("Cliente: " + cliente.getRazonSocial());
        } else {
            System.out.println("Cliente no válido");
        }

        System.out.println("Monto Total: " + cotizacion.calcularMontoTotal());

        if (cotizacion instanceof CotizacionCompra) {
            System.out.println("Estado: " + cotizacion.getEstado());
        } else {
            System.out.println("Estado no válido");
        }

        System.out.println("-----");
    }
}