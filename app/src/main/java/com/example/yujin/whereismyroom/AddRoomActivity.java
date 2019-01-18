package com.example.yujin.whereismyroom;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yujin.whereismyroom.databinding.ActivityAddRoomBinding;

public class AddRoomActivity extends AppCompatActivity {

    ActivityAddRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_room);
        binding.setActivity(this);

        init();
    }

    private void init() {
        setSupportActionBar(binding.addToolbar);
        getSupportActionBar().setTitle(getString(R.string.addToolBar));

        //툴바 왼쪽 back 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
    }

    public void onRentTypeButtonClick(View view) {
        if (view == binding.addBtnRentYear) {
            //TODO: change button background color -> search material design
            binding.addBtnRentMonth.setBackgroundColor(Color.TRANSPARENT);
            binding.addBtnRentYear.setBackgroundColor(getResources().getColor(R.color.blue));
        } else if (view == binding.addBtnRentMonth) {
            binding.addBtnRentYear.setBackgroundColor(Color.TRANSPARENT);
            binding.addBtnRentMonth.setBackgroundColor(getResources().getColor(R.color.blue));
        }
    }
}
