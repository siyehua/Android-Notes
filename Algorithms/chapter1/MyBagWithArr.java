package com.example;


import java.util.Iterator;

/**
 * Created by huangxk on 2016/12/13.
 */
public class MyBagWithArr<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new MyItertor();
    }

    private class MyItertor implements Iterator<T> {
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

    public void add(T item) {
        if (size == items.length) {
            T[] tmps = (T[]) new Object[items.length * 2];
            for (int i = 0; i < items.length; i++) {
                tmps[i] = items[i];
            }
            items = tmps;
        }
        items[size++] = item;
    }

    public static void main(String[] args) {
        MyBagWithArr<String> strings = new MyBagWithArr<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");
        for (String str : strings) {
            System.out.println(str);
        }
    }
}
