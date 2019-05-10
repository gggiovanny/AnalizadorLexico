package vistas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

public class LeyendaTokens {
    private JTable tablaLeyenda;
    public JPanel pnlLeyenda;
    private JScrollPane scrolltablaLeyenda;
    DefaultTableModel mdlTablaLeyenda;

    public LeyendaTokens()
    {
        String[] nombreColumnas = { "Token", "Significado" };
        Object [][] datos =
            {
                { "OPAR", 	"operador aritmetico (+|-|/|*)" },
                { "OPAS", 		"operador de asignacion (=\")" },
                { "DEL", 		"delimitador" },
                { "IDE", 		"identificador" },
                { "DIG", 		"digito normal o decimal" },
                { "ERROR_IDE", 	"error para cuando se pone un numero al principio de un identificador." },
                { "ERROR_UNEX",	"simbolo inesperado e invalido" }
            };

        mdlTablaLeyenda = new DefaultTableModel(datos, nombreColumnas);
        tablaLeyenda.setModel(mdlTablaLeyenda);
        tablaLeyenda.setRowHeight(25);
        TableColumnModel columnModel = tablaLeyenda.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(300);


    }
}
