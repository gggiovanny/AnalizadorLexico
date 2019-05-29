package lexico;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* 
 * RESUMEN:
 * Esta es la clase principal y donde se lleva a cabo toda la funcionalidad.
 * Permite comparar definiciones regulares con cadenas de entrada y genera sus tokens
 * respectivos que se anexan a tablaSimbolos.
 */
public class Analizador {
	private Pattern p; // permite designar un patron (regex) a buscar en una cadena
	private Matcher m; // permite mostrar resultados de busqueda de un patron en una cadena
	public TablaTokens tablaSimbolos; // esto es basicamente un ArrayList de tokens con funciones extra (ver la clase)
	public boolean patronEncontrado; // indica si se tuvo exito al encontrar en su totalidad todos los tokens en la
										// cadena de entrada
	public TablaTokens tablaErrores; // tabla donde se guadan los tokens de error
	public String cadenaError;
	public String cadenaAnalizar;

	public Analizador() {
		tablaSimbolos = new TablaTokens();
		tablaErrores = new TablaTokens();
		patronEncontrado = false;
		cadenaError = "";
		cadenaAnalizar = "";
		for (DefinicionRegular defReg : DefinicionRegular.values())
			defReg.contador = 1;
	}

	public String getError() {
		return this.cadenaError;
	}

	private boolean existeEnTabla(Token token, TablaTokens tabla)
	{
		for (Token tokenTabla : tabla.tokens)
		{
			if(token.defReg == tokenTabla.defReg)
			{
				if(token.lexema.equals(tokenTabla.lexema))
					return true;
			}
		}
		return false;
	}

	private boolean esOPERADOR(Token token)
	{

		if(token.defReg == DefinicionRegular.IDE || token.defReg == DefinicionRegular.DIG)
		{
			return true;
		}
		return false;
	}

	private void analisisSintactico(ArrayList<Token> ENTRADA)
	{
		int c  = 0;
		DefinicionRegular valorEsperado;


		if(ENTRADA.size() < c + 1)
			return;
		valorEsperado = DefinicionRegular.IDE;
		if(ENTRADA.get(c).defReg != valorEsperado)
		{
			ENTRADA.get(c).info =  " (ESPERADO " + valorEsperado.toString() + ")";
			if(!ENTRADA.get(c).defReg.name().contains("ERROR"))
				tablaErrores.add(ENTRADA.get(c));
		}
		c++;

		if(ENTRADA.size() < c + 1)
			return;
		valorEsperado = DefinicionRegular.OPAS;
		if(ENTRADA.get(c).defReg != valorEsperado)
		{
			ENTRADA.get(c).info =  " (ESPERADO " + valorEsperado.toString() + ")";
			if(!ENTRADA.get(c).defReg.name().contains("ERROR"))
				tablaErrores.add(ENTRADA.get(c));
		}
		c++;

		if(ENTRADA.size() < c + 1)
			return;
		valorEsperado = DefinicionRegular.OPAS;
		if(!esOPERADOR(ENTRADA.get(c)))
		{
			ENTRADA.get(c).info =  " (ESPERADO IDE|DIG )";
			if(!ENTRADA.get(c).defReg.name().contains("ERROR"))
				tablaErrores.add(ENTRADA.get(c));
		}
		c++;

		if(ENTRADA.size() < c + 1)
			return;
		valorEsperado = DefinicionRegular.OPAR;
		if(ENTRADA.get(c).defReg != valorEsperado)
		{
			ENTRADA.get(c).info =  " (ESPERADO " + valorEsperado.toString() + ")";
			if(!ENTRADA.get(c).defReg.name().contains("ERROR"))
				tablaErrores.add(ENTRADA.get(c));
		}
		c++;

		if(ENTRADA.size() < c + 1)
			return;
		valorEsperado = DefinicionRegular.OPAS;
		if(!esOPERADOR(ENTRADA.get(c)))
		{
			ENTRADA.get(c).info =  " (ESPERADO IDE|DIG )";
			if(!ENTRADA.get(c).defReg.name().contains("ERROR"))
				tablaErrores.add(ENTRADA.get(c));
		}
		c++;

		if(ENTRADA.size() < c + 1)
			return;
		valorEsperado = DefinicionRegular.DEL;
		if(ENTRADA.get(c).defReg != valorEsperado)
		{
			ENTRADA.get(c).info =  " (ESPERADO " + valorEsperado.toString() + ")";
			if(!ENTRADA.get(c).defReg.name().contains("ERROR"))
				tablaErrores.add(ENTRADA.get(c));
		}
		c++;

	}

	public void buscarPatrones(String cadena, boolean mostrarErrores) {
		this.cadenaAnalizar = cadena;

		ArrayList<Token> lsTokens = new ArrayList<Token>();

		Token token = new Token(); // objeto que contendra la informacion de los tokens que se hallen
									// no se usa individualmente, se anexa a tablaTokens
		int contadorLexema = 0;
		boolean saltoLinea = false;
		while (!saltoLinea && cadena.length() > 0) // la busqueda de patrones se detiene cuando se llega a una cadena que no se
									// puede "tokenizar"
		{
			saltoLinea = false;
//			System.out.println(cadena);//se descomenta para visualizar como se va cortando la cadena
			for (DefinicionRegular defReg : DefinicionRegular.values()) // se recorre un arreglo generado por el enum
																		// que contiene las definiciones regulares
			{
				p = Pattern.compile(defReg.regex()); // se designa una expresion regular para buscar coincidencias, que
														// esta guardada en el enum DefinicionRegular
				m = p.matcher(cadena); 	// se inicializa el objeto de busqueda de coincidencias con la expresion regular
										// anterior
				if (m.lookingAt()) // se buscan coincidencias unicamente al principio de la cadena de entrada
				{
					// si hay coincidencia, se define un nuevo token del tipo de la definicion
					// regular que coincidio con la cadena
					String lexema = m.group();
					token = new Token(defReg, lexema, contadorLexema);

					if (token.defReg != DefinicionRegular.ESPACIO)
						lsTokens.add(token); // se agrega indistintamente a la lista para el analis sintactico

					if (token.defReg.name().contains("ERROR")) {
						if(!existeEnTabla(token, tablaErrores))
						{
							tablaErrores.add(token); // se agrega el nuevo token como error
							tablaSimbolos.add(token);
						}
						cadena = cadena.substring(m.end()); // se corta de la cadena de entrada la seccion de la cadena
						// que si coincidió
					} else {
						if(!existeEnTabla(token, tablaSimbolos))
						{
							// se agrega el token nuevo a la tabla simbolos, donde se le asigna su
							// numero consecutivo automaticamente
							tablaSimbolos.add(token);
						}

						cadena = cadena.substring(m.end()); // se corta de la cadena de entrada la seccion de la cadena
															// que si coincidió
					}

					break;
				}
			}
			if (token.defReg != DefinicionRegular.ESPACIO) // No se toma en cuenta como lexema los espacios
				contadorLexema++;

			if(cadena.length() > 0)
				if(cadena.charAt(0) == '\n' )
					saltoLinea = true;

		}

		analisisSintactico(lsTokens);
		lsTokens = new ArrayList<Token>();

		if (!cadena.isEmpty()) {
			buscarPatrones(cadena, false);
		}

	}
}
