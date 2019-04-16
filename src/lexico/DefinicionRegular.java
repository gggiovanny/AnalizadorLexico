package lexico;

public enum DefinicionRegular
{
	ESPACIO ("\\s"),
	IDE ("([a-zA-Z]|_)\\w*"),
	DIG ("\\d+"),
	OPAR ("\\+|-|\\*|/");
	
	private final String regex;
	public int contador;
	DefinicionRegular(String regex)
	{
		this.regex = regex;
		this.contador = 1;
	}
	
	public String regex() { return this.regex; }	
}