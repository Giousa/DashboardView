package com.zmm.dashboardview.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zmm.dashboardview.GameView;
import com.zmm.dashboardview.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    GameView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


    }

    @OnClick({R.id.btn_one, R.id.btn_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                Intent intent1 = new Intent(this,FirstActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_two:
                Intent intent2 = new Intent(this,SecondActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
