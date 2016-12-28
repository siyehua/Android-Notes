package com.example.chapter2;

/**
 * 堆排序
 * Created by siyehua on 2016/12/26.
 */
public class MyHeap {
    public static void sort(Comparable[] a) {
        int n = a.length;
        for (int i = n / 2; i >= 1; i--) {
            //注意n-1,此时n是数组a的长度,不是a真实的
            sink(a, i, n - 1);
        }
        while (n > 1) {
            //交换第一个数和最后一个
            //注意下标从0开始,到length-1
            BaseSort.exch(a, 0, --n);

            //下沉,交换的元素要下沉重新排序
            //需要下沉的是1,下沉的是之前交换的前一个元素
            sink(a, 0, n - 1);
        }
    }

    private static void sink(Comparable[] a, int k, int n) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && BaseSort.less(a[j], a[j + 1])) j++;
            if (!BaseSort.less(a[k], a[j])) break;
            BaseSort.exch(a, k, j);
            k = j;
        }
    }

    public static void main(String[] args) {
        String[] data = new String[]{"E", "A", "S", "Y", "Q", "E", "S", "T", "I", "O", "N"};
        MyHeap.sort(data);
        for (String str : data) {
            System.out.println(str);
        }
    }
}
