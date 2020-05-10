package com.assignment.workerexample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    ServiceWorker serviceWorker1 = new ServiceWorker("service_worker");
    ServiceWorker serviceWorker2 = new ServiceWorker("service_worker");
    OkHttpClient okHttpClient = new OkHttpClient();
    ImageView imageView1, imageView2;
    Bitmap bitmapImg1, bitmapImg2;
    ProgressDialog progress;
    private boolean isDataReceived1= false;

    public static final String IMAGE_1= "https://i.pinimg.com/originals/ef/be/75/efbe755d1f03e5011e926709651f13f2.jpg";
    public static final String IMAGE_2= "https://lh4.googleusercontent.com/proxy/ihUFMyxUTA1drq81PQWMZqvA73rdJNY4h8xEidhMZk__o7a3qxUNMo78JFGfkfNI5P0kGhebexwE6zNlzXCcMW1WZ5MPpEcl1cnoL-Nt1usajYcsVQd_tcXFhg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = findViewById(R.id.iv_1);
        imageView2 = findViewById(R.id.iv_2);
        Button button1 = findViewById(R.id.button_1);
        Button button2 = findViewById(R.id.button_2);

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
    }

    private void fetchImage1AndSet() {
        serviceWorker1.addTask(new Task<Bitmap>() {
            @Override
            public Bitmap onExecuteTask() {
                //Fetching image1 through okhttp
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
                    bitmapImg1 = bitmap;
                    ServiceWorker.dataReceivedTask1Count ++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            public void onTaskComplete(Bitmap result) {
                //Set bitmap to image 1
                result = bitmapImg1;
                imageView1.setVisibility(View.VISIBLE);
                imageView1.setImageBitmap(result);
                imageView2.setVisibility(View.INVISIBLE);
            }
        });
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
                    bitmapImg2 = bitmap;
                    ServiceWorker.dataReceivedTask2Count ++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            public void onTaskComplete(Bitmap result) {
                //Set bitmap to image 2
                result = bitmapImg2;
                imageView2.setVisibility(View.VISIBLE);
                imageView2.setImageBitmap(result);
                imageView1.setVisibility(View.INVISIBLE);
            }
        });
    }
}
