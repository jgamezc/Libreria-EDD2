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
public class List<T> extends LinkedList<T> {

    @Override
    public void add(T value) {
        super.addLast(value);
    }

    @Override
    public T get(int index) {
        return super.get(index);
    }

    @Override
    public void set(int index, T value) {
        super.set(index, value);
    }

    @Override
    public T first() {
        return super.first();
    }

    @Override
    public T last() {
        return super.last();
    }

}
