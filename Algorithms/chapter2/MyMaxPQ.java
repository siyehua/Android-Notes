package com.example.chapter2;

/**
 * 优先队列
 * <p>优先队列最重要的操作就是<b>删除最大元素</b>和<b>插入元素</b></p>
 * <p>优先队列可以</p>
 * Created by siyehua on 2016/12/25.
 */
public class MyMaxPQ<T extends Comparable<T>> {
    private T[] pq;
    private int count = 0;

    public MyMaxPQ() {
        pq = (T[]) new Comparable[1];
    }

    public MyMaxPQ(int max) {
        pq = (T[]) new Comparable[max + 1];
    }

    public MyMaxPQ(Comparable<T> a[]) {
        pq = (T[]) a;
    }

    public void insert(T value) {
        ++count;
        //数组越界时动态调整数组大小
        if (pq.length == count) {
            reSize(pq.length * 2);
        }
        pq[count] = value;
        swim(count);
    }

    public T max() {
        return pq[count];
    }

    public T delMax() {
        T tmp = pq[1];
        BaseSort.exch(pq, 1, count);
        pq[count--] = null;
        //数组真实数据过小时,适当减小其长度
        if (count <= pq.length / 4) {
            reSize(pq.length / 2);
        }
        sink(1);
        return tmp;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void reSize(int max) {
        T[] tmps = (T[]) new Comparable[max];
        for (int i = 0; i < pq.length; i++) {
            tmps[i] = pq[i];
        }
        pq = tmps;
    }

    /**
     * 上升
     * <p>当完全二叉树插入数值时,需要改变二叉树的结构来保证其特性</p>
     * <p>新插入的元素下上逐个寻找自己合适的位置</p>
     *
     * @param k 新插入元素的位置
     */
    private void swim(int k) {
        while (k > 1) {
            if (BaseSort.less(pq[k / 2], pq[k])) {
                BaseSort.exch(pq, k / 2, k);
            }
            k /= 2;
        }
    }

    /**
     * 下沉
     * <p>当最大元素被替换成其他元素时,需要改变二叉树的结构来保证其特性</p>
     * <p>检查其子节点是否比自己大,来寻找自己的位置</p>
     *
     * @param k 改变的元素位置
     */
    private void sink(int k) {
        while (k * 2 <= count) {
            int j = k * 2;
            if (j < count && BaseSort.less(pq[j], pq[j + 1])) {
                j++;
            }
            BaseSort.exch(pq, k, j);
            k = j;
        }
    }

    public static void main(String[] args) {
        MyMaxPQ<String> maxPQ = new MyMaxPQ<>(10);
        maxPQ.insert("R");
        maxPQ.insert("O");
        maxPQ.insert("G");
        maxPQ.insert("N");
        maxPQ.insert("I");
        maxPQ.insert("H");
        maxPQ.insert("E");
        maxPQ.insert("Q");
        maxPQ.insert("A");
        maxPQ.insert("P");
        maxPQ.insert("T");
        maxPQ.insert("S");
        System.out.print(maxPQ.delMax());


        System.out.print(maxPQ.size());
    }
}
