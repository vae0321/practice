package countdownlatch;

import java.util.concurrent.CountDownLatch;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

/**
 * Created by sifan on 2017/9/22.
 */
@Log4j2
public abstract class Cook implements Runnable {

    protected String cookerName;
    protected String mealName;
    @Getter
    private boolean finished;
    private CountDownLatch latch;

    public Cook(String cookerName, String mealName, @NonNull CountDownLatch latch) {
        this.cookerName = cookerName;
        this.mealName = mealName;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            cook();
            finished = true;
        } catch (Exception e) {
            log.error("oh, cooker {} cook {} failed.", cookerName, mealName, e);
        } finally {
            latch.countDown();
        }
    }

    public abstract void cook() throws Exception;
}
