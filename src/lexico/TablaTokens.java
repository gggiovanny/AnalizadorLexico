package lexico;

import java.util.ArrayList;

public class TablaTokens
{
	private ArrayList<Token> items;
	
	private int contadorIDE;
	private int contadorDIG;
	private int contadorOPAR;
	
	private int [] contadores;
	
	
	public TablaTokens()
	{
		items = new ArrayList<Token>();
		contadorIDE = 0;
		contadorDIG = 0;
		contadorOPAR = 0;
		
		contadores = new int[DefinicionRegular.values().length];
	}
	
	public void mostrar()
	{
		for(Token token : items)
		{
			System.out.println("Token: "+token.toString());
			System.out.println("Lexema: [ "+token.lexema+" ]");
			System.out.println("Numero de lexema: "+token.numeroLexema + '\n');
		}
	}
	
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
		
		items.add(token);
	}
	
	public Token get(int i)
	{
		return items.get(i);
	}
}
