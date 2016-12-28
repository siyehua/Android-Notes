package com.example.chapter2;

/**
 * shell sort
 * Created by siyehua on 2016/12/18.
 */
public class MyShell {
    public static void sort(Comparable[] a) {
        int length = a.length;
        int h = 1;
        while (h < length / 3) h = h * 3 + 1;
        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && BaseSort.less(a[j], a[j - h]); j -= h) {
                    BaseSort.exch(a, j, j - h);
                }
            }
            h /= 3;
        }
    }

    public static void main(String[] args) {
        Integer[] a = {11, 102, 4, 6, 6, 24, 21, 20, 4, 1, 0};
        MyShell.sort(a);
        System.out.println(BaseSort.isSorted(a));
        BaseSort.show(a);
    }
}
