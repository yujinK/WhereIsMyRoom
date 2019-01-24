package com.example.yujin.whereismyroom;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

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
        //Toolbar 초기화
        initToolbar();

        //관리비 spinner 초기화
        initUtilities();

        //건물 층 수 spinner 초기화
        initBuildFloor();

        //해당 층 수 spinner 초기화
        initMyFloor();

        //방향 spinner 초기화
        initDirection();

        //방 구조 spinner 초기화
        initRoomType();

        //옵션 spinner 초기화
        initOption();

        //반려동물 toggle button 초기화
        initAnimal();
    }

    private void initToolbar() {
        setSupportActionBar(binding.addToolbar);
        getSupportActionBar().setTitle(getString(R.string.addToolBar));

        //툴바 왼쪽 back 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
    }

    private void initUtilities() {
        String items[] = getResources().getStringArray(R.array.utilities);
        binding.addSpinnerUtilities.setItems(items);
    }

    private void initBuildFloor() {
        String items[] = getResources().getStringArray(R.array.floors);

        SpinnerAdapter sa = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        binding.addSpinnerBuildFloor.setAdapter(sa);
    }

    private void initMyFloor() {
        String items[] = getResources().getStringArray(R.array.floors);

        SpinnerAdapter sa = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        binding.addSpinnerMyFloor.setAdapter(sa);
    }

    private void initDirection() {
        String items[] = getResources().getStringArray(R.array.direction);

        SpinnerAdapter sa = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        binding.addSpinnerDirection.setAdapter(sa);
    }

    private void initRoomType() {
        String items[] = getResources().getStringArray(R.array.roomType);

        SpinnerAdapter sa = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        binding.addSpinnerRoomType.setAdapter(sa);
    }

    private void initOption() {
        String items[] = getResources().getStringArray(R.array.option);
        binding.addSpinnerOption.setItems(items);
    }

    private void initAnimal() {
//        binding.addToggleAnimal.setColorRes(R.color.red, R.color.white);
    }
}
