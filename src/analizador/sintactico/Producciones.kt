package analizador.sintactico

enum class Producciones(comboTokens: String) {
    /*
        int c;
        char b;
        j = a + b - c;
    */
     DECLARACION_ASIGNACION_DIG("%TIPO\\d*%" + "%IDE\\d*%%OPAS\\d*%(%IDE\\d*%|%DIG\\d*%)(%OPAR\\d*%(%IDE\\d*%|%DIG\\d*%))*")
    ,DECLARACION_ASIGNACION_TEXT("%TIPO\\d*%" + "%IDE\\d*%%OPAS\\d*%(%IDE\\d*%|%TEXT\\d*%)")
    ,DECLARACION_ASIGNACION_CHARVALUE("%TIPO\\d*%" + "%IDE\\d*%%OPAS\\d*%(%IDE\\d*%|%CHARVALUE\\d*%)")
    ,DECLARACION("%TIPO\\d*%%IDE\\d*%(%COMMA\\d*%%IDE\\d*%)*")
    ,ASIGNACION_DIG("%IDE\\d*%%OPAS\\d*%(%IDE\\d*%|%DIG\\d*%)(%OPAR\\d*%(%IDE\\d*%|%DIG\\d*%))*")
    ,ASIGNACION_TEXT("%IDE\\d*%%OPAS\\d*%(%IDE\\d*%|%TEXT\\d*%)")
    ,ASIGNACION_CHARVALUE("%IDE\\d*%%OPAS\\d*%(%IDE\\d*%|%CHARVALUE\\d*%)")
    ,DEL("%DEL\\d*%")
    ,INICIO_DO_WHILE("%DO\\d*%%CORA\\d*%")
    ,FIN_DO_WHILE("%CORB\\d*%%WHILE\\d*%%PARA\\d*%(%IDE\\d*%|%DIG\\d*%)%OPCMP\\d*%(%IDE\\d*%|%DIG\\d*%)%PARB\\d*%")
    ,ERROR_UNEX ("(%.+\\d*%)+")
    ;

    private val text = comboTokens
    fun regex() = this.text
}