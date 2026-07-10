import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Esta clase denominada VentanaPrincipal define una interfaz grafica con un
 * formulario y los botones Crear, Leer, Actualizar y Eliminar (CRUD) que operan
 * sobre un archivo de estudiantes a traves de la clase GestorArchivo. Los
 * estudiantes se muestran en una tabla.
 * @version 1.0/2026
 */
public class VentanaPrincipal extends JFrame implements ActionListener, ListSelectionListener {

    Container contenedor;
    JLabel etId, etNombre, etPrograma, etEdad, mensaje;
    JTextField campoId, campoNombre, campoPrograma, campoEdad;
    JButton crear, leer, actualizar, eliminar, limpiar;
    JTable tabla;
    DefaultTableModel modelo;
    JScrollPane scroll;

    GestorArchivo gestor; // Administrador de las operaciones sobre el archivo

    public VentanaPrincipal() {
        gestor = new GestorArchivo("estudiantes.txt");
        inicio();
        setTitle("CRUD de Estudiantes - Archivo");
        setSize(640, 470);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void inicio() {
        contenedor = getContentPane();
        contenedor.setLayout(null);

        etId = new JLabel("ID:");
        etId.setBounds(20, 20, 80, 23);
        campoId = new JTextField();
        campoId.setBounds(100, 20, 180, 23);

        etNombre = new JLabel("Nombre:");
        etNombre.setBounds(20, 50, 80, 23);
        campoNombre = new JTextField();
        campoNombre.setBounds(100, 50, 180, 23);

        etPrograma = new JLabel("Programa:");
        etPrograma.setBounds(300, 20, 80, 23);
        campoPrograma = new JTextField();
        campoPrograma.setBounds(390, 20, 220, 23);

        etEdad = new JLabel("Edad:");
        etEdad.setBounds(300, 50, 80, 23);
        campoEdad = new JTextField();
        campoEdad.setBounds(390, 50, 220, 23);

        crear = new JButton("Crear");
        crear.setBounds(20, 90, 110, 28);
        crear.addActionListener(this);

        leer = new JButton("Leer");
        leer.setBounds(140, 90, 110, 28);
        leer.addActionListener(this);

        actualizar = new JButton("Actualizar");
        actualizar.setBounds(260, 90, 110, 28);
        actualizar.addActionListener(this);

        eliminar = new JButton("Eliminar");
        eliminar.setBounds(380, 90, 110, 28);
        eliminar.addActionListener(this);

        limpiar = new JButton("Limpiar");
        limpiar.setBounds(500, 90, 110, 28);
        limpiar.addActionListener(this);

        mensaje = new JLabel(" ");
        mensaje.setForeground(new Color(0, 100, 0));
        mensaje.setBounds(20, 125, 590, 23);

        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nombre");
        modelo.addColumn("Programa");
        modelo.addColumn("Edad");
        tabla = new JTable(modelo);
        scroll = new JScrollPane(tabla);
        scroll.setBounds(20, 155, 590, 265);

        // Al seleccionar una fila, se cargan sus datos en el formulario
        tabla.getSelectionModel().addListSelectionListener(this);

        contenedor.add(etId);
        contenedor.add(campoId);
        contenedor.add(etNombre);
        contenedor.add(campoNombre);
        contenedor.add(etPrograma);
        contenedor.add(campoPrograma);
        contenedor.add(etEdad);
        contenedor.add(campoEdad);
        contenedor.add(crear);
        contenedor.add(leer);
        contenedor.add(actualizar);
        contenedor.add(eliminar);
        contenedor.add(limpiar);
        contenedor.add(mensaje);
        contenedor.add(scroll);
    }

    /**
     * Carga en el formulario los datos de la fila seleccionada en la tabla.
     */
    @Override
    public void valueChanged(ListSelectionEvent evento) {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            campoId.setText(modelo.getValueAt(fila, 0).toString());
            campoNombre.setText(modelo.getValueAt(fila, 1).toString());
            campoPrograma.setText(modelo.getValueAt(fila, 2).toString());
            campoEdad.setText(modelo.getValueAt(fila, 3).toString());
        }
    }

    /**
     * Refresca la tabla con los estudiantes almacenados en el archivo.
     */
    private void refrescarTabla() throws IOException {
        modelo.setRowCount(0);
        ArrayList<Estudiante> lista = gestor.leer();
        for (int i = 0; i < lista.size(); i++) {
            Estudiante e = lista.get(i);
            modelo.addRow(new Object[]{e.id, e.nombre, e.programa, e.edad});
        }
    }

    private void limpiarCampos() {
        campoId.setText("");
        campoNombre.setText("");
        campoPrograma.setText("");
        campoEdad.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        try {
            mensaje.setForeground(new Color(0, 100, 0));
            if (evento.getSource() == crear) {
                Estudiante e = new Estudiante(
                        Integer.parseInt(campoId.getText()),
                        campoNombre.getText(),
                        campoPrograma.getText(),
                        Integer.parseInt(campoEdad.getText()));
                gestor.crear(e);
                refrescarTabla();
                mensaje.setText("Estudiante creado correctamente.");
                limpiarCampos();
            } else if (evento.getSource() == leer) {
                refrescarTabla();
                mensaje.setText("Se leyeron los estudiantes del archivo.");
            } else if (evento.getSource() == actualizar) {
                Estudiante e = new Estudiante(
                        Integer.parseInt(campoId.getText()),
                        campoNombre.getText(),
                        campoPrograma.getText(),
                        Integer.parseInt(campoEdad.getText()));
                if (gestor.actualizar(e)) {
                    mensaje.setText("Estudiante actualizado correctamente.");
                } else {
                    mensaje.setText("No se encontro un estudiante con ese ID.");
                }
                refrescarTabla();
                limpiarCampos();
            } else if (evento.getSource() == eliminar) {
                int id = Integer.parseInt(campoId.getText());
                if (gestor.eliminar(id)) {
                    mensaje.setText("Estudiante eliminado correctamente.");
                } else {
                    mensaje.setText("No se encontro un estudiante con ese ID.");
                }
                refrescarTabla();
                limpiarCampos();
            } else if (evento.getSource() == limpiar) {
                limpiarCampos();
                mensaje.setText(" ");
            }
        } catch (NumberFormatException e) {
            mensaje.setForeground(Color.RED);
            mensaje.setText("El ID y la edad deben ser numeros enteros.");
        } catch (IOException e) {
            mensaje.setForeground(Color.RED);
            mensaje.setText("Error al acceder al archivo.");
        }
    }
}
