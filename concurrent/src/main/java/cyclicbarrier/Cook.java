package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by pansifan on 17/9/24.
 */
public class Cook implements Runnable {

    private CyclicBarrier barrier;
    private String materialName;
    private long prepareTime;

    public Cook(CyclicBarrier barrier, String materialName, long prepareTime) {
        this.barrier = barrier;
        this.materialName = materialName;
        this.prepareTime = prepareTime;
    }

    @Override
    public void run() {
        System.out.println("准备食材: " + materialName);
        try {
            Thread.sleep(prepareTime);
            barrier.await();

            System.out.println(materialName + ": 我要下油锅了..");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> System.out.println("蒜蓉空心菜准备好了，下锅"));
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        executorService.execute(new Cook(barrier, "蒜末",1000));
        executorService.execute(new Cook(barrier, "辣椒",3000));
        executorService.execute(new Cook(barrier, "空心菜",6000));
        executorService.shutdown();
    }
}
