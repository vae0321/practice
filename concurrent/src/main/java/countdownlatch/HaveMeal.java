package countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.log4j.Log4j2;

/**
 * Created by sifan on 2017/9/22.
 */
@Log4j2
public class HaveMeal {

    private List<Cook> cooks = new ArrayList<>();
    private CountDownLatch latch;

    public void haveMeal() {
        latch = new CountDownLatch(3);
        cooks.add(new CookMeat("Mr. Zhang", "beaf", latch));
        cooks.add(new CookVegetables("Mr. Liu", "cauliflower", latch));
        cooks.add(new CookMeat("Mr. Jiang", "beancurd soup", latch));

        ExecutorService service = Executors.newFixedThreadPool(3);
        for (Cook cook : cooks) {
            service.execute(cook);
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            log.error("latch be interrupted", e);
        }

        boolean canHaveMeal = true;

        for (Cook cook : cooks) {
            if (!cook.isFinished()) {
                canHaveMeal = false;
            }
        }

        if (canHaveMeal) {
            System.out.println("Ahh all cook have be ready, i can have my meal");
        } else {
            System.out.println("Oh, my god. some meal have not be finished, i must be hungry");
        }
    }
}
