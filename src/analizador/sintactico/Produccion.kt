package analizador.sintactico

import analizador.lexico.DefinicionRegular

data class Produccion(
        val producciones: ArrayList<Produccion> = ArrayList<Produccion>(),
        var defReg: DefinicionRegular? = null,
        var padre: Produccion? = null
) {
    val esTerminal: Boolean
        get() = producciones.isEmpty() && defReg != null // es terminal cuando NO tiene produccione hijas y cuando SI tiene una definicion regular\

    val esInicial: Boolean
        get() = padre == null

    val esValido: Boolean
        get() = (esTerminal && !esInicial) || (!esTerminal && esInicial)

    fun siguienteHoja(nodo: Produccion = this, indexHijo: Int = 0): Produccion {
        if (nodo.esTerminal) {
            if (nodo === this) {
                //hacer algo para recorrer el arbol hacia arriba
                if (nodo.padre == null) {
                    //bajar
                    return siguienteHoja(nodo.producciones.get(indexHijo + 1), indexHijo + 1)
                } else {
                    return siguienteHoja(nodo.padre!!)
                }
            } else {
                return nodo
            }
        } else { //si NO es terminal
            return siguienteHoja(nodo.producciones.get(indexHijo))
        }
    }
}