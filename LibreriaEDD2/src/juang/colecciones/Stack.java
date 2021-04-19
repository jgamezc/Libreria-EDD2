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
public class Stack<T> extends LinkedList<T> {

    @Override
    public void add(T value) {
        super.addFirst(value);
    }

    @Override
    public T first() {
        return super.first();
    }

    @Override
    public T poll() {
        return super.poll();
    }
}
