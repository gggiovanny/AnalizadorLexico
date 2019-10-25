package analizador.sintactico

import analizador.lexico.DefinicionRegular
import analizador.lexico.Token

class Analizador {

    // Declarando producciones terminales
    val tipo = ChomskyProduction("tdato").apply { setValueTerminal(DefinicionRegular.TIPO) }
    val variable = ChomskyProduction("variable").apply { setValueTerminal(DefinicionRegular.IDE) }
    val comma = ChomskyProduction("comma").apply { setValueTerminal(DefinicionRegular.COMMA) }

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
    val terminals
        get() = ArrayList<ChomskyProduction>().apply {
            add(tipo.copy())
            add(variable.copy())
            add(comma.copy())
    }

    // Agrupando todos los no terminales
    val nonTerminals
        get() = ArrayList<ChomskyProduction>().apply {
            add(COMMA_VARIABLE.copy())
            add(ARGS.copy())
            add(DECLARACION.copy())
            add(DECLARACION2.copy())
            add(COMMA_VARIABLE2.copy())
    }

    // Agrupando todas las iniciales
    val startingProductions = ArrayList<ChomskyProduction>().apply {
        add(DECLARACION)
        add(DECLARACION)
    }

    private fun isStartingProduction(queryProduction: ChomskyProduction): Boolean = startingProductions.find { startingProduction -> startingProduction.name == queryProduction.name } != null


    private fun getMatchedTerminals(tokens: ArrayList<Token>): ArrayList<ChomskyProduction>? {
        val matchedTerminals = ArrayList<ChomskyProduction>()

        var tokenMatchedOnce = false

        for (token in tokens) {

            for (production in terminals) {
                tokenMatchedOnce = false
                if (token.defReg == production.getValueTerminal()) {
                    matchedTerminals.add(production.apply { tokenAdj = token }) // se le adjunta el token para usos futuros
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

    private fun matchNonTerminalRecurse(inputProductions: ArrayList<ChomskyProduction>?): ArrayList<ChomskyProduction>? {
        if (inputProductions == null)
            return null

        var matchCounter = 0 //al menos debe haber un match
        var alreadyMatchedInPreviusIteration = false
        val reducedProductions: ArrayList<ChomskyProduction>? = ArrayList()

        for ((i, currentProduction) in inputProductions.withIndex()) {
            try {
                val nextProduction = inputProductions[i + 1]

                if (alreadyMatchedInPreviusIteration) {
                    alreadyMatchedInPreviusIteration = false
                    continue
                }

                for (aNonTerminal in nonTerminals) {

                    if (currentProduction.name == aNonTerminal.leftNonTerminal!!.name && nextProduction.name == aNonTerminal.rightNonTerminal!!.name) {

                        // si hay match...
                        matchCounter++

                        //TODO("Setear parents de los hijos")
                        reducedProductions?.add(aNonTerminal.apply {
                            leftNonTerminal = currentProduction/*.apply { parent = aNonTerminal }*/
                            rightNonTerminal = nextProduction/*.apply { parent = aNonTerminal }*/
                        })
                        //saltarse el siguiente ciclo, porquela siguiente producción ya está contenida en esta
                        alreadyMatchedInPreviusIteration = true //hace el salto del ciclo (continue) en el for principal
                        break //sale del for anidado, porque ya no hay que buscar (ya hubo match)

                    } else {
                        reducedProductions?.add(currentProduction)
                    }
                }

            } catch (e: java.lang.IndexOutOfBoundsException) {
                break
            }
        }

        if (matchCounter == 0) {
            //TODO("No hubo ningún match, lanzar error")
            return null
        } else {
            //verificar condición de escape (si es produccion inicial)
            if (reducedProductions?.size == 1 && isStartingProduction(reducedProductions[0])) //checar si es de tamaño 1 y si es producción inicial S
            {
                //Si hubo match y no es produccion inicial ...
                return reducedProductions // ya regresar la expresion reducida sin recursividad
            }

            return matchNonTerminalRecurse(reducedProductions)
        }


    }

    fun analizar(tokens: ArrayList<Token>) {

        val matchedTerminals = getMatchedTerminals(tokens)

        if (matchedTerminals.isNullOrEmpty()) {
            //Error de sintaxis
            //TODO("Mostrar error en la tabla de errores")
            throw Exception("Error sintactico")
        }


        val productionsTreeWithTokens = matchNonTerminalRecurse(matchedTerminals)

        if (productionsTreeWithTokens == null) {
            //TODO("Mostrar error en la tabla de errores, considerar hacer una clase Respuesta que regrese un mensaje y que permita agregar un objeto adjunto")
            throw Exception("Error! Las producciones NO terminales no coinciden con el texto")
        }

    }


}