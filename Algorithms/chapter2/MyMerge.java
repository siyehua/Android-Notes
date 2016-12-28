package com.example.chapter2;

/**
 * 自顶向下的归并方法
 * Created by siyehua on 2016/12/20.
 */
public class MyMerge {

    /**
     * 基本归并方法
     *
     * @param a   归并的总数组
     * @param lo  左下限
     * @param mid 右上限
     * @param hi  中间值(用于区分左半边与右半边)
     */
    public static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;

        //复制需要归并的数组数据
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        /**
         * 左半边取尽(取右半边元素)
         * 右半边取尽(取左半边元素)
         * 右半边元素小于左半边(取右半边)
         * 右半边元素大于左半边(取左半边)
         */
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (BaseSort.less(aux[j], aux[i])) {
                a[k] = aux[j++];
            } else a[k] = aux[i++];
        }

    }

    public static void sort(Comparable[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        //可以判断a[mid] 与 a[mid+1]的大小再决定是否要合并
        //因为合并之前,左右两边的数组肯定是有序的,
        //所以可以通过判断左边的最大数,与右边的最小数,直接决定是否要合并
        if (BaseSort.less(a[mid], a[mid + 1])) return;
        System.out.println("sort  " + lo + "  " + hi);
        merge(a, aux, lo, mid, hi);
    }

    public static void main(String[] args) {
//        String[] data = new String[]{"E", "A", "S", "Y", "Q", "E", "S", "T", "I", "O", "N"};
        String[] data = {"D", "B", "C", "A"};
        MyMerge.sort(data);
        for (String str : data) {
            System.out.println(str);
        }
    }
}
