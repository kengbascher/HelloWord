package kengbascher.helloworld;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.BoolRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Kengbascher on 5/12/2016.
 */
public class CustomView extends View {

    private Boolean isBlue = false;
    private Boolean isDown = false;
    private GestureDetector gestureDetector;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs,0,0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs,defStyleAttr,0);
    }

    @TargetApi(21)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs,defStyleAttr,defStyleRes);
    }

    private void init() {
        // แม่สามารถขโมย Event ของ Class ลูกไปใช้ได้
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                // Decide : Care or not care?

                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                // Do whatever you want
                getParent().requestDisallowInterceptTouchEvent(true);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                // Action Up
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                // Action ที่ปัดขึ้น,ลง ซ้าย,ขวา
                isBlue = !isBlue;
                invalidate();
                return true;
            }
        });

        // Enable Click

        setClickable(true);

        //After is call, if it's not clickable, it will be clickable.
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs, R.styleable.CustomView, defStyleAttr, defStyleRes);
        try {
            isBlue = a.getBoolean(R.styleable.CustomView_isBlue,false);
        }finally {
            a.recycle();

        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        if (isBlue){
            paint.setColor(0xFF0000FF); // #Blue
        }else {
            paint.setColor(0xFFFF0000); // #Red
        }

        canvas.drawLine(0, 0, getMeasuredWidth(), getMeasuredHeight(), paint);

        if (isDown){
            paint.setColor(0xFF00FF00);

            //Convert dp tp px
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5,
                    getContext().getResources().getDisplayMetrics());
            paint.setStrokeWidth(px);

            canvas.drawLine(0, getMeasuredHeight(), getMeasuredWidth(),0 , paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Pass Event to gestureDetector
        gestureDetector.onTouchEvent(event);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                invalidate();
                return true;
            case MotionEvent.ACTION_CANCEL:
                isDown = false;
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected Parcelable onSaveInstanceState() {

        Parcelable superState = super.onSaveInstanceState();

        // วิธีใช้แบบ CustomViewSavedState
//        CustomViewSavedState savedState = new CustomViewSavedState(superState);
//        savedState.setBlue(isBlue);

        //วิธีใช้แบบ Bundle
        BundleSavedState  savedState = new BundleSavedState(superState);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBlue", isBlue);
        savedState.setBundle(bundle);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        //วิธี Restore แบบ CustomSavedState
        //CustomViewSavedState savedState = (CustomViewSavedState) state;
        //super.onRestoreInstanceState(savedState.getSuperState());
        //isBlue = savedState.isBlue();

        //วิธี Restore แบบ Bundle
        BundleSavedState savedState = (BundleSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        Bundle bundle = savedState.getBundle();
        isBlue = bundle.getBoolean("isBlue");


    }
}
