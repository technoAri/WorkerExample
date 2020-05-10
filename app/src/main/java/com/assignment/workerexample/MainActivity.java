package com.assignment.workerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ServiceWorker serviceWorker1 = new ServiceWorker("service_worker");
    ServiceWorker serviceWorker2 = new ServiceWorker("service_worker");
    OkHttpClient okHttpClient = new OkHttpClient();
    ImageView imageView1, imageView2;
    Bitmap bitmapImg;
//    Task taskForWorker1 = ;

//    ExecutorService pool = Executors.newFixedThreadPool(2);

    public static final String IMAGE_1= "https://cdn.mos.cms.futurecdn.net/SkEZSnqyaZXKLBvyTB8z-1200-80.jpg";
    public static final String IMAGE_2= "https://www.apple.com/ac/structured-data/images/knowledge_graph_logo.png?201903191911";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.iv_1);
        imageView2 = findViewById(R.id.iv_2);
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);

//        pool.execute(serviceWorker1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImage1AndSet();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchImage2AndSet();
            }
        });
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try  {
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        thread.start();

//        Thread thread = new Thread() {
//            @Override
//            public void run() {
//
//            }
//        };
//        thread.start();


//        Task taskForWorker2 = new Task<String>() {
//            //method implementations
//        }
    }

    private void fetchImage1AndSet() {
        final Task taskForWorker1 = new Task<Bitmap>() {
            @Override
            public Bitmap onExecuteTask() {
                Bitmap bitmap = null;
                Request request = new Request.Builder().url(IMAGE_1).build();
                Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    bitmapImg = bitmap;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            public void onTaskComplete(Bitmap result) {
                System.out.println("RESULT"+result);
                imageView1.setImageBitmap(result);
            }
        };

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    taskForWorker1.onExecuteTask();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        if(bitmapImg != null) {
            taskForWorker1.onTaskComplete(bitmapImg);
        }


//        taskForWorker1.onTaskComplete();
//        taskForWorker1 = new Task<Bitmap>() {
//            @Override
//            public Bitmap onExecuteTask() {
//                Bitmap bitmap = null;
//                Request request = new Request.Builder().url(IMAGE_1).build();
//                Response response = null;
//                try {
//                    response = okHttpClient.newCall(request).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    bitmap = BitmapFactory.decodeStream(response.body().byteStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return bitmap;
//            }
//
//            @Override
//            public void onTaskComplete(Bitmap result) {
//                System.out.println("RESULT"+result);
//                imageView1.setImageBitmap(result);
//            }
//            //method implementations
//        };


//        serviceWorker1.addTask(new Task<Bitmap>() {
//            @Override
//            public Bitmap onExecuteTask() {
//                //Fetching image1 through okhttp
//                Bitmap bitmap = null;
//                Request request = new Request.Builder().url(IMAGE_1).build();
//                Response response = null;
//                try {
//                    response = okHttpClient.newCall(request).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                try {
//                    bitmap = BitmapFactory.decodeStream(response.body().byteStream());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return bitmap;
//            }
//
//            @Override
//            public void onTaskComplete(Bitmap result) {
//                //Set bitmap to image 1
//                System.out.println("RESULT"+result);
//                imageView1.setImageBitmap(result);
//            }
//        });
    }

    private void fetchImage2AndSet() {
        serviceWorker2.addTask(new Task<Bitmap>() {
            @Override
            public Bitmap onExecuteTask() {
                //Fetching image2 through okhttp
                Bitmap bitmap = null;
                Request request = new Request.Builder().url(IMAGE_2).build();
                Response response = null;
                try {
                    response = okHttpClient.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            public void onTaskComplete(Bitmap result) {
                //Set bitmap to image 2
                imageView2.setImageBitmap(result);
            }
        });
    }
}
