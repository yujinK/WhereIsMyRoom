package com.example.yujin.whereismyroom;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yujin.whereismyroom.databinding.ActivityAddRoomBinding;

public class AddRoomActivity extends AppCompatActivity {

    ActivityAddRoomBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_room);
        binding.setActivity(this);

        setSupportActionBar(binding.addToolbar);
        getSupportActionBar().setTitle(getString(R.string.addToolBar));
    }
}
