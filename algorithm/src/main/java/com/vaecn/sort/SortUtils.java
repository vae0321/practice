package com.vaecn.sort;

/**
 * Created by sifan on 2017/9/28.
 */
public class SortUtils {

    public static void swap(int[] array, int a, int b) {
        int tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }
}
