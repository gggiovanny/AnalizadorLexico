package vistas;

import lexico.Analizador;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainWindow extends JFrame {

	private JPanel contentPane;
	private JTextField txtEntrada;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
				    JDialog.setDefaultLookAndFeelDecorated(true);
				    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
					MainWindow frame = new MainWindow();
					frame.setResizable(false);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Analizar Lexico");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 490, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("C\u00F3digo fuente a evaluar");
		lblNewLabel.setBounds(10, 11, 141, 14);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 144, 454, 256);
		contentPane.add(scrollPane);
		
		txtEntrada = new JTextField();
		txtEntrada.setBounds(10, 36, 454, 30);
		contentPane.add(txtEntrada);
		txtEntrada.setColumns(10);
		
		JTextPane txtSalida = new JTextPane();
		txtSalida.setEditable(false);
		scrollPane.setViewportView(txtSalida);
		txtSalida.setForeground(Color.GREEN);
		txtSalida.setBackground(Color.BLACK);
		
		JLabel lblSalida = new JLabel("Resultado del analizador lexico");
		lblSalida.setBounds(10, 119, 189, 14);
		contentPane.add(lblSalida);
		
		JButton btnLimpiar = new JButton("Limpiar");
		btnLimpiar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				//Limpiar textos
				txtEntrada.setText("");
				txtSalida.setText("");
			}
		});
		btnLimpiar.setBounds(375, 77, 89, 31);
		contentPane.add(btnLimpiar);
		
		JButton btnAnalizar = new JButton("Analizar");
		btnAnalizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Analizador analizador = new Analizador();
				String codigoAnalizar = txtEntrada.getText();
				
				analizador.buscarPatrones(codigoAnalizar, true);
				
				if(analizador.patronEncontrado) {
					txtSalida.setText(analizador.tablaSimbolos.obtenerLista());
				} else {
					txtSalida.setText(analizador.getError());
					//txtSalida.setText(analizador.tablaErrores.obtenerLista());
				}
				
			}
		});
		btnAnalizar.setBounds(276, 77, 89, 31);
		contentPane.add(btnAnalizar);
	}
}
