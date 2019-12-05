package com.zh.customviews.activitys;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.zh.zhcustomviews.R;

public class MenuActivity extends AppCompatActivity {

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        findViewById(R.id.wechart_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }

    private void showMenu(){
        View menuView = getLayoutInflater().inflate(R.layout.menu,null,false);
        popupWindow = new PopupWindow(menuView, ActionBar.LayoutParams.WRAP_CONTENT,ActionBar.LayoutParams.WRAP_CONTENT,true);
        popupWindow.showAsDropDown(findViewById(R.id.wechart_menu));

        menuView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }
                return false;
            }
        });

    }
}
