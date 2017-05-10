package com.chestnut.lidong.ui.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by AshZheng on 2016/9/8.
 */
public class MyScrollView extends ScrollView {

    private int lastScrollY;

    MyScrollViewListener myScrollViewListener;

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setMyScrollViewListener(MyScrollViewListener myScrollViewListener) {
        this.myScrollViewListener = myScrollViewListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (myScrollViewListener != null) {
            myScrollViewListener.onScroll(this.getScrollY());
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            handler.sendMessageDelayed(handler.obtainMessage(), 20);
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 用于用户手指离开MyScrollView的时候获取MyScrollView滚动的Y距离，然后回调给onScroll方法中
     */
    private Handler handler = new Handler() {

        public void handleMessage(android.os.Message msg) {
            int scrollY = MyScrollView.this.getScrollY();

            //此时的距离和记录下的距离不相等，再隔5毫秒给handler发送消息
            if (lastScrollY != scrollY) {
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
            }
            if (myScrollViewListener != null) {
                myScrollViewListener.onScroll(scrollY);
            }
        }
    };

    public interface MyScrollViewListener {
        void onScroll(int t);
    }
}
