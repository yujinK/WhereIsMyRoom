package com.example.yujin.whereismyroom;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yujin.whereismyroom.adapter.SubwayAdapter;
import com.example.yujin.whereismyroom.common.Util;
import com.example.yujin.whereismyroom.db.DbOpenHelper;
import com.example.yujin.whereismyroom.databinding.ActivityAddRoomBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import org.honorato.multistatetogglebutton.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class AddRoomActivity extends AppCompatActivity {

    ActivityAddRoomBinding binding;
    String animal, elevator, parking, pageType;
    Room room;
    Subway subway;
    List<Subway> subwayList;
    PermissionListener permissionListener;
    String imgUrl;

    public static final double EXCHANGE_P = 0.3025; // 평으로 환산, 1제곱미터 = 0.3025평
    public static final double EXCHANGE_M = 3.3;    // 제곱미터로 환산, 1평 = 약 3.3제곱미터

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_room);
        binding.setActivity(this);

        init();

        pageType = getIntent().getStringExtra("pageType");
        room = (Room) getIntent().getSerializableExtra("room");

        if (pageType.equals("EDIT")) {
            initEdit();
        }
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

        SubwayAdapter subwayAdapter = new SubwayAdapter(this, binding.addAutoSubway.getText().toString());
        binding.addAutoSubway.setAdapter(subwayAdapter);

        subway = new Subway();

        binding.addAutoSubway.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subway = (Subway) parent.getItemAtPosition(position);
                hideKeyboard();
            }
        });

        permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(AddRoomActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(AddRoomActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }


    public void initEdit() {
        if (room.getImgUrl() != null && !room.getImgUrl().isEmpty()) {
            //기존 view 숨기기
            binding.addBtnRoomImg.setVisibility(View.GONE);
            binding.addTxtRoomImg.setVisibility(View.GONE);
            binding.addLayoutImg.setBackgroundColor(getResources().getColor(R.color.transparency));

            binding.addImgId1.setVisibility(View.VISIBLE);
            Glide.with(this).load(room.getImgUrl()).into(binding.addImgId1);
        }

        binding.addEditDeposit.setText(room.getDeposit());
        binding.addEditRentMonth.setText(room.getRentMonth());
        binding.addEditUtilities.setText(room.getUtilities());

        String[] utilities = room.getIncludedUtilities().split(", ");
        List<String> lUtilities = Arrays.asList(utilities);
        binding.addSpinnerUtilities.setSelection(lUtilities);

        binding.addAutoSubway.setText(room.getStationName());

        ArrayAdapter buildFloor = (ArrayAdapter) binding.addSpinnerBuildFloor.getAdapter();
        binding.addSpinnerBuildFloor.setSelection(buildFloor.getPosition(room.getBuildFloor()));

        ArrayAdapter myFloor = (ArrayAdapter) binding.addSpinnerMyFloor.getAdapter();
        binding.addSpinnerMyFloor.setSelection(myFloor.getPosition(room.getMyFloor()));

        ArrayAdapter direction = (ArrayAdapter) binding.addSpinnerDirection.getAdapter();
        binding.addSpinnerDirection.setSelection(direction.getPosition(room.getDirection()));

        ArrayAdapter roomType = (ArrayAdapter) binding.addSpinnerRoomType.getAdapter();
        binding.addSpinnerRoomType.setSelection(roomType.getPosition(room.getRoomType()));

        binding.addEditRoomSizeM.setText(room.getRoomSizeM());
        binding.addEditRoomSizeP.setText(room.getRoomSizeP());

        String[] options = room.getOption().split(", ");
        List<String> lOptions = Arrays.asList(options);
        binding.addSpinnerOption.setSelection(lOptions);

        if (room.getAnimal() != null) {
            binding.addToggleAnimal.setValue(Integer.parseInt(room.getAnimal()));
        }

        if (room.getElevator() != null) {
            binding.addToggleElevator.setValue(Integer.parseInt(room.getElevator()));
        }

        if (room.getParking() != null) {
            binding.addToggleParking.setValue(Integer.parseInt(room.getParking()));
        }

        binding.addEditDetail.setText(room.getDetail());
    }

    private void initToolbar() {
        setSupportActionBar(binding.addToolbar.toolbar);

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

    public boolean onRoomImageLongClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddRoomActivity.this);
        builder.setTitle(R.string.alert);
        builder.setMessage(R.string.addDeleteImgMessage);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //LongClick시 사진 삭제
                binding.addImgId1.setImageResource(0);
                binding.addImgId1.setVisibility(View.GONE);

                //기존 view 보이기
                binding.addBtnRoomImg.setVisibility(View.VISIBLE);
                binding.addTxtRoomImg.setVisibility(View.VISIBLE);
                binding.addLayoutImg.setBackgroundColor(getResources().getColor(R.color.alphaGray));
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();

        return false;
    }

    public void onAddRoomImage(View view) {
        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage("사진 추가를 하기 위해서 사진첩 접근 권한이 필요합니다.")
                .setDeniedMessage("사진첩 접근 권한이 거부되어있어 사진 추가가 불가능합니다.\n[설정] > [권한] 에서 권한을 허용해주세요.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();

        TedBottomPicker.with(AddRoomActivity.this)
                .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                    @Override
                    public void onImageSelected(Uri uri) {
                        //TODO: 나중에 사진 여러장 추가 가능하도록 수정하기
                        imgUrl = uri.toString();

                        //기존 view 숨기기
                        binding.addBtnRoomImg.setVisibility(View.GONE);
                        binding.addTxtRoomImg.setVisibility(View.GONE);

                        //ImageView 보이기
                        binding.addImgId1.setVisibility(View.VISIBLE);

                        //layout 배경색 없애기
                        binding.addLayoutImg.setBackgroundColor(getResources().getColor(R.color.transparency));

                        Glide.with(AddRoomActivity.this).load(uri).into(binding.addImgId1);
                    }
                });
    }

    public void onSubmitButtonClick(View view) {
        if (binding.addEditDeposit.getText().length() != 0 &&
                binding.addEditRentMonth.getText().length() != 0&&
                binding.addEditUtilities.getText().length() != 0) {
            //방 추가
            String deposit = binding.addEditDeposit.getText().toString();
            String rentMonth = binding.addEditRentMonth.getText().toString();
            String rentType = rentMonth.equals("0") ? getString(R.string.rentYear) : getString(R.string.rentMonth);
            String utilities = binding.addEditUtilities.getText().toString();
            String includedUtilities = binding.addSpinnerUtilities.getSelectedItemsAsString();
            String stationName = subway.getStationName();
            String routeName = subway.getRouteNameList().size()!=0 ? Util.convertArrayToString(subway.getRouteNameList().toArray(new String[subway.getRouteNameList().size()])) : "";
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

            if (pageType.equals("ADD")) {
                helper.insertColumn(deposit, rentMonth, rentType, utilities, includedUtilities
                        , stationName, routeName, buildFloor, myFloor, direction, roomType
                        , roomSizeM, roomSizeP, option, animal, elevator, parking, detail, imgUrl);
                helper.close();
                Toast.makeText(this, "방 추가 완료", Toast.LENGTH_SHORT).show();
                finish();
            } else if (pageType.equals("EDIT")) {
                helper.updateColumn(Integer.parseInt(room.getId()), deposit, rentMonth, rentType, utilities, includedUtilities
                        , stationName, routeName
                        , buildFloor, myFloor, direction, roomType, roomSizeM, roomSizeP, option
                        , animal, elevator, parking, detail, imgUrl);
                helper.close();
                Toast.makeText(this, "방 수정 완료", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent();
                Room editRoom = new Room(room.getId(), deposit, rentMonth, rentType, utilities, includedUtilities
                                , stationName, routeName, buildFloor, myFloor, direction, roomType, roomSizeM, roomSizeP, option
                                , animal, elevator, parking, detail, imgUrl);
                intent.putExtra("room", editRoom);
                setResult(RESULT_OK, intent);
                finish();
            }
        } else {
            binding.addRootView.clearFocus();
            hideKeyboard();

            //필수 항목 체크 알림
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.alert);
            builder.setMessage(R.string.addInputAlertMessage);
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //ScrollView 상단으로 이동
                    binding.addScrollview.smoothScrollTo(0, 0);
                }
            });
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
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.showSoftInput(input,0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}