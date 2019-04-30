package lexico;

/*
 * RESUMEN:
 * Esta clase tiene los atributos necesarios para definir a un token que
 * posteriormente se agregara a una tabla de simbolos.
 */
public class Token
{
	protected DefinicionRegular defReg; //la definicion regular que indica el tipo de token
	protected String lexema;			//esto es el contenido en texto del token 
	protected int numeroLexema;			//es la posicion que ocupa el lexema en la cadena de entrada original
	protected int numeroToken;			//es numero que se va incrementando cada vez que se agrega un token del mismo tipo y que permite identificarlos
	
	//constructor para inicializar un token. El numero de token se agrega automatica y consecutivamente en la clase TablaTokens
	public Token(DefinicionRegular defReg, String contenido, int numeroLexema)
	{ 
		this.defReg = defReg;
		this.lexema = contenido;
		this.numeroLexema = numeroLexema;
		this.numeroToken = -1;
	}
	
	//contructor para inicializar a valores "nulos"
	public Token()
	{
		this.defReg = DefinicionRegular.ESPACIO;
		this.lexema = "";
		this.numeroLexema = -1;
		this.numeroToken = -1;
	}

	//permite regresar una texto con el tipo de toke y su valor consecutivo.Ej: IDE1, IDE2, OPREL1, etc.
	public String toString()
	{
		String texto = "NODEFINIDO";
		
		if(this.numeroToken >= 0)
			texto = this.defReg.toString() + numeroToken;
		
		return texto;		
	}
}
