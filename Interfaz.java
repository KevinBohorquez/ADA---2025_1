package Semana6;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Random;

public class Interfaz extends JFrame {

    private TablaHashLineal tablaLineal;
    private TablaHash tablaArbol;

    private JTextField txtCodigo, txtNombres, txtApellidos, txtTelefono;
    private JTextField txtCorreo, txtDireccion, txtCodigoPostal;
    private JTextField txtBuscarNombres, txtBuscarApellidos;
    private JTextArea txtResultados, txtEstadisticas, txtComparacion;
    private JButton btnInsertar, btnBuscar, btnGenerarDatos, btnLimpiar, btnComparar;
    private JProgressBar progressBar;

    public Interfaz() {
        initializeComponents();
        setupLayout();
        setupEventListeners();

        tablaLineal = new TablaHashLineal(101);
        tablaArbol = new TablaHash(101);

        setTitle("Laboratorio 07 - Aplicaciones de Tablas de Dispersión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 900);
        setLocationRelativeTo(null);
    }

    private void initializeComponents() {
        txtCodigo = new JTextField(12);
        txtNombres = new JTextField(12);
        txtApellidos = new JTextField(12);
        txtTelefono = new JTextField(12);
        txtCorreo = new JTextField(12);
        txtDireccion = new JTextField(12);
        txtCodigoPostal = new JTextField(12);

        txtBuscarNombres = new JTextField(12);
        txtBuscarApellidos = new JTextField(12);

        txtResultados = new JTextArea(15, 40);
        txtResultados.setEditable(false);
        txtResultados.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));

        txtEstadisticas = new JTextArea(15, 35);
        txtEstadisticas.setEditable(false);
        txtEstadisticas.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));

        txtComparacion = new JTextArea(10, 50);
        txtComparacion.setEditable(false);
        txtComparacion.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));

        btnInsertar = new JButton("Insertar");
        btnBuscar = new JButton("Buscar");
        btnGenerarDatos = new JButton("Generar Datos");
        btnLimpiar = new JButton("Limpiar");
        btnComparar = new JButton("Comparar");

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = createInputPanel();
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));

        JPanel resultsAndStats = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(new TitledBorder("Resultados de Búsqueda"));
        resultsPanel.add(new JScrollPane(txtResultados), BorderLayout.CENTER);

        JPanel statsPanel = new JPanel(new BorderLayout());
        statsPanel.setBorder(new TitledBorder("Estadísticas"));
        statsPanel.add(new JScrollPane(txtEstadisticas), BorderLayout.CENTER);

        resultsAndStats.add(resultsPanel);
        resultsAndStats.add(statsPanel);

        centerPanel.add(resultsAndStats, BorderLayout.CENTER);

        JPanel compPanel = new JPanel(new BorderLayout());
        compPanel.setBorder(new TitledBorder("Comparación de Rendimiento"));
        compPanel.add(new JScrollPane(txtComparacion), BorderLayout.CENTER);
        compPanel.setPreferredSize(new Dimension(0, 200));
        centerPanel.add(compPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(progressBar);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createInputPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new TitledBorder("Gestión de Clientes"));

        JPanel fieldsPanel = new JPanel(new GridLayout(3, 6, 8, 8));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fieldsPanel.add(new JLabel("Código:", JLabel.RIGHT));
        fieldsPanel.add(txtCodigo);
        fieldsPanel.add(new JLabel("Nombres:", JLabel.RIGHT));
        fieldsPanel.add(txtNombres);
        fieldsPanel.add(new JLabel("Apellidos:", JLabel.RIGHT));
        fieldsPanel.add(txtApellidos);

        fieldsPanel.add(new JLabel("Teléfono:", JLabel.RIGHT));
        fieldsPanel.add(txtTelefono);
        fieldsPanel.add(new JLabel("Correo:", JLabel.RIGHT));
        fieldsPanel.add(txtCorreo);
        fieldsPanel.add(new JLabel("", JLabel.RIGHT));
        fieldsPanel.add(btnInsertar);

        fieldsPanel.add(new JLabel("Dirección:", JLabel.RIGHT));
        fieldsPanel.add(txtDireccion);
        fieldsPanel.add(new JLabel("Cód. Postal:", JLabel.RIGHT));
        fieldsPanel.add(txtCodigoPostal);
        fieldsPanel.add(new JLabel("", JLabel.RIGHT));
        fieldsPanel.add(new JLabel(""));

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        searchPanel.setBorder(new TitledBorder("Búsqueda"));

        searchPanel.add(new JLabel("Nombres:"));
        searchPanel.add(txtBuscarNombres);
        searchPanel.add(new JLabel("Apellidos:"));
        searchPanel.add(txtBuscarApellidos);
        searchPanel.add(btnBuscar);

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        controlPanel.add(btnGenerarDatos);
        controlPanel.add(btnLimpiar);
        controlPanel.add(btnComparar);

        JPanel bottomInputPanel = new JPanel(new BorderLayout());
        bottomInputPanel.add(searchPanel, BorderLayout.CENTER);
        bottomInputPanel.add(controlPanel, BorderLayout.SOUTH);

        mainPanel.add(bottomInputPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void setupEventListeners() {
        btnInsertar.addActionListener(e -> insertarCliente());
        btnBuscar.addActionListener(e -> buscarCliente());
        btnGenerarDatos.addActionListener(e -> generarDatosPrueba());
        btnLimpiar.addActionListener(e -> limpiarTablas());
        btnComparar.addActionListener(e -> compararRendimiento());
    }

    private void insertarCliente() {
        try {
            if (validarCamposCliente()) {
                Cliente cliente = new Cliente(
                        txtCodigo.getText().trim(),
                        txtNombres.getText().trim(),
                        txtApellidos.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtCorreo.getText().trim(),
                        txtDireccion.getText().trim(),
                        txtCodigoPostal.getText().trim()
                );

                boolean insertadoLineal = tablaLineal.insertar(cliente);
                boolean insertadoArbol = tablaArbol.insertar(cliente);

                if (insertadoLineal && insertadoArbol) {
                    txtResultados.append("Cliente insertado exitosamente:\n");
                    txtResultados.append(cliente.toStringCompleto() + "\n\n");
                    limpiarCamposCliente();
                    actualizarEstadisticas();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "El cliente ya existe o hay un error en la inserción",
                            "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al insertar cliente: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCliente() {
        String nombres = txtBuscarNombres.getText().trim();
        String apellidos = txtBuscarApellidos.getText().trim();

        if (nombres.isEmpty() || apellidos.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese nombres y apellidos para buscar",
                    "Campos requeridos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long inicioLineal = System.nanoTime();
        Cliente clienteLineal = tablaLineal.buscar(nombres, apellidos);
        long finLineal = System.nanoTime();

        long inicioArbol = System.nanoTime();
        Cliente clienteArbol = tablaArbol.buscar(nombres, apellidos);
        long finArbol = System.nanoTime();

        txtResultados.append("=== RESULTADO DE BÚSQUEDA ===\n");
        txtResultados.append("Nombres: " + nombres + "\n");
        txtResultados.append("Apellidos: " + apellidos + "\n\n");

        if (clienteLineal != null) {
            txtResultados.append("CLIENTE ENCONTRADO:\n");
            txtResultados.append(clienteLineal.toStringCompleto() + "\n\n");
        } else {
            txtResultados.append("Cliente NO encontrado\n\n");
        }

        txtResultados.append("TIEMPOS DE BÚSQUEDA:\n");
        txtResultados.append(String.format("Reasignación Lineal: %.2f microsegundos\n",
                (finLineal - inicioLineal) / 1000.0));
        txtResultados.append(String.format("Encadenamiento (Árboles): %.2f microsegundos\n\n",
                (finArbol - inicioArbol) / 1000.0));

        txtResultados.setCaretPosition(txtResultados.getDocument().getLength());
    }

    private void generarDatosPrueba() {
        progressBar.setVisible(true);

        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                String[] nombres = {"Ana", "Carlos", "María", "José", "Laura", "Pedro", "Carmen", "Luis",
                        "Elena", "Miguel", "Rosa", "Antonio", "Isabel", "Francisco", "Patricia"};
                String[] apellidos = {"García", "Rodríguez", "González", "Fernández", "López", "Martínez",
                        "Sánchez", "Pérez", "Gómez", "Martín", "Jiménez", "Ruiz", "Hernández"};

                Random random = new Random();
                int totalClientes = 500;

                for (int i = 0; i < totalClientes; i++) {
                    String codigo = String.format("CLI%04d", i + 1);
                    String nombre = nombres[random.nextInt(nombres.length)];
                    String apellido = apellidos[random.nextInt(apellidos.length)];
                    String telefono = String.format("+51-9%08d", random.nextInt(100000000));
                    String correo = (nombre + "." + apellido + "@email.com").toLowerCase();
                    String direccion = "Av. Principal " + (random.nextInt(9999) + 1);
                    String codigoPostal = String.format("%05d", random.nextInt(100000));

                    Cliente cliente = new Cliente(codigo, nombre, apellido, telefono, correo, direccion, codigoPostal);

                    tablaLineal.insertar(cliente);
                    tablaArbol.insertar(cliente);

                    int progreso = (i * 100) / totalClientes;
                    publish(progreso);

                    if (i % 50 == 0) {
                        Thread.sleep(10);
                    }
                }

                return null;
            }

            @Override
            protected void process(java.util.List<Integer> chunks) {
                int ultimoProgreso = chunks.get(chunks.size() - 1);
                progressBar.setValue(ultimoProgreso);
            }

            @Override
            protected void done() {
                progressBar.setVisible(false);
                txtResultados.append("=== DATOS DE PRUEBA GENERADOS ===\n");
                txtResultados.append("Se han insertado 500 clientes de prueba\n\n");
                actualizarEstadisticas();
                JOptionPane.showMessageDialog(Interfaz.this,
                        "Datos de prueba generados exitosamente",
                        "Completado", JOptionPane.INFORMATION_MESSAGE);
            }
        };

        worker.execute();
    }

    private void limpiarTablas() {
        tablaLineal = new TablaHashLineal(101);
        tablaArbol = new TablaHash(101);
        txtResultados.setText("");
        txtEstadisticas.setText("");
        txtComparacion.setText("");
        limpiarCamposCliente();
        txtBuscarNombres.setText("");
        txtBuscarApellidos.setText("");
        JOptionPane.showMessageDialog(this, "Tablas limpiadas exitosamente",
                "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void compararRendimiento() {
        SwingWorker<Void, String> worker = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish("Iniciando comparación de rendimiento...\n");

                long tiempoLineal = medirTiempoInsercion(new TablaHashLineal(101));
                long tiempoArbol = medirTiempoInsercion(new TablaHash(101));

                publish("=== ANÁLISIS DE COMPLEJIDAD TEMPORAL ===\n\n");

                publish("INSERCIÓN:\n");
                publish(String.format("Reasignación Lineal: %.2f ms\n", tiempoLineal / 1_000_000.0));
                publish(String.format("Encadenamiento (Árboles): %.2f ms\n\n", tiempoArbol / 1_000_000.0));

                publish("COMPLEJIDAD TEÓRICA:\n");
                publish("Reasignación Lineal:\n");
                publish("  - Mejor caso: O(1)\n");
                publish("  - Caso promedio: O(1)\n");
                publish("  - Peor caso: O(n) - tabla llena\n\n");

                publish("Encadenamiento con Árboles:\n");
                publish("  - Mejor caso: O(1)\n");
                publish("  - Caso promedio: O(log n) por posición\n");
                publish("  - Peor caso: O(n) - todas las claves en misma posición\n\n");

                publish("VENTAJAS Y DESVENTAJAS:\n\n");
                publish("Reasignación Lineal:\n");
                publish("+ Memoria: No requiere punteros adicionales\n");
                publish("+ Velocidad: Acceso directo a memoria\n");
                publish("- Agrupamiento: Clustering alrededor de colisiones\n");
                publish("- Factor de carga: Degrada con alta ocupación\n\n");

                publish("Encadenamiento con Árboles:\n");
                publish("+ Flexibilidad: Maneja cualquier número de colisiones\n");
                publish("+ Búsqueda: O(log n) en estructuras balanceadas\n");
                publish("- Memoria: Overhead de punteros\n");
                publish("- Complejidad: Implementación más compleja\n\n");

                return null;
            }

            @Override
            protected void process(java.util.List<String> chunks) {
                for (String texto : chunks) {
                    txtComparacion.append(texto);
                }
                txtComparacion.setCaretPosition(txtComparacion.getDocument().getLength());
            }
        };

        worker.execute();
    }

    private long medirTiempoInsercion(Object tabla) {
        Random random = new Random();
        String[] nombres = {"Ana", "Carlos", "María", "José", "Laura"};
        String[] apellidos = {"García", "López", "Martín", "Pérez", "Ruiz"};

        long inicio = System.nanoTime();

        for (int i = 0; i < 100; i++) {
            Cliente cliente = new Cliente(
                    "TEST" + i,
                    nombres[random.nextInt(nombres.length)],
                    apellidos[random.nextInt(apellidos.length)],
                    "123456789",
                    "test@email.com",
                    "Dirección Test",
                    "12345"
            );

            if (tabla instanceof TablaHashLineal) {
                ((TablaHashLineal) tabla).insertar(cliente);
            } else if (tabla instanceof TablaHash) {
                ((TablaHash) tabla).insertar(cliente);
            }
        }

        return System.nanoTime() - inicio;
    }

    private void actualizarEstadisticas() {
        txtEstadisticas.setText("");
        txtEstadisticas.append(tablaLineal.obtenerEstadisticas() + "\n\n");
        txtEstadisticas.append("=" + "=".repeat(30) + "\n\n");
        txtEstadisticas.append(tablaArbol.obtenerEstadisticas() + "\n");
    }

    private boolean validarCamposCliente() {
        if (txtCodigo.getText().trim().isEmpty() ||
                txtNombres.getText().trim().isEmpty() ||
                txtApellidos.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Los campos Código, Nombres y Apellidos son obligatorios",
                    "Campos requeridos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void limpiarCamposCliente() {
        txtCodigo.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtCodigoPostal.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Interfaz().setVisible(true);
        });
    }
}