package analizador.sintactico

enum class Producciones(comboTokens: String) {
    /*
        int c;
        char b;
        j = a + b - c;
    */
     DECLARACION_ASIGNACION("%TIPO\\d*%" + "%IDE\\d*%%OPAS\\d*%(%IDE\\d*%|%DIG\\d*%)(%OPAR\\d*%(%IDE\\d*%|%DIG\\d*%))*")
    ,DECLARACION("%TIPO\\d*%%IDE\\d*%(%COMMA\\d*%%IDE\\d*%)*")
    ,ASIGNACION("%IDE\\d*%%OPAS\\d*%(%IDE\\d*%|%DIG\\d*%)(%OPAR\\d*%(%IDE\\d*%|%DIG\\d*%))*")
    ,DEL("%DEL\\d*%")
    ,ERROR_UNEX ("(%.+\\d*%)+")
    ;

    private val text = comboTokens
    fun regex() = this.text
}