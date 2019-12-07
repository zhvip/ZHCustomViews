package com.zh.customviews.activitys;

import android.os.Bundle;
import android.widget.TextView;

import com.zh.customviews.customView.IOSUISwitch;
import com.zh.zhcustomviews.R;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class IOSUISwitchActivity extends AppCompatActivity {

    @BindView(R.id.switch_one)
    IOSUISwitch switchOne;
    @BindView(R.id.switch_state_one)
    TextView switchStateOne;
    @BindView(R.id.switch_two)
    IOSUISwitch switchTwo;
    @BindView(R.id.switch_state_two)
    TextView switchStateTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iosuiswitch);
        ButterKnife.bind(this);

        switchOne.setOnSwitchValueChange(new IOSUISwitch.OnSwitchValueChange() {
            @Override
            public void onValueChanged(boolean isOpen) {
                if (isOpen){
                    switchStateOne.setText("开");
                }else {
                    switchStateOne.setText("关");
                }
            }
        });

        switchTwo.setOnSwitchValueChange(new IOSUISwitch.OnSwitchValueChange() {
            @Override
            public void onValueChanged(boolean isOpen) {
                if (isOpen){
                    switchStateTwo.setText("开");
                }else {
                    switchStateTwo.setText("关");
                }
            }
        });
    }
}
