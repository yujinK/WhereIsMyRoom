package com.example.yujin.whereismyroom;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.yujin.whereismyroom.DB.DbOpenHelper;
import com.example.yujin.whereismyroom.databinding.ActivityAddRoomBinding;

import org.honorato.multistatetogglebutton.ToggleButton;

public class AddRoomActivity extends AppCompatActivity {

    ActivityAddRoomBinding binding;
    int animal, elevator, parking;

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

        //MultiToggleButton 초기화
        initAnimal();
        initElevator();
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

//    public void onValueChangeAnimal(int position) {
//        //position 0: 가능, 1: 고양이만, 2: 불가능
//        animal = position;
//    }

    public void initAnimal() {
        binding.addToggleAnimal.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                //position 0: 가능, 1: 고양이만, 2: 불가능
                animal = position;
            }
        });
    }

    public void initElevator() {
        binding.addToggleElevator.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                //position 0: 있음, 1: 없음
                elevator = position;
            }
        });
    }

    public void initParking() {
        binding.addToggleParking.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                //position 0: 가능, 1: 불가능
                parking = position;
            }
        });
    }

    public void onSubmitButtonClick(View view) {
        if (binding.addEditDeposit.getText().length() != 0 &&
                binding.addEditRentMonth.getText().length() != 0&&
                binding.addEditDeposit.getText().length() != 0) {
            //방 추가
            int deposit = Integer.parseInt(binding.addEditDeposit.getText().toString());
            int rentMonth = Integer.parseInt(binding.addEditRentMonth.getText().toString());
            int utilities = Integer.parseInt(binding.addEditUtilities.getText().toString());
            String includedUtilities = binding.addSpinnerUtilities.getSelectedItemsAsString();
            int buildFloor = binding.addSpinnerBuildFloor.getSelectedItem().toString().equals("-") ? 0 : Integer.parseInt(binding.addSpinnerBuildFloor.getSelectedItem().toString());
            int myFloor = binding.addSpinnerMyFloor.getSelectedItem().toString().equals("-") ? 0 : Integer.parseInt(binding.addSpinnerMyFloor.getSelectedItem().toString());
            String direction = binding.addSpinnerDirection.getSelectedItem().toString().equals("-") ? "" : binding.addSpinnerDirection.getSelectedItem().toString();
            String roomType = binding.addSpinnerRoomType.getSelectedItem().toString().equals("-") ? "" : binding.addSpinnerRoomType.getSelectedItem().toString();
            float roomSizeM = binding.addEditRoomSizeM.getText().toString().equals("") ? 0 : Float.parseFloat(binding.addEditRoomSizeM.getText().toString());
            float roomSizeP = binding.addEditRoomSizeP.getText().toString().equals("") ? 0 : Float.parseFloat(binding.addEditRoomSizeP.getText().toString());
            String option = binding.addSpinnerOption.getSelectedItemsAsString();
            String detail = binding.addEditDetail.getText().toString();

            DbOpenHelper helper = new DbOpenHelper(this);
            helper.open();
            helper.insertColumn(deposit, rentMonth, utilities, includedUtilities, buildFloor, myFloor, direction, roomType
                    , roomSizeM, roomSizeP, option, animal, elevator, parking, detail);

            Toast.makeText(this, "방 추가 완료", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            //필수 항목 체크 알림
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.alert);
            builder.setMessage(R.string.addAlertMessage);
            builder.setPositiveButton(R.string.ok, null);
            builder.show();
        }
    }
}
