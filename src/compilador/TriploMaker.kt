package compilador

import analizador.lexico.TablaTokens
import analizador.lexico.Token
import analizador.sintactico.Producciones
import analizador.sintactico.TokenSentence
import analizador.sintactico.getTokenListCorrectPosition
import java.util.regex.Matcher
import java.util.regex.Pattern

class TriploMaker(tablaSimbolos: TablaTokens, tablaErrores: TablaTokens, listaErrores: ArrayList<String>) {
    private lateinit var tablaSimbolos: TablaTokens // tabla de simbolos obtenida del analizador lexico
    private lateinit var tablaErrores: TablaTokens // tabla de errores obtenida del analizador lexico
    private lateinit var listaErrores: ArrayList<String>


    private lateinit var p: Pattern // permite designar un patron (regex) a buscar en una cadena
    private lateinit var m: Matcher // permite mostrar resultados de busqueda de un patron en una cadena

    init {
        this.tablaSimbolos = tablaSimbolos
        this.tablaErrores = tablaErrores
        this.listaErrores = listaErrores
    }


    fun compilar(rawTokens: ArrayList<Token>, tokenSentences: ArrayList<TokenSentence>): Boolean { // Funcion que se llama desde el validador sintactico
        val tablaTriplo = ArrayList<Triplo>()


        for (tokenSentence in tokenSentences) {
            val sentenceTokens: ArrayList<Token> = getTokenListCorrectPosition(tokenSentence.sentence, tablaSimbolos, rawTokens)

            when(tokenSentence.tipoProduccion) {
                Producciones.DECLARACION -> {
                    tablaTriplo.add(Triplo(
                            datoObjeto = "",
                            datoFuente = "",
                            operador = ""
                    ))
                }
            }


        }


        return false
    }
}