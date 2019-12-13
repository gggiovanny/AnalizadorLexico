package analizador.lexico;

/*
 * RESUMEN:
 * Esta clase tiene los atributos necesarios para definir a un token que
 * posteriormente se agregara a una tabla de simbolos.
 */
public class Token {
    public DefinicionRegular defReg; //la definicion regular que indica el tipo de token
    public String lexema;            //esto es el contenido en texto del token
    public int numeroLexema;            //es la posicion que ocupa el lexema en la cadena de entrada original
    public int numeroToken;            //es numero que se va incrementando cada vez que se agrega un token del mismo tipo y que permite identificarlos
    public int numeroLinea;
    public int numeroColumna;
    public String info;
    private TipoDato tipoDato;

    //constructor para inicializar un token. El numero de token se agrega automatica y consecutivamente en la clase TablaTokens
    public Token(DefinicionRegular defReg, String contenido, int numeroLexema) {
        this.defReg = defReg;
        this.lexema = contenido;
        this.numeroLexema = numeroLexema;
        this.numeroToken = -1;
        this.numeroLinea = -1;
        this.numeroColumna = -1;
        info = "";
        this.tipoDato = null;
    }

    //contructor para inicializar a valores "nulos"
    public Token() {
        this.defReg = DefinicionRegular.ESPACIO;
        this.lexema = "";
        this.numeroLexema = -1;
        this.numeroToken = -1;
        info = "";
    }

    //para obtener el valor de tipoDato, ya que se puso privado para usar el setter
    public TipoDato getTipoDato() {
        return tipoDato;
    }

    //solo setear el tipo de dato cuando se trate de un token de tipo IDE (identificador o variable).
    public void setTipoDato(TipoDato tipoDato) {
        if (defReg == DefinicionRegular.IDE)
            this.tipoDato = tipoDato;
    }

    //permite regresar una texto con el tipo de token y su valor consecutivo.Ej: IDE1, IDE2, OPREL1, etc.
    public String toString() {
        return this.defReg.toString() + numeroToken;
    }
}
