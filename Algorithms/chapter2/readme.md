#


排序

本章主要介绍了各种排序算法,包括:插入排序,选择排序,希尔排序,归并排序,快速排序,以及堆排序.

并对排序算法的各种特点进行了分析,包括时间复杂度,空间复杂度,以及稳定性等等

##


2.1初级排序算法

本节主要介绍了几个初级排序

基础排序算法工具类[BaseSort](/Algorithms/chapter2/BaseSort.java)

<b>注意所有排序算法都是基于该基础排序工具类</b>

选择排序[MySelection](/Algorithms/chapter2/MySelection.java)

插入排序[MyInsertion](/Algorithms/chapter2/MyInsertion.java)

希尔排序[MyShell](/Algorithms/chapter2/MyShell.java)

##


2.2归并排序

本节介绍了归并排序的特点

原地归并的抽象方法

```java
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
```

[自顶向下的归并方法](/Algorithms/chapter2/MyMerge.java)

[自底向上的归并方法](/Algorithms/chapter2/MyMergeBU.java)


##


快速排序

快速排序目前是最快的排序算法,它的重要性毋庸置疑.

快速排序也是一种分支的排序算法,核心在于切割(二分).

[标准快速排序](/Algorithms/chapter2/MyQuick.java)

快速排序要求输入是随机的,假设切割的第一个元素是最小值,则会达到最坏情况.

快速排序对有大量重复的元素的排序,表现也不好,阅读其代码可知,相等的元素,也会进行切割比较,
解决这个问题的方法,可以使用三向切分的快速排序

```java

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

```

##


优先队列

实际的应用中,我们总是希望队列能处理一下特殊情况,并不要求整个队列都要有序.

例如,删除最大值,删除最小元素等等.

这种数据类型叫优先队列.
优先队列最重要的操作就是插入元素和查找最大值.

数据结构|插入元素|删除最大元素
---|---|---
有序数组|N|1
无序数组|1|N
堆|logN|logN


从上表可以看出,我们使用堆(二叉树)来实现优先队列,能保证无论在插入还是查找最大元素,
都能高效的操作.

[基于堆的优先队列](/Algorithms/chapter2/MyMaxPQ.java)

从表格中,我们可以知道,优先队列能用最优的利用空间和时间,所以可以用它延伸出一个全新的排序算法

但是因为堆排序无法Ali用缓存,所以一般很少用到它.

[堆排序](/Algorithms/chapter2/MyHeap.java)


##


应用

本节总结了各个排序算法的特点,已经我们应该用哪种算法,还有这些算法在实际中的应用

###


各种排序算法的性能特点

算法|是否稳定|是否为原地排序|时间复杂度|空间复杂度|备注
---|---|---|---|---|---
选择排序|否|是|N平方|1|
插入排序|是|是|介于N~N平方之间|1|取决于输入元素的排序情况
希尔排序|否|是|NlogN? N的6/5次方?|1
快速排序|否|是|NlogN|lgN|运行效率由概率提供保证
三向快速排序|否|是|N~NlogN|lgN|运行效率由概率提供保证<br>同时取决于输入元素的分布情况
归并排序|是|否|NlogN|N|
堆排序|否|是|NlogN|1


