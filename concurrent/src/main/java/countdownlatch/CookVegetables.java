package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sifan on 2017/9/22.
 */
public class CookVegetables extends Cook {

    public CookVegetables(String cookerName, String mealName, CountDownLatch latch) {
        super(cookerName, mealName, latch);
    }

    @Override
    public void cook() throws Exception {
        System.out.println("cooker " + cookerName + " is cooking vegetables " + mealName);
        Thread.sleep(1000);
    }
}
