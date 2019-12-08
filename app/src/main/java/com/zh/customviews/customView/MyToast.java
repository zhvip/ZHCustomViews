package com.zh.customviews.customView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zh.customviews.drawable.BgColorDrawable;
import com.zh.zhcustomviews.R;

/**
 * @ClassName MyToast
 * @Description 我就是一个Toast
 * @Author zh
 * @Date 2019-12-08 11:04
 */


public final class MyToast {

    public final Toast toast;
    public final View view;
    public final ImageView imageView;
    public final TextView textView;

    public MyToast(Context mContext) {

        this.toast = new Toast(mContext);
        this.view = LayoutInflater.from(mContext).inflate(R.layout.mytoast_layout,null);
        this.imageView = view.findViewById(R.id.toast_icon);
        this.textView = view.findViewById(R.id.toast_message);
    }

    public void show(){
        this.toast.show();
    }

    public static class Builder{
        private Bitmap icon;
        private int iconID = R.mipmap.ic_launcher;
        private String message;
        private int bgColor = Color.parseColor("#560000");
        private Context mContext;
        private int duration = Toast.LENGTH_LONG;
        private MyToast myToast;
        private int gravity = Gravity.NO_GRAVITY;//设置位置
        private int offsetX = 0;//设置偏移度X
        private int offsetY = 0;//设置偏移度Y
        private boolean isShowIcon;//是否显示图标

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setIcon(Bitmap icon) {
            this.icon = icon;
            return this;
        }

        public Builder setIconID(int iconID) {
            this.iconID = iconID;
            return this;
        }
        public Builder showIcon(boolean isShow){
            this.isShowIcon = isShow;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setBgColor(int bgColor) {
            this.bgColor = bgColor;
            return this;
        }

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setMyToast(MyToast myToast) {
            this.myToast = myToast;
            return this;
        }

        public Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public Builder setOffsetX(int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public Builder setOffsetY(int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public MyToast build(){
            if (myToast == null){
                myToast = new MyToast(mContext);
            }

            if (isShowIcon){
                myToast.imageView.setVisibility(View.VISIBLE);
                if (icon != null){
                    myToast.imageView.setImageBitmap(icon);
                }else {
                    myToast.imageView.setBackgroundResource(iconID);
                }
            }

            if (!message.isEmpty()){
                myToast.textView.setText(message);
            }else {
                myToast.textView.setText("");
            }
            myToast.view.setBackground(new BgColorDrawable(mContext,bgColor));
            myToast.toast.setDuration(duration);//设置时长
            myToast.toast.setView(myToast.view);//添加自定义效果
            myToast.toast.setGravity(gravity,offsetX,offsetY);//设置偏移量
            return myToast;
        }
    }

}
