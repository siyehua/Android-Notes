package com.example;

/**
 * Created by huangxk on 2016/12/10.
 */
public class UnionFind {
    int[] id;
    int[] treeSize;

    int count;

    public UnionFind(int N) {
        id = new int[N];
        treeSize = new int[N];
        count = N;
        for (int i = 0; i < N; i++) {
            id[i] = i;
            treeSize[i] = 1;
        }
    }

    public int getCount() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int find(int p) {
        if (p != id[p]) p = id[p];
        return p;
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot) return;
        if (treeSize[pRoot] < treeSize[qRoot]) {
            id[pRoot] = qRoot;
            treeSize[qRoot] += treeSize[pRoot];
        } else {
            id[qRoot] = pRoot;
            treeSize[pRoot] += treeSize[qRoot];
        }
        count--;
    }
}
