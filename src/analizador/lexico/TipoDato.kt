package analizador.lexico

enum class TipoDato(text: String) {
    INT("int"),
    FLOAT("float"),
    CHAR("char"),
    BOOLEAN("boolean"),
    STRING("string");

    var text: String = text

    fun toRegex(): String {
        var acumulador = ""
        for ((i, tdato) in values().withIndex()) {
            if (i == 0)
                acumulador += tdato.text
            else
                acumulador += "|" + tdato.text
        }

        return acumulador
    }

    override fun toString(): String = text
}