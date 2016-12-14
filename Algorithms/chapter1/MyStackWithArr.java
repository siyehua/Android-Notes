package com.example;

import java.util.Iterator;

/**
 * API
 * <p>
 * Stack()         创建一个空栈
 * void    push(T item)    添加一个元素
 * T       pop()           删除最近添加的元素
 * boolean isEmpty()       栈是否为空
 * int     size()          栈中的元素数量
 * </p>
 * Created by siyehua on 2016/12/3.
 */
public class MyStackWithArr<T> implements Iterable<T> {
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }

    private class MyIterator implements Iterator<T> {
        private int currentIndex = number - 1;

        @Override
        public boolean hasNext() {
            return currentIndex != -1;
        }

        @Override
        public T next() {
            return items[currentIndex--];
        }

        @Override
        public void remove() {

        }
    }

    private int number;
    private T[] items = (T[]) new Object[1];

    public int size() {
        return number;
    }

    public boolean isEmpty() {
        return number != 0;
    }

    public void add(T item) {
        if (number == items.length) {
            items = reSizeArr(number * 2);
        }
        items[number++] = item;
    }

    public T pop() {
        T tmp = items[--number];
        items[number] = null;
        if (number == items.length / 4) items = reSizeArr(items.length / 2);
        return tmp;
    }

    private T[] reSizeArr(int maxLength) {
        T[] tmps = (T[]) new Object[maxLength];
        for (int i = 0; i < items.length; i++) {
            tmps[i] = items[i];
        }
        return tmps;
    }

    public static void main(String[] args) {
        MyStackWithArr<String> strings = new MyStackWithArr<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.pop();
        strings.add("5");
        for (String str : strings) {
            System.out.println(str);
        }
    }
}
