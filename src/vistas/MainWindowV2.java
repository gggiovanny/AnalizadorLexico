package vistas;

import analizador.lexico.Analizador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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
    private JPanel pnlErrores;
    private JTextPane txtpErrores;
    private JScrollPane scrollTextoIngreso;
    private JPanel pnlGLC;
    private JButton btnTest;
    private JScrollPane scrollGLC;
    DefaultTableModel mdlTokens;
    DefaultTableModel mdlErrores;
    final String[] columnNamesSimbolos = {"Token", "Lexema", "Tipo"};
    final String[] columnNamesErrores = {"Token", "Lexema"};
    JFrame frmMain;
    StyledDocument doc;
    Style style;

    public MainWindowV2() {

        mdlTokens = new DefaultTableModel(columnNamesSimbolos, 0);
        tablaTokens.setModel(mdlTokens);

        mdlErrores = new DefaultTableModel(columnNamesErrores, 0);
        tablaErrores.setModel(mdlErrores);

        doc = txtpErrores.getStyledDocument();
        style = txtpErrores.addStyle("Estilo", null);

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

    private void agregarError(String textoError) {
        //configurando color del texto
        StyleConstants.setForeground(style, Color.red);
        try {
            doc.insertString(doc.getLength(), textoError+"\n", style);
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }

    private void agregarLabels(String texto)
    {
        JLabel lbl = new JLabel();
        lbl.setText(texto + " ");
        pnlGLC.add(lbl);
        pnlPrincipal.revalidate();
        pnlPrincipal.repaint();
    }

    private void Analizar(String codigoAnalizar)
    {
        if(codigoAnalizar.isEmpty())
            return;

        Limpiar(false);
        Analizador analizador = new Analizador();
        analizador.buscarPatrones(codigoAnalizar);

        Object[] filaToken = analizador.tablaSimbolos.obtenerSiguienteFilaConTipo();
        while (filaToken != null)
        {
            mdlTokens.addRow(filaToken);
            filaToken = analizador.tablaSimbolos.obtenerSiguienteFilaConTipo();
        }

        Object[] filaError = analizador.tablaErrores.obtenerSiguienteFila();
        while (filaError != null)
        {
            mdlErrores.addRow(filaError);
            filaError = analizador.tablaErrores.obtenerSiguienteFila();
        }

        for (String error : analizador.listaErrores) {
            agregarError(error);
        }

        


    }

    private void Limpiar(boolean LimpiarTexto)
    {
        if (LimpiarTexto)
            txtCadenaIngreso.setText("");
        mdlTokens.setRowCount(0);
        mdlErrores.setRowCount(0);
        txtpErrores.setText("");
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
                    MainWindowV2 app = new MainWindowV2();
                    app.frmMain = new JFrame("Analizador Léxico");
                    ImageIcon icon = new ImageIcon("img//analizador_lexico_icon_2.png");
                    app.frmMain.setContentPane(app.pnlPrincipal);
                    app.frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    app.frmMain.setBounds(100, 100, 700, 450);
                    app.frmMain.setIconImage(icon.getImage());
                    app.frmMain.setVisible(true);
                    app.txtCadenaIngreso.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
