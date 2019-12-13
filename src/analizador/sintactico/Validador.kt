package analizador.sintactico

import analizador.lexico.TablaTokens
import analizador.lexico.Token
import common.Response

class Validador(tablaSimbolos: TablaTokens, tablaErrores: TablaTokens) {
    lateinit var tablaSimbolos: TablaTokens
    lateinit var tablaErrores: TablaTokens

    init {
        this.tablaSimbolos = tablaSimbolos
        this.tablaErrores = tablaErrores
    }

    fun validar(tokens: ArrayList<Token>): Response<ArrayList<Token>> {
        val stringTokens = tokensStringify(tokens)



        return Response(false)
    }

    fun tokensStringify(tokens: ArrayList<Token>): String {
        var tokensCadena = ""

        for (token in tokens)
            tokensCadena += "%${token.defReg}%"

        return tokensCadena
    }

}