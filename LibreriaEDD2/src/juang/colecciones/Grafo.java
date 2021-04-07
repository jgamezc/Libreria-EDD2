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
public class Grafo<T> extends Coleccion<Grafo<T>>{
    
    public T dato;
    private Lista<Grafo<T>> subgrafos;
    
    public Grafo(T dato){
        this.dato = dato;
        subgrafos = new Lista();
    }
    
    public Grafo<T> obtenerSubgrafo(int indice){
        if (indice < 0 || indice >= subgrafos.tamaño) {
            throw new IndexOutOfBoundsException();
        }
        return iterador().siguiente(indice);
    }
    
    public Grafo<T> obtenerSubGrafo(T dato)
    {
        Iterador<Grafo<T>> iterador = subgrafos.iterador();
        while(iterador.tieneSiguiente())
        {
            Grafo subGrafo = iterador.siguiente();
            if (subGrafo.dato.equals(dato)) {
                return subGrafo;
            }
        }
        return null;
    }

    @Override
    public void agregar(Grafo<T> dato) {
        subgrafos.agregar(dato);
    }

    @Override
    public Iterador<Grafo<T>> iterador() {
        return subgrafos.iterador();
    }
    
}
