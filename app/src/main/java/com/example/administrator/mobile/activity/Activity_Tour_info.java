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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.mobile.R;
import com.example.administrator.mobile.fragment.Fragment_mapinfo;
import com.example.administrator.mobile.fragment.Fragment_textinfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Activity_Tour_info extends ActionBarActivity {

    Button button_mapit;
    Button button_info;

    TextView place_name;

    LinearLayout view_info;
    LinearLayout view_map;

    static final LatLng SEOUL = new LatLng( 37.56, 126.97);
    private GoogleMap map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__tour_info);

        button_mapit = (Button) findViewById(R.id.button_mapit);
        button_info = (Button) findViewById(R.id.button_info);

        place_name = (TextView) findViewById(R.id.place_name);

        view_info = (LinearLayout) findViewById(R.id.view_info);
        view_map = (LinearLayout) findViewById (R.id.view_map);

        final Intent intent = getIntent();

        place_name.setText(intent.getStringExtra("place_name"));

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

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        Marker seoul = map.addMarker(new MarkerOptions().position(SEOUL)
                .title("Seoul"));

        map.moveCamera(CameraUpdateFactory.newLatLngZoom( SEOUL, 15));

        map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
    }
    @Override
    public void onBackPressed() {

        Intent intent = getIntent();

        intent.putExtra("place_name", place_name.getText());
        setResult(RESULT_OK,intent);
        finish();
        super.onBackPressed();

    }

//    public void selectFrag(int select){
//        Fragment fr;
//        MapFragment mMapFragment = MapFragment.newInstance();
//
//
//        if(select == 1){
//            fr = new Fragment_textinfo();
//        }else {
//            fr = mMapFragment;
//        }.
//
//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_place, fr);
//        fragmentTransaction.commit();
//    }


}
