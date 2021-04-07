/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juang.colecciones;

import java.lang.reflect.Array;

/**
 *
 * @author juang
 */
abstract class Coleccion<T> implements Iterable<T> {

    public abstract void agregar(T dato);
    
    public void remover(T dato) {
        remover(dato, false);
    }

    public void removerTodos(T dato) {
        remover(dato, true);
    }

    private void remover(T dato, boolean removerTodos) {
        Iterador<T> iterador = iterador();
        while (iterador.tieneSiguiente()) {
            if (iterador.siguiente().equals(dato)) {
                iterador.remover();
                if (!removerTodos) {
                    return;
                }
            }
        }
    }

    public Integer tamaño() {
        Integer tamaño = 0;
        Iterador<T> iterador = iterador();
        while (iterador.tieneSiguiente()) {
            iterador.siguiente();
            tamaño++;
        }
        return tamaño;
    }

    public boolean contiene(T elemento) {
        Iterador<T> iterador = iterador();
        while (iterador.tieneSiguiente()) {
            if (elemento.equals(iterador.siguiente())) {
                return true;
            }
        }
        return false;
    }

    public void vaciar() {
        Iterador<T> iterador = iterador();
        while (iterador.tieneSiguiente()) {
            iterador.siguiente();
            iterador.remover();
        }
    }

    public boolean estaVacia() {
        Iterador iterador = iterador();
        return !iterador.tieneSiguiente();
    }

    public T[] toArray() {
        Integer tamaño = tamaño();
        if (tamaño == 0) {
            throw new IllegalStateException();
        }

        Iterador iterador = iterador();
        T[] array = (T[]) Array.newInstance(iterador.siguiente().getClass(), tamaño);
        iterador = iterador();
        for (int i = 0; i < tamaño; i++) {
            array[i] = (T) iterador.siguiente();
        }
        return array;
    }

}
