package com.zh.customviews.drawable;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.TypedValue;

import com.zh.customviews.utils.Utils;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @ClassName BgColorDrawable
 * @Description TODO
 * @Author zh
 * @Date 2019-12-08 11:30
 */


public class BgColorDrawable extends Drawable{
    private Paint paint;
    private Context mContext;
    public BgColorDrawable(Context context,@ColorInt int color) {
        mContext = context.getApplicationContext();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setDither(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void draw(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        canvas.drawRoundRect(new RectF(0,0,width,height),Utils.dip2px(mContext,20),Utils.dip2px(mContext,20),paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}
