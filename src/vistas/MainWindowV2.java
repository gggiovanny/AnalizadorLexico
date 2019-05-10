package vistas;

import lexico.Analizador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MainWindowV2 {
    private JTextField txtCadenaIngreso;
    private JButton btnAnalizar;
    private JLabel lblCadenaIngreso;
    private JPanel pnlPrincipal;
    private JButton btnLimpiar;
    private JScrollPane scrollTokens;
    private JTable tablaTokens;
    private JScrollPane scrollErrores;
    private JTable tablaErrores;
    DefaultTableModel mdlTokens;
    DefaultTableModel mdlErrores;
    final String[] columnNames = {"Lexema", "Token"};

    public MainWindowV2() {

        mdlTokens = new DefaultTableModel(columnNames, 0);
        tablaTokens.setModel(mdlTokens);

        mdlErrores = new DefaultTableModel(columnNames, 0);
        tablaErrores.setModel(mdlErrores);

        btnAnalizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Analizar(txtCadenaIngreso.getText());
            }
        });
        btnLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Limpiar(true);
            }
        });
        txtCadenaIngreso.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int key = e.getKeyCode();

                if( e.isControlDown() && key == KeyEvent.VK_ENTER )
                    Analizar(txtCadenaIngreso.getText());

                if( e.isControlDown() && key == KeyEvent.VK_L)
                {
                    //Limpiar textos
                    Limpiar(true);
                }
            }
        });
    }

    private void Analizar(String codigoAnalizar)
    {

        Limpiar(false);
        Analizador analizador = new Analizador();
        analizador.buscarPatrones(codigoAnalizar, true);

        if(analizador.patronEncontrado)
        {
            Object[] fila = analizador.tablaSimbolos.obtenerSiguienteFila();
            while (fila != null)
            {
                mdlTokens.addRow(fila);
                fila = analizador.tablaSimbolos.obtenerSiguienteFila();
            }
        }
        else
        {
            Object[] fila = analizador.tablaErrores.obtenerSiguienteFila();
            while (fila != null)
            {
                mdlErrores.addRow(fila);
                fila = analizador.tablaErrores.obtenerSiguienteFila();
            }
        }
    }

    private void Limpiar(boolean LimpiarTexto)
    {
        if (LimpiarTexto)
            txtCadenaIngreso.setText("");
        mdlTokens.setRowCount(0);
        mdlErrores.setRowCount(0);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    //Aspecto
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    //Crear frame
                    JFrame frame = new JFrame("Analizador Léxico");
                    frame.setContentPane(new MainWindowV2().pnlPrincipal);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setBounds(100, 100, 490, 450);
                    frame.setVisible(true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
