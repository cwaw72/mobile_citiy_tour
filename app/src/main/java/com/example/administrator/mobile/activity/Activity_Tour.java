package com.example.administrator.mobile.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.mobile.R;
import com.example.administrator.mobile.RegisterActivity;

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
}
