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
public class MyQueueWithArr<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex != size;
        }

        @Override
        public T next() {
            return items[currentIndex++];
        }

        @Override
        public void remove() {

        }
    }

    private int size;
    private T[] items = (T[]) new Object[1];

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(T item) {
        if (size == items.length) {
            items = reSizeArr(size * 2);
        }
        items[size++] = item;
    }

    public T dequeue() {
        T tmp = items[0];
        int maxSize = items.length;
        if (size == items.length / 4) maxSize = items.length / 2;
        T[] tmps = (T[]) new Object[maxSize];
        for (int i = 1; i < size; i++) {
            tmps[i - 1] = items[i];
        }
        items = tmps;
        size--;
        return tmp;
    }


    private T[] reSizeArr(int arrLength) {
        T[] tmps = (T[]) new Object[arrLength];
        for (int i = 0; i < size; i++) {
            tmps[i] = items[i];
        }
        return tmps;
    }


    public static void main(String[] args) {
        MyQueueWithArr<String> strings = new MyQueueWithArr<>();
        strings.enqueue("1");
        strings.enqueue("2");
        strings.enqueue("3");
        strings.enqueue("4");
        strings.enqueue("5");
        strings.dequeue();
        for (String str : strings) {
            System.out.println(str);
        }
    }
}
