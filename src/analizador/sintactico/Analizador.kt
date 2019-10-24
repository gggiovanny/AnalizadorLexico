package analizador.sintactico

import analizador.lexico.DefinicionRegular
import analizador.lexico.Token

class Analizador {

    private fun getGlcAritmetico(): Produccion {
        val termino = Produccion().apply { defReg = DefinicionRegular.DIG }

        val operador = Produccion().apply { defReg = DefinicionRegular.OPAR }

        val expr = Produccion()
        expr.producciones.add(expr)
        expr.producciones.add(operador)
        expr.producciones.add(termino)
        return expr
    }


    fun analizar(tokens: ArrayList<Token>) {
        val glcAritmetico = getGlcAritmetico()

        for (prod: Produccion in glcAritmetico.producciones) {

        }


    }
}