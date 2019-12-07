package com.zh.customviews.customView;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.zh.customviews.utils.GestureUtils;
import com.zh.customviews.utils.Utils;
import com.zh.zhcustomviews.R;

import androidx.annotation.Nullable;

public class IOSUISwitch extends View implements View.OnClickListener {

    private Context mComtext;
    private int mHandleColor = Color.parseColor("#FFFFFF"); //把手的颜色
    private int mOpenSlotColor = Color.parseColor("#4ebb7f"); //开关打开槽的颜色
    private int mOffSlotColor = Color.parseColor("#EEEEEE");//开关关闭槽的颜色

    private int mSlotColor;//槽的过渡颜色
    private float BORDER_WIDTH = 2; //边框
    private boolean mIsToggleOn = false;//默认关闭

    private float mSlotRadius;//槽的半径
    private float mHandleRadius;//把手的半径
    private boolean isSrcollEvent = false;//是否是滑动事件 是就取消点击事件
    private boolean isMoving = false;//是否正在滑动 动画需要一定的时间

    //把手的起始位置
    private float mHandleStartX; //X : [BORDER_WIDTH , getWidth() - BORDER_WIDTH - 2 * mHandleRadius]
    private float mHandleStartY;
    private float mHandleOffsetX;//滑块水平滑动的距离

    private GestureUtils gestureUtils;
    private RectF mRect = new RectF();
    private Paint mPaint;//画笔

    private OnSwitchValueChange onSwitchValueChange;

    public void setOnSwitchValueChange(OnSwitchValueChange onSwitchValueChange) {
        this.onSwitchValueChange = onSwitchValueChange;
    }

    public float getMHandleStartX() {
        return mHandleStartX;
    }

    public void setMHandleStartX(float mHandleStartX) {
        this.mHandleStartX = mHandleStartX;
    }

    public IOSUISwitch(Context context) {
        this(context,null);
    }

    public IOSUISwitch(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public IOSUISwitch(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mComtext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IOSUISwitch);
        if (typedArray != null){
            mHandleColor = typedArray.getColor(R.styleable.IOSUISwitch_mHandleColor, mHandleColor);
            mOpenSlotColor = typedArray.getColor(R.styleable.IOSUISwitch_mOpenSlotColor,mOpenSlotColor);
            mOffSlotColor = typedArray.getColor(R.styleable.IOSUISwitch_mOffSlotColor,mOffSlotColor);
            mIsToggleOn = typedArray.getBoolean(R.styleable.IOSUISwitch_switchState,mIsToggleOn);
            BORDER_WIDTH = Utils.dip2px(context,typedArray.getDimension(R.styleable.IOSUISwitch_mBorderWidth,BORDER_WIDTH));
            typedArray.recycle();
        }
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setEnabled(true);
        setOnClickListener(this);
        gestureUtils = new GestureUtils();
    }


    public void setSwitchOpen(final boolean isOpen){
        this.post(new Runnable() {
            @Override
            public void run() {
                touchToggle(isOpen,false);
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                gestureUtils.actionDown(event);
                isSrcollEvent = false;
                isMoving = false;
                break;
            case MotionEvent.ACTION_MOVE:
                gestureUtils.actionMove(event);
                if (gestureUtils.getGesture(GestureUtils.Gesture.PullLeft)){//左滑 关闭开关
                    isSrcollEvent = true;
                    touchToggle(false,true);
                    return true;
                }else if (gestureUtils.getGesture(GestureUtils.Gesture.PullRight)){//右滑 打开开关
                    isSrcollEvent = true;
                    touchToggle(true,true);
                    return true;
                }
                break;

            case MotionEvent.ACTION_CANCEL:
                break;

            case MotionEvent.ACTION_UP:
                isMoving = false;
                if (isSrcollEvent) return true;
                //到这里就会触发 onClick事件
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    /*
    * 设置开关
    * isCallback 是否是代码设置 如果是代码设置 那就没有回调
    * */

    private void touchToggle(boolean isOpen,boolean isCallback){
        if (isMoving) return;
        if (mIsToggleOn == isOpen) return;
        isMoving = true;
        if (isOpen){//打开开关
            toggleOn();
        }else {
            toggleOff();
        }
        mIsToggleOn = isOpen;

        if (isCallback){
            if (onSwitchValueChange != null) onSwitchValueChange.onValueChanged(mIsToggleOn);
        }


    }

    //打开开关
    private void toggleOn(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(this,"mHandleStartX",BORDER_WIDTH,mHandleOffsetX);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                calculateColor(fraction,mOffSlotColor,mOpenSlotColor);
                postInvalidate();
            }
        });
        animator.start();
    }

    private void toggleOff(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(this,"mHandleStartX",mHandleOffsetX,BORDER_WIDTH);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float fraction = valueAnimator.getAnimatedFraction();
                calculateColor(fraction,mOpenSlotColor,mOffSlotColor);
                postInvalidate();
            }
        });
        animator.start();
    }

    //点击事件
    @Override
    public void onClick(View view) {
        onClickToggle();
    }

    private void onClickToggle(){
        if (mIsToggleOn){
            toggleOff();
        }else {
            toggleOn();
        }

        mIsToggleOn = !mIsToggleOn;

        if (onSwitchValueChange != null) onSwitchValueChange.onValueChanged(mIsToggleOn);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int wMode = MeasureSpec.getMode(widthMeasureSpec);
        int hMode = MeasureSpec.getMode(heightMeasureSpec);
        int wSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSize = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = wSize;
        int resultHeight = hSize;

        if (wMode == MeasureSpec.AT_MOST){//wrapcontent
            resultWidth = Utils.dip2px(mComtext,50);
        }
        if (hMode == MeasureSpec.AT_MOST){
            resultHeight = Utils.dip2px(mComtext,30);
        }
        setMeasuredDimension(resultWidth,resultHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSlotRadius = Math.min(getWidth(),getHeight()) * 0.5f;
        mHandleRadius = mSlotRadius - BORDER_WIDTH;

        mHandleStartY = BORDER_WIDTH;
        mHandleOffsetX = getWidth() - BORDER_WIDTH - 2 * mHandleRadius;

        if (mIsToggleOn){//打开
            mSlotColor = mOpenSlotColor;
            mHandleStartX = getWidth() - (2 * mHandleRadius) - BORDER_WIDTH;
        }else {
            mSlotColor = mOffSlotColor;
            mHandleStartX = BORDER_WIDTH;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //先画滑动槽
        mRect.set(0,0,getMeasuredWidth(),getMeasuredHeight());
        mPaint.setColor(mSlotColor);
        canvas.drawRoundRect(mRect,mSlotRadius,mSlotRadius,mPaint);

        //再画滑块
        mRect.set(mHandleStartX,
                mHandleStartY,
                mHandleStartX + 2 * mHandleRadius,
                mHandleStartY + 2 * mHandleRadius);
        mPaint.setColor(mHandleColor);
        canvas.drawRoundRect(mRect, mHandleRadius, mHandleRadius, mPaint);

    }

    /**
     * 计算切换时的手柄槽的颜色
     *
     * @param fraction   动画播放进度
     * @param startColor 起始颜色
     * @param endColor   终止颜色
     */
    public void calculateColor(float fraction, int startColor, int endColor) {
        final int fb = Color.blue(startColor);
        final int fr = Color.red(startColor);
        final int fg = Color.green(startColor);

        final int tb = Color.blue(endColor);
        final int tr = Color.red(endColor);
        final int tg = Color.green(endColor);

        //RGB三通道线性渐变
        int sr = (int) (fr + fraction * (tr - fr));
        int sg = (int) (fg + fraction * (tg - fg));
        int sb = (int) (fb + fraction * (tb - fb));
        //范围限定
        sb = clamp(sb, 0, 255);
        sr = clamp(sr, 0, 255);
        sg = clamp(sg, 0, 255);

        mSlotColor = Color.rgb(sr, sg, sb);
    }

    private int clamp(int value, int low, int high) {
        return Math.min(Math.max(value, low), high);
    }

    public interface  OnSwitchValueChange{
        void onValueChanged(boolean isOpen);
    }

}
