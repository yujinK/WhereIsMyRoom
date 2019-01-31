package com.example.yujin.whereismyroom;

import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.yujin.whereismyroom.DB.DbOpenHelper;
import com.example.yujin.whereismyroom.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Room> roomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        setSupportActionBar(binding.mainToolbar.toolbar);

        loadRooms();
    }

    public void loadRooms() {
        getRooms();


    }

    public void getRooms() {
        DbOpenHelper helper = new DbOpenHelper(this);
        helper.open();
        Cursor cursor = helper.selectColumns();

        while(cursor.moveToNext()) {
            Room room = new Room();
            room.setId(cursor.getInt(cursor.getColumnIndex("_id")));
            room.setDeposit(cursor.getInt(cursor.getColumnIndex("deposit")));
            room.setRentMonth(cursor.getInt(cursor.getColumnIndex("rent_month")));
            room.setUtilities(cursor.getInt(cursor.getColumnIndex("utilities")));
            room.setIncludedUtilities(cursor.getString(cursor.getColumnIndex("included_utilities")));
            room.setBuildFloor(cursor.getInt(cursor.getColumnIndex("build_floor")));
            room.setMyFloor(cursor.getInt(cursor.getColumnIndex("my_floor")));
            room.setDirection(cursor.getString(cursor.getColumnIndex("direction")));
            room.setRoomType(cursor.getString(cursor.getColumnIndex("room_type")));
            room.setRoomSizeM(cursor.getDouble(cursor.getColumnIndex("room_size_m")));
            room.setRoomSizeP(cursor.getDouble(cursor.getColumnIndex("room_size_p")));
            room.setOption(cursor.getString(cursor.getColumnIndex("option")));
            room.setAnimal(cursor.getInt(cursor.getColumnIndex("animal")));
            room.setElevator(cursor.getInt(cursor.getColumnIndex("elevator")));
            room.setParking(cursor.getInt(cursor.getColumnIndex("parking")));
            room.setDetail(cursor.getString(cursor.getColumnIndex("detail")));

            roomList.add(room);
        }
    }

    public void onButtonClick(View view) {
        Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
        startActivity(intent);
    }
}