package analizador.lexico

enum class TipoDato(text: String) {
    INT("int"),
    FLOAT("float"),
    DOUBLE("double"),
    CHAR("char"),
    BOOLEAN("boolean"),
    STRING("string"),
    STRING2("String"),
    INDEFINIDO("indefinido")
    ;

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