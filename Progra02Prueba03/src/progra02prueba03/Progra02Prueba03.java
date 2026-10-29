/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package progra02prueba03;

/**
 *
 * @author marlo
 */
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class Progra02Prueba03 extends JFrame{

    private ArrayList<Empleado> empleados;
    private JComboBox<String> cbTipo;
    private JTextField txtCodigo,txtNombre,txtSalarioBase;
    private JTextField txtDescuento,txtBonificacion;
    private JTextField txtTarifa,txtHoras;
    private DefaultTableModel modeloTabla;
    private JTable tablaEmple;
    
    private void configurarVentana(){
        setTitle("----Sistema de gestion de empleados----");
        setSize(850,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));
    }
    private void inicializarComponentes() {
        cbTipo = new JComboBox<>(new String[]{"administrativo", "por horas"});
        JPanel panelFormulario = new JPanel(new GridLayout(4, 4, 10, 10));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del empleado"));
        panelFormulario.add(new JLabel("codigo:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);
        panelFormulario.add(new JLabel("nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("salario base:"));
        txtSalarioBase = new JTextField();
        panelFormulario.add(txtSalarioBase);
        panelFormulario.add(new JLabel("tipo:"));
        panelFormulario.add(cbTipo);
        panelFormulario.add(new JLabel("bonificacion:"));
        txtBonificacion = new JTextField();
        panelFormulario.add(txtBonificacion);
        panelFormulario.add(new JLabel("descuento(en porcentaje):"));
        txtDescuento = new JTextField();
        panelFormulario.add(txtDescuento);
        panelFormulario.add(new JLabel("horas trabajadas:"));
        txtHoras = new JTextField();
        txtHoras.setEnabled(false);
        panelFormulario.add(txtHoras);
        panelFormulario.add(new JLabel("tarifa por hora:"));
        txtTarifa = new JTextField();
        txtTarifa.setEnabled(false);
        
        
        panelFormulario.add(txtTarifa);
        cbTipo.addActionListener(e -> alternarCampos());
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnClonar = new JButton("Clonar Empleado");
        JButton btnComparar = new JButton("Comparar (Seleccionar 2)");
        JButton btnResumen = new JButton("Estadísticas");
        JButton btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnClonar);
        panelBotones.add(btnComparar);
        panelBotones.add(btnResumen);
        panelBotones.add(btnLimpiar);

        String[] columnas = {"codigo", "nombre", "tipo", "salario calculado", "categoría"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column){ 
                return false;
            }
        };
        tablaEmple = new JTable(modeloTabla);
        tablaEmple.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollTabla = new JScrollPane(tablaEmple);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("listado de empleados"));
        btnRegistrar.addActionListener(e -> registrarEmpleado());
        btnBuscar.addActionListener(e -> buscarEmpleado());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
        btnClonar.addActionListener(e -> clonarEmpleado());
        btnComparar.addActionListener(e -> compararEmpleados());
        btnResumen.addActionListener(e -> mostrarResumen());
        JPanel panelNorte = new JPanel(new BorderLayout());
        panelNorte.add(panelFormulario, BorderLayout.CENTER);
        panelNorte.add(panelBotones, BorderLayout.SOUTH);
        add(panelNorte, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);
    }

    private void alternarCampos() {
        boolean esAdmin = cbTipo.getSelectedIndex() == 0;
        txtBonificacion.setEnabled(esAdmin);
        txtDescuento.setEnabled(esAdmin);
        txtHoras.setEnabled(!esAdmin);
        txtTarifa.setEnabled(!esAdmin);
        if(esAdmin) {
            txtHoras.setText(""); txtTarifa.setText("");
        } else {
            txtBonificacion.setText(""); txtDescuento.setText("");
        }
    }
    private void registrarEmpleado() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();
            String strBase = txtSalarioBase.getText().trim();

            if (codigo.isEmpty()||nombre.isEmpty()||strBase.isEmpty()){
                JOptionPane.showMessageDialog(this, "debe completar los campos obligatorios", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (existeCodigo(codigo)){
                JOptionPane.showMessageDialog(this, "el codigo ya exist", "error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double base = Double.parseDouble(strBase);
            if (base < 0){
                throw new NumberFormatException();
            }
            Empleado emp;
            if (cbTipo.getSelectedIndex() == 0){
                double bonificacion = Double.parseDouble(txtBonificacion.getText().trim());
                double descuento = Double.parseDouble(txtDescuento.getText().trim());
                if (bonificacion < 0 || descuento < 0 || descuento > 100){
                    throw new NumberFormatException();
                }
                emp = new EmpleadoAdministrativo(codigo, nombre, base, bonificacion, descuento);
            }
            else{
                int horas = Integer.parseInt(txtHoras.getText().trim());
                double tarifa = Double.parseDouble(txtTarifa.getText().trim());
                if (horas < 0 || tarifa < 0){
                    throw new NumberFormatException();
                }
                emp = new EmpleadoPorHora(codigo, nombre, base, horas, tarifa);
            }

            empleados.add(emp);
            actualizarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "empleado registrado con exito!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "error en los datos numéricos (formatos invalidos o negativos)", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private boolean existeCodigo(String codigo) {
        for (Empleado e: empleados) {
            if (e.getCodigo().equalsIgnoreCase(codigo)) return true;
        }
        return false;
    }
    private void buscarEmpleado() {
        String codigoBuscado = JOptionPane.showInputDialog(this, "ingrese el codigo a buscar:");
        if (codigoBuscado != null && !codigoBuscado.isEmpty()) {
            for (Empleado e : empleados) {
                if (e.getCodigo().equalsIgnoreCase(codigoBuscado)) {
                    JOptionPane.showMessageDialog(this, 
                        e.mostrarInformacion() + "\nSalario Calculado: " + String.format("%.2f", e.calcuSalario()), 
                        "Empleado Encontrado", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            JOptionPane.showMessageDialog(this, "Empleado no encontrado", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
    private void clonarEmpleado() {
        int fila = tablaEmple.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "debe seleccionar un empleado de la tabla para clonar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Empleado original = empleados.get(fila);
        Empleado clon = original.clone(); 
        String nuevoCodigo = JOptionPane.showInputDialog(this, "Ingrese el nuevo codigo unico para el clon:");
        if (nuevoCodigo == null || nuevoCodigo.trim().isEmpty()){
            return;
        }
        if (existeCodigo(nuevoCodigo.trim())) {
            JOptionPane.showMessageDialog(this, "Ese codigo ya se esta usando, clonacion cancelada ", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        clon.setCodigo(nuevoCodigo.trim());
        clon.setNombre("Clon de " + clon.getNombre());
        empleados.add(clon);
        actualizarTabla();
        JOptionPane.showMessageDialog(this, "Clon registrado con exito!");
    }

    private void compararEmpleados() {
        int[] filasSeleccionadas = tablaEmple.getSelectedRows();
        if (filasSeleccionadas.length != 2) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione exactamente 2 empleados en la tabla (use Ctrl+Clic).", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Empleado emp1 = empleados.get(filasSeleccionadas[0]);
        Empleado emp2 = empleados.get(filasSeleccionadas[1]);
        boolean mismaReferencia = (emp1==emp2);
        boolean equivalentes = emp1.getClass().equals(emp2.getClass()) && emp1.getSalarioBase() == emp2.getSalarioBase();
        String mensaje = "comparacion entre: " + emp1.getCodigo() + " y " + emp2.getCodigo() + "\n\n" +
                         "son el mismo objeto (referencia)? " + (mismaReferencia ? "Sí" : "No") + "\n" +
                         "mismo tipo y salario base? " + (equivalentes ? "Sí" : "No");

        JOptionPane.showMessageDialog(this, mensaje, "Resultado de Comparación", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarResumen() {
        if (empleados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros.");
            return;
        }
        double suma = 0;
        Empleado maxEmpleado = empleados.get(0);
        for (Empleado e : empleados) {
            double salario = e.calcuSalario();
            suma += salario;
            if (salario > maxEmpleado.calcuSalario()) {
                maxEmpleado = e;
            }
        }
        double promedio = suma/empleados.size();
        String mensaje = "Empleado con mas salario: " + maxEmpleado.getNombre() + " (€" + String.format("%.2f", maxEmpleado.calcuSalario()) + ")\n" +
                         "Salario promedio global: €" + String.format("%.2f", promedio);
        
        JOptionPane.showMessageDialog(this, mensaje, "estadisticas", JOptionPane.INFORMATION_MESSAGE);
    }
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Empleado e: empleados) {
            String tipo = (e instanceof EmpleadoAdministrativo) ? "administrativo" : "por horas";
            Object[] fila = {
                e.getCodigo(),e.getNombre(),tipo,String.format("%.2f €", e.calcuSalario()),e.obtenerCategoria()
            };
            modeloTabla.addRow(fila);
        }
    }
    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtSalarioBase.setText("");
        txtBonificacion.setText("");
        txtDescuento.setText("");
        txtHoras.setText("");
        txtTarifa.setText("");
        cbTipo.setSelectedIndex(0);
    }
    public Progra02Prueba03(){
        empleados=new ArrayList<>();
        configurarVentana();
        inicializarComponentes();
    }
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(() -> {
            new Progra02Prueba03().setVisible(true);
        });
    }
    
}
