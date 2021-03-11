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
        super.agregarUltimo(dato);
    }

    @Override
    public T get(int indice) {
        return super.get(indice);
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
