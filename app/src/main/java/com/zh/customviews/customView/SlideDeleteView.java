package com.zh.customviews.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.zh.zhcustomviews.R;

public class SlideDeleteView extends HorizontalScrollView {

    private TextView deleteTextView;
    private int scrollWidth;
    private Boolean isFirst = true;
    public SlideDeleteView(Context context) {
        this(context,null);
    }

    public SlideDeleteView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SlideDeleteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (isFirst){
            deleteTextView = findViewById(R.id.tv_delete);
            isFirst = false;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            this.scrollTo(0,0);
            scrollWidth = deleteTextView.getWidth();
        }
    }

    /**
     *滑动手指抬起时的手势判断
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollx();            //根据滑动距离判断是否显示删除按钮
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 根据滑动距离判断是否显示删除按钮
     */
    public void changeScrollx(){
        //触摸滑动的距离大于删除按钮宽度的一半
        if(getScrollX() >= (scrollWidth/2)){
            //显示删除按钮
            this.smoothScrollTo(scrollWidth, 0);
        }else{
            //隐藏删除按钮
            this.smoothScrollTo(0, 0);
        }
    }

}
