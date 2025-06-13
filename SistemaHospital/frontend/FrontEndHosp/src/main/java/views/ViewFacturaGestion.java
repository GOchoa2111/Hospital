
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.borders.Border;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import util.EmailRequestDTO;

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
        btnEnviarCorreo.addActionListener(e -> {//se obtiene la variable factura del metodo enviar correo
            int fila = tablaFacturas.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una factura.");
                return;
            }

            int idFactura = (int) modeloTabla.getValueAt(fila, 0);

            try {
                ModeloGestionFactura factura = serviceGestionFactura.getFacturaById(idFactura);

                if (factura.getCorreoPaciente() == null || factura.getCorreoPaciente().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El paciente no tiene correo registrado.");
                    return;
                }

                enviarCorreoFactura(factura); // Aquí ya tienes la factura cargada
                JOptionPane.showMessageDialog(this, "Correo enviado exitosamente.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al enviar correo: " + ex.getMessage());
            }
        });

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
                    new SimpleDateFormat("dd-MM-yyyy").format(f.getFecha()),
                    //f.getFecha(),
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
                SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES"));
                Date fecha = factura.getFecha();
                String fechaFormateada = dateformat.format(fecha);

                modeloTabla.addRow(new Object[]{
                    factura.getIdFactura(),
                    fechaFormateada,
                    factura.getNombrePaciente() + " " + factura.getApellidoPaciente(),
                    factura.getNombreUsuario() + " " + factura.getApellidoUsuario(),
                    String.format("Q%.2f", subtotal),
                    String.format("Q%.2f", iva),
                    String.format("Q%.2f", total),
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

                //log temporal para ver los datos de la factura
                System.out.println("Factura cargada con ID: " + factura.getIdFactura());
                System.out.println("Estado: " + factura.getEstado());
                if (factura.getDetalles() != null) {
                    System.out.println("Detalles cargados: " + factura.getDetalles().size());
                    for (ModeloGestionFactura.Detalle d : factura.getDetalles()) {
                        System.out.println(" - Servicio: " + d.getNombreServicio()
                                + ", Cantidad: " + d.getCantidad()
                                + ", Precio: " + d.getPrecioServicio()
                                + ", Subtotal: " + d.getSubtotal());
                    }
                } else {
                    System.out.println("⚠️ No se cargaron detalles (lista null).");
                }
                if ("Inactivo".equalsIgnoreCase(factura.getEstado())) {
                    int opcion = JOptionPane.showConfirmDialog(this,
                            "La factura está anulada. ¿Desea imprimirla con marca de anulación?",
                            "Factura anulada",
                            JOptionPane.YES_NO_OPTION);

                    if (opcion == JOptionPane.YES_OPTION) {
                        generarPdfFactura(factura); // se imprime con sello
                    }
                } else {
                    generarPdfFactura(factura); // factura activa
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró la factura seleccionada.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al obtener factura: " + e.getMessage());
        }
    }

    public void enviarCorreoFactura(ModeloGestionFactura factura) throws Exception {
        String url = baseUrl + "/Email/send";
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + token);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        // Construir mensaje
        String mensaje = "<div style='font-family: Arial, sans-serif; padding: 20px; border: 1px solid #ccc; max-width: 600px;'>"
                + "<div style='text-align: center; padding-bottom: 10px; border-bottom: 2px solid #3498db;'>"
                + "<h1 style='margin: 0; color: #2c3e50;'>Hospital Salud</h1>"
                + "<p style='margin: 0; font-size: 14px; color: #7f8c8d;'>Tel. (502) 1234-5678</p>"
                + "</div>"
                + "<h2 style='color: #2980b9; margin-top: 20px;'>Factura #" + factura.getIdFactura() + "</h2>"
                + "<table style='width: 100%; font-size: 14px;'>"
                + "<tr><td><strong>Paciente:</strong></td><td>" + factura.getNombrePaciente() + " " + factura.getApellidoPaciente() + "</td></tr>"
                + "<tr><td><strong>Fecha:</strong></td><td>" + new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(factura.getFecha()) + "</td></tr>"
                + "</table>"
                + "<hr style='margin: 20px 0;'>"
                + "<table style='width: 100%; font-size: 14px;'>"
                + "<tr><td><strong>Subtotal:</strong></td><td>$" + factura.getSubtotal() + "</td></tr>"
                + "<tr><td><strong>IVA:</strong></td><td>$" + factura.getIva() + "</td></tr>"
                + "<tr><td><strong>Total:</strong></td><td><strong style='color: #27ae60;'>$" + factura.getTotal() + "</strong></td></tr>"
                + "</table>"
                + "<hr style='margin: 20px 0;'>"
                + "<p style='font-size: 12px; color: #7f8c8d;'>Este correo fue generado automáticamente. Si tiene preguntas, por favor contáctenos.</p>"
                + "<p style='font-size: 12px; color: #95a5a6;'>Gracias por confiar en nuestros servicios médicos.</p>"
                + "</div>";

        EmailRequestDTO email = new EmailRequestDTO();
        email.setDestinatario(factura.getCorreoPaciente());
        email.setAsunto("Factura del Hospital");
        email.setMensaje(mensaje);

        String json = new ObjectMapper().writeValueAsString(email);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            System.out.println("Correo enviado exitosamente.");
        } else {
            throw new RuntimeException("Error al enviar el correo. Código: " + responseCode);
        }
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

    //generar pdf de factura
    private void generarPdfFactura(ModeloGestionFactura factura) {
        try {
            String dest = "factura_" + factura.getIdFactura() + ".pdf";
            PdfWriter writer = new PdfWriter(dest);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Estilos generales
            DeviceRgb fondoEncabezado = new DeviceRgb(230, 230, 250);
            DeviceRgb colorBordes = new DeviceRgb(180, 180, 180);

            document.setMargins(30, 20, 30, 20);

            // Encabezado de empresa
            Paragraph encabezado = new Paragraph("CLÍNICA MEDI-SALUD\nRUC: 1234567890001\nTel: 1234-5678\nDirección: Calle Falsa 123, Ciudad")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12)
                    .setBold();
            document.add(encabezado);

            // Línea separadora
            document.add(new LineSeparator(new SolidLine()));

            // Título
            Paragraph titulo = new Paragraph("Factura Electrónica")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(10);
            document.add(titulo);

            // Si la factura está anulada, mostrar marca roja
            if ("Inactivo".equalsIgnoreCase(factura.getEstado())) {
                Paragraph anulada = new Paragraph("FACTURA ANULADA")
                        .setFontSize(20)
                        .setFontColor(ColorConstants.RED)
                        .setBold()
                        .setTextAlignment(TextAlignment.CENTER)
                        .setMarginTop(10);
                document.add(anulada);
            }

            // Fecha formateada
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String fechaFormateada = dateFormat.format(factura.getFecha());

            // Datos principales
            Table datosTabla = new Table(UnitValue.createPercentArray(new float[]{1, 3}))
                    .useAllAvailableWidth()
                    .setMarginTop(15)
                    .setBorder(new SolidBorder(colorBordes, 0.5f));

            datosTabla.addCell(new Cell().add(new Paragraph("Factura Nº").setBold()).setBackgroundColor(fondoEncabezado));
            datosTabla.addCell(new Cell().add(new Paragraph(factura.getIdFactura() + "")));

            datosTabla.addCell(new Cell().add(new Paragraph("Fecha").setBold()).setBackgroundColor(fondoEncabezado));
            datosTabla.addCell(new Cell().add(new Paragraph(fechaFormateada)));

            datosTabla.addCell(new Cell().add(new Paragraph("Paciente").setBold()).setBackgroundColor(fondoEncabezado));
            datosTabla.addCell(new Cell().add(new Paragraph(factura.getNombrePaciente() + " " + factura.getApellidoPaciente())));

            datosTabla.addCell(new Cell().add(new Paragraph("Atendido por").setBold()).setBackgroundColor(fondoEncabezado));
            datosTabla.addCell(new Cell().add(new Paragraph(factura.getNombreUsuario() + " " + factura.getApellidoUsuario())));

            datosTabla.addCell(new Cell().add(new Paragraph("Estado").setBold()).setBackgroundColor(fondoEncabezado));
            datosTabla.addCell(new Cell().add(new Paragraph(factura.getEstado())));

            document.add(datosTabla);

            // Detalles de factura
            Table tablaDetalles = new Table(UnitValue.createPercentArray(new float[]{4, 1, 2, 2}))
                    .useAllAvailableWidth()
                    .setMarginTop(20);

            tablaDetalles.addHeaderCell(new Cell().add(new Paragraph("Servicio").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tablaDetalles.addHeaderCell(new Cell().add(new Paragraph("Cant.").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tablaDetalles.addHeaderCell(new Cell().add(new Paragraph("Precio Unit.").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));
            tablaDetalles.addHeaderCell(new Cell().add(new Paragraph("Subtotal").setBold()).setBackgroundColor(ColorConstants.LIGHT_GRAY));

            for (ModeloGestionFactura.Detalle detalle : factura.getDetalles()) {
                tablaDetalles.addCell(new Cell().add(new Paragraph(detalle.getNombreServicio())));
                tablaDetalles.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getCantidad()))));
                tablaDetalles.addCell(new Cell().add(new Paragraph(String.format("$%.2f", detalle.getPrecioServicio()))));
                tablaDetalles.addCell(new Cell().add(new Paragraph(String.format("$%.2f", detalle.getSubtotal()))));
            }

            document.add(tablaDetalles);

            // Totales
            Table tablaTotales = new Table(UnitValue.createPercentArray(new float[]{6, 2}))
                    .useAllAvailableWidth()
                    .setMarginTop(15);

            tablaTotales.addCell(new Cell().add(new Paragraph("Subtotal:").setBold()).setBorder(Border.NO_BORDER));
            tablaTotales.addCell(new Cell().add(new Paragraph(String.format("$%.2f", factura.getSubtotal()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            tablaTotales.addCell(new Cell().add(new Paragraph("IVA:").setBold()).setBorder(Border.NO_BORDER));
            tablaTotales.addCell(new Cell().add(new Paragraph(String.format("$%.2f", factura.getIva()))).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

            tablaTotales.addCell(new Cell().add(new Paragraph("TOTAL:").setBold()).setBorder(Border.NO_BORDER));
            tablaTotales.addCell(new Cell().add(new Paragraph(String.format("$%.2f", factura.getTotal()))).setTextAlignment(TextAlignment.RIGHT).setBold().setBorder(Border.NO_BORDER));

            document.add(tablaTotales);

            // Pie de página
            document.add(new Paragraph("\n\nGracias por su preferencia").setTextAlignment(TextAlignment.CENTER).setFontSize(10));

            document.close();

            // Mostrar ubicación del archivo
            JOptionPane.showMessageDialog(null, "PDF generado: " + new File(dest).getAbsolutePath());

            // 🧾 Abrir PDF en visor predeterminado del sistema
            File pdfFile = new File(dest);
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile); // Esto abrirá con el visor predeterminado (Adobe, Foxit, navegador, etc.)
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al generar PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* private JDialog mostrarCarga(String mensaje) {
    JDialog dialog = new JDialog(this, "Enviando...", true);
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // Icono de carga (agrega tu icono en la ruta correcta)
    JLabel icono = new JLabel(new ImageIcon("ruta/a/tu/icono.gif")); // Puedes usar un GIF de loading
    JLabel texto = new JLabel(mensaje);
    texto.setAlignmentX(Component.CENTER_ALIGNMENT);
    icono.setAlignmentX(Component.CENTER_ALIGNMENT);

    panel.add(icono);
    panel.add(Box.createVerticalStrut(10));
    panel.add(texto);

    dialog.getContentPane().add(panel);
    dialog.setSize(200, 150);
    dialog.setLocationRelativeTo(this);

    // Mostrar en un hilo aparte para no bloquear
    new Thread(() -> dialog.setVisible(true)).start();

    return dialog;
}*/
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
