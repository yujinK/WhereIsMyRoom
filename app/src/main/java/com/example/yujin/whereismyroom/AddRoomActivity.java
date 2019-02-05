package com.example.yujin.whereismyroom;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.example.yujin.whereismyroom.db.DbOpenHelper;
import com.example.yujin.whereismyroom.databinding.ActivityAddRoomBinding;

import org.honorato.multistatetogglebutton.ToggleButton;

public class AddRoomActivity extends AppCompatActivity {

    ActivityAddRoomBinding binding;
    String animal, elevator, parking;
    public static final double EXCHANGE_P = 0.3025; // 평으로 환산, 1제곱미터 = 0.3025평
    public static final double EXCHANGE_M = 3.3;    // 제곱미터로 환산, 1평 = 약 3.3제곱미터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_room);
        binding.setActivity(this);

        init();
    }

    @Override
    public void onBackPressed() {
        checkExit();
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
        initParking();

        //TODO: 입력하자마자 자동 변환은 생각 좀 해보기
        binding.addEditRoomSizeM.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 제곱미터를 평으로 환산(소수점 둘째자리 반올림)
                double input = binding.addEditRoomSizeM.getText().toString().equals("") ? 0 : Double.parseDouble(binding.addEditRoomSizeM.getText().toString());

                if (!hasFocus && input != 0) {
                    binding.addEditRoomSizeP.setText(String.valueOf(Math.round(input * EXCHANGE_P * 100) / 100.0));
                }
            }
        });

        binding.addEditRoomSizeP.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // 평을 제곱미터로 환산(소수점 둘째자리 반올림)
                double input = binding.addEditRoomSizeP.getText().toString().equals("") ? 0 : Double.parseDouble(binding.addEditRoomSizeP.getText().toString());

                if (!hasFocus && input != 0) {
                    binding.addEditRoomSizeM.setText(String.valueOf(Math.round(input * EXCHANGE_M * 100) / 100.0));
                }
            }
        });
    }

    private void initToolbar() {
        setSupportActionBar(binding.addToolbar.toolbar);
        getSupportActionBar().setTitle(getString(R.string.addToolBar));

        //툴바 왼쪽 back 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);
        binding.addToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkExit();
            }
        });
    }

    private void initUtilities() {
        String items[] = getResources().getStringArray(R.array.utilities);
        binding.addSpinnerUtilities.setItems(items);

        //TODO: Spinner들 공통 사항 하나로 묶을 수 있는 지 생각해보기
        binding.addSpinnerUtilities.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.addRootView.clearFocus();
                hideKeyboard();
                return false;
            }
        });
    }

    private void initBuildFloor() {
        String items[] = getResources().getStringArray(R.array.floors);

        SpinnerAdapter sa = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        binding.addSpinnerBuildFloor.setAdapter(sa);

        binding.addSpinnerBuildFloor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.addRootView.clearFocus();
                hideKeyboard();
                return false;
            }
        });
    }

    private void initMyFloor() {
        String items[] = getResources().getStringArray(R.array.floors);

        SpinnerAdapter sa = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        binding.addSpinnerMyFloor.setAdapter(sa);

        binding.addSpinnerMyFloor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.addRootView.clearFocus();
                hideKeyboard();
                return false;
            }
        });
    }

    private void initDirection() {
        String items[] = getResources().getStringArray(R.array.direction);

        SpinnerAdapter sa = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        binding.addSpinnerDirection.setAdapter(sa);

        binding.addSpinnerDirection.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.addRootView.clearFocus();
                hideKeyboard();
                return false;
            }
        });
    }

    private void initRoomType() {
        String items[] = getResources().getStringArray(R.array.roomType);

        SpinnerAdapter sa = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        binding.addSpinnerRoomType.setAdapter(sa);

        binding.addSpinnerRoomType.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.addRootView.clearFocus();
                hideKeyboard();
                return false;
            }
        });
    }

    private void initOption() {
        String items[] = getResources().getStringArray(R.array.option);
        binding.addSpinnerOption.setItems(items);

        binding.addSpinnerOption.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                binding.addRootView.clearFocus();
                hideKeyboard();
                return false;
            }
        });
    }

    public void initAnimal() {
        binding.addToggleAnimal.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                //position 0: 가능, 1: 고양이만, 2: 불가능
                binding.addRootView.clearFocus();
                hideKeyboard();
                animal = String.valueOf(position);
            }
        });
    }

    public void initElevator() {
        binding.addToggleElevator.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                //position 0: 있음, 1: 없음
                binding.addRootView.clearFocus();
                hideKeyboard();
                elevator = String.valueOf(position);
            }
        });
    }

    public void initParking() {
        binding.addToggleParking.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                //position 0: 가능, 1: 불가능
                binding.addRootView.clearFocus();
                hideKeyboard();
                parking = String.valueOf(position);
            }
        });
    }

    public void onSubmitButtonClick(View view) {
        if (binding.addEditDeposit.getText().length() != 0 &&
                binding.addEditRentMonth.getText().length() != 0&&
                binding.addEditDeposit.getText().length() != 0) {
            //방 추가
            String deposit = binding.addEditDeposit.getText().toString();
            String rentMonth = binding.addEditRentMonth.getText().toString();
            String utilities = binding.addEditUtilities.getText().toString();
            String includedUtilities = binding.addSpinnerUtilities.getSelectedItemsAsString();
            String buildFloor = binding.addSpinnerBuildFloor.getSelectedItem().toString();
            String myFloor = binding.addSpinnerMyFloor.getSelectedItem().toString();
            String direction = binding.addSpinnerDirection.getSelectedItem().toString();
            String roomType = binding.addSpinnerRoomType.getSelectedItem().toString();
            String roomSizeM = binding.addEditRoomSizeM.getText().toString();
            String roomSizeP = binding.addEditRoomSizeP.getText().toString();
            String option = binding.addSpinnerOption.getSelectedItemsAsString();
            String detail = binding.addEditDetail.getText().toString();

            DbOpenHelper helper = new DbOpenHelper(this);
            helper.open();
            helper.insertColumn(deposit, rentMonth, utilities, includedUtilities, buildFloor, myFloor, direction, roomType
                    , roomSizeM, roomSizeP, option, animal, elevator, parking, detail);

            Toast.makeText(this, "방 추가 완료", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            binding.addRootView.clearFocus();
            hideKeyboard();

            //필수 항목 체크 알림
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.alert);
            builder.setMessage(R.string.addInputAlertMessage);
            builder.setPositiveButton(R.string.ok, null);
            builder.show();
        }
    }

    public void checkExit() {
        if (binding.addEditDeposit.getText().length() != 0 || binding.addEditRentMonth.getText().length() != 0
                || binding.addEditUtilities.getText().length() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AddRoomActivity.this);
            builder.setTitle(R.string.alert);
            builder.setMessage(R.string.addExitAlertMessage);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setNegativeButton(R.string.cancel, null);
            builder.show();
        } else {
            finish();
        }
    }

    public void showKeyboard(EditText input) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.showSoftInput(input,0);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}