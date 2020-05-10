package com.assignment.workerexample;

import android.graphics.Bitmap;
import android.os.AsyncTask;

interface Task<T> {
    T onExecuteTask();
    void onTaskComplete(Bitmap result);
}

public class ServiceWorker{

    String task;
    public ServiceWorker(String task) {
        this.task = task;
    }

    public Task addTask(Task<Bitmap> task) {
        return task;
    }

//    @Override
//    public void run() {
//        addTask(new Task<Bitmap>() {
//            @Override
//            public Bitmap onExecuteTask() {
//                return null;
//            }
//
//            @Override
//            public void onTaskComplete(Bitmap result) {
//
//            }
//        });
//    }

//    @Override
//    public Object onExecuteTask() {
//        return null;
//    }
//
//    @Override
//    public void onTaskComplete(Bitmap result) {
//
//    }
//
//    class Test extends AsyncTask<void, void, void> {
//
//
//        @Override
//        protected void doInBackground(void... voids) {
//
//        }
//    }

}
