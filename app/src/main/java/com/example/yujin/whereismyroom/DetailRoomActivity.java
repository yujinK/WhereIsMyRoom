package com.example.yujin.whereismyroom;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.yujin.whereismyroom.databinding.ActivityDetailRoomBinding;

public class DetailRoomActivity extends AppCompatActivity {

    ActivityDetailRoomBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_room);

        init();
    }

    public void init() {
        setSupportActionBar(binding.detailToolbar.toolbar);
        getSupportActionBar().setTitle(getString(R.string.detailToolBar)); //TODO: ToolBar 뭘로 표시할 지 생각해보기

        //ToolBar 왼쪽 back 버튼
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_arrow_back_white_24);

        binding.detailToolbar.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //exit
                finish();
            }
        });
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
                Toast.makeText(getApplicationContext(), "Select Edit", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
