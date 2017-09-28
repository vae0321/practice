/**
 * Created by sifan on 2017/9/27.
 */
public class FindJavaThreadInTaskManager {
    public static void main(String[] args) {
        Thread thread = new Thread(new Worker());
        thread.start();
    }
    static class Worker implements Runnable {
        @Override
        public void run() {
            while (true) {
                System.out.println("Thread Name:" + Thread.currentThread().getName());
            }
        }
    }
}
