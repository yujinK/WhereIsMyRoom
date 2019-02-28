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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        binding.detailTxtRentType.setText(room.getRentType());
        if (room.getRentType().equals("전세")) {
            //전세
            binding.detailTxtRentCost.setText(util.calDeposit(room.getDeposit()));
        } else {
            //월세
            binding.detailTxtRentCost.setText(String.format("%s / %s", room.getDeposit(), room.getRentMonth()));
        }

        //TODO: 지하철 셋팅
        binding.detailLayoutRoute.removeAllViews();

        String[] routeList = Util.convertStringToArray(room.getRouteName());

        for (int i=0; i<routeList.length; i++) {
            ImageView iv = new ImageView(getApplicationContext());
            iv.setImageResource(Util.findRouteImageResource(routeList[i]));
            float density = getApplicationContext().getResources().getDisplayMetrics().density;
            int size = (int)(density * 18);
            //int margin = (int)(density * 3);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
            binding.detailLayoutRoute.addView(iv, params);
        }

        binding.detailTxtSubwayName.setText(room.getStationName());

        //Table에 데이터 셋팅
        if (room.getUtilities().equals("0")) {
            binding.detailTxtTableUtilities.setText("-");
        } else {
            String utilities = String.format("%s%s ", room.getUtilities(), getString(R.string.currencyUnit));

            if (room.getIncludedUtilities().length() != 0) {
                utilities += "(" + room.getIncludedUtilities() + ")";
            }

            binding.detailTxtTableUtilities.setText(utilities);
        }

        if (room.getMyFloor().equals("-") && room.getMyFloor().equals("-")) {
            binding.detailTxtTableFloor.setText("-");
        } else {
            binding.detailTxtTableFloor.setText(String.format("%s%s / %s%s", room.getMyFloor(), getString(R.string.detailFloor), room.getBuildFloor(), getString(R.string.detailFloor)));
        }

        if (room.getRoomSizeM().length() == 0 && room.getRoomSizeP().length() == 0) {
            binding.detailTxtTableSize.setText("-");
        } else {
            binding.detailTxtTableSize.setText(String.format("%s%s / %s%s", room.getRoomSizeM(), getString(R.string.roomSizeM) , room.getRoomSizeP(), getString(R.string.roomSizeP)));
        }

        binding.detailTxtTableDirection.setText(room.getDirection());
        binding.detailTxtTableType.setText(room.getRoomType());

        if (room.getOption().length() == 0) {
            binding.detailTxtTableOption.setText("-");
        } else {
            String[] s = room.getOption().split(", ");
            String option = "";

            for (int i=0; i<s.length; i++) {
                option += s[i];

                if (i != s.length-1) {
                    option += "\n";
                }
            }

            binding.detailTxtTableOption.setText(option);
        }

        if (room.getParking() == null) {
            binding.detailTxtTableParking.setText("-");
        } else if (room.getParking().equals("0")) {
            binding.detailTxtTableParking.setText("가능");
        } else if (room.getParking().equals("1")) {
            binding.detailTxtTableParking.setText("불가능");
        }

        if (room.getElevator() == null) {
            binding.detailTxtTableElevator.setText("-");
        } else if (room.getElevator().equals("0")) {
            binding.detailTxtTableElevator.setText("있음");
        } else if (room.getElevator().equals("1")) {
            binding.detailTxtTableElevator.setText("없음");
        }

        if (room.getAnimal() == null) {
            binding.detailTxtTableAnimal.setText("-");
        } else if (room.getAnimal().equals("0")) {
            binding.detailTxtTableAnimal.setText("가능");
        } else if (room.getAnimal().equals("1")) {
            binding.detailTxtTableAnimal.setText("고양이만");
        } else if (room.getAnimal().equals("2")) {
            binding.detailTxtTableAnimal.setText("불가능");
        }

        binding.detailTxtRoomDetailContent.setText(room.getDetail());
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
