package com.vaecn.search;

/**
 * Created by pansifan on 17/9/28.
 */
public class FindIntBinary1Num {

    public static int findBinary1Count(int num) {
        int count = 0;
        while (num != 0) {
            num = (num - 1) & num;
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        int num = 1081;
        int count = findBinary1Count(num);
        System.out.println(num + "  " + Integer.toBinaryString(num) + "  " + count);
    }
}
