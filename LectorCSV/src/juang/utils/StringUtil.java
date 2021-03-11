package juang.utils;

import juang.colecciones.Lista;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Un conjunto de métodos y funciones que facilitan el manejo de los datos
 * guardados en los archivos csv.
 *
 * @author juang
 */
public class StringUtil {

    /**
     * Convierte el {@code String s} al tipo de dato especificado. en caso de no
     * ser posible; sera devuelto un valor nulo.
     *
     * @param cadena el {@code String} que representa el dato.
     * @param clase el tipo de dato al que desea ser convertido.
     * @return un dato del tipo {@code c} representativo del {@code String s},
     * si la conversión no es posible; este método retorna un valor nulo.
     */
    public static Object parseClass(String cadena, Class clase) {

        if (clase == int.class || clase == Integer.class) {
            try {
                return Integer.parseInt(cadena);
            } catch (NumberFormatException e) {
            }
            return 0;
        }

        if (clase == float.class || clase == Float.class) {
            try {
                return Float.parseFloat(cadena);
            } catch (NumberFormatException e) {
            }
            return 0f;
        }

        if (clase == Boolean.class) {
            if ("true".equals(cadena)) {
                return true;
            }
            if ("false".equals(cadena)) {
                return false;
            }
            return null;
        }

        if (clase == Lista.class) {
            return separar(cadena, '|');
        }

//        if (clase == String.class) {
//            return cadena;
//        }
        return cadena;
    }

    /**
     * Divide el {@code String s} y guarda los {@code String} resultantes en una
     * lista enlazada simple.
     *
     * @see utils.colecciones.Lista
     * @param cadena la cadena de texto que se desea dividir.
     * @param caracter el caracter que identifica al elemento divisor del
     * {@code String s}.
     * @return {@code  List<String>} con las respectivas cadenas de texto
     * resultantes de la división.
     */
    public static Lista<String> separar(String cadena, char caracter) {

        Lista<String> cadenas = new Lista<>();
        int indiceInicial = 0;
        int indiceFinal = 0;

        while (indiceFinal < cadena.length()) {
            indiceFinal = getIndiceDe(cadena, caracter, indiceInicial);
            String subcadena = cadena.substring(indiceInicial, indiceFinal);
            if (subcadena.startsWith("\"")) {
                subcadena = subcadena.substring(1, subcadena.length() - 1);
            }
            cadenas.agregar(subcadena);
            indiceInicial = indiceFinal + 1;
        }

        return cadenas;
    }

    /**
     * Devuelve el índice del primer caracter {@code c} encontrado en la cadena
     * de texto {@code s}.
     *
     * @param cadena {@code cadena} que contiene el caracter que se desea
     * encontrar.
     * @param caracter {@code char} que se desea encontrar entre los
     * {@code char} del {@code String s}.
     * @return {@code int} igual a la posición donde se encontro el caracter.
     */
    public static int getIndiceDe(String cadena, char caracter) {
        return StringUtil.getIndiceDe(cadena, caracter, 0);
    }

    private static int getIndiceDe(String cadena, char caracter, int indiceActual) {

        if (indiceActual == cadena.length()) {
            return indiceActual;
        }

        if (cadena.charAt(indiceActual) == caracter) {
            return indiceActual;
        }

        if (cadena.charAt(indiceActual) == '"') {
            indiceActual = StringUtil.getIndiceDe(cadena, '"', indiceActual + 1);
        }

        return StringUtil.getIndiceDe(cadena, caracter, indiceActual + 1);
    }

    public static String ajusteHorizontal(String s, int tamaño) {
        if (s.length() > tamaño) {
            for (int i = tamaño; i >= 0; i--) {
                if (s.charAt(i) == ' ') {
                    String cadenaDerecha = ajusteHorizontal(s.substring(i + 1, s.length()), tamaño);
                    String nuevaCadena = s.substring(0, i) + "\n" + cadenaDerecha;
                    return nuevaCadena;
                }
            }
        }
        return s;
    }
}
