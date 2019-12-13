package analizador.lexico;

import analizador.sintactico.Validador;
import compilador.TriploMaker;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * RESUMEN:
 * Esta es la clase principal y donde se lleva a cabo toda la funcionalidad.
 * Permite comparar definiciones regulares con cadenas de entrada y genera sus tokens
 * respectivos que se anexan a tablaSimbolos.
 */
public class Analizador {
    public TablaTokens tablaSimbolos; // esto es basicamente un ArrayList de tokens con funciones extra (ver la clase)
    public TablaTokens tablaErrores; // tabla donde se guadan los tokens de error
    public boolean patronEncontrado; // indica si se tuvo exito al encontrar en su totalidad todos los tokens en la cadena de entrada
    private Pattern p; // permite designar un patron (regex) a buscar en una cadena
    private Matcher m; // permite mostrar resultados de busqueda de un patron en una cadena

    private Validador analizadorSintactico;
    private TriploMaker compilador;

    public Analizador() {
        tablaSimbolos = new TablaTokens();
        tablaErrores = new TablaTokens();
        patronEncontrado = false;
        for (DefinicionRegular defReg : DefinicionRegular.values())
            defReg.contador = 1;

        analizadorSintactico = new Validador(tablaSimbolos, tablaErrores);
        compilador = new TriploMaker();
    }

    public void buscarPatrones(String cadena) {
        ArrayList<Token> lsTokens = new ArrayList<Token>(); // contendra una lista completa secuencial de los tokens hallados (sin espacios).
        Token token = new Token(); // objeto que contendra la informacion de los tokens que se hallen
        // no se usa individualmente, se anexa a tablaTokens
        int contadorLexema = 0;
        int contadorColumna = 0;
        int contadorLinea = 1;

        //Ciclo de recorrido de la cadena
        while (cadena.length() > 0) // la busqueda de patrones se detiene cuando se llega a una cadena que no se
        // puede "tokenizar"
        {
            // Ciclo de matching: recorre todas las definiciones regulares y verifica en cual coincide la cadena de entrada.
            // Se recorre un arreglo generado por el enum que contiene las definiciones regulares
            for (DefinicionRegular defReg : DefinicionRegular.values()) {
                p = Pattern.compile(defReg.regex()); // se designa una expresion regular para buscar coincidencias, que
                // esta guardada en el enum DefinicionRegular
                m = p.matcher(cadena);    // se inicializa el objeto de busqueda de coincidencias con la expresion regular
                // anterior
                if (m.lookingAt()) // se buscan coincidencias unicamente al principio de la cadena de entrada
                {
                    // si hay coincidencia, se define un nuevo token del tipo de la definicion
                    // regular que coincidio con la cadena
                    String lexema = m.group();
                    token = new Token(defReg, lexema, contadorLexema);
                    token.numeroLinea = contadorLinea;
                    token.numeroColumna = contadorColumna;

					if (token.defReg != DefinicionRegular.ESPACIO)
                        lsTokens.add(token); // se agrega indistintamente a la lista para analisis posteriores

                    // En la siguiente seccion se agrega el token a su tabla correspondiente.
                    if (token.defReg.name().contains("ERROR")) {
                        if (!existeEnTabla(token, tablaErrores)) {
                            tablaErrores.add(token); // se agrega el nuevo token como error
                            tablaSimbolos.add(token);
                        }
                    } else {
                        if (!existeEnTabla(token, tablaSimbolos)) {
                            // se agrega el token nuevo a la tabla simbolos, donde se le asigna su
                            // numero consecutivo automaticamente
                            tablaSimbolos.add(token);
                        }
                    }
                    cadena = cadena.substring(m.end());    // se corta de la cadena de entrada la seccion de la cadena
                    // que si coincidió
                    contadorColumna += m.end(); // Antes de salir del ciclo, contabilizar la columna
                    break; // Sale del FOR. Si ya se encontró el token, salirse del ciclo de matching
                }
            }
            if (token.defReg != DefinicionRegular.ESPACIO) // No se toma en cuenta como lexema los espacios
                contadorLexema++;

            if (cadena.length() > 0)
                if (cadena.charAt(0) == '\n') {
                    contadorLinea++;
                    contadorColumna = 0;
                }
        }


        compilador.compilar(lsTokens);

    }

    private boolean existeEnTabla(Token token, TablaTokens tabla) {
        for (Token tokenEnTabla : tabla.tokens) {
            if (token.defReg == tokenEnTabla.defReg) {
                if (token.lexema.equals(tokenEnTabla.lexema)) {
                    token.numeroToken = tokenEnTabla.numeroToken;
                    return true;
                }
            }
        }
        return false;
    }


}
