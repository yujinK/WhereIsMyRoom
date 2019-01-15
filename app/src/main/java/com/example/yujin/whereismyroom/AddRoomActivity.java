package com.example.yujin.whereismyroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class AddRoomActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);

        //TODO: xml에 databinding 추가
        setSupportActionBar();
    }
}
