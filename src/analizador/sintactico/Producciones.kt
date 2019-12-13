package analizador.sintactico

enum class Producciones(comboTokens: String) {
    /*
        int c;
        char b;
        j = a + b - c;
    */
    DECLARACION_ASIGNACION("%TIPO%" + Producciones.ASIGNACION.toString())
    ,
    DECLARACION("%TIPO%%IDE%")
    ,
    ASIGNACION("%IDE%%OPAS%%IDE%(%OPAR%%IDE%)*")
    ;

    val text = comboTokens
    override fun toString(): String {
        return this.text
    }

}