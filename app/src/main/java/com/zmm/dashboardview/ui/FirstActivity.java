package com.zmm.dashboardview.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.zmm.dashboardview.GameView;
import com.zmm.dashboardview.R;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/9/28
 * Time:上午10:48
 */

public class FirstActivity extends AppCompatActivity {

    GameView view;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        view=(GameView) findViewById(R.id.view);
//        view.setVisibility(View.INVISIBLE);
        view.setMaxValue(180);
        view.setMinValue(0);
        view.setStartRadian(160);
        view.setEndRadian(380);
        view.setValue(120);
    }
}
