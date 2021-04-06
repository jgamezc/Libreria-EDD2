/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juang.colecciones;

/**
 *
 * @author juang
 * @param <T>
 */
public class Grafo<T> extends Coleccion<T> {

    protected class NodoG<T> extends Nodo<T> {

        protected Lista<NodoG> nodosAdyacentes;

        protected NodoG(T dato) {
            super(dato);
            nodosAdyacentes = new Lista<>();
        }
    }

    @Override
    public void agregar(T dato) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterador<T> iterador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
