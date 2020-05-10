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
    static Boolean isDataReceived = false;
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
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        task.onTaskComplete(bitmap);
        return task;
    }

}
