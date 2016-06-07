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

    private static String DongDaeMun = "http://contents.visitseoul.net/file_save/art_img/2015/03/20/20150320141418_D.jpg";
    private static String MungDong = "http://contents.visitseoul.net/images/contents/9000/large/kr/581_1.jpg";
    private static String BugGung = "http://contents.visitseoul.net/file_save/art_img/2011/04/12/20110412181814_D.jpg";
    private static String SeoulTower = "http://contents.visitseoul.net/images/contents/9000/large/kr/166_1.jpg";

    ImageView p1;
    ImageView p2;
    ImageView p3;
    ImageView p4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tour);

        p1 = (ImageView) findViewById(R.id.place_1);
        p2 = (ImageView) findViewById(R.id.place_2);
        p3 = (ImageView) findViewById(R.id.place_3);
        p4 = (ImageView) findViewById(R.id.place_4);

        Glide.with(getApplicationContext()).load(DongDaeMun).into(p1);
        Glide.with(getApplicationContext()).load(MungDong).into(p2);
        Glide.with(getApplicationContext()).load(BugGung).into(p3);
        Glide.with(getApplicationContext()).load(SeoulTower).into(p4);

        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Tour.this, Activity_Tour_info.class);
                intent.putExtra("place_name","seoul city tour!!");

                startActivityForResult(intent,0);
            }
        });
        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Tour.this, Activity_Tour_info.class);
                startActivity(intent);
            }
        });
        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Tour.this, Activity_Tour_info.class);
                startActivity(intent);
            }
        });
        p4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Tour.this, Activity_Tour_info.class);
                startActivity(intent);
            }
        });
     }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            if(requestCode == 0){
                Toast.makeText(getApplicationContext(), "you visited "+data.getStringExtra("place_name"), Toast.LENGTH_LONG).show();
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
                nameValuePairs.add(new BasicNameValuePair("email", params[1]));
                nameValuePairs.add(new BasicNameValuePair("password", params[2]));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

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
            StringTokenizer tokens = new StringTokenizer(result,"[]");
            for(int i = 0; tokens.hasMoreElements(); i++) {
                data[i] = tokens.nextToken();
            }
            System.out.println("tokens : " + data[1]);

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
