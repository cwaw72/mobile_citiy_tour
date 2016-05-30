package com.example.administrator.mobile.fragment;

import android.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.mobile.R;
import com.example.administrator.mobile.activity.Linear_map;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Fragment_mapinfo extends Fragment {


    static final LatLng SEOUL = new LatLng( 37.56, 126.97);
    private GoogleMap map;
    private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){


//        Linear_map a = new Linear_map(getContext());
        View view = inflater.inflate(R.layout.activity_map, container, false);

       // mapView = (MapView) view.findViewById(R.id.map);
//        mapView.onCreate(savedInstanceState);

//        map = mapView.getMap();
//        map = ((MapView) view.findViewById(R.id.map)).getMap();

//        MapFragment mapfragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
//        map = mapfragment.getMap();
//        //map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();

        //mGoogleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

        return view;
    }
}
