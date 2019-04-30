package lexico;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Prueba 
{
	Analizador analizador;
	
	public Prueba()
	{
		analizador = new Analizador();
	}

	private void ProbarMuchasCadenas()
	{
		ArrayList<Analizador> lista = new ArrayList<Analizador>();
		
		String cadenas[] = {
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
		
		for(String cadena : cadenas)
		{
			Analizador analisis = new Analizador();
			analizador.buscarPatrones(cadena, false);
			if(analizador.patronEncontrado)
				System.out.println("O - ACEPTADO: "+cadena);
			else
				System.out.println("X - RECHAZADO: TOKEN: "+analizador.tablaErrores.tokens.get(0).toString() + " Cadena: "+cadena);
			lista.add(analisis);
		}
		
		String input = JOptionPane.showInputDialog("Elija una cadena para ver sus detalles:");
		int opcion = Integer.parseInt(input);
		Analizador analisisMostrar = lista.get(opcion);
		
		if(analisisMostrar.patronEncontrado)
			analisisMostrar.tablaSimbolos.mostrar();
		else
			analisisMostrar.tablaErrores.mostrar();
	}
	
	private void PruebaGeneral()
	{	
		/*
		analizador.buscarPatrones("suma_3 = num1 + 4.5;", true);
		
		if(analizador.patronEncontrado)
			analizador.tablaSimbolos.mostrar();
		else
			analizador.tablaErrores.mostrar();
		*/
		System.out.println("================ Probando varias cadenas a la vez ================");
		ProbarMuchasCadenas();
	}
	
	public static void main(String[] args)
	{		
		Prueba prueba = new Prueba();
		prueba.PruebaGeneral();		
	}
}