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
public class Pila<T> extends ListaEnlazada<T> {

    @Override
    public void agregar(T dato) {
        super.agregarInicio(dato);
    }

    @Override
    public T primero() {
        return super.primero();
    }

    @Override
    public T siguiente() {
        return super.siguiente();
    }
}
