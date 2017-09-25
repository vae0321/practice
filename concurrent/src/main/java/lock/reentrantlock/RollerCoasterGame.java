package lock.reentrantlock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by sifan on 2017/9/25.
 */
public class RollerCoasterGame {

    private final static int MAX_SIZE = 200;
    private List<Person> personQueue = new ArrayList<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition notFull = lock.newCondition();
    private Condition notEmpty = lock.newCondition();

    public void queue(Person person) {
        try {
            lock.lock();
            while (MAX_SIZE == personQueue.size()) {
                notFull.await();
            }
            personQueue.add(person);
            System.out.println(person.getName() + " 进入排队队伍...");
            Thread.sleep(500);
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void play(Person person) {
        try {
            lock.lock();
            while (0 == personQueue.size()) {
                notEmpty.await();
            }
            personQueue.remove(person);
            Thread.sleep(1000);
            System.out.println("终于轮到 " + person.getName() + " 啦！去玩咯~");
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    @AllArgsConstructor
    @Data
    public static class Person {
        private String name;
    }

    public static void main(String[] args) {
        List<Person> personList = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            personList.add(new Person("游客" + i));
        }

        RollerCoasterGame player = new RollerCoasterGame();
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(() -> {
            for (int i = 0; i < 400; i++) {
                player.queue(personList.get(i));
            }
        });
        executorService.execute(() -> {
            for (int i = 0; i < 400; i++) {
                player.play(personList.get(i));
            }
        });
    }
}
