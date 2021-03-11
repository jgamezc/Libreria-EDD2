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
public abstract class Iterador<T> {

    public abstract boolean tieneSiguiente();

    public abstract T siguiente();

    public void remover() {
    }
}
