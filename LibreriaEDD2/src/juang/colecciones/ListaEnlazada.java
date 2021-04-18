/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juang.colecciones;

/**
 *
 * @author juang
 */
public abstract class ListaEnlazada<T> extends Coleccion<T> {

    protected class NodoL<T> extends Nodo<T> {

        protected NodoL<T> siguienteNodo;

        protected NodoL(T dato) {
            super(dato);
            siguienteNodo = null;
        }
        
    }

    protected NodoL<T> nodoPuntero;
    protected NodoL<T> ultimoNodo;
    protected Integer tamaño;

    public ListaEnlazada() {
        nodoPuntero = new NodoL(null);
        ultimoNodo = nodoPuntero;
        tamaño = 0;
    }
    
    public abstract void agregar(T dato);

    protected void agregarInicio(T dato) {
        NodoL nuevoNodo = new NodoL(dato);
        nuevoNodo.siguienteNodo = nodoPuntero.siguienteNodo;
        nodoPuntero.siguienteNodo = nuevoNodo;
        tamaño++;
    }

    protected void agregarFinal(T dato) {
        NodoL nuevoNodo = new NodoL(dato);
        ultimoNodo.siguienteNodo = nuevoNodo;
        ultimoNodo = nuevoNodo;
        tamaño++;
    }

    protected T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException();
        }

        return iterador().siguiente(indice);
    }

    protected void establecer(int indice, T dato) {
        if (indice < 0 || indice >= tamaño) {
            throw new IndexOutOfBoundsException();
        }

        iterador().siguienteNodo(indice).dato = dato;
    }

    protected T primero() {
        return nodoPuntero.siguienteNodo.dato;
    }

    protected T ultimo() {
        return ultimoNodo.dato;
    }

    protected T siguiente() {
        NodoL<T> primerNodo = nodoPuntero.siguienteNodo;
        if (primerNodo != null) {
            if (primerNodo == ultimoNodo) {
                ultimoNodo = nodoPuntero;
            }
            nodoPuntero.siguienteNodo = primerNodo.siguienteNodo;
            tamaño--;
            return primerNodo.dato;
        }
        return null;
    }

    @Override
    public Integer tamaño() {
        return tamaño;
    }

    @Override
    public void vaciar() {
        nodoPuntero = new NodoL(null);
        ultimoNodo = nodoPuntero;
        tamaño = 0;
    }

    @Override
    public Iterador<T> iterador() {
        Iterador<T> iterador = new Iterador() {

            NodoL<T> nodoActual = nodoPuntero;
            NodoL nodoAnterior = nodoActual;

            @Override
            public boolean tieneSiguiente() {
                return nodoActual.siguienteNodo != null;
            }

            @Override
            public Nodo<T> siguienteNodo() {
                nodoAnterior = nodoActual;
                nodoActual = nodoActual.siguienteNodo;
                return nodoActual;
            }

            @Override
            public T siguiente() {
                return siguienteNodo().dato;
            }

            @Override
            public void remover() {
                if (nodoActual == ultimoNodo) {
                    ultimoNodo = nodoAnterior;
                }
                nodoAnterior.siguienteNodo = nodoActual.siguienteNodo;
                nodoActual = nodoAnterior;
                tamaño--;
            }
        };
        return iterador;
    }

}
