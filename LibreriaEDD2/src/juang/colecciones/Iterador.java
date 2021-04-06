/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juang.colecciones;

import juang.utils.Funcion;

/**
 *
 * @author juang
 */
public abstract class Iterador<T> {

    public abstract boolean tieneSiguiente();

    public abstract Nodo<T> siguienteNodo();

    public Nodo<T> siguienteNodo(int indice) {
        for (int i = 0; i < indice; i++) {
            siguienteNodo();
        }
        return siguienteNodo();
    }

    public abstract T siguiente();

    public T siguiente(int indice) {
        for (int i = 0; i < indice; i++) {
            siguiente();
        }
        return siguiente();
    }

    public void remover() {
    }

}
