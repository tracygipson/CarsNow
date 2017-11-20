package com.carsnow.tracy.carsnow;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
    public ImageView imageView;
    public TextView txt,price,manu;
    public OkHttpClient client;
    int count=1;
    JSONObject jobj;
    JSONArray jary;
    String name = "",price1 = "",manu1 = "",img="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.nextbtn);
        txt = (TextView) findViewById(R.id.title);
        price = (TextView) findViewById(R.id.price);
        manu = (TextView) findViewById(R.id.manu);
        imageView = (ImageView) findViewById(R.id.imageView);
        connectiony();
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (count >= jary.length()){
                try {
                    getcardata(0);
                    count = 1;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    getcardata(count);
                    count = count + 1;
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }); }
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
                        try {
                            String data = response.body().string();
                            try {
                                jobj = new JSONObject(data);
                                jary = jobj.getJSONArray("cars");
                                JSONObject jobj2 = jary.getJSONObject(0);
                                name = jobj2.getString("name");
                                txt.setText(name);
                                price1 = jobj2.getString("price");
                                price.setText(price1);
                                manu1 = jobj2.getString("manufacturer");
                                manu.setText(manu1);
                                img = jobj2.getString("img");
                                Picasso.with(getApplicationContext()).load(img).resize(350, 220).into(imageView);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            txt.setText(name);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }}
                });}
        });
    }
    public void getcardata(int count) throws JSONException {
        JSONObject jobj2 = jary.getJSONObject(count);
        name = jobj2.getString("name");
        txt.setText(name);
        price1 = jobj2.getString("price");
        price.setText(price1);
        manu1 = jobj2.getString("manufacturer");
        manu.setText(manu1);
        img = jobj2.getString("img");
        Picasso.with(getApplicationContext()).load(img).resize(350, 220).into(imageView);
    }
}
