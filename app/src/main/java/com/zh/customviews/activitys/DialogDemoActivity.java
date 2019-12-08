package com.zh.customviews.activitys;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.zh.customviews.customView.IOSAlertView;
import com.zh.customviews.customView.MyToast;
import com.zh.zhcustomviews.R;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogDemoActivity extends AppCompatActivity {

    private AlertDialog alertDialog;
    private Button okBtn, cancelBtn;

    private IOSAlertView alertView;
    private MyToast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_demo);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.dialog_btn, R.id.iOSAlerViewBtn,R.id.toastBtn})
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


            case R.id.toastBtn:

                myToast = new MyToast.Builder(this)
                        .setMessage("自定义Toast效果！")//设置提示文字
                        .setGravity(Gravity.CENTER)//设置吐司位置
                        .showIcon(true)//是否显示图标
                        .build();//创建吐司

                myToast.show();
                break;


        }
    }
}
