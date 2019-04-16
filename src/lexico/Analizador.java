package lexico;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analizador
{		
	Pattern p;
	Matcher m;
	TablaTokens tabla;
	
	public Analizador()
	{		
		tabla = new TablaTokens();
	}
	
	public void buscarPatrones(String cadena)
	{	
		Token token = new Token();
		int i = 0;
		boolean encontrado = true;
		while(encontrado)
		{
			encontrado = false;
			//System.out.println(cadena);
			for(DefinicionRegular defReg : DefinicionRegular.values())
			{
				p = Pattern.compile(defReg.regex());
				m = p.matcher(cadena);
				if(m.lookingAt())
				{
					token = new Token(defReg, m.group(), i);
					tabla.add(token);
					
					cadena = cadena.substring(m.end());
					encontrado = true;
					break;
				}
			}
			if(token.defReg != DefinicionRegular.ESPACIO)
				i++;
		}
		tabla.mostrar();	
	}
	
	
	public static void main(String[] args)
	{
		Analizador analizador = new Analizador();
		analizador.buscarPatrones("var00/*var01 var04 * 09 45");
		
		
		
	}
}
