package com.vaecn;

/**
 * Created by sifan on 2017/9/29.
 */
public class Helix {

    private static int N = 0;

    public static int helix(int x, int y) {
        if ((x == 1) && (y == 1)) return 10;
        if ((x - y >= 1) && (x + y <= N)) return (1 + helix(x + 1, y));
        if ((x - y >= 1) && (x + y > N)) return (1 + helix(x, y + 1));
        if ((x - y < 1) && (x + y <= N + 1)) return (1 + helix(x, y - 1));
        if ((x - y < 1) && (x + y) > N + 1) return (1 + helix(x - 1, y));
        else return 0;
    }

    public static void main(String[] args) {
        N = 5;//输入int值;
        for (int x = 1; x <= N; x++) {
            for (int y = 1; y <= N; y++) {
                System.out.print(helix(x, y) + "\t");
            }
            System.out.println("");
        }
    }
}