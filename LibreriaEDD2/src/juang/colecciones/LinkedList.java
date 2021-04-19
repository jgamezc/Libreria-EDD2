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
public abstract class LinkedList<T> extends Collection<T> {

    protected class Node<T> {

        public T value;
        protected Node<T> next;

        protected Node(T value) {
            this.value = value;
            next = null;
        }

    }

    protected Node<T> pointer;
    protected Node<T> last;
    protected Integer size;

    public LinkedList() {
        pointer = new Node(null);
        last = pointer;
        size = 0;
    }

    public abstract void add(T value);

    protected void addFirst(T value) {
        Node newNode = new Node(value);
        newNode.next = pointer.next;
        pointer.next = newNode;
        size++;
    }

    protected void addLast(T value) {
        Node newNode = new Node(value);
        last.next = newNode;
        last = newNode;
        size++;
    }

    protected T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        int count = 0;
        for (T value : this) {
            if (count == index) {
                return value;
            } else {
                count++;
            }
        }
        return null;
    }

    protected void set(int index, T value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        Node<T> current = pointer;
        for (int i = 0; i < size; i++) {
            current = current.next;
        }
        current.value = value;
    }

    protected T first() {
        return pointer.next.value;
    }

    protected T last() {
        return last.value;
    }

    protected T poll() {
        Node<T> first = pointer.next;
        if (first != null) {
            if (first == last) {
                last = pointer;
            }
            pointer.next = first.next;
            size--;
            return first.value;
        }
        return null;
    }

    @Override
    public Integer count() {
        return size;
    }

    @Override
    public void clear() {
        pointer = new Node(null);
        last = pointer;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> iterador = new Iterator() {

            Node<T> current = pointer;
            Node previous = current;

            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public T next() {
                previous = current;
                current = current.next;
                return current.value;
            }

            @Override
            public void remove() {
                if (current == last) {
                    last = previous;
                }
                previous.next = current.next;
                current = previous;
                size--;
            }
        };
        return iterador;
    }

}
