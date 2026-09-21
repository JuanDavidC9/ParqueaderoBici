package edu.unilibre.interaccion;

import edu.unilibre.datos.Bicicleta;
import edu.unilibre.datos.RegistroParqueo;
import edu.unilibre.gestion.GestionParqueadero;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class Programa extends JFrame {

    private final GestionParqueadero gestion = new GestionParqueadero();
    private final DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private JTextField txtIdPropietario, txtSerial, txtColor, txtSerialRetiro;
    private JTextArea txtAreaConsola;
    private JButton btnRegistrarEntrada, btnRegistrarSalida, btnAvanzarDia;

    public Programa() {
        setTitle("Sistema de Parqueadero de Bicicletas");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        initComponentes();
        actualizarAreaTexto();
    }

    private void initComponentes() {
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pnlEstado = new JPanel(new GridLayout(2, 1, 6, 6));
        pnlEstado.setBorder(BorderFactory.createTitledBorder("Estado del Parqueadero"));
        pnlEstado.add(new JLabel("Cupo Maximo: 20 bicicletas"));
        pnlEstado.add(new JLabel("Tarifa: $10.0 por minuto"));

        JPanel pnlEntrada = new JPanel(new GridLayout(4, 2, 6, 6));
        pnlEntrada.setBorder(BorderFactory.createTitledBorder("1. Registrar Entrada"));

        pnlEntrada.add(new JLabel("ID Propietario:"));
        txtIdPropietario = new JTextField();
        pnlEntrada.add(txtIdPropietario);

        pnlEntrada.add(new JLabel("Serial Bicicleta:"));
        txtSerial = new JTextField();
        pnlEntrada.add(txtSerial);

        pnlEntrada.add(new JLabel("Color:"));
        txtColor = new JTextField();
        pnlEntrada.add(txtColor);

        btnRegistrarEntrada = new JButton("Registrar Entrada");
        estilizarBoton(btnRegistrarEntrada);
        pnlEntrada.add(new JLabel());
        pnlEntrada.add(btnRegistrarEntrada);

        // Se cambió a 3 filas ya que se eliminó el botón de actualizar
        JPanel pnlSalida = new JPanel(new GridLayout(3, 2, 6, 6));
        pnlSalida.setBorder(BorderFactory.createTitledBorder("2. Retiro y Simulacion"));

        pnlSalida.add(new JLabel("Serial Bicicleta:"));
        txtSerialRetiro = new JTextField();
        pnlSalida.add(txtSerialRetiro);

        btnRegistrarSalida = new JButton("Calcular Cobro y Retirar");
        estilizarBoton(btnRegistrarSalida);
        pnlSalida.add(new JLabel());
        pnlSalida.add(btnRegistrarSalida);

        btnAvanzarDia = new JButton("Avanzar 1 Dia");
        estilizarBoton(btnAvanzarDia);
        pnlSalida.add(new JLabel());
        pnlSalida.add(btnAvanzarDia);

        panelIzquierdo.add(pnlEstado);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(pnlEntrada);
        panelIzquierdo.add(Box.createVerticalStrut(10));
        panelIzquierdo.add(pnlSalida);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Consola del Sistema"));
        txtAreaConsola = new JTextArea();
        txtAreaConsola.setEditable(false);
        txtAreaConsola.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panelDerecho.add(new JScrollPane(txtAreaConsola), BorderLayout.CENTER);

        add(panelIzquierdo, BorderLayout.WEST);
        add(panelDerecho, BorderLayout.CENTER);

        btnRegistrarEntrada.addActionListener(e -> {
            String id = txtIdPropietario.getText().trim();
            String serial = txtSerial.getText().trim();
            String color = txtColor.getText().trim();

            if (id.isEmpty() || serial.isEmpty() || color.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (gestion.verificarDisponibilidad() == false) {
                JOptionPane.showMessageDialog(this, "El parqueadero esta lleno.", "Parqueadero Lleno", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Bicicleta nuevaBici = new Bicicleta(serial, color, id);
            boolean exito = gestion.registrarBicicleta(nuevaBici);

            if (exito == true) {
                JOptionPane.showMessageDialog(this, "Bicicleta registrada exitosamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                txtIdPropietario.setText("");
                txtSerial.setText("");
                txtColor.setText("");
                actualizarAreaTexto();
            } else {
                JOptionPane.showMessageDialog(this, "Ya existe una bicicleta activa con ese serial.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRegistrarSalida.addActionListener(e -> {
            String serial = txtSerialRetiro.getText().trim();
            if (serial.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingrese el serial de la bicicleta.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double costo = gestion.retirarBicicleta(serial);

            if (costo >= 0) {
                JOptionPane.showMessageDialog(this, String.format("Retiro exitoso.\nTotal a pagar: $%.2f", costo), "Cobro Realizado", JOptionPane.INFORMATION_MESSAGE);
                txtSerialRetiro.setText("");
                actualizarAreaTexto();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontro una bicicleta activa con ese serial.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAvanzarDia.addActionListener(e -> {
            GestionParqueadero.avanzarTiempo(1);
            JOptionPane.showMessageDialog(this, "Se avanzo 1 dia en el tiempo simulado.\nFecha actual: " + GestionParqueadero.getTiempoSimulado().format(formatoFecha), "Tiempo Avanzado", JOptionPane.INFORMATION_MESSAGE);
            actualizarAreaTexto();
        });
    }

    private void estilizarBoton(JButton boton) {
        boton.setBackground(new Color(51, 111, 158));
        boton.setForeground(Color.WHITE);
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);
        boton.setFocusPainted(false);
    }

    private void actualizarAreaTexto() {
        StringBuilder sb = new StringBuilder();
        sb.append("=============================================================\n");
        sb.append("                 ESTADO DEL PARQUEADERO                      \n");
        sb.append("=============================================================\n\n");

        sb.append("Fecha y hora actual (simulada): " + GestionParqueadero.getTiempoSimulado().format(formatoFecha) + "\n");
        sb.append(String.format("Espacios disponibles: %02d / 20\n", gestion.getParqueadero().getCapacidadDisponible()));
        sb.append(String.format("Ingresos generados HOY: $%.2f\n", gestion.calcularIngresosDelDia()));
        sb.append("-------------------------------------------------------------\n");
        sb.append("REGISTROS ACTIVOS (Bicicletas parqueadas):\n");

        int contadorActivos = 0;

        for (int i = 0; i < gestion.getParqueadero().getRegistros().size(); i++) {
            RegistroParqueo reg = gestion.getParqueadero().getRegistros().get(i);
            if (reg.estaActivo() == true) {
                contadorActivos = contadorActivos + 1;
                Bicicleta b = reg.getBicicleta();
                double valorActual = gestion.consultarValorParqueo(b.getSerial());
                sb.append(String.format("   [%02d] Serial: %-10s | Dueno ID: %-10s | Color: %-8s | Debe: $%.2f\n",
                        contadorActivos, b.getSerial(), b.getIdentificacionPropietario(), b.getColor(), valorActual));
            }
        }

        if (contadorActivos == 0) {
            sb.append("   No hay bicicletas parqueadas en este momento.\n");
        }

        sb.append("=============================================================\n");
        txtAreaConsola.setText(sb.toString());
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            Font fuenteGeneral = new Font("Arial", Font.PLAIN, 14);
            java.util.Enumeration<Object> keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof javax.swing.plaf.FontUIResource) {
                    UIManager.put(key, fuenteGeneral);
                }
            }
        } catch (Exception e) {
            System.err.println("No se pudo establecer el Look and Feel del sistema.");
        }

        SwingUtilities.invokeLater(() -> {
            new Programa().setVisible(true);
        });
    }
}