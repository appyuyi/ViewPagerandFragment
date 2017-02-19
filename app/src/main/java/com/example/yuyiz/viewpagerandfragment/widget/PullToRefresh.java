package com.example.yuyiz.viewpagerandfragment.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by yuyiz on 2017/2/18.
 */

public class PullToRefresh extends RelativeLayout {

    //TAG
    public static final String TAG = "PullToRefresh";


    //初始状态
    public static final int ORIGINAL_STATE = 0;
    //正在刷新状态
    public static final int REFRESHING = 1;
    //释放刷新状态
    public static final int RELEASE_TO_REFRESH = 2;
    //加载状态
    public static final int LOADING = 3;
    //释放加载状态
    public static final int RELEASE_TO_LOADING = 4;
    //完成
    public static final int DONE = 5;

    //刷新成功
    public static final int SUCCEED = 0;
    //刷新失败
    public static final int FAILED = 1;
    //当前状态
    public int currentState;
    //按下时记录Y坐标，上一个事件点Y坐标
    private float downY, lastY;
    //下拉距离
    private float pullDownY;
    //上拉距离
    private float pullUpY;
    //回弹速度
    private float MOVE_SPEED;
    //释放刷新要达到的距离
    private float refreshDist = 200;
    //释放加载时要达到的距离
    private float loadMoreDist = 200;
    //用于过滤多点操控
    private int mEvents;
    //用来控制方向，控制是上拉和下拉
    private boolean canPullDown = true;
    private boolean canPullUp = true;
    //滑动比例
    private float ratio = 1;
    //正在刷新
    private boolean isTouch = false;
    //接口
    private OnRefreshListener myListener;
    //
    private MyTimer myTimer;
    Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //设置回弹速度，回弹速度会随着下拉的距离moveDeltaY增大而增大
            MOVE_SPEED = (float) (8 + 5 * Math.tan(Math.PI / 2
                    / getMeasuredHeight() * (pullDownY + Math.abs(pullUpY))));
            if (!isTouch) {
                //正在刷新，且没有往上推的话则悬停，显示"正在刷新..."
                if (currentState == REFRESHING && pullDownY <= refreshDist) {
                    pullDownY = refreshDist;
                    myTimer.cancel();
                } else if (currentState == LOADING && -pullUpY <= loadMoreDist) {
                    pullUpY = -loadMoreDist;
                    myTimer.cancel();
                }
                if (pullDownY > 0)
                    pullDownY -= MOVE_SPEED;
                else if (pullUpY < 0)
                    pullUpY += MOVE_SPEED;
                if (pullDownY < 0)
                {
                    // 已完成回弹
                    pullDownY = 0;
//                    pullView.clearAnimation();
                    // 隐藏下拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
                    if (currentState != REFRESHING && currentState != LOADING)
                        changeState(ORIGINAL_STATE);
                    myTimer.cancel();
                    requestLayout();
                }
                if (pullUpY > 0)
                {
                    // 已完成回弹
                    pullUpY = 0;
//                    pullUpView.clearAnimation();
                    // 隐藏上拉头时有可能还在刷新，只有当前状态不是正在刷新时才改变状态
                    if (currentState != REFRESHING && currentState != LOADING)
                        changeState(ORIGINAL_STATE);
                    myTimer.cancel();
                    requestLayout();
                }
                Log.d("handle", "handle");
                // 刷新布局,会自动调用onLayout
                requestLayout();
                // 没有拖拉或者回弹完成
                if (pullDownY + Math.abs(pullUpY) == 0)
                    myTimer.cancel();
            }
        }
    };

    public PullToRefresh(Context context) {
        super(context);
        initView();
    }

    public PullToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PullToRefresh(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public OnRefreshListener getMyListener() {
        return myListener;
    }

    public void setMyListener(OnRefreshListener myListener) {
        this.myListener = myListener;
    }

    void initView() {
        //初始化
        myTimer = new MyTimer(updateHandler);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN://手指按下
                //记录按下时手指Y轴位置
                downY = getY();
                lastY = downY;
                mEvents = 0;
                releasePull();
                break;
            case MotionEvent.ACTION_POINTER_DOWN://非主要手指按下
                break;
            case MotionEvent.ACTION_POINTER_UP://非主要手指抬起
                mEvents = -1;
                break;
            case MotionEvent.ACTION_MOVE://手指移动
                if (mEvents == 0) {
                    //计算上拉或计算下拉距离
                    if (pullDownY >= 0 && canPullDown && currentState != REFRESHING) {//可以向下滑动，并且当前状态不在加载刷新状态
                        //计算下拉距离
                        pullDownY += (event.getY() - lastY) / ratio;
                        if (pullDownY < 0) {
                            pullDownY = 0;
                            canPullDown = true;
                            canPullUp = false;
                        }
                        if (pullDownY > getMeasuredHeight()) {//如果下拉距离大于MeasuredHeight,即超过整个控件的高度
                            pullDownY = getMeasuredHeight();
                        }
                        if (currentState == LOADING) {
                            isTouch = true;
                        }
                    } else if (pullUpY <= 0 && canPullUp && currentState != LOADING) {//可以向下滑动，并且当前状态不在加载刷新状态
                        //计算上拉距离
                        pullUpY += (event.getY() - lastY) / ratio;

                        if (pullUpY > 0) {
                            pullUpY = 0;
                            canPullDown = false;
                            canPullUp = true;
                        }
                        if (pullUpY > -getMeasuredHeight()) {//如果上拉距离大于MeasuredHeight,即超过整个控件的高度
                            pullUpY = -getMeasuredHeight();
                        }
                        if (currentState == REFRESHING) {
                            isTouch = true;
                        }
                    } else
                        releasePull();
                } else
                    mEvents = 0;
                lastY = event.getY();
                /*// 根据下拉距离改变比例
                ratio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight()
                        * (pullDownY + Math.abs(pullUpY))));*/
                if (pullDownY > 0 || pullUpY < 0) {
                    requestLayout();
                }
                //修改状态
                if (pullDownY > 0) {
                    if (pullDownY < refreshDist && (currentState == RELEASE_TO_REFRESH || currentState == DONE)) {
                        //如果下拉距离没有达到刷新距离并且当前状态为下拉刷新或为DONE
                        changeState(ORIGINAL_STATE);
                    }
                    if (pullDownY >= refreshDist && currentState == ORIGINAL_STATE) {
                        changeState(RELEASE_TO_REFRESH);
                    }
                } else if (pullUpY < 0) {
                    if (pullUpY < loadMoreDist && (currentState == RELEASE_TO_LOADING || currentState == DONE)) {
                        //如果上拉距离没有达到加载距离并且当前状态为上拉加载或为DONE
                        changeState(ORIGINAL_STATE);
                    }
                    if (pullUpY >= loadMoreDist && currentState == ORIGINAL_STATE) {
                        changeState(RELEASE_TO_LOADING);
                    }
                }
                if ((pullDownY + Math.abs(pullUpY)) > 8) {
                    // 防止下拉过程中误触发长按事件和点击事件
                    event.setAction(MotionEvent.ACTION_CANCEL);
                }

                break;
            case MotionEvent.ACTION_UP://手指抬起
                if (pullDownY > refreshDist || -pullUpY > loadMoreDist) {
                    isTouch = false;
                }
                if (currentState == RELEASE_TO_REFRESH) {
                    changeState(REFRESHING);
                    // 刷新操作
                    if (myListener != null)
                        myListener.onRefresh(this);
                } else if (currentState == RELEASE_TO_LOADING) {
                    changeState(LOADING);
                    //加载操作
                    if (myListener != null)
                        myListener.onLoadMore(this);
                }
                hide();
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void changeState(int originalState) {
    }


    private void releasePull() {
        canPullDown = true;
        canPullUp = true;
    }

    private void hide() {
        myTimer.schedule(5);
    }

    /**
     * 刷新加载回调接口
     *
     * @author chenjing
     */
    public interface OnRefreshListener {
        /**
         * 刷新操作
         */
        void onRefresh(PullToRefresh pullToRefresh);

        /**
         * 加载操作
         */
        void onLoadMore(PullToRefresh pullToRefresh);
    }

    private class MyTimer {
        private Handler myHandler;
        private Timer timer;
        private MyTask myTadk;

        public MyTimer(Handler myHandler) {
            this.myHandler = myHandler;
            timer = new Timer();
        }

        public void schedule(long period) {
            if (myTadk != null) {
                myTadk.cancel();
                myTadk = null;
            }
            myTadk = new MyTask(myHandler);
            timer.schedule(myTadk, 0, period);
        }

        public void cancel() {
            if (myTadk != null) {
                myTadk.cancel();
                myTadk = null;
            }
        }
    }

    private class MyTask extends TimerTask {
        private Handler handler;

        public MyTask(Handler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.obtainMessage().sendToTarget();
        }
    }

    ;
}