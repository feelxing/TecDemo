package thread;

/**
 * Created by MEV on 2017/3/31.
 */
public class Job extends Thread {
    private Object service;
    private int bookId;
    public Job(Object service,Integer bookId){
        this.service=service;
        this.bookId=bookId;
    }
    @Override
    public void run(){
        this.handle();
        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void handle(){
        System.out.println(bookId);
    }
}
