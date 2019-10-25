package analizador.sintactico

import analizador.lexico.DefinicionRegular

class ChomskyProduction(name: String) {

    var name: String = name
    var parent: ChomskyProduction? = null
    var leftNonTerminal: ChomskyProduction? = null
    var rightNonTerminal: ChomskyProduction? = null
    var valueTerminal: DefinicionRegular? = null
        set(value) {
            if (leftNonTerminal == null && rightNonTerminal == null) {
                field = value
            } else {
                throw Exception("No se puede poner un valor final terminal en una produccion con hijos NO terminales. Te equivocaste?")
            }
        }

    fun isTerminal() = valueTerminal != null
}