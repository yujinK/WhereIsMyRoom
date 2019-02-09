package com.example.yujin.whereismyroom;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.yujin.whereismyroom.common.Globals;
import com.example.yujin.whereismyroom.common.Util;
import com.example.yujin.whereismyroom.databinding.ActivityDetailRoomBinding;

public class DetailRoomActivity extends AppCompatActivity {

    ActivityDetailRoomBinding binding;
    Util util;
    Room room;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_room);

        init();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (Globals.STATIC_INTEGER_VALUE): {
                if (resultCode == RESULT_OK) {
                    room = (Room) data.getSerializableExtra("room");
                    setRoomData();
                }
                break;
            }
        }

    }

    public void init() {
        setSupportActionBar(binding.detailToolbar.toolbar); //TODO: ToolBar 뭘로 표시할 지 생각해보기

        //ToolBar 왼쪽 back 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);

        binding.detailToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        util = new Util();
        room = (Room) getIntent().getSerializableExtra("room");

        setRoomData();
    }

    public void setRoomData() {
        //전월세 셋팅
        binding.detailTxtRentType.setText(room.rentType);
        if (room.rentType.equals("전세")) {
            //전세
            binding.detailTxtRentCost.setText(util.calDeposit(room.deposit));
        } else {
            //월세
            binding.detailTxtRentCost.setText(String.format("%s / %s", room.deposit, room.rentMonth));
        }

        //Table에 데이터 셋팅
        if (room.utilities.equals("0")) {
            binding.detailTxtTableUtilities.setText("-");
        } else {
            String utilities = String.format("%s%s ", room.utilities, getString(R.string.currencyUnit));

            if (room.includedUtilities.length() != 0) {
                utilities += "(" + room.includedUtilities + ")";
            }

            binding.detailTxtTableUtilities.setText(utilities);
        }

        if (room.myFloor.equals("-") && room.myFloor.equals("-")) {
            binding.detailTxtTableFloor.setText("-");
        } else {
            binding.detailTxtTableFloor.setText(String.format("%s%s / %s%s", room.myFloor, getString(R.string.detailFloor), room.buildFloor, getString(R.string.detailFloor)));
        }

        if (room.roomSizeM.length() == 0 && room.roomSizeP.length() == 0) {
            binding.detailTxtTableSize.setText("-");
        } else {
            binding.detailTxtTableSize.setText(String.format("%s%s / %s%s", room.roomSizeM, getString(R.string.roomSizeM) , room.roomSizeP, getString(R.string.roomSizeP)));
        }

        binding.detailTxtTableDirection.setText(room.direction);
        binding.detailTxtTableType.setText(room.roomType);

        if (room.option.length() == 0) {
            binding.detailTxtTableOption.setText("-");
        } else {
            String[] s = room.option.split(", ");
            String option = "";

            for (int i=0; i<s.length; i++) {
                option += s[i];

                if (i != s.length-1) {
                    option += "\n";
                }
            }

            binding.detailTxtTableOption.setText(option);
        }

        if (room.parking == null) {
            binding.detailTxtTableParking.setText("-");
        } else if (room.parking.equals("0")) {
            binding.detailTxtTableParking.setText("가능");
        } else if (room.parking.equals("1")) {
            binding.detailTxtTableParking.setText("불가능");
        }

        if (room.elevator == null) {
            binding.detailTxtTableElevator.setText("-");
        } else if (room.elevator.equals("0")) {
            binding.detailTxtTableElevator.setText("있음");
        } else if (room.elevator.equals("1")) {
            binding.detailTxtTableElevator.setText("없음");
        }

        if (room.animal == null) {
            binding.detailTxtTableAnimal.setText("-");
        } else if (room.animal.equals("0")) {
            binding.detailTxtTableAnimal.setText("가능");
        } else if (room.animal.equals("1")) {
            binding.detailTxtTableAnimal.setText("고양이만");
        } else if (room.animal.equals("2")) {
            binding.detailTxtTableAnimal.setText("불가능");
        }

        binding.detailTxtRoomDetailContent.setText(room.detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ToolBar 오른쪽 edit 버튼
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.detail_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                //방 수정
                Intent intent = new Intent(this, AddRoomActivity.class);
                intent.putExtra("pageType", "EDIT");
                intent.putExtra("room", room);
                startActivityForResult(intent, Globals.STATIC_INTEGER_VALUE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
