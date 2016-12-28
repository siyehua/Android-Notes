package com.example.chapter2;

/**
 * selection sort
 * Created by siyehua on 2016/12/17.
 */
public class MySelection {

    public static void sort(Comparable[] a) {

        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (BaseSort.less(a[j], a[min])) {
                    min = j;
                }
            }
            BaseSort.exch(a, i, min);
        }
    }

    public static void main(String[] args) {
        Integer[] a = {11, 102, 4, 6, 6, 24, 21, 20, 4, 1, 0};
        MySelection.sort(a);
        BaseSort.show(a);
    }
}
