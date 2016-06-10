package com.example.administrator.mobile.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.mobile.R;
import com.example.administrator.mobile.fragment.Fragment_mapinfo;
import com.example.administrator.mobile.fragment.Fragment_textinfo;
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

public class Activity_Tour_info extends ActionBarActivity {

    Button button_mapit;
    Button button_info;

    TextView place_name;
    TextView info;

    LinearLayout view_info;
    LinearLayout view_map;

    int p;

    private GoogleMap map;

    private final String serverUrl = "http://52.79.197.58/conv/index.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tour_info);

        button_mapit = (Button) findViewById(R.id.button_mapit);
        button_info = (Button) findViewById(R.id.button_info);

        place_name = (TextView) findViewById(R.id.place_name);
        info = (TextView) findViewById(R.id.info);

        view_info = (LinearLayout) findViewById(R.id.view_info);
        view_map = (LinearLayout) findViewById (R.id.view_map);



        final Intent intent = getIntent();
        place_name.setText(intent.getStringExtra("place_name"));
        p = intent.getIntExtra("p",-1);

        button_mapit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectFrag(0);
//                Log.e("button", "1");
                view_map.setVisibility(View.VISIBLE);
                view_info.setVisibility(View.GONE);
            }
        });


        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                selectFrag(1);
//                Log.e("button", "2");
                view_map.setVisibility(View.GONE);
                view_info.setVisibility(View.VISIBLE);
            }
        });

        AsyncDataClass asyncRequestObject = new AsyncDataClass();
        asyncRequestObject.execute(serverUrl, "getInfo");
    }
    @Override
    public void onBackPressed() {

        Intent intent = getIntent();

        intent.putExtra("place_name", place_name.getText());
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();

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
                Toast.makeText(Activity_Tour_info.this, "Server connection failed", Toast.LENGTH_LONG).show();
                return;
            }

            String data[] = new String[100];
            StringTokenizer tokens = new StringTokenizer(result,"[]");
            for(int i = 0; tokens.hasMoreElements(); i++) {
                data[i] = tokens.nextToken();
                System.out.println(i+"tokens : " + data[i]);
            }
            LatLng position = null;
            position= new LatLng( 37.56, 126.97);
            if(p == 0) {
                info.setText(data[5]);
                position= new LatLng( Double.parseDouble(data[7]), Double.parseDouble(data[9]));
            }
            else if(p == 1){
                info.setText(data[15]);
                position= new LatLng( Double.parseDouble(data[17]), Double.parseDouble(data[19]));
            }
            else if(p == 2){
                info.setText(data[25]);
                position= new LatLng( Double.parseDouble(data[27]), Double.parseDouble(data[29]));
            }
            else if(p == 3){
                info.setText(data[35]);
                position= new LatLng( Double.parseDouble(data[37]), Double.parseDouble(data[39]));
            }

            map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            Marker seoul = map.addMarker(new MarkerOptions().position(position)
                    .title("Seoul"));

            map.moveCamera(CameraUpdateFactory.newLatLngZoom( position, 15));

            map.animateCamera(CameraUpdateFactory.zoomTo(16), 2000, null);

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
