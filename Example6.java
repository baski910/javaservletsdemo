public class Example6 {
    /* shared variable */
    private int sharedVariable = 0;

    private final Object lock = new Object();

    private boolean isRunning = true;

    public void updateVariable (){
        while (isRunning){
            try {
                Thread.sleep(500);

            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }

            synchronized(lock){
                sharedVariable++;
                System.out.println("Updater thread value updated to "+sharedVariable);

                if (sharedVariable>5){
                    isRunning = false; // simulate a situation to end thread
                }
            }
        }
    }
    
    public void displayVariable() {
        int lastSeenValue = -1;

        while (isRunning || lastSeenValue < sharedVariable){
            synchronized(lock){
                if (sharedVariable != lastSeenValue){
                    System.out.println("Display thread: Read Value : "+sharedVariable);
                    lastSeenValue = sharedVariable;
                }
            }

            try {
                Thread.sleep(500);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
            }
        }
    }


    public static void  main(String args[]){
        Example6 demo = new Example6();

        Thread updaterThread = new Thread(demo::updateVariable);
        Thread displayThread = new Thread(demo::displayVariable);

        updaterThread.start();
        displayThread.start();
    }
}