package com.vaecn.sort;

/**
 * Created by sifan on 2017/9/28.
 */
public class QuickSort {

    public static void sort(int[] array) {
        if (array.length <= 1) {
            return;
        }
        quickSort(array, 0, array.length - 1);
    }

    public static void sort2(int[] array) {
        if (array.length <= 1) {
            return;
        }
        quickSort2(array, 0, array.length - 1);
    }

    public static void sort3(int[] array) {
        if (array.length <= 1) {
            return;
        }
        quickSort3(array, 0, array.length - 1);
    }

    public static void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int p = partition(array, low, high);
            quickSort(array, low, p - 1);
            quickSort(array, p + 1, high);
        }
    }

    public static int partition(int[] array, int low, int high) {
        int base = array[low];
        while (low < high) {
            while (low < high && array[high] >= base) {
                high--;
            }
            array[low] = array[high];
            while (low < high && array[low] < base) {
                low++;
            }
            array[high] = array[low];
        }
        array[low] = base;
        return low;
    }

    public static void quickSort3(int[] array, int low, int high) {
        if (low < high) {
            int p = partition3(array, low, high);
            quickSort(array, low, p - 1);
            quickSort(array, p + 1, high);
        }
    }

    public static int partition3(int[] array, int low, int high) {
        int base = array[low];
        while (low < high) {
            while (low < high && array[high] >= base) {
                high--;
            }
            while (low < high && array[low] < base) {
                low++;
            }
            SortUtils.swap(array, low, high);
        }
        return low;
    }

    public static void quickSort2(int[] array, int low, int high) {
        if (low < high) {
            int p = partition2(array, low, high);
            quickSort(array, low, p);
            quickSort(array, p + 1, high);
        }
    }

    public static int partition2(int[] array, int low, int high) {
        int base = array[low];
        int mark = low;
        SortUtils.swap(array, low, high - 1);
        for (int i = low; i < high - 1; i++) {
            if (array[i] > base) {
                SortUtils.swap(array, i, mark);
                mark++;
            }
        }
        SortUtils.swap(array, high - 1, mark);
        return mark;
    }
}
