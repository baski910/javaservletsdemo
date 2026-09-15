import java.time.Instant;
class WorkerThread implements Runnable{
    private final String threadname;
    private final long timegap;

    public WorkerThread(String threadname,long timegap){
        this.threadname = threadname;
        this.timegap = timegap;
    }

    public void printTime(String threadname,long timegap,int counter){
        
        while (counter >0){
            try{
                Instant curtime = Instant.now();
                System.out.println(threadname+":"+curtime);
                Thread.sleep(timegap);
                counter--;
            }catch(InterruptedException e){
                System.out.println(e.getMessage());
            }
            
        }
    }
    public void run(){
        System.out.println("starting thread "+threadname);
        printTime(threadname, timegap, 5);
        System.out.println("exiting thread "+threadname);
    }
}

public class Example5 {
    public static void main(String args[]){
        Thread th1=new Thread(new WorkerThread("thread1",1000));
        Thread th2=new Thread(new WorkerThread("thread2",2000));
        th1.start();
        th2.start();
    }    
}
