package com.example.yujin.whereismyroom;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.yujin.whereismyroom.DB.DbOpenHelper;
import com.example.yujin.whereismyroom.databinding.ActivityMainBinding;
import com.example.yujin.whereismyroom.databinding.ItemRoomBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private List<Room> roomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        setSupportActionBar(binding.mainToolbar.toolbar);

        loadRooms();

        binding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        RoomAdapter roomAdapter = new RoomAdapter(roomList);
        binding.mainRecyclerview.setAdapter(roomAdapter);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void loadRooms() {
        getRooms();
    }

    public void getRooms() {
        DbOpenHelper helper = new DbOpenHelper(this);
        helper.open();
        Cursor cursor = helper.selectColumns();

        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String deposit = cursor.getString(cursor.getColumnIndex("deposit"));
            String rentMonth = cursor.getString(cursor.getColumnIndex("rent_month"));
            String utilities = cursor.getString(cursor.getColumnIndex("utilities"));
            String includedUtilities = cursor.getString(cursor.getColumnIndex("included_utilities"));
            String buildFloor = cursor.getString(cursor.getColumnIndex("build_floor"));
            String myFloor = cursor.getString(cursor.getColumnIndex("my_floor"));
            String direction = cursor.getString(cursor.getColumnIndex("direction"));
            String roomType = cursor.getString(cursor.getColumnIndex("room_type"));
            String roomSizeM = cursor.getString(cursor.getColumnIndex("room_size_m"));
            String roomSizeP = cursor.getString(cursor.getColumnIndex("room_size_p"));
            String option = cursor.getString(cursor.getColumnIndex("option"));
            String animal = cursor.getString(cursor.getColumnIndex("animal"));
            String elevator = cursor.getString(cursor.getColumnIndex("elevator"));
            String parking = cursor.getString(cursor.getColumnIndex("parking"));
            String detail = cursor.getString(cursor.getColumnIndex("detail"));

            roomList.add(new Room(id, deposit, rentMonth, utilities, includedUtilities, buildFloor, myFloor
                        , direction, roomType, roomSizeM, roomSizeP, option, animal, elevator, parking, detail));
        }
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
        startActivity(intent);
    }
}
