package com.example.administrator.mobile.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.mobile.R;
import com.example.administrator.mobile.fragment.Fragment_mapinfo;
import com.example.administrator.mobile.fragment.Fragment_textinfo;
import com.google.android.gms.maps.MapFragment;

public class Activity_Tour_info extends ActionBarActivity {

    Button button_mapit;
    Button button_info;

    TextView place_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tour_info);

        button_mapit = (Button) findViewById(R.id.button_mapit);
        button_info = (Button) findViewById(R.id.button_info);

        place_name = (TextView) findViewById(R.id.place_name);

        final Intent intent = getIntent();

        place_name.setText(intent.getStringExtra("place_name"));

        button_mapit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(0);
                Log.e("button", "1");
            }
        });


        button_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFrag(1);
                Log.e("button", "2");

            }
        });

    }
    @Override
    public void onBackPressed() {

        Intent intent = getIntent();

        intent.putExtra("place_name", place_name.getText());
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();

    }

    public void selectFrag(int select){
        Fragment fr;
        MapFragment mMapFragment = MapFragment.newInstance();


        if(select == 1){
            fr = new Fragment_textinfo();
        }else {
            fr = mMapFragment;
        }

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();
    }


}
