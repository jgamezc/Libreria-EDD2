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
public class HashTable<T> extends Collection<T> {

    List<Entry<T>>[] hashTable;

    public HashTable(int size) {
        hashTable = new List[size];
        for (int i = 0; i < hashTable.length; i++) {
            hashTable[i] = new List();
        }
    }

    public void add(Object key, T value) {
        int index = hashValue(key);
        hashTable[index].add(new Entry(key, value));
    }

    public T getValue(Object key) {
        int index = hashValue(key);
        List<Entry<T>> entries = hashTable[index];

        if (entries != null) {
            for (Entry<T> entry : entries) {
                if (entry.key.equals(key)) {
                    return entry.value;
                }
            }
        }

        return null;
    }

    //vaciar()
    //contiene(T dato)
    public boolean containsKey(Object key)
    {
        return getValue(key) != null;
    }
    //remover(T dato)
    //remover (Object llave)
    public int hashValue(Object key) {
        int hash = 11;
        String keyString = key.toString();
        for (int i = 0; i < keyString.length(); i++) {
            hash = hash * (int) keyString.charAt(i) % hashTable.length;
        }
        return hash;

    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class Entry<T> {

        Object key;
        T value;

        public Entry(Object key, T value) {
            this.key = key;
            this.value = value;
        }

    }


}
