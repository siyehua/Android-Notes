package com.example;

import java.util.Iterator;

/**
 * API<br>
 * public class Bag<I> implements Iterable<T><br>
 * Bag()           创建一个背包<br>
 * void     add(T item)     添加一个元素<br>
 * boolean  isEmpty()       背包是否为空<br>
 * int      size()          背包大小<br>
 * Created by siyehua in 2016/12/10.
 */
public class MyBag<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {

        }
    }

    private class Node {
        Node next;
        T item;
    }

    private Node first;
    int size;

    public void add(T item) {
        Node node = new Node();
        node.item = item;
        node.next = first;
        first = node;
        size++;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
