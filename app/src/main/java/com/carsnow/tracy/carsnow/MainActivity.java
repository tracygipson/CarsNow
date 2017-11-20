package com.carsnow.tracy.carsnow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    int count;
    JSONObject jobj;
    JSONArray jary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.nextbtn);
        txt = (TextView) findViewById(R.id.title);
        connectiony();


    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            count = count + 1;
            if (count == 1) {
                firstdisplay(count);
                }
            else {
                getcardata();
            }
        }
    });
    }

    public void connectiony() {
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
                        String name = "";
                        try {
                            String data = response.body().string();
                            try {
                                jobj = new JSONObject(data);
                                jary = jobj.getJSONArray("cars");
                                for(int i=0;i<jary.length();i++){
                                    JSONObject jobj2 = jary.getJSONObject(i);
                                    name = jobj2.getString("name");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            txt.setText(name);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        });


    }

    public void firstdisplay(int count) {


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
                        String name = "";
                        try {
                            String data = response.body().string();


                            try {
                                JSONObject jobj = new JSONObject(data);
                                JSONArray jary = jobj.getJSONArray("cars");

                                for(int i=0;i<jary.length();i++){
                                    JSONObject jobj2 = jary.getJSONObject(i);
                                    name = jobj2.getString("name");
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            txt.setText(name);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

});

}



}
