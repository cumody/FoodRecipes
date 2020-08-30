package com.mahmoudshaaban.foodrecipes;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AppExecutors {

    public static AppExecutors instance;
    public static AppExecutors getInstance() {
        if (instance == null) {
            instance = new AppExecutors();
        }
        return instance;
    }

    //  Executors is => An object that executes submitted Runnable tasks. This interface provides
    //  a way of decoupling task submission from the mechanics of how each task will be run,
    //  including details of thread use, scheduling, etc. An Executor is normally used instead of explicitly creating threads.
    //  For example, rather than invoking new Thread(new(RunnableTask())).start() for each of a set of tasks, you might use:
    // Executor executor = anExecutor;
    // executor.execute(new RunnableTask1());
    // executor.execute(new RunnableTask2());

    //  ExecutorService which can schedule tasks to run after a delay,
    //  or to execute repeatedly with a fixed interval of time in between each execution.
    //  Tasks are executed asynchronously by a worker thread, and not by the thread handing the task to the ScheduledExecutorService.
    private final ScheduledExecutorService mNetworkIo = Executors.newScheduledThreadPool(3);


    public ScheduledExecutorService netWorkIo(){
        return  mNetworkIo;
    }
}
