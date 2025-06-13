
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.Date;
import java.util.List;
import models.ModeloGestionFactura;
import services.ServiceFacturaGestion;
import java.text.SimpleDateFormat;
import java.util.Locale;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;


import java.io.File;
import java.text.SimpleDateFormat;


public class ViewFacturaGestion extends JInternalFrame {

    private JTable tablaFacturas;
    private DefaultTableModel modeloTabla;
    private JButton btnImprimir, btnEnviarCorreo, btnAnular, btnBuscar, btnCargarFacturas;
    private JTextField txtBuscarId;
    private ServiceFacturaGestion serviceGestionFactura;
    private String token;
    private int usuarioId;
    private String baseUrl;

    public ViewFacturaGestion(String baseUrl, String token, int usuarioId) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.usuarioId = usuarioId;
        this.serviceGestionFactura = new ServiceFacturaGestion(baseUrl, token);

        setTitle("Gestión de Facturas");
        setSize(900, 600);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setLocation(50, 50); // Posición inicial
        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        initComponents();
        cargarFacturas();
    }

    private void initComponents() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen

        // Título
        JLabel lblTitulo = new JLabel("Gestión de Facturas");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Margen inferior

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblBuscarId = new JLabel("Buscar por ID:");
        txtBuscarId = new JTextField(10);
        btnBuscar = new JButton("Buscar");
        btnCargarFacturas = new JButton("Cargar Facturas"); // Botón para cargar todas las facturas
        panelBusqueda.add(lblBuscarId);
        panelBusqueda.add(txtBuscarId);
        panelBusqueda.add(btnBuscar);
        panelBusqueda.add(btnCargarFacturas);

        // Modelo de tabla con columnas
        String[] columnas = {
            "ID Factura", "Fecha", "Paciente", "Usuario", "Subtotal", "IVA", "Total", "Estado"
        };
        modeloTabla = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };

        tablaFacturas = new JTable(modeloTabla);
        tablaFacturas.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaFacturas.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tablaFacturas);
        scrollPane.setPreferredSize(new Dimension(800, 300));
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Borde

        // Renderer para pintar filas inactivas
        tablaFacturas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus,
                    int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                String estado = table.getValueAt(row, 7).toString();

                if ("Inactivo".equalsIgnoreCase(estado)) {
                    c.setBackground(new Color(255, 204, 204)); // Rosa claro
                } else {
                    c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
                }

                return c;
            }
        });

        // Botones
        btnImprimir = new JButton("Imprimir Factura");
        btnEnviarCorreo = new JButton("Enviar Correo");
        btnAnular = new JButton("Anular Factura");

        // Panel botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnImprimir);
        panelBotones.add(btnEnviarCorreo);
        panelBotones.add(btnAnular);

        // Layout
        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.CENTER);

        // Agregar componentes al panel principal
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelBusqueda, BorderLayout.BEFORE_LINE_BEGINS);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Eventos botones
        btnImprimir.addActionListener(e -> imprimirFactura());
        btnEnviarCorreo.addActionListener(e -> enviarCorreo());
        btnAnular.addActionListener(e -> anularFactura());
        btnBuscar.addActionListener(e -> buscarFacturaPorId());
        btnCargarFacturas.addActionListener(e -> cargarFacturas()); // Cargar todas las facturas
    }

    private void cargarFacturas() {
        try {
            modeloTabla.setRowCount(0);

            List<ModeloGestionFactura> facturas = serviceGestionFactura.getAllFacturas();

            for (ModeloGestionFactura f : facturas) {
                double total = f.getTotal();
                double iva = Math.round((total / 1.12) * 0.12 * 100.0) / 100.0;
                double subtotal = Math.round((total - iva) * 100.0) / 100.0;

                modeloTabla.addRow(new Object[]{
                    f.getIdFactura(),
                    f.getFecha(),
                    f.getNombrePaciente() + " " + f.getApellidoPaciente(),
                    f.getNombreUsuario() + " " + f.getApellidoUsuario(),
                    String.format("Q%.2f", subtotal),
                    String.format("Q%.2f", iva),
                    String.format("Q%.2f", total),
                    f.getEstado()
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar facturas: " + ex.getMessage());
        }
    }

    private void buscarFacturaPorId() {
        try {
            int idFactura = Integer.parseInt(txtBuscarId.getText());
            ModeloGestionFactura factura = serviceGestionFactura.getFacturaById(idFactura);

            if (factura != null) {
                modeloTabla.setRowCount(0);

                double total = factura.getTotal();
                double iva = Math.round((total / 1.12) * 0.12 * 100.0) / 100.0;
                double subtotal = Math.round((total - iva) * 100.0) / 100.0;

                //formatear fecha
                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy", new Locale("es","ES"));
                Date fecha = factura.getFecha();
                String fechaFormateada = dateformat.format(fecha);

                modeloTabla.addRow(new Object[]{
                    factura.getIdFactura(),
                    fechaFormateada,
                    factura.getNombrePaciente() + " " + factura.getApellidoPaciente(),
                    factura.getNombreUsuario() + " " + factura.getApellidoUsuario(),
                    String.format("$%.2f", subtotal),
                    String.format("$%.2f", iva),
                    String.format("$%.2f", total),
                    factura.getEstado()
                });
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la factura con ID: " + idFactura);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingrese un ID de factura válido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al buscar factura: " + ex.getMessage());
        }
    }

    private void imprimirFactura() {
    int fila = tablaFacturas.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una factura para imprimir.");
        return;
    }
    int idFactura = (int) modeloTabla.getValueAt(fila, 0);

    try {
        ModeloGestionFactura factura = serviceGestionFactura.getFacturaById(idFactura);
        if (factura != null) {
            generarPdfFactura(factura);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró la factura seleccionada.");
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al obtener factura: " + e.getMessage());
    }
}

    private void enviarCorreo() {
        int fila = tablaFacturas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una factura para enviar correo.");
            return;
        }
        int idFactura = (int) modeloTabla.getValueAt(fila, 0);
        JOptionPane.showMessageDialog(this, "Enviando correo de factura ID: " + idFactura);
    }

    private void anularFactura() {
        int fila = tablaFacturas.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una factura para anular.");
            return;
        }
        int idFactura = (int) modeloTabla.getValueAt(fila, 0);

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de anular la factura ID: " + idFactura + "?",
                "Confirmar Anulación",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                serviceGestionFactura.anularFactura(idFactura);
                JOptionPane.showMessageDialog(this, "Factura anulada correctamente.");
                cargarFacturas();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al anular factura: " + ex.getMessage());
            }
        }
    }
    
    //Metodo para generar pdf
    private void generarPdfFactura(ModeloGestionFactura factura) {  
    try {  
        String dest = "factura_" + factura.getIdFactura() + ".pdf";  
        PdfWriter writer = new PdfWriter(dest);  
        PdfDocument pdf = new PdfDocument(writer);  
        Document document = new Document(pdf);  
  
        // Fuente y formato de fecha  
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
        String fechaFormateada = dateFormat.format(factura.getFecha());  
  
        // Título  
        Paragraph titulo = new Paragraph("Factura ID: " + factura.getIdFactura())  
                .setFontSize(18)  
                .setBold()  
                .setTextAlignment(TextAlignment.CENTER);  
        document.add(titulo);  
  
        // Datos generales  
        Table tablaDatos = new Table(UnitValue.createPercentArray(new float[]{1, 2}))  
                .useAllAvailableWidth()  
                .setMarginTop(20);  
  
        tablaDatos.addCell(new Cell().add(new Paragraph("Fecha:").setBold()));  
        tablaDatos.addCell(new Cell().add(new Paragraph(fechaFormateada)));  
  
        tablaDatos.addCell(new Cell().add(new Paragraph("Paciente:").setBold()));  
        tablaDatos.addCell(new Cell().add(new Paragraph(factura.getNombrePaciente() + " " + factura.getApellidoPaciente())));  
  
        tablaDatos.addCell(new Cell().add(new Paragraph("Usuario:").setBold()));  
        tablaDatos.addCell(new Cell().add(new Paragraph(factura.getNombreUsuario() + " " + factura.getApellidoUsuario())));  
  
        tablaDatos.addCell(new Cell().add(new Paragraph("Estado:").setBold()));  
        tablaDatos.addCell(new Cell().add(new Paragraph(factura.getEstado())));  
  
        document.add(tablaDatos);  
  
        // Espacio  
        document.add(new Paragraph("\n"));  
  
        // Tabla de detalles  
        Table tablaDetalles = new Table(UnitValue.createPercentArray(new float[]{3, 1, 2, 2}))  
                .useAllAvailableWidth();  
  
        // Encabezados  
        tablaDetalles.addHeaderCell(new Cell().add(new Paragraph("Servicio").setBold().setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.DARK_GRAY));  
        tablaDetalles.addHeaderCell(new Cell().add(new Paragraph("Cantidad").setBold().setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.DARK_GRAY));  
        tablaDetalles.addHeaderCell(new Cell().add(new Paragraph("Precio").setBold().setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.DARK_GRAY));  
        tablaDetalles.addHeaderCell(new Cell().add(new Paragraph("Subtotal").setBold().setFontColor(ColorConstants.WHITE)).setBackgroundColor(ColorConstants.DARK_GRAY));  
  
        // Agregar filas de detalles  
        for (ModeloGestionFactura.Detalle detalle : factura.getDetalles()) {  
            tablaDetalles.addCell(new Cell().add(new Paragraph(detalle.getNombreServicio())));  
            tablaDetalles.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getCantidad()))));  
            tablaDetalles.addCell(new Cell().add(new Paragraph(String.format("$%.2f", detalle.getPrecioServicio()))));  
            tablaDetalles.addCell(new Cell().add(new Paragraph(String.format("$%.2f", detalle.getSubtotal()))));  
        }  
  
        document.add(tablaDetalles);  
  
        // Espacio  
        document.add(new Paragraph("\n"));  
  
        // Totales  
        Table tablaTotales = new Table(UnitValue.createPercentArray(new float[]{3, 1}))  
                .useAllAvailableWidth();  
  
        tablaTotales.addCell(new Cell().add(new Paragraph("Subtotal:").setBold()).setBorder(null));  
        tablaTotales.addCell(new Cell().add(new Paragraph(String.format("$%.2f", factura.getSubtotal()))).setTextAlignment(TextAlignment.RIGHT).setBorder(null));  
  
        tablaTotales.addCell(new Cell().add(new Paragraph("IVA:").setBold()).setBorder(null));  
        tablaTotales.addCell(new Cell().add(new Paragraph(String.format("$%.2f", factura.getIva()))).setTextAlignment(TextAlignment.RIGHT).setBorder(null));  
  
        tablaTotales.addCell(new Cell().add(new Paragraph("Total:").setBold()).setBorder(null));  
        tablaTotales.addCell(new Cell().add(new Paragraph(String.format("$%.2f", factura.getTotal()))).setTextAlignment(TextAlignment.RIGHT).setBorder(null));  
  
        document.add(tablaTotales);  
  
        document.close();  
  
        JOptionPane.showMessageDialog(this, "PDF generado: " + new File(dest).getAbsolutePath());  
  
    } catch (Exception e) {  
        JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage());  
    }  
}

    // Método para ocultar columnas por índice
    private void ocultarColumnas(int[] columnas) {
        for (int col : columnas) {
            TableColumn column = tablaFacturas.getColumnModel().getColumn(col);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setWidth(0);
            column.setPreferredWidth(0);
        }
    }
}
