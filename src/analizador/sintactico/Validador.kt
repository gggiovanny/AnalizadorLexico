package analizador.sintactico

import analizador.lexico.DefinicionRegular
import analizador.lexico.TablaTokens
import analizador.lexico.TipoDato
import analizador.lexico.Token
import common.Response
import compilador.TriploMaker
import java.util.regex.Matcher
import java.util.regex.Pattern

class Validador(tablaSimbolos: TablaTokens, tablaErrores: TablaTokens, listaErrores: ArrayList<String>) {
    private lateinit var tablaSimbolos: TablaTokens // tabla de simbolos obtenida del analizador lexico
    private lateinit var tablaErrores: TablaTokens // tabla de errores obtenida del analizador lexico
    private lateinit var listaErrores: ArrayList<String>
    private lateinit var compilador: TriploMaker

    private lateinit var p: Pattern // permite designar un patron (regex) a buscar en una cadena
    private lateinit var m: Matcher // permite mostrar resultados de busqueda de un patron en una cadena

    init {
        this.tablaSimbolos = tablaSimbolos
        this.tablaErrores = tablaErrores
        this.listaErrores = listaErrores
        this.compilador = TriploMaker(tablaSimbolos, tablaErrores, listaErrores)
    }

    fun validar(rawTokens: ArrayList<Token>): Response<ArrayList<Token>> {
        var stringTokens = tokensStringify(rawTokens)
        val lsTokenSentences = ArrayList<TokenSentence>()

        while (stringTokens.length > 0) {
            for (produccion in Producciones.values()) { // se recorren una por una las expresiones regulares de las producciones
                p = Pattern.compile(produccion.regex())
                m = p.matcher(stringTokens)

                if (m.lookingAt()) { // se buscan coincidencias unicamente al principio de la cadena de entrada
                    val stringTokenSentence: String = m.group()
                    if(produccion != Producciones.DEL)
                        lsTokenSentences.add(TokenSentence(stringTokenSentence, produccion)) // agregando todas las sentencias a una lista

                    // cuando se encuentra una declaracion, se detecta que tipo de dato es y se le pone a los
                    // identificadores que abarca la declaracion en la tabla de simbolos global
                    if(produccion == Producciones.DECLARACION || produccion == Producciones.DECLARACION_ASIGNACION_DIG) {
                        // obteniendo una lista de tokens a partir del string de tokens
                        val produccionTokens: ArrayList<Token> = getTokenList(stringTokenSentence, tablaSimbolos)
                        // obteniendo el tipo de dato a partir del lexema primer token de la lista (que es un token TIPO)
                        val tipoDato: TipoDato? = getTipoByText(produccionTokens.first().lexema)

                        // poniendole el tipo de dato hallado a todos los tokens de tipo IDE de la declaracion
                        produccionTokens.forEach { token ->
                            if (token.defReg == DefinicionRegular.IDE) {
                                val indiceAEditar = tablaSimbolos.tokens.indexOf(token)
                                token.tipoDato = tipoDato
                                tablaSimbolos.tokens.set(indiceAEditar, token)
                            }
                        }
                    }

                    // buscando errores en asignaciones (incompatibilidad de tipos y variable no declarada)
                    if(produccion == Producciones.ASIGNACION_DIG
                            || produccion == Producciones.DECLARACION_ASIGNACION_DIG
                            || produccion == Producciones.FIN_DO_WHILE
                            || produccion == Producciones.ASIGNACION_CHARVALUE
                            || produccion == Producciones.ASIGNACION_TEXT
                    ) {
                        // obteniendo una lista de tokens a partir del string de tokens
                        val produccionTokens: ArrayList<Token> = getTokenListCorrectPosition(stringTokenSentence, tablaSimbolos, rawTokens)

                        /** incompatibilidad de tipos */
                        // obteniendo el tipo de dato del primer identificador IDE en aparecer en la declaracion
                        val tipoDatoEsperado: TipoDato? = produccionTokens.first { token -> token.defReg == DefinicionRegular.IDE }.tipoDato
                        if(tipoDatoEsperado != null) {
                            // verificando que todos los tokens en la declaracion sean del mismo tipo de dato
                            produccionTokens.forEach { token ->
                                if(token.defReg == DefinicionRegular.IDE
                                        || token.defReg == DefinicionRegular.DIG
                                        || token.defReg == DefinicionRegular.CHARVALUE
                                        || token.defReg == DefinicionRegular.TEXT
                                ) {
                                    if(token.tipoDato == TipoDato.INDEFINIDO)
                                        listaErrores.add("Variable no declarada '${token.lexema}' en la linea ${token.numeroLinea} columna ${token.numeroColumna}.")

                                    if (token.tipoDato != tipoDatoEsperado)
                                        listaErrores.add("Error! Incompatibilidad de tipos en el lexema '${token.lexema}' en la linea ${token.numeroLinea} columna ${token.numeroColumna}. Se esperaba el tipo ${tipoDatoEsperado}.")
                                }


                            }
                        }
                    }

                    if(produccion == Producciones.ERROR_UNEX) {
                        val produccionTokens: ArrayList<Token> = getTokenListCorrectPosition(stringTokenSentence, tablaSimbolos, rawTokens)

                        listaErrores.add("Error! Expresion no valida en la linea ${produccionTokens.first().numeroLinea} columna ${produccionTokens.first().numeroColumna}.")
                    }


                    stringTokens = stringTokens.substring(m.end()) // se corta de la cadena de entrada la seccion de la cadena que hizo match
                    break
                }
            }
        }

        /** */
        compilador.compilar(rawTokens, lsTokenSentences)
        /** */



        return Response(false)
    }







}

// convierte a string la lista de tokens proporcionada
fun tokensStringify(tokens: ArrayList<Token>): String {
    var tokensCadena = ""

    for (token in tokens)
        tokensCadena += "%${token}%"

    return tokensCadena
}
fun CharSequence.splitIgnoreEmpty(vararg delimiters: String): List<String> =
    this.split(*delimiters).filter {
        it.isNotEmpty()
    }

// devuelve una lista de tokens partir de una cadena. Los datos de posicion pueden no estar correctos.
fun getTokenList(stringTokens: String, tablaTokens: TablaTokens): ArrayList<Token> {
    val tokens = ArrayList<Token>()

    // dividiendo la sentencia en una arreglo de los nombres de los tokens
    stringTokens.splitIgnoreEmpty("%").forEach { stringToken ->
        // hallando su equivalente objeto en la tabla de simbolos
        val token: Token = tablaTokens.getToken(stringToken)
        tokens.add(token)
    }
    return tokens
}

// devuelve una lista de tokens con sus datos de posicion correctos a partir de una cadena
fun getTokenListCorrectPosition(stringTokens: String, tablaTokens: TablaTokens, rawTokens: ArrayList<Token>): ArrayList<Token> {
    val subTokens: ArrayList<Token> = getTokenList(stringTokens, tablaTokens)

    //obteniendo numero correcto de linea y columna a partir de la lista de tokens completa
    var offset = 0
    val maxoffset = rawTokens.size - subTokens.size
    while(offset <= maxoffset) {
        var countMatched = 0
        for (i in subTokens.indices) {
            if(rawTokens[i+offset].toString() == subTokens[i].toString()) {
                subTokens[i].numeroLinea = rawTokens[i+offset].numeroLinea
                subTokens[i].numeroColumna = rawTokens[i+offset].numeroColumna
                countMatched++
            }
        }
        if(countMatched == subTokens.size) {
            break
        } else {
            offset++
        }
    }

    return subTokens
}

fun getTipoByText(textTipo: String): TipoDato? =
        TipoDato.values().find { tipoDato ->
            tipoDato.text == textTipo
        }