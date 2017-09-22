package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * Created by sifan on 2017/9/22.
 */
public class CookSoup extends Cook {

    public CookSoup(String cookerName, String mealName, CountDownLatch latch) {
        super(cookerName, mealName, latch);
    }

    @Override
    public void cook() throws Exception {
        System.out.println("cooker " + cookerName + " is cooking soup " + mealName);
        Thread.sleep(1000);
    }
}
