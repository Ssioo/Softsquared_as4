package com.softsqaured.softsquared_as4;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

public class Ball extends View {
    public int viewId;
    public float x;
    public float y;
    public int width;
    public int height;
    public float vel_x;
    public float vel_y;
    public boolean running = false;
    private int VIEW_HEIGHT;
    private int VIEW_WIDTH;
    private RelativeLayout parentLayout;

    private Context context;

    public Ball(Context context, RelativeLayout parentLayout) {
        super(context);
        init(parentLayout, context);
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public void setVIEW_HEIGHT_WIDTH(int VIEW_HEIGHT, int VIEW_WIDTH) {
        this.VIEW_HEIGHT = VIEW_HEIGHT;
        this.VIEW_WIDTH = VIEW_WIDTH;
    }


    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    }

    void init(RelativeLayout parentLayout, Context context) {
        /* Initializing */
        this.context = context;
        this.parentLayout = parentLayout;
        vel_x = 10;
        vel_y = 10;
    }

    public void setVel(float dX, float dY) {
        vel_x = (float) (10 * dX / Math.sqrt(dX * dX + dY * dY));
        vel_y = (float) (10 * dY / Math.sqrt(dX * dX + dY * dY));
    }

    public void startThread() {
        BallThread r = new BallThread();
        if (!running) {
            running = true;
            new Thread(r).start();
        }
    }

    public void updateBallPos() {
        x += vel_x;
        y += vel_y;
        parentLayout.getChildAt(viewId).setX(x);
        parentLayout.getChildAt(viewId).setY(y);
        Log.i("Change", x + "," + y);
        check_boundary();
    }

    public void destroyBall() {
        running = false;
        ((MainActivity) context).ballPosX = x;
        ((MainActivity) context).ballPosY = y;
    }


    public void check_crash(Block block) {

    }

    private void check_boundary() {
        if (x >= VIEW_WIDTH - width) {
            x = VIEW_WIDTH - width;
            vel_x = -vel_x;
        } else if (x <= 0) {
            x = 0;
            vel_x = -vel_x;
        }
        if (y > VIEW_HEIGHT - height) {
            y = VIEW_HEIGHT - height;
            destroyBall();
        } else if (y <= 0) {
            y = 0;
            vel_y = -vel_y;
        }
    }

    public class BallThread implements Runnable{

        @Override
        public void run() {
            while (running) {
                updateBallPos();
                Log.i("currentThread", Thread.currentThread().getName());
                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            running = false;
        }
    }
}
