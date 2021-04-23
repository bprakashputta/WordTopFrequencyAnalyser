package word.topfrequencyanalyser;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskManager {

    private int threadCount;
    private ExecutorService executorService;

    public TaskManager(int threadCount){
        this.threadCount = threadCount;
        executorService = Executors.newFixedThreadPool(threadCount);
    }

    public void waitTillQueueIsFreeAndAddTask(Runnable runnable){
        while(getQueueSize() >= threadCount)
        {
            try {
                System.out.println("Sleeping");
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        addTask(runnable);
    }

    private int getQueueSize() {
        ThreadPoolExecutor executor = (ThreadPoolExecutor)(executorService);
        return executor.getQueue().size();
    }

    private void addTask(Runnable runnable) {
        this.executorService.submit(runnable);
    }

    public static void main(String[] args) {

        System.out.println("This is Bhanu Prakash, Running FIleUtility at "+ new Date().toString());
//        ExecutorService executorService = Executors.newFixedThreadPool(1000);
//        executorService.submit(new RunnableExample("Thread_A",0,500));

        //1. Task Manager for N threads to run parallely
        //2. Let's say I want Main Thread to pause when there is enough queue size!!

        TaskManager taskManager = new TaskManager(8);
        for(int i=0;i<1;i++)
        {
//            String filePath = "/home/snowflake3509/IdeaProjects/WordTopFrequencyAnalyser/src/main/java/word/topfrequencyanalyser/IndianNationalAnthem.txt";
            String filePath = "/home/theharrypotter1999/IdeaProjects/JavaProjects/WordTopFrequencyAnalyser/data/practice/file/IndianNationalAnthem";
            FileReaderRunnable temp = new FileReaderRunnable("Thread_"+i,filePath);
            taskManager.waitTillQueueIsFreeAndAddTask(temp);
        }
    }
}
