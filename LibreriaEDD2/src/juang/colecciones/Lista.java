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
public class Lista<T> extends ListaEnlazada<T> {

    @Override
    public void agregar(T dato) {
        super.agregarFinal(dato);
    }

    @Override
    public T obtener(int indice) {
        return super.obtener(indice);
    }

    @Override
    public void establecer(int indice, T dato) {
        super.establecer(indice, dato);
    }

    @Override
    public T primero() {
        return super.primero();
    }

    @Override
    public T ultimo() {
        return super.ultimo();
    }

}
