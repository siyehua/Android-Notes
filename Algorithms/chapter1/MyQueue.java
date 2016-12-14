package com.example;

import java.util.Iterator;

/**
 * API
 * <p>
 * Queue()             创建空队列<br>
 * void     enqueue(T item)     添加一个元素<br>
 * T        dequeue()           删除最早添加的元素<br>
 * boolean  isEmpty()           队列是否为空<br>
 * int      size()              队列中的元素数量<br>
 * </p>
 * Created by siyehua on 2016/12/3.
 */
public class MyQueue<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new MyQueueAdapter();
    }

    private Node first;
    private Node last;
    private int number;

    public boolean isEmpty() {
        return number == 0;
    }

    public int size() {
        return number;
    }

    public void enQueue(T item) {
        Node oldFirst = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldFirst.next = last;
        number++;
    }

    public T deQueue() {
        T tmp = first.item;
        first = first.next;
        number--;
        if (isEmpty()) {
            last = null;
        }
        return tmp;
    }

    /**
     * push item in front of the index.<br>
     * the index start with <b>0</b>.
     *
     * @param index 0 ~ size()
     * @param item  item
     */
    public void enQueue(int index, T item) {
        if (index < 0 || index > size()) throw new RuntimeException("index must be 0 ~ size()");
        if (index == size()) {
            enQueue(item);
            return;
        }

        Node node = new Node();
        node.item = item;

        number++;
        Node tmp = first;
        if (index == 0) {
            node.next = first;
            first = node;
            return;
        }
        for (int i = 0; i < index - 1; i++) {
            tmp = tmp.next;
        }
        node.next = tmp.next;
        tmp.next = node;
    }


    /**
     * delete the item
     * the index start with <b>0</b>.
     *
     * @param k index: 0 ~ size()-1
     * @return item
     */
    public T delete(int k) {
        if (k > size() - 1 || k < 0) throw new RuntimeException("k must be 0 ~ size()-1.");

        number--;

        T tmp;
        Node node = first;
        if (k == 0) {
            tmp = first.item;
            first = first.next;
            return tmp;
        }
        for (int i = 0; i < k - 1; i++) {
            node = node.next;
        }
        tmp = node.next.item;
        if (k == size()) node.next = null;
        else node.next = node.next.next;
        return tmp;
    }

    private class Node {
        T item;
        Node next;
    }

    private class MyQueueAdapter implements Iterator<T> {
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

    public static void main(String[] args) {
        MyQueue<String> myQueue = new MyQueue<>();
        for (int i = 0; i < 10; i++) {
            myQueue.enQueue(i + 1 + "");
        }
        myQueue.enQueue(11,"new data");
//        System.out.println("delete: " + myQueue.delete(10));
        for (String str : myQueue) {
            System.out.println(str);
        }
    }

}
