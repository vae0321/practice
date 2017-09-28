package com.vaecn.sort;

/**
 * Created by sifan on 2017/9/28.
 */
public class BubblingSort {

    public static void sort(int[] array) {
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < (array.length - 1 - i); j++) {
                if (array[j] > array[j + 1]) {
                    SortUtils.swap(array, j, j + 1);
                }
            }
        }
    }
}
