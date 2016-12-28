package com.example.chapter2;

/**
 * 自底向上的归并排序
 * Created by huangxk on 2016/12/22.
 */
public class MyMergeBU {
    private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            } else if (j > hi) {
                a[k] = aux[i++];
            } else if (BaseSort.less(aux[j], aux[i])) {
                a[k] = a[j++];
            } else {
                a[k] = a[i++];
            }
        }
    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        for (int i = 1; i < a.length; i = i + i) {
            for (int j = 0; j < a.length - i; j += i + i) {
                merge(a, aux, j, j + i - 1, Math.min(j + i + i - 1, a.length - 1));
            }
        }
    }

//    public static void sort(Comparable[] a) {
//        int N = a.length;
//        Comparable[] aux = new Comparable[N];
//        for (int sz = 1; sz < N; sz = sz + sz) {
//            for (int lo = 0; lo < N - sz; lo += sz + sz) {
//                merge(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
//            }
//        }
//    }

    public static void main(String[] args) {
        String[] data = new String[]{"E", "A", "S", "Y", "Q", "E", "S", "T", "I", "O", "N"};
        MyMergeBU.sort(data);
        for (String str : data) {
            System.out.println(str);
        }

    }
}
