package com.vaecn.search;

import com.vaecn.sort.SortUtils;

import java.util.Arrays;
import java.util.function.IntPredicate;

/**
 * Created by pansifan on 17/9/28.
 */
public class PredicateSort {

    public static void predicateSort(int[] array, IntPredicate predicate) {
        int unsuit = 0;//条件不成立
        int suit = array.length - 1;//条件成立
        while (unsuit < suit) {
            while (unsuit < suit && !predicate.test(array[unsuit])) {
                unsuit++;
            }

            while (unsuit < suit && predicate.test(array[suit])) {
                suit--;
            }

            SortUtils.swap(array, unsuit, suit);
        }
    }

    public static void main(String[] args) {
        int[] array = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        predicateSort(array, value -> (value & 1) == 0);
        System.out.println(Arrays.toString(array));
        predicateSort(array, value -> value % 3 == 0);
        System.out.println(Arrays.toString(array));
    }
}
