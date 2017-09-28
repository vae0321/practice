package com.vaecn.sort;

import java.util.Arrays;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by sifan on 2017/9/28.
 */
public class SortTest {

    private int[] array;

    @Before
    public void before() {
        array = new Random().ints(20, 0, 100).toArray();
//        array = new int[]{1, 8, 7, 6, 3, 5, 9};
    }

    @Test
    public void testBubbling() {
        System.out.println("Source : " + Arrays.toString(array));
        BubblingSort.sort(array);
        System.out.println("Sorted : " + Arrays.toString(array));
    }

    @Test
    public void testQuickSort1() {
        System.out.println("Source : " + Arrays.toString(array));
        QuickSort.sort(array);
        System.out.println("Sorted : " + Arrays.toString(array));
    }

    @Test
    public void testQuickSort2() {
        System.out.println("Source : " + Arrays.toString(array));
        QuickSort.sort2(array);
        System.out.println("Sorted : " + Arrays.toString(array));
    }

    @Test
    public void testQuickSort3() {
        System.out.println("Source : " + Arrays.toString(array));
        QuickSort.sort3(array);
        System.out.println("Sorted : " + Arrays.toString(array));
    }
}
