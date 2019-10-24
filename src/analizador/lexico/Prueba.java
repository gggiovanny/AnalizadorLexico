package analizador.lexico;

import javax.swing.*;
import java.util.ArrayList;

public class Prueba 
{
	private void PruebaGeneral()
	{
		ArrayList<Analizador> lista;
		lista = new ArrayList<>();

		String[] cadenas = {
				"_decim = .5 + 0.14;",
				"var1=var2*var3		;",
				"var1=1var2/var3;",
				"var1=var2-var3;",
				"var1 = var2 +var3;",
				"_num = 5 + var3;",
				"sepa_rado = 21.3 / 324.2     ;",
				"_algo = op * 50;",
				"no-se-puede = 5 + 1;",
				"si_se_puede = var2 / var545 ;",
				"$no_se_puede = var1 + var2;",
				"00no = 3 + 1;",
				"si00 = 3 + 1;",
				"variable = 0num - num0;",
				"suma = -5 + 6",
				"didier se la come"
		};

		Analizador analisis;
		int c = 1;
		for(String cadena : cadenas)
		{
			analisis = new Analizador();
			analisis.buscarPatrones(cadena);
			lista.add(analisis);
			if(analisis.patronEncontrado) {
				System.out.println(c+"\t- O - ACEPTADO: "+cadena);
			}
			else {
				System.out.println(c+"\t- X - RECHAZADO: TOKEN: "+analisis.tablaErrores.tokens.get(0).toString() + " Cadena: "+cadena);
			}
			c++;
		}

		String input = JOptionPane.showInputDialog("Elija una cadena para ver sus detalles:");
		int opcion = Integer.parseInt(input) - 1;
		Analizador analisisMostrar = lista.get(opcion);

		System.out.println("Cadena ingresada: "+analisisMostrar.cadenaAnalizar);

		if(analisisMostrar.patronEncontrado) {
			analisisMostrar.tablaSimbolos.mostrar();
		}
		else {
			analisisMostrar.tablaErrores.mostrar();
		}
	}
	
	public static void main(String[] args)
	{		
		Prueba prueba = new Prueba();
		prueba.PruebaGeneral();		
	}
}