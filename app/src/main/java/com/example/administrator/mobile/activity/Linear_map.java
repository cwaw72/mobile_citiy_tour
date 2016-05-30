package com.example.administrator.mobile.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.mobile.R;

/**
 * Created by Administrator on 2016-05-30.
 */
public class Linear_map extends LinearLayout{

    public Linear_map(Context context) {
        super(context);
        init();

    }

    private void init(){

        //      final Activity_user_view aActivity = (Activity_user_view) Activity_user_view.AActivty;

        final View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_map,null);
        view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));


        this.addView(view);
    }

}

