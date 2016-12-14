package com.example;

import java.util.Iterator;

/**
 * Created by huangxk on 2016/12/4.
 */

public class DoubleNodeStack<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class Node {
        Node next;
        Node last;
        T item;
    }

    private Node first;
    int number;

    public boolean isEmpty() {
        return number == 0;
    }

    public int size() {
        return number;
    }

    public void push(T item) {
        Node old = first;
        first = new Node();
        first.item = item;
        first.next = old;
        if (old != null) old.last = first;
        number++;
    }

    public T pop() {
        T tmp = first.item;
        first = first.next;
        first.last = null;
        number--;
        return tmp;
    }

    private class MyIterator implements Iterator<T> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            T tmp = current.item;
            current = current.next;
            return tmp;
        }

        @Override
        public void remove() {

        }
    }

    public void addHead(T item) {
        Node node = first;
        for (int i = 0; i < size() - 1; i++) {
            node = node.next;
        }
        Node newNode = new Node();
        newNode.item = item;
        newNode.last = node;
        node.next = newNode;
        number++;
    }

    public void addTail(T item) {
        push(item);
    }

    public void removeHead() {
        Node node = first;
        for (int i = 0; i < size() - 1; i++) {
            node = node.next;
        }
        node.last.next = null;
        node.last = null;
        number--;
    }

    public void removeTail() {
        pop();
    }

    /**
     * add item in the index's left.<br>
     * if index < 0 ,the item will add to the left of 0<br>
     * if index > {@link MyStack#size()} - 1, the item will add to the end.
     *
     * @param index index
     * @param item  item
     */
    public void addLeft(int index, T item) {
        if (index <= 0) {
            addHead(item);
            return;
        }
        if (index > size() - 1) {
            addTail(item);
            return;
        }
        Node newNode = new Node();
        newNode.item = item;
        Node node = first;
        for (int i = index; i < size() - 1; i++) {
            node = node.next;
        }
        newNode.next = node.next;
        newNode.last = node;

        node.next.last = newNode;

        node.next = newNode;
        number++;
    }

    /**
     * add item in the index's right.<br>
     * if index < 0 ,the item will add to the left of 0<br>
     * if index > {@link MyStack#size()} - 1, the item will add to the end.
     *
     * @param index index
     * @param item  item
     */
    private void addRight(int index, T item) {
        if (index < 0) {
            addHead(item);
            return;
        }
        if (index > size() - 1) {
            addTail(item);
            return;
        }
        Node newNode = new Node();
        newNode.item = item;
        Node node = first;
        for (int i = index; i < size() - 1; i++) {
            node = node.next;
        }
        newNode.last = node.last;
        newNode.next = node;

        node.last.next = newNode;

        node.last = newNode;

        number++;
    }

    public boolean remove(int index) {
        if (index < 0 || index > size() - 1) return false;
        if (index == 0) removeHead();
        else if (index == size() - 1) removeTail();
        else {
            Node node = first;
            for (int i = index; i < size() - 1; i++) {
                node = node.next;
            }
            node.next.last = node.last;
            node.last.next = node.next;

            node.last = null;
            node.next = null;
        }
        return true;
    }

    public static void main(String[] args) {
        DoubleNodeStack<String> doubleNodeStack = new DoubleNodeStack<>();
        for (int i = 0; i < 10; i++) {
            doubleNodeStack.push(i + 1 + "");
        }
//        doubleNodeStack.addHead("add head");
//        doubleNodeStack.addTail("add Tail");
//        doubleNodeStack.removeHead();
//        doubleNodeStack.removeTail();
//        doubleNodeStack.addLeft(5, "add left");
//        doubleNodeStack.addRight(0, "add right");
        System.out.println(doubleNodeStack.remove(2));
        for (String str : doubleNodeStack) {
            System.out.println(str);
        }

    }
}
