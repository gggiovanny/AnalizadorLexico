package analizador.sintactico

import analizador.lexico.TipoDato
import analizador.lexico.Token

data class TablaSimbolosFila(
        private val tipoDatoObj: TipoDato,
        private val tokenObj: Token
) {
    val lexema: String = tokenObj.lexema
    val token = tokenObj.toString()
    val tipoDato = tipoDatoObj.toString()
}