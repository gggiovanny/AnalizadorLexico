package lexico;
/*
 * RESUMEN:
 * Este enumerador permite identificar cada tipo de token segun su definicion regular,
 * asi como tambien contiene su expresion regular asociada y un contador que permite
 * asignarle un numero consecutivo a cada token distinto del mismo tipo.
 */
public enum DefinicionRegular
{
	//Estos son los items del enumerador
	ESPACIO ("\\s"),
	IDE ("([a-zA-Z]|_)\\w*"),
	DIG ("\\s\\d+\\s"),
	OPAR ("\\+|-|\\*|/");
	
	/* 
	 * Cabe resaltar que las propiedades que se ven a continuacion aplican
	 * a individualmente a cada uno de los items del enumerador (los de arriba),
	 * por ello el atributo 'contador' tiene sentido, ya que pertenece a cada
	 * definicion regular de manera individual.
	 */
	
	private final String regex;
	public int contador;
	DefinicionRegular(String regex)
	{
		this.regex = regex;
		this.contador = 1;
	}
	
	public String regex() { return this.regex; }	
}