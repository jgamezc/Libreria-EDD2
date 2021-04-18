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
public class TablaHash<T> extends Coleccion<T> {

    Lista<Entrada<T>>[] tabla;

    public TablaHash() {
        tabla = new Lista[100];
        for (int i = 0; i < tabla.length; i++) {
            tabla[i] = new Lista();
        }
    }

    public void agregar(Object llave, T dato) {
        int indice = obtenerHash(llave);
        tabla[indice].agregar(new Entrada(llave, dato));
    }

    public T obtener(Object llave) {
        int indice = obtenerHash(llave);
        Lista<Entrada<T>> entradas = tabla[indice];
        Iterador<Entrada<T>> iterador = entradas.iterador();
        while(iterador.tieneSiguiente())
        {
            Entrada<T> entrada = iterador.siguiente();
            if (entrada.llave.equals(llave)) {
                return entrada.dato;
            }
        }
        return null;
    }

    //vaciar()
    //contiene(T dato)
    //contieneLlave(Object llave)
    //remover(T dato)
    //remover (Object llave)
    
    public int obtenerHash(Object llave) {
        int hash = 11;
        String llaveString = llave.toString();
        for (int i = 0; i < llaveString.length(); i++) {
            hash = hash * (int)llaveString.charAt(i) % tabla.length;
        }
        return hash;
        
    }

    @Override
    public Iterador<T> iterador() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class Entrada<T> {

        Object llave;
        T dato;

        public Entrada(Object llave, T dato) {
            this.llave = llave;
            this.dato = dato;
        }
        
        
    }

}
