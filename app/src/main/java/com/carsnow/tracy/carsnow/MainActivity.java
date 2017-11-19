package com.carsnow.tracy.carsnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public String url= "https://raw.githubusercontent.com/tracygipson/CarsNow/master/cars.json";
    public Button btn;
    public TextView txt;
    public OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn1);
        txt = (TextView) findViewById(R.id.txt);


    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getcardata();
        }
    });

    }

    public void getcardata(){
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txt.setText("Failed");
                    }
                });
            }

            @Override
            public void onResponse(Call call,final Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            txt.setText(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

});

}



}
