/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juang.colecciones;

import java.util.Iterator;

/**
 *
 * @author juang
 */
public class Vertex extends Collection<Vertex> {

    public Object value;
    private List<Vertex> neighbors;

    public Vertex(Object value) {
        this.value = value;
        neighbors = new List();
    }

    public List<Vertex> neightbors() {
        return neighbors;
    }

    public Vertex getNeighbor(int index) {
        return neighbors.get(index);
    }

    public Vertex getNeighbor(Object value) {
        for (Vertex neighbor : neighbors) {
            if (neighbor.value.equals(value)) {
                return neighbor;
            }
        }
        return null;
    }

    @Override
    public Iterator<Vertex> iterator() {
        return (Iterator<Vertex>) neighbors.iterator();
    }

}
