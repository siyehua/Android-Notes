package com.example;


import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * API
 * <p>
 *          Stack()         创建一个空栈
 *  void    push(T item)    添加一个元素
 *  T       pop()           删除最近添加的元素
 *  boolean isEmpty()       栈是否为空
 *  int     size()          栈中的元素数量
 * </p>
 * Created by siyehua on 2016/12/3.
 */
public class MyStack<T> implements Iterable<T> {
    public static <T> MyStack<T> copy(MyStack<T> myStack) {
        MyStack<T> copyTMyStack = new MyStack<>();
        for (T tmp : myStack) {
            copyTMyStack.push(tmp);
        }
        return copyTMyStack;
    }

    private Node first;
    int number;

    @Override
    public Iterator<T> iterator() {
        return new MyStackAdapter();
    }


    public boolean isEmpty() {
        return number == 0;
    }

    public int size() {
        return number;
    }

    public T pop() {
        number--;
        Node oldFirst = first;
        first = first.next;
        return oldFirst.item;
    }

    public void push(T item) {
        number++;
        Node node = new Node();
        node.item = item;
        node.next = first;
        first = node;
    }

    /**
     * push item in front of the index.<br>
     * the index start with <b>0</b>.
     *
     * @param index 0 ~ size()
     * @param item  item
     */
    public void push(int index, T item) {
        if (index < 0 || index > size()) throw new RuntimeException("index must be 1 ~ size()");
        if (index == size()) {
            push(item);
            return;
        }

        Node newItem = new Node();
        newItem.item = item;

        Node tmpNode = first;
        for (int i = size() - index; i > 1; i--) {
            tmpNode = tmpNode.next;
        }
        if (tmpNode.next != null) newItem.next = tmpNode.next;
        tmpNode.next = newItem;
        number++;
    }

    /**
     * delete item<br>
     * the index start with <b>0</b>.
     *
     * @param k index (0 ~ size()-1)
     * @return item
     */
    public T delete(int k) {
        if (k < 0 || k > size() - 1) throw new RuntimeException("k must be 0 ~ size()-1");
        if (k == size() - 1) return pop();
        Node lastNode = first;
        for (int i = size() - 1 - k; i > 1; i--) {
            lastNode = lastNode.next;
        }
        T result = lastNode.next.item;
        lastNode.next = lastNode.next.next;
        number--;
        return result;
    }

    private class Node {
        T item;
        Node next;
    }


    private class MyStackAdapter implements Iterator<T> {
        private int currentSize;
        private Node current = first;

        public MyStackAdapter() {
            currentSize = number;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (currentSize != number)
                throw new ConcurrentModificationException("Can't modification data when foreach.");
            T tmp = current.item;
            current = current.next;
            return tmp;
        }

        @Override
        public void remove() {
        }
    }

    public static void main(String[] agrs) {
        MyStack<String> myStack = new MyStack<>();
        for (int i = 0; i < 10; i++) {
            myStack.push(i + 1 + "");
        }
//        myStack.push(11, "new data");
//        System.out.println("delete: " + myStack.delete(10));
        for (String tmp : myStack) {
            myStack.push("123");
            System.out.println(tmp);
        }
    }
}
