package com.example.chapter2;

/**
 * quick sort
 * Created by huangxk on 2016/12/24.
 */
public class MyQuick {
    public static void sort(Comparable[] a) {

        //let the arrays is random.
        //让数组是随机是非常有必要的,能避免最坏的情况
        sort(a, 0, a.length - 1);
    }

    /**
     * 排序
     * <p>快速排序在小数组的情况下要比插入排序慢</p>
     * <p>因此,可以将<br>if (hi <= lo) return;<br>
     * 改为<br>
     * if(hi <= ho + M) (Insterion.sort(a,lo,hi); return;)
     * <br>
     * M的值和系统有关,一般在5~15之间.
     * </p>
     *
     * @param a  数组
     * @param lo 下限
     * @param hi 上限
     */
    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }


    /**
     * 数组排序之前尽量是随机的,当a[lo]为最小值时,则快速排序是最坏的结果
     * <p>最好的情况是每次都正好能将数组对半分</p>
     * <p>所以适应的取中位数能加快算法效率,代价是需要计算中位数
     * <br>研究表明取样3并用大小居中的元素切分的效果(三取样切分)</p>
     *
     * @param a  数组
     * @param lo 下限
     * @param hi 上限
     * @return 切割元素下标
     */
    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo, j = hi + 1;
        Comparable v = a[lo];
        while (true) {
            /**
             * 左侧的边距检查是多余的(j == ho),<br>
             * 因为v取的是a[lo],所以j永远不可能等于lo
             * <p>
             * 右侧的边距检查,可以在开始打乱数组的时候,<br>
             * 取最大值放置到a[a.length - 1]处,则也不需要检查右侧边距
             * </p>
             */
            while (BaseSort.less(a[++i], v)) if (i == hi) break;
            while (BaseSort.less(v, a[--j])) if (j == lo) break;
            if (i >= j) break;
            BaseSort.exch(a, i, j);
        }
        BaseSort.exch(a, lo, j);
        return j;
    }


    /**
     * 对于拥有大量重复的元素时,快速排序无法保证最佳性能
     * <p>可以使用三向切分的快速排序</p>
     * <p>原理是把重复的元素移动至之间,同时修改lo与hi
     * <br>保证重复元素不参与排序,则极大的提高了快速排序的性能</p>
     *
     * @param a  数组
     * @param lo 上限
     * @param hi 下限
     *           <p>(注意修改sort3方法名)</p>
     */
    private static void sort3(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int lt = lo, i = lo + 1, gt = hi;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp < 0) BaseSort.exch(a, lt++, i++);
            else if (cmp > 0) BaseSort.exch(a, i, gt--);
            else i++;
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    public static void main(String[] args) {
        String[] data = new String[]{"E", "A", "S", "Y", "Q", "E", "S", "T", "I", "O", "N"};
        MyQuick.sort(data);
        for (String str : data) {
            System.out.println(str);
        }

    }
}
