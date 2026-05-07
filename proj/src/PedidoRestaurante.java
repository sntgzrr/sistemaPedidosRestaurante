import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

class Pedido {

    private int numeroPedido;
    private String nombrePlato;
    private String categoria;
    private int cantidad;
    private String metodoEntrega;
    private String direccion;

    public Pedido(int numeroPedido,
                  String nombrePlato,
                  String categoria,
                  int cantidad,
                  String metodoEntrega,
                  String direccion) {

        this.numeroPedido = numeroPedido;
        this.nombrePlato = nombrePlato;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.metodoEntrega = metodoEntrega;
        this.direccion = direccion;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public String getNombrePlato() {
        return nombrePlato;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getMetodoEntrega() {
        return metodoEntrega;
    }

    public String getDireccion() {
        return direccion;
    }
}

class VentanaPedidos extends JFrame {
    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;

    private JButton btnCancelar;
    private JButton btnExportar;

    private List<Pedido> listaPedidos;

    public VentanaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
        setTitle("Pedidos Realizados");
        setSize(900, 400);
        setLocationRelativeTo(null);

        initComponents();
        cargarPedidos();
    }

    private void initComponents() {
        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("N° Pedido");
        modeloTabla.addColumn("Plato");
        modeloTabla.addColumn("Categoría");
        modeloTabla.addColumn("Cantidad");
        modeloTabla.addColumn("Entrega");
        modeloTabla.addColumn("Dirección");

        tablaPedidos = new JTable(modeloTabla);

        JScrollPane scrollPane = new JScrollPane(tablaPedidos);

        btnCancelar = new JButton("Cancelar Pedido");
        btnExportar = new JButton("Exportar Pedidos");

        JPanel panelBotones = new JPanel();

        panelBotones.add(btnCancelar);
        panelBotones.add(btnExportar);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        btnCancelar.addActionListener(e -> cancelarPedido());
        btnExportar.addActionListener(e -> exportarPedidos());
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);
        for (Pedido pedido : listaPedidos) {
            modeloTabla.addRow(new Object[]{
                    pedido.getNumeroPedido(),
                    pedido.getNombrePlato(),
                    pedido.getCategoria(),
                    pedido.getCantidad(),
                    pedido.getMetodoEntrega(),
                    pedido.getDireccion()
            });
        }
    }

    private void cancelarPedido() {
        int filaSeleccionada = tablaPedidos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido para cancelar."
            );

            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea cancelar el pedido seleccionado?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            listaPedidos.remove(filaSeleccionada);
            cargarPedidos();
            JOptionPane.showMessageDialog(
                    this,
                    "Pedido cancelado correctamente."
            );
        }
    }

    private void exportarPedidos() {
        System.out.println("=========== PEDIDOS EXPORTADOS ===========");
        for (Pedido pedido : listaPedidos) {
            System.out.println(
                    "Pedido #" + pedido.getNumeroPedido() +
                            " | Plato: " + pedido.getNombrePlato() +
                            " | Categoría: " + pedido.getCategoria() +
                            " | Cantidad: " + pedido.getCantidad() +
                            " | Entrega: " + pedido.getMetodoEntrega() +
                            " | Dirección: " + pedido.getDireccion()
            );
        }
        System.out.println("==========================================");
        JOptionPane.showMessageDialog(
                this,
                "Pedidos exportados correctamente.\nRevise la consola."
        );
    }
}

public class PedidoRestaurante extends JFrame {

    private JTextField txtNombrePlato;
    private JComboBox<String> cbCategoria;
    private JTextField txtCantidad;

    private JRadioButton rbDomicilio;
    private JRadioButton rbRecogida;

    private JTextField txtDireccion;

    private JButton btnBorrar;
    private JButton btnConfirmar;

    private List<Pedido> listaPedidos = new ArrayList<>();

    private int contadorPedidos = 1;

    public PedidoRestaurante() {

        setTitle("Sistema de Pedidos");
        setSize(700, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {

        JPanel panelFormulario = new JPanel(new GridBagLayout());

        panelFormulario.setBorder(
                BorderFactory.createTitledBorder("Formulario de Pedido")
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNombre = new JLabel("Nombre del Plato:");
        txtNombrePlato = new JTextField(20);

        JLabel lblCategoria = new JLabel("Categoría:");

        cbCategoria = new JComboBox<>(new String[]{
                "Seleccione una categoría",
                "Entrante",
                "Plato Principal",
                "Postre"
        });

        JLabel lblCantidad = new JLabel("Cantidad:");
        txtCantidad = new JTextField(10);

        // Método entrega
        JLabel lblEntrega = new JLabel("Método de Entrega:");

        rbDomicilio = new JRadioButton("Domicilio");
        rbRecogida = new JRadioButton("Recogida");

        ButtonGroup grupoEntrega = new ButtonGroup();

        grupoEntrega.add(rbDomicilio);
        grupoEntrega.add(rbRecogida);

        JPanel panelRadio = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panelRadio.add(rbDomicilio);
        panelRadio.add(rbRecogida);

        JLabel lblDireccion = new JLabel("Dirección:");
        txtDireccion = new JTextField(20);

        txtDireccion.setEnabled(false);

        rbDomicilio.addActionListener(e ->
                txtDireccion.setEnabled(true)
        );

        rbRecogida.addActionListener(e -> {
            txtDireccion.setText("");
            txtDireccion.setEnabled(false);
        });

        // Botones
        btnBorrar = new JButton("Borrar");
        btnConfirmar = new JButton("Confirmar");

        btnBorrar.addActionListener(e -> limpiarFormulario());

        btnConfirmar.addActionListener(e -> confirmarPedido());

        JPanel panelBotones = new JPanel();

        panelBotones.add(btnBorrar);
        panelBotones.add(btnConfirmar);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(lblNombre, gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtNombrePlato, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(lblCategoria, gbc);

        gbc.gridx = 1;
        panelFormulario.add(cbCategoria, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panelFormulario.add(lblCantidad, gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtCantidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panelFormulario.add(lblEntrega, gbc);

        gbc.gridx = 1;
        panelFormulario.add(panelRadio, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panelFormulario.add(lblDireccion, gbc);

        gbc.gridx = 1;
        panelFormulario.add(txtDireccion, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);
        add(panelFormulario);
    }

    private void limpiarFormulario() {
        txtNombrePlato.setText("");
        cbCategoria.setSelectedIndex(0);
        txtCantidad.setText("");
        txtDireccion.setText("");
        rbDomicilio.setSelected(false);
        rbRecogida.setSelected(false);
        txtDireccion.setEnabled(false);
        resetBorders();
    }

    private void confirmarPedido() {

        resetBorders();
        String nombre = txtNombrePlato.getText().trim();
        String categoria = (String) cbCategoria.getSelectedItem();
        String cantidadTexto = txtCantidad.getText().trim();
        String direccion = txtDireccion.getText().trim();
        String metodoEntrega = "";

        if (rbDomicilio.isSelected()) {
            metodoEntrega = "Domicilio";
        } else if (rbRecogida.isSelected()) {
            metodoEntrega = "Recogida";
        }

        if (nombre.isEmpty()
                || !Pattern.matches(
                "^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ ]+$",
                nombre)) {
            marcarError(txtNombrePlato);
            JOptionPane.showMessageDialog(
                    this,
                    "Nombre del plato inválido."
            );
            return;
        }

        if (cbCategoria.getSelectedIndex() == 0) {
            marcarError(cbCategoria);
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una categoría."
            );
            return;
        }

        int cantidad;
        try {
            cantidad = Integer.parseInt(cantidadTexto);
            if (cantidad <= 0) {
                throw new NumberFormatException();
            }

        } catch (NumberFormatException e) {
            marcarError(txtCantidad);
            JOptionPane.showMessageDialog(
                    this,
                    "La cantidad debe ser un número entero positivo."
            );
            return;
        }

        if (metodoEntrega.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un método de entrega."
            );
            return;
        }

        if (metodoEntrega.equals("Domicilio")) {
            if (direccion.isEmpty() || direccion.length() < 5) {
                marcarError(txtDireccion);
                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese una dirección válida."
                );

                return;
            }
        }

        Pedido pedido = new Pedido(
                contadorPedidos++,
                nombre,
                categoria,
                cantidad,
                metodoEntrega,
                metodoEntrega.equals("Domicilio")
                        ? direccion
                        : "-"
        );

        listaPedidos.add(pedido);
        JOptionPane.showMessageDialog(
                this,
                "Pedido registrado correctamente."
        );

        VentanaPedidos ventanaPedidos =
                new VentanaPedidos(listaPedidos);
        ventanaPedidos.setVisible(true);

        limpiarFormulario();
    }

    private void marcarError(JComponent componente) {
        componente.setBorder(
                new LineBorder(Color.RED, 2)
        );
    }

    private void resetBorders() {
        txtNombrePlato.setBorder(
                UIManager.getBorder("TextField.border")
        );
        txtCantidad.setBorder(
                UIManager.getBorder("TextField.border")
        );
        txtDireccion.setBorder(
                UIManager.getBorder("TextField.border")
        );
        cbCategoria.setBorder(
                UIManager.getBorder("ComboBox.border")
        );
    }
}
