package thread;


import java.util.concurrent.TimeUnit;

/**
 * Created by MEV on 2017/3/31.
 */
public class Main {
    public static void main(String[] args) {
 /*       int book1=1;
        int book2=2;
        int book3=3;
        Job job1=new Job(new Object(),book1);
        ThreadPool.add(job1);

        Job job2=new Job(new Object(),book2);
        ThreadPool.add(job2);
        Job job3=new Job(new Object(),book3);
        ThreadPool.add(job3);*/
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String threadName = Thread.currentThread().getName();
            System.out.println("Hello " + threadName);

        };

        task.run();
        Thread thread = new Thread(task);
        thread.start();
        System.out.println("aaaaaaaaaaaaa");
    }
}
