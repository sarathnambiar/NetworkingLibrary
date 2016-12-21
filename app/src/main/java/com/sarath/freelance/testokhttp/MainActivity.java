package com.sarath.freelance.testokhttp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    Button GetQuotes;

    Request request,request_post;

    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetQuotes = (Button) findViewById(R.id.btn);

        // initilize okhttp

        // should be a singleton
        client = new OkHttpClient();

        request = new Request.Builder()
                .url("https://andruxnet-random-famous-quotes.p.mashape.com/")
                .header("X-Mashape-Key", "gOcrIRi4okmshI3tTdkTvrR8Ovyop1FyNymjsnbCPCMGsBK4aq")
                .build();


        //async call
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                String responseData = response.body().string();

                JSONObject json = null;
                try {

                    json = new JSONObject(responseData);
                    final String quote = json.getString("quote");

                    Log.e("response", " " + quote);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView myTextView = (TextView) findViewById(R.id.textview);
                            myTextView.setText(quote);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        GetQuotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("cat", "Movies")
                        .build();

                request_post = new Request.Builder()
                        .url("https://andruxnet-random-famous-quotes.p.mashape.com/")
                        .header("X-Mashape-Key", "gOcrIRi4okmshI3tTdkTvrR8Ovyop1FyNymjsnbCPCMGsBK4aq")
                        .method("POST", RequestBody.create(null, new byte[0]))
                        .post(requestBody)
                        .build();


                //async call
                client.newCall(request_post).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        String responseData = response.body().string();

                        JSONObject json = null;
                        try {

                            json = new JSONObject(responseData);
                            final String quote = json.getString("quote");

                            Log.e("response", " " + quote);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TextView myTextView = (TextView) findViewById(R.id.textview1);
                                    myTextView.setText(quote);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        });



    }
}
