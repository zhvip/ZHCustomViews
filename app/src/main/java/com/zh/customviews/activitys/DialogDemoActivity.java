package com.zh.customviews.activitys;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zh.customviews.customView.IOSAlertView;
import com.zh.zhcustomviews.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogDemoActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private Button okBtn, cancelBtn;

    private IOSAlertView alertView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_demo);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.dialog_btn, R.id.iOSAlerViewBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dialog_btn:
                alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.show();

                Window window = alertDialog.getWindow();
                window.setGravity(Gravity.CENTER);
                window.setWindowAnimations(R.style.Dialog_style);
                window.setContentView(R.layout.dialog_layout);

                okBtn = window.findViewById(R.id.btn_determine);
                cancelBtn = window.findViewById(R.id.btn_cancel);

                okBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        alertDialog = null;
                        finish();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                        alertDialog = null;
                    }
                });
                break;
            case R.id.iOSAlerViewBtn:
                alertView = new IOSAlertView(this);
                alertView.setOnCancelBtnListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        alertView.dismiss();
                    }
                });

                alertView.setOnOkBtnListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        alertView.dismiss();
                    }
                });
                alertView.show();
                break;
        }
    }
}
