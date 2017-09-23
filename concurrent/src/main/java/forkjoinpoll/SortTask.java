package forkjoinpoll;

import lombok.NonNull;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Created by pansifan on 17/9/23.
 */
public class SortTask extends RecursiveTask<int[]> {

    private int[] array;

    public SortTask(@NonNull int[] array) {
        this.array = array;
    }

    @Override
    protected int[] compute() {
        int arrayLength = array.length;
        if (arrayLength > 1) {
            int half = arrayLength / 2;
            int[] leftHalf = Arrays.copyOf(array, half);
            int[] rightHalf = Arrays.copyOfRange(array, half, arrayLength);

            SortTask leftSortTask = new SortTask(leftHalf);
            SortTask rightSortTask = new SortTask(rightHalf);
            leftSortTask.fork();
            rightSortTask.fork();

            int[] leftResult = leftSortTask.join();
            int[] rightResult = rightSortTask.join();
            return merge(leftResult, rightResult);
        } else {
            return array;
        }
    }

    private int[] merge(int[] leftArray, int[] rightArray) {
        int leftLength = leftArray.length;
        int rightLength = rightArray.length;
        int totalLength = leftLength + rightLength;
        int[] tmp = new int[totalLength];
        int tmpC = 0;
        int leftC = 0;
        int rightC = 0;
        while (tmpC < totalLength) {
            if (rightC == rightLength ||
                    (leftC < leftLength && leftArray[leftC] <= rightArray[rightC])) {
                tmp[tmpC] = leftArray[leftC];
                leftC++;
            } else {
                tmp[tmpC] = rightArray[rightC];
                rightC++;
            }

            tmpC++;
        }
        System.out.println("left array: " + toString(leftArray) +
                "\nright array: " + toString(rightArray) +
                "\n---sort result: " + toString(tmp));
        return tmp;
    }

    private String toString(@NonNull int[] array) {
        StringBuilder sb = new StringBuilder("{ ");
        for (int i : array) {
            sb.append(i).append(" ");
        }
        sb.append("}");
        return sb.toString();
    }

    public static void main(String[] args) {
        int[] testArray = new int[]{1, 2, 9, 8, 15, 4, 6, 7, 5, 3, 2, 8, 19, 21};
        SortTask sortTask = new SortTask(testArray);
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask result = forkJoinPool.submit(sortTask);
        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        forkJoinPool.shutdown();
    }
}
