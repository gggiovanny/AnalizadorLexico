package compilador

import analizador.lexico.DefinicionRegular
import analizador.lexico.TablaTokens
import analizador.lexico.Token
import analizador.sintactico.Producciones
import analizador.sintactico.SentenceToken
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


    fun compilar(rawTokens: ArrayList<Token>, sentenceTokens: ArrayList<SentenceToken>): Boolean { // Funcion que se llama desde el validador sintactico
        val tablaTriplo = ArrayList<Triplo>()

        var inicio_while: Int = 0
        for (sentenceToken in sentenceTokens) {
            val tokensInSentence: ArrayList<Token> = getTokenListCorrectPosition(sentenceToken.sentence, tablaSimbolos, rawTokens)

            /** GUARDANDO POSICION DEL INICIO DEL DO WHILE */
            if(sentenceToken.tipoProduccion == Producciones.INICIO_DO_WHILE) {
                inicio_while = tablaTriplo.size
            }

            /** ASIGNACIONES A TRIPLO */
            if(sentenceToken.tipoProduccion == Producciones.ASIGNACION_DIG
                    || sentenceToken.tipoProduccion == Producciones.ASIGNACION_TEXT
                    || sentenceToken.tipoProduccion == Producciones.ASIGNACION_CHARVALUE
                    || sentenceToken.tipoProduccion == Producciones.DECLARACION_ASIGNACION_DIG
                    || sentenceToken.tipoProduccion == Producciones.DECLARACION_ASIGNACION_TEXT
                    || sentenceToken.tipoProduccion == Producciones.DECLARACION_ASIGNACION_CHARVALUE
            ) {

                val tokensInSentenceArithOnly: ArrayList<Token> = ArrayList(tokensInSentence.filterIndexed{i, token -> 0 != i && 1 != i })
                val operadoresCount = tokensInSentenceArithOnly.
                        filter { token -> token.defReg == DefinicionRegular.DIG || token.defReg == DefinicionRegular.IDE}
                        .size
                // si el numero de operadores en la aritmetica es mayor a 1
                if(operadoresCount > 1) {

                    tablaTriplo.add(Triplo(
                            datoObjeto = "T1",
                            datoFuente = tokensInSentenceArithOnly.get(0).lexema,
                            operador = DefinicionRegular.OPAS.toString()
                    ))

                    for(i in 1 until tokensInSentenceArithOnly.size step 2) {
                        tablaTriplo.add(Triplo(
                                datoObjeto = "T1",
                                datoFuente = tokensInSentenceArithOnly.get(i+1).lexema,
                                operador = tokensInSentenceArithOnly.get(i).lexema
                        ))
                    }

                    tablaTriplo.add(Triplo(
                            datoObjeto = tokensInSentence.get(0).lexema,
                            datoFuente = "T1",
                            operador = DefinicionRegular.OPAS.toString()
                    ))

                    true
                } else { // cuando solo hay un operador aritmetico, es asignacion simple
                    val IDEQueRecibeValor: Token = tokensInSentence.first { token -> token.defReg == DefinicionRegular.IDE }
                    val operador: Token = tokensInSentence.first { token -> token.defReg == DefinicionRegular.OPAS }
                    val valor: Token = tokensInSentence.first { token -> token.defReg == DefinicionRegular.DIG }

                    tablaTriplo.add(Triplo(
                            datoObjeto = IDEQueRecibeValor.lexema,
                            datoFuente = valor.lexema,
                            operador = operador.lexema
                    ))
                    true
                }



                true
            }

            /** FIN DO WHILE A TRIPLO */
            if(sentenceToken.tipoProduccion == Producciones.FIN_DO_WHILE) {
                val tokensDentroDelParentesis: ArrayList<Token> = ArrayList(tokensInSentence.filterIndexed{i, token ->
                    0 != i &&
                    1 != i &&
                    token.defReg != DefinicionRegular.PARA &&
                    token.defReg != DefinicionRegular.PARB
                })


                tablaTriplo.add(Triplo(
                        datoObjeto = "T1",
                        datoFuente = tokensDentroDelParentesis.get(0).lexema,
                        operador = DefinicionRegular.OPAS.toString()
                ))

                tablaTriplo.add(Triplo(
                        datoObjeto = "T1",
                        datoFuente = tokensDentroDelParentesis.get(2).lexema,
                        operador = tokensDentroDelParentesis.get(1).lexema
                ))


                tablaTriplo.add(Triplo(
                        datoObjeto = "CMP",
                        datoFuente = "TRUE",
                        operador = inicio_while.toString()
                ))

                true
            }





        }


        return false
    }
}