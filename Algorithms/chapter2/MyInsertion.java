package com.example.chapter2;

/**
 * insert sort
 * Created by siyehua on 2016/12/17.
 */
public class MyInsertion {
    public static void sort(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            for (int j = i; j > 0 && BaseSort.less(a[j], a[j - 1]); j--) {
                BaseSort.exch(a, j, j - 1);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] a = {11, 102, 4, 6, 6, 24, 21, 20, 4, 1, 0};
        MyInsertion.sort(a);
        BaseSort.show(a);
    }
}
