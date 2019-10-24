package analizador.lexico;

import java.util.ArrayList;

/*
 * RESUMEN:
 * Esta clase basicamente contiene una lista con todos los tokens,
 * pero existe por la necesidad de que al agregar un token del mismo tipo
 * se les asigne un valor consecutivo.
 */
public class TablaTokens
{
	public ArrayList<Token> tokens;
	private int contador;
		
	public TablaTokens()
	{
		tokens = new ArrayList<Token>();
		contador = 0;
	}
	
	//Metodo para imprimir en consola el contenido de la lista
	public void mostrar()
	{
		for(Token token : tokens)
		{
			System.out.println("Token: "+token.toString());
			System.out.println("Lexema: [ "+token.lexema+" ]");
			System.out.println("Numero de lexema: "+token.numeroLexema + '\n');
		}
	}
	
	public String obtenerLista()
	{
		String cadenaFinal = "";
		
		for(Token token : tokens)
		{
			cadenaFinal += "Token: "+token.toString();
			cadenaFinal += "\nLexema: "+token.lexema;
			cadenaFinal += "\nNumero de lexema: "+token.numeroLexema;
			cadenaFinal += "\n=======================================================\n";
		}
		
		return cadenaFinal;
	}

	public Object[] obtenerSiguienteFila()
	{
		Object[] fila;
		try
		{
			fila = new Object[] {tokens.get(contador).toString() + tokens.get(contador).info, tokens.get(contador).lexema};
		}
		catch (Exception e)
		{
			fila = null;
		}
		contador++;
		return fila;
	}
	
	//Este metodo es la razon de existir de esta clase, pues 
	//le da la funcion lleva un numero consecutivo en tokens
	//del mismo tipo.
	public void add(Token token)
	{
		if(token.defReg == DefinicionRegular.ESPACIO)
			return;
		
		for(DefinicionRegular defReg : DefinicionRegular.values())
		{
			if(token.defReg == defReg)
			{
				token.numeroToken = defReg.contador;
				defReg.contador++;
			}
		}		
		
		tokens.add(token);
	}
	
	public Token get(int i)
	{
		return tokens.get(i);
	}
}
