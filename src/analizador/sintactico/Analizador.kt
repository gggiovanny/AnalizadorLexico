package analizador.sintactico

import analizador.lexico.DefinicionRegular
import analizador.lexico.Token

class Analizador {

    // Declarando producciones terminales
    val tipo = ChomskyProduction("tdato").apply { valueTerminal = DefinicionRegular.TIPO }
    val variable = ChomskyProduction("variable").apply { valueTerminal = DefinicionRegular.IDE }
    val comma = ChomskyProduction("comma").apply { valueTerminal = DefinicionRegular.COMMA }

    //Declarando producciones no terminales
    val COMMA_VARIABLE = ChomskyProduction("COMMA_VARIABLE").apply {
        leftNonTerminal = comma
        rightNonTerminal = variable
    }

    val ARGS = ChomskyProduction("DECLARACION_ARGS").apply {
        leftNonTerminal = variable
        rightNonTerminal = COMMA_VARIABLE
    }

    val DECLARACION = ChomskyProduction("DECLARACION").apply {
        leftNonTerminal = tipo
        rightNonTerminal = ARGS
    }

    val DECLARACION2 = ChomskyProduction("DECLARACION").apply {
        leftNonTerminal = tipo
        rightNonTerminal = variable
    }

    val COMMA_VARIABLE2 = ChomskyProduction("COMMA_VARIABLE").apply {
        leftNonTerminal = comma
        rightNonTerminal = ARGS
    }

    // Agrupando todos los terminales
    val terminals = ArrayList<ChomskyProduction>().apply {
        add(tipo)
        add(variable)
        add(comma)
    }

    // Agrupando todos los no terminales
    val nonTerminals = ArrayList<ChomskyProduction>().apply {
        add(COMMA_VARIABLE)
        add(ARGS)
        add(DECLARACION)
        add(DECLARACION2)
        add(COMMA_VARIABLE2)
    }


    private fun getMatchedTerminals(tokens: ArrayList<Token>): ArrayList<ChomskyProduction?>? {
        val matchedTerminals = ArrayList<ChomskyProduction?>()

        var tokenMatchedOnce = false

        for (token in tokens) {

            for (production in terminals) {
                tokenMatchedOnce = false
                if (token.defReg == production.valueTerminal) {
                    matchedTerminals.add(production)
                    tokenMatchedOnce = true
                    break //sale del for
                }
            }
            if (!tokenMatchedOnce) {
                //Si el token no coincidio con ningun terminal definido en la gramatica...
                return null
            }

        }

        return matchedTerminals
    }

    private fun matchNonTerminalRecurse(leftProd: ChomskyProduction, rightProd: ChomskyProduction): Boolean { //ver que regresar


        for (aNonTerminal in nonTerminals) {
            if (leftProd.name == aNonTerminal.leftNonTerminal!!.name && rightProd.name == aNonTerminal.rightNonTerminal!!.name) {
                // si hay match...

            }
        }
    }

    fun analizar(tokens: ArrayList<Token>) {

        val matchedTerminals = getMatchedTerminals(tokens)

        if (matchedTerminals.isNullOrEmpty()) {
            //Error de sintaxis
            //TODO("Mostrar error en la tabla de errores")
            throw Exception("Error sintactico")
        }

        for ((i, terminal) in matchedTerminals.withIndex()) {
            val nextTerminal = matchedTerminals[i + 1] ?: break

            matchNonTerminalRecurse(terminal ?: break, nextTerminal)
        }


    }


}