package com.zmm.dashboardview.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.zmm.dashboardview.R;
import com.zmm.dashboardview.view.DashboardView;

/**
 * Description:
 * Author:zhangmengmeng
 * Date:2017/9/28
 * Time:上午10:50
 */

public class SecondActivity extends AppCompatActivity {

    private DashboardView mDashboardView2;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mDashboardView2 = (DashboardView) findViewById(R.id.panelView2);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mDashboardView2.setPercent(progress);
                mDashboardView2.setPowerValue(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
