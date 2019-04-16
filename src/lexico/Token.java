package lexico;

public class Token
{
	protected DefinicionRegular defReg;
	protected String lexema;
	protected int numeroLexema;	
	protected int numeroToken;
		
	public Token(DefinicionRegular defReg, String contenido, int numeroPalabra)
	{
		this.defReg = defReg;
		this.lexema = contenido;
		this.numeroLexema = numeroPalabra;
		this.numeroToken = -1;
	}
	
	public Token()
	{
		this.defReg = DefinicionRegular.ESPACIO;
		this.lexema = "";
		this.numeroLexema = -1;
		this.numeroToken = -1;
	}

	public String toString()
	{
		String texto = "NODEFINIDO";
		
		if(this.numeroToken >= 0)
			texto = this.defReg.toString() + numeroToken;
		
		return texto;		
	}
}
