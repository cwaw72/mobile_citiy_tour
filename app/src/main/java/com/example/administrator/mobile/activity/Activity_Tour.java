package com.example.administrator.mobile.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.mobile.R;
import com.example.administrator.mobile.RegisterActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Activity_Tour extends Activity {

    ImageView p1;
    ImageView p2;
    ImageView p3;
    ImageView p4;

    String p1_info[];
    String p2_info[];
    String p3_info[];
    String p4_info[];

    private final String serverUrl = "http://52.79.197.58/conv/index.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tour);

        p1 = (ImageView) findViewById(R.id.place_1);
        p2 = (ImageView) findViewById(R.id.place_2);
        p3 = (ImageView) findViewById(R.id.place_3);
        p4 = (ImageView) findViewById(R.id.place_4);

        final Intent intent = getIntent();
        String p = intent.getStringExtra("USEREMAIL");

        //p1
        if( (int)p.charAt(0) - 48 == 0){
            p1.setVisibility(View.GONE);
        }
        //p2
        if( (int)p.charAt(1) - 48 == 0){
            p2.setVisibility(View.GONE);
        }
        //p3
        if( (int)p.charAt(2) - 48 == 0){
            p3.setVisibility(View.GONE);

        }
        //p4
        if( (int)p.charAt(3) - 48 == 0){
            p4.setVisibility(View.GONE);
        }

        AsyncDataClass asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(serverUrl, "getInfo");

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Tour.this, Activity_Tour_info.class);
                intent.putExtra("place_name","동대문");
                intent.putExtra("p",0);
                startActivityForResult(intent,0);
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Tour.this, Activity_Tour_info.class);
                intent.putExtra("place_name","명동");
                intent.putExtra("p",1);
                startActivityForResult(intent,0);

            }
        });
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Tour.this, Activity_Tour_info.class);
                intent.putExtra("place_name","경복궁");
                intent.putExtra("p",2);
                startActivityForResult(intent,0);
            }
        });
        p4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Tour.this, Activity_Tour_info.class);
                intent.putExtra("place_name","N서울타워");
                intent.putExtra("p",3);
                startActivityForResult(intent,0);
            }
        });
     }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode == 0){
                Toast.makeText(getApplicationContext(), "You've just Watched "+data.getStringExtra("place_name"), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AsyncDataClass extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 5000);
            HttpConnectionParams.setSoTimeout(httpParameters, 5000);

            HttpClient httpClient = new DefaultHttpClient(httpParameters);
            HttpPost httpPost = new HttpPost(params[0]);

            String jsonResult = "";
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("id", params[1]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));

                HttpResponse response = httpClient.execute(httpPost);
                jsonResult = inputStreamToString(response.getEntity().getContent()).toString();

                System.out.println("Returned Json object2 " + jsonResult.toString());


            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResult;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            System.out.println("Resulted Value: " + result);

            if(result.equals("") || result == null){
                Toast.makeText(Activity_Tour.this, "Server connection failed", Toast.LENGTH_LONG).show();
                return;
            }

            String data[] = new String[100];
            //문자 확인;
            p1_info = new String[4];
            p2_info = new String[4];
            p3_info = new String[4];
            p4_info = new String[4];

            StringTokenizer tokens = new StringTokenizer(result,"[]");
            for(int i = 0; tokens.hasMoreElements(); i++) {
                data[i] = tokens.nextToken();
                System.out.println(i+"tokens : " + data[i]);
            }

            Glide.with(getApplicationContext()).load(data[3]).into(p1);
            Glide.with(getApplicationContext()).load(data[13]).into(p2);
            Glide.with(getApplicationContext()).load(data[23]).into(p3);
            Glide.with(getApplicationContext()).load(data[33]).into(p4);

            int jsonResult = returnParsedJsonObject(result);
            System.out.println("Resulted jsonResult: " + jsonResult);

        }
        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = br.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return answer;
        }
    }
    private int returnParsedJsonObject(String result){

        JSONObject resultObject = null;
        int returnedResult = 0;
        try {
            resultObject = new JSONObject(result);

            returnedResult = resultObject.getInt("success");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return returnedResult;
    }
}
