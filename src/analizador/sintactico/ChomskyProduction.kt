package analizador.sintactico

import analizador.lexico.DefinicionRegular
import analizador.lexico.Token

data class ChomskyProduction(
        var name: String,
        var parent: ChomskyProduction? = null,
        var leftNonTerminal: ChomskyProduction? = null,
        var rightNonTerminal: ChomskyProduction? = null,
        private var valueTerminal: DefinicionRegular? = null
) {

    fun setValueTerminal(value: DefinicionRegular) {
            if (leftNonTerminal == null && rightNonTerminal == null) {
                valueTerminal = value
            } else {
                throw Exception("No se puede poner un valor final terminal en una produccion con hijos NO terminales. Te equivocaste?")
            }
    }

    fun getValueTerminal() = valueTerminal

    var tokenAdj: Token? = null
        set(value) {
            if (isTerminal()) {
                field = value
            } else {
                throw Exception("No se puede adjuntar a un token a una produccion NO terminal. Te equivocaste?")
            }
        }

    fun isTerminal() = valueTerminal != null
}