package lexico;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 
 * RESUMEN:
 * Esta es la clase principal y donde se lleva a cabo toda la funcionalidad.
 * Permite comparar definiciones regulares con cadenas de entrada y genera sus tokens
 * respectivos que se anexan a tablaSimbolos.
 */
public class Analizador
{		
	private Pattern p;	//permite designar un patron (regex) a buscar en una cadena
	private Matcher m;	//permite mostrar resultados de busqueda de un patron en una cadena
	public TablaTokens tablaSimbolos; 	//esto es basicamente un ArrayList de tokens con funciones extra (ver la clase)
	public boolean patronEncontrado;	//indica si se tuvo exito al encontrar en su totalidad todos los tokens en la cadena de entrada 
	public TablaTokens tablaErrores;	//tabla donde se guadan los tokens de error
	
	public Analizador()
	{		
		tablaSimbolos = new TablaTokens();
		tablaErrores =  new TablaTokens();
		patronEncontrado = false;
		for(DefinicionRegular defReg : DefinicionRegular.values())
			defReg.contador = 1;
	}
	
	public void buscarPatrones(String cadena, boolean mostrarErrores)
	{	
		Token token = new Token(); 	//objeto que contendra la informacion de los tokens que se hallen
									//no se usa individualmente, se anexa a tablaTokens
		int contadorLexema = 0;
		patronEncontrado = true;
		while(patronEncontrado) //la busqueda de patrones se detiene cuando se llega a una cadena que no se puede "tokenizar"
		{
			patronEncontrado = false;
//			System.out.println(cadena);//se descomenta para visualizar como se va cortando la cadena
			for(DefinicionRegular defReg : DefinicionRegular.values())	//se recorre un arreglo generado el enum que contiene las definiciones regulares
			{
				p = Pattern.compile(defReg.regex()); //se designa una expresion regular para buscar coincidencias, que esta guardada en el enum DefinicionRegular
				m = p.matcher(cadena);	//se inicializa el objeto de busqueda de coincidencias con la expresion regular anterior
				if(m.lookingAt()) 		//se buscan coincidencias unicamente al principio de la cadena de entrada
				{
					//si hay coincidencia, se define un nuevo token del tipo de la definicion regular que coincidio con la cadena
					token = new Token(defReg, m.group(), contadorLexema);
					
					if(token.defReg.name().contains("ERROR"))
					{
//						System.out.println("ERROR!!! Token: "+token.defReg.name()+" Lexema: ["+token.lexema+"]\n");
						tablaErrores.add(token);	//se agrega el nuevo token como error
						patronEncontrado = false;
					}
					else
					{
						tablaSimbolos.add(token); //se agrega el token nuevo a la tabla simbolos, donde se le asigna su numero consecutivo automaticamente	
						patronEncontrado = true;						
						cadena = cadena.substring(m.end()); //se corta de la cadena de entrada la seccion de la cadena que si coincidió
					}
						
					break;
				}
			}
			if(token.defReg != DefinicionRegular.ESPACIO) //No se toma en cuenta como lexema los espacios
				contadorLexema++;
			
			
			//si algun texto de la cadena no se corresponde con ningun token, se interumpe la busqueda y se arroja un error.
			if(mostrarErrores && !patronEncontrado && !cadena.isEmpty())
				System.out.println("ERROR!!! Simbolo inesperado en lexema "+contadorLexema+": ["+cadena+"]");
			
		
				
		}
		
		//si se termino de recorrer la cadena y por ello ésta quedo vacia, se encontraron todos los patrones
		if(cadena.isEmpty())
			patronEncontrado = true;
	}
	
	private void ProbarMuchasCadenas()
	{
		
		
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
			Analizador analizador = new Analizador();
			analizador.buscarPatrones(cadena, false);
			if(analizador.patronEncontrado)
				System.out.println("O - ACEPTADO: "+cadena);
			else
				System.out.println("X - RECHAZADO: TOKEN: "+analizador.tablaErrores.tokens.get(0).toString() + " Cadena: "+cadena);
		}

	}
	
	private void PruebaGeneral()
	{		
		buscarPatrones("suma_3 = num1 + 4.5;", true);
		
		if(patronEncontrado)
			tablaSimbolos.mostrar();
		else
			tablaErrores.mostrar();
		
		System.out.println("================ Probando varias cadenas a la vez ================");
		ProbarMuchasCadenas();
	}
	
	public static void main(String[] args)
	{
		
		Analizador analizador = new Analizador();
		analizador.PruebaGeneral();
		
	}
}
