package com.example.yujin.whereismyroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.yujin.whereismyroom.adapter.RoomAdapter;
import com.example.yujin.whereismyroom.db.DbOpenHelper;
import com.example.yujin.whereismyroom.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewClickListener {

    private ActivityMainBinding binding;
    private List<Room> roomList = new ArrayList<>();
    private RoomAdapter roomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);

        init();
    }

    public void init() {
        setSupportActionBar(binding.mainToolbar.toolbar);

        loadRooms();

        binding.mainRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        roomAdapter = new RoomAdapter(this, this, roomList);
        binding.mainRecyclerview.setAdapter(roomAdapter);
    }

    @Override
    public void recyclerViewListClicked(final View view, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(R.string.alert);
        builder.setMessage(R.string.deleteItem);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteRoom(position);
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        loadRooms();
//        binding.mainRecyclerview.getAdapter().notifyDataSetChanged();
        roomAdapter.notifyDataSetChanged();
    }

    public void loadRooms() {
        DbOpenHelper helper = new DbOpenHelper(this);
        helper.open();
        Cursor cursor = helper.selectColumns();

        roomList.clear();

        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex("_id"));
            String deposit = cursor.getString(cursor.getColumnIndex("deposit"));
            String rentMonth = cursor.getString(cursor.getColumnIndex("rent_month"));
            String rentType = cursor.getString(cursor.getColumnIndex("rent_type"));
            String utilities = cursor.getString(cursor.getColumnIndex("utilities"));
            String includedUtilities = cursor.getString(cursor.getColumnIndex("included_utilities"));
            String stationName = cursor.getString(cursor.getColumnIndex("station_name"));
            String routeName = cursor.getString(cursor.getColumnIndex("route_name"));
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
            String imgUrl = cursor.getString(cursor.getColumnIndex("img_url"));

            roomList.add(new Room(id, deposit, rentMonth, rentType, utilities, includedUtilities
                    , stationName, routeName, buildFloor, myFloor
                    , direction, roomType, roomSizeM, roomSizeP, option, animal, elevator, parking, detail, imgUrl));
        }

        helper.close();
    }

    public void deleteRoom(int position) {
        DbOpenHelper helper = new DbOpenHelper(this);
        helper.open();
        helper.deleteColumn(Integer.parseInt(roomList.get(position).getId()));
        helper.close();

        roomList.remove(position);
        roomAdapter.notifyItemRemoved(position);
    }

    public void onButtonClick(View view) {
        //방 추가
        Intent intent = new Intent(MainActivity.this, AddRoomActivity.class);
        intent.putExtra("pageType", "ADD");
        startActivity(intent);
    }
}
