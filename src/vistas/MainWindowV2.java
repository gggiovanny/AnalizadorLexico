package vistas;

import lexico.Analizador;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class MainWindowV2 {
    public JTextArea txtCadenaIngreso;
    private JButton btnAnalizar;
    private JPanel pnlPrincipal;
    private JButton btnLimpiar;
    private JScrollPane scrollTokens;
    private JTable tablaTokens;
    private JScrollPane scrollErrores;
    private JTable tablaErrores;
    private JButton leyendaButton;
    private JPanel pnlTablas;
    private JPanel pnlBotones;
    private JPanel pnlTexto;
    DefaultTableModel mdlTokens;
    DefaultTableModel mdlErrores;
    final String[] columnNames = {"Lexema", "Token"};

    public MainWindowV2() {

        mdlTokens = new DefaultTableModel(columnNames, 0);
        tablaTokens.setModel(mdlTokens);

        mdlErrores = new DefaultTableModel(columnNames, 0);
        tablaErrores.setModel(mdlErrores);

        btnAnalizar.addActionListener(e -> Analizar(txtCadenaIngreso.getText()));
        btnLimpiar.addActionListener(e -> Limpiar(true));
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
        leyendaButton.addActionListener(e -> EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LeyendaTokens leyendaTokens = new LeyendaTokens();
                    JFrame frameLeyenda = new JFrame();
                    frameLeyenda.setContentPane(leyendaTokens.pnlLeyenda);
                    frameLeyenda.setTitle("Leyenda");
                    frameLeyenda.setBounds(580, 100, 490, 275);
                    frameLeyenda.setVisible(true);
                    frameLeyenda.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }));

    }

    private void Analizar(String codigoAnalizar)
    {
        Limpiar(false);
        Analizador analizador = new Analizador();
        analizador.buscarPatrones(codigoAnalizar, true);

        Object[] filaToken = analizador.tablaSimbolos.obtenerSiguienteFila();
        while (filaToken != null)
        {
            mdlTokens.addRow(filaToken);
            filaToken = analizador.tablaSimbolos.obtenerSiguienteFila();
        }

        Object[] filaError = analizador.tablaErrores.obtenerSiguienteFila();
        while (filaError != null)
        {
            mdlErrores.addRow(filaError);
            filaError = analizador.tablaErrores.obtenerSiguienteFila();
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
                    ImageIcon icon = new ImageIcon("img//analizador_lexico_icon_2.png");
                    MainWindowV2 app = new MainWindowV2();
                    frame.setContentPane(app.pnlPrincipal);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setBounds(100, 100, 490, 450);
                    frame.setIconImage(icon.getImage());
                    frame.setVisible(true);
                    app.txtCadenaIngreso.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
