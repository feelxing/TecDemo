package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by MEV on 2017/3/31.
 */
public class ThreadPool {
    static ExecutorService service = Executors.newFixedThreadPool(10);

    public static void add(Thread thread) {
        service.execute(thread);
    }
}
