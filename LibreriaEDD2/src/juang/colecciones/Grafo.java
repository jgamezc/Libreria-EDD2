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
public class Grafo<T> extends Coleccion<Grafo>{
    
    public T dato;
    public Lista<Grafo> subgrafos;
    
    public Grafo(T dato){
        this.dato = dato;
        subgrafos = new Lista();
    }
    
    public Grafo obtenerSubgrafo(int indice){
        if (indice < 0 || indice >= subgrafos.tamaño) {
            throw new IndexOutOfBoundsException();
        }
        return iterador().siguiente(indice);
    }
    
    public Grafo obtenerSubGrafo(Object dato)
    {
        Iterador<Grafo> iterador = subgrafos.iterador();
        while(iterador.tieneSiguiente())
        {
            Grafo subGrafo = iterador.siguiente();
            if (subGrafo.dato.equals(dato)) {
                return subGrafo;
            }
        }
        return null;
    }

    public void agregar(Grafo dato) {
        subgrafos.agregar(dato);
    }

    @Override
    public Iterador<Grafo> iterador() {
        return subgrafos.iterador();
    }
    
}
