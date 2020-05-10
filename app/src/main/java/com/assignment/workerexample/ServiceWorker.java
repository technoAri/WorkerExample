package com.assignment.workerexample;

import android.graphics.Bitmap;

import java.util.concurrent.TimeUnit;

interface Task<T> {
    T onExecuteTask();
    void onTaskComplete(Bitmap result);
}

public class ServiceWorker{

    String task;
    Bitmap bitmap;
    static int dataReceivedTask1Count = 0;
    static int dataReceivedTask2Count = 0;
    public ServiceWorker(String task) {
        this.task = task;
    }

    public Task addTask(final Task<Bitmap> task) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    task.onExecuteTask();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        if(dataReceivedTask1Count <= 1 && dataReceivedTask1Count <= 1)
            try {
                TimeUnit.MILLISECONDS.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        task.onTaskComplete(bitmap);
        return task;
    }

}
