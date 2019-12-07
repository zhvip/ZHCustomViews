package com.zh.customviews.customView;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zh.zhcustomviews.R;

import androidx.annotation.NonNull;


public class IOSAlertView extends Dialog {

    private TextView titleText;
    private Button cancelBtn;
    private Button okBtn;
    public IOSAlertView(@NonNull Context context) {
        super(context,R.style.Dialog_iOS_style);
        View view = LayoutInflater.from(context).inflate(R.layout.ios_alert_view,null);
        titleText = view.findViewById(R.id.title_text);
        cancelBtn = view.findViewById(R.id.btn_cancel);
        okBtn = view.findViewById(R.id.btn_ok);

        setContentView(view);
    }

    public void setTitle(String title){
        titleText.setText(title);
    }

    public void setOnCancelBtnListener(View.OnClickListener listener){
        cancelBtn.setOnClickListener(listener);
    }

    public void setOnOkBtnListener(View.OnClickListener listener){
        okBtn.setOnClickListener(listener);
    }
}
