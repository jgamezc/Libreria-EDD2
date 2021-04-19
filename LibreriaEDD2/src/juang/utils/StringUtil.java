package juang.utils;

import juang.colecciones.List;

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
     * @param s el {@code String} que representa el dato.
     * @param type el tipo de dato al que desea ser convertido.
     * @return un dato del tipo {@code c} representativo del {@code String s},
     * si la conversión no es posible; este método retorna un valor nulo.
     */
    public static Object parseClass(String s, Class type) {

        if (type == int.class || type == Integer.class) {
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
            }
            return 0;
        }

        if (type == float.class || type == Float.class) {
            try {
                return Float.parseFloat(s);
            } catch (NumberFormatException e) {
            }
            return 0f;
        }

        if (type == boolean.class || type == Boolean.class) {
            if ("true".equals(s)) {
                return true;
            }
            if ("false".equals(s)) {
                return false;
            }
            return null;
        }

        if (type == List.class) {
            return split(s, '|');
        }

//        if (clase == String.class) {
//            return cadena;
//        }
        return s;
    }

    /**
     * Divide el {@code String s} y guarda los {@code String} resultantes en una
     * lista enlazada simple.
     *
     * @see utils.colecciones.Lista
     * @param s la cadena de texto que se desea dividir.
     * @param c el caracter que identifica al elemento divisor del
     * {@code String s}.
     * @return {@code  List<String>} con las respectivas cadenas de texto
     * resultantes de la división.
     */
    public static List<String> split(String s, char c) {

        List<String> strings = new List();
        int firstIndex = 0;
        int lastIndex = 0;

        while (lastIndex < s.length()) {
            lastIndex = getIndexOf(s, c, firstIndex);
            String subString = s.substring(firstIndex, lastIndex);
            if (subString.startsWith("\"")) {
                subString = subString.substring(1, subString.length() - 1);
            }
            strings.add(subString);
            firstIndex = lastIndex + 1;
        }

        return strings;
    }

    /**
     * Devuelve el índice del primer caracter {@code c} encontrado en la cadena
     * de texto {@code s}.
     *
     * @param s {@code cadena} que contiene el caracter que se desea encontrar.
     * @param c {@code char} que se desea encontrar entre los {@code char} del
     * {@code String s}.
     * @return {@code int} igual a la posición donde se encontro el caracter.
     */
    public static int getIndexOf(String s, char c) {
        return StringUtil.getIndexOf(s, c, 0);
    }

    private static int getIndexOf(String s, char c, int currentIndex) {

        if (currentIndex == s.length()) {
            return currentIndex;
        }

        if (s.charAt(currentIndex) == c) {
            return currentIndex;
        }

        if (s.charAt(currentIndex) == '"') {
            currentIndex = StringUtil.getIndexOf(s, '"', currentIndex + 1);
        }

        return StringUtil.getIndexOf(s, c, currentIndex + 1);
    }

}
