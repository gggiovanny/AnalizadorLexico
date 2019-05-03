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
	ERROR_IDE("\\d+([a-zA-Z]|_)+"),		//error para cuando se pone un numero al principio de un identificador. Ej: 1var, 4num.
	ESPACIO ("\\s"),					//espacios de cualquier tipo, incluso tabuladores y saltos de linea
	OPAR ("\\+|-|\\*|/"),				//operador aritmetico (+|-|/|*)
	OPAS ("="),							//operador de asignacion (=)
	DEL (";"),							//delimitador
	IDE ("([a-zA-Z]|_)\\w*"),			//identificador
	DIG ("(\\d*\\.\\d+)|\\d+"),			//digito normal o decimal
	ERROR_UNEX (".+");					//simbolo inesperado e invalido
	
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