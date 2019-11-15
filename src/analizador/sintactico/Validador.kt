package analizador.sintactico

import analizador.lexico.DefinicionRegular
import analizador.lexico.Token
import common.Response

class Validador {

    fun validar(tokens: ArrayList<Token>): Response<ArrayList<Token>> {

        val identificatorForAddInTable = ArrayList<Token>()


        val basicSequence = ArrayList<DefinicionRegular>().apply {
            add(DefinicionRegular.TIPO)
            add(DefinicionRegular.IDE)
        }

        val extraSequence = ArrayList<DefinicionRegular>().apply {
            add(DefinicionRegular.COMMA)
            add(DefinicionRegular.IDE)
        }

        for (i in 0 until basicSequence.size) {
            if (tokens[i].defReg == basicSequence[i]) {
                identificatorForAddInTable.add(tokens[1])
            } else {

            }
        }

        return Response(
                sucess = false
        )


    }

}