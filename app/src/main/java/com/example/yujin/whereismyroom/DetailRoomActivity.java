package com.example.yujin.whereismyroom;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yujin.whereismyroom.databinding.ActivityDetailRoomBinding;

public class DetailRoomActivity extends AppCompatActivity {

    ActivityDetailRoomBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_room);

        init();
    }

    public void init() {
        setSupportActionBar(binding.detailToolbar.toolbar);
        getSupportActionBar().setTitle(getString(R.string.detailToolBar)); //TODO: ToolBar 뭘로 표시할 지 생각해보기

        //툴바 왼쪽 back 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);

        binding.detailToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //exit
                finish();
            }
        });
    }
}
