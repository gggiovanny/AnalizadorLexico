# Analizador Lexico

Programa en Java para implementar las expresiones regulares que representan el patrón de las instrucciones de un lenguaje de programación. El programa permite ingresar varios lexemas que corresponden a expresiones aritmeticas en C++. El programa verifica que cada lexema cumpla con el patrón del lenguaje de programación.

#### Notas adicionales sobre entradas y salidas esperadas
El programa permitirá ingresar varias cadenas a la vez, y como salida genera una tabla de simbolos donde se visualizan los lexemas y los tokens, asi como una tabla de errores donde se visualizan el lexema que genera el error.
##### El cumple con todo lo siguiente:
+ Que deje ingresar varias líneas
+ Que cuando halla errores, muestre los tokens correctos antes y después del error
+ Los errores deben estar también en la tabla de tokens normales
+ No se deben de repetir los tokens iguales en las tablas (x = x/2;)


# Analizador Sintáctico
El analizador sintáctico es la segunda fase de un compilador. Recibe como entrada una secuencia de tokens y genera un árbol de análisis sintáctico de acuerdo con la gramática libre del contexto definida para determinar que la instrucción esté escrita correctamente. El analizador sintáctico puede identificar un error cuando la secuencia de tokens no corresponde con las reglas gramaticales del lenguaje de programación y lo incorpora a la tabla de errores.


#### Informacion adicional
+ [Como hacer los tokens de error](https://drive.google.com/open?id=1ZMFsDPFu0uCkqSe7WkhR5SwxjhEPyi5l)
+ [Como hacer los tokens normales](https://drive.google.com/open?id=1zpympdkJSz5FdZ3Kb_IHILJ2rYJkRrCI)
+ [Info de las librerias regex y matcher con ejemplos muy comprensibles](http://tutorials.jenkov.com/java-regex/matcher.html)
+ [Documentacion oficial de Pattern (info acerca del uso de las regex)](https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html)