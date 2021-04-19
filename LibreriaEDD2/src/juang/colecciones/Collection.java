/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juang.colecciones;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 *
 * @author juang
 */
abstract class Collection<T> implements Iterable<T> {

    public void remove(T value) {
        remove(value, false);
    }

    public void removeAll(T value) {
        remove(value, true);
    }

    private void remove(T value, boolean removeAll) {
        for (Iterator<T> it = this.iterator(); it.hasNext();) {
            T next = it.next();
            if (next.equals(value)) {
                it.remove();
                if (!removeAll) {
                    break;
                }
            }
        }
    }

    public Integer count() {
        Integer count = 0;
        for (Iterator<T> it = this.iterator(); it.hasNext();) {
            it.next();
            count++;
        }
        return count;
    }

    public boolean contains(T value) {
        for (Iterator<T> it = this.iterator(); it.hasNext();) {
            if (it.next().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public void clear() {
        for (Iterator<T> it = this.iterator(); it.hasNext();) {
            it.next();
            it.remove();
        }
    }

    public boolean isEmpty() {
        return !iterator().hasNext();
    }

    public T[] toArray() {
        Integer size = count();
        if (size == 0) {
            throw new IllegalStateException();
        }
        
        T[] array = (T[]) Array.newInstance(iterator().next().getClass(), size);
        Iterator<T> it = iterator();
        for (int i = 0; i < size; i++) {
            array[i] = it.next();
        }
        return array;
    }

}
