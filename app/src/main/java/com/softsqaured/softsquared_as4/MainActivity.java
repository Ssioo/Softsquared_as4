package com.softsqaured.softsquared_as4;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout gamespace;
    private int VIEW_WIDTH;
    private int VIEW_HEIGHT;

    private ArrayList<Ball> balls = new ArrayList<>();

    public float ballPosX, ballPosY;

    private Handler handler = new Handler();
    //MotionEvent Pos
    private float dX, dY;
    private boolean isRunningThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* findViewByID */
        gamespace = findViewById(R.id.frame_game_space);

        /* Get Display Size */
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        VIEW_WIDTH = size.x;
        VIEW_HEIGHT = size.y - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
        Log.i("gamespace size", VIEW_HEIGHT + ", " + VIEW_WIDTH);

        /* Ball Initializing */
        Ball newBall = new Ball(this, gamespace);
        newBall.setBackground(getResources().getDrawable(R.drawable.ic_circle));
        newBall.setVIEW_HEIGHT_WIDTH(VIEW_HEIGHT, VIEW_WIDTH);
        balls.add(newBall);
        Ball newBall2 = new Ball(this, gamespace);
        newBall2.setBackground(getResources().getDrawable(R.drawable.ic_circle));
        newBall2.setVIEW_HEIGHT_WIDTH(VIEW_HEIGHT, VIEW_WIDTH);
        balls.add(newBall2);

        int ballSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ballSize, ballSize);
        ballPosX = VIEW_WIDTH / 2 - ballSize / 2;
        ballPosY = VIEW_HEIGHT - ballSize;
        for (int i = 0; i < balls.size(); i++) {
            balls.get(i).x = ballPosX;
            balls.get(i).y = ballPosY;
            balls.get(i).width = ballSize;
            balls.get(i).height = ballSize;
            gamespace.addView(balls.get(i), params);
            gamespace.getChildAt(i).setX(ballPosX);
            gamespace.getChildAt(i).setY(ballPosY);
            gamespace.getChildAt(i).setVisibility(View.VISIBLE);
        }
        Log.i("ballSize", String.valueOf(gamespace.getChildCount()));
        //gamespace.getChildAt(1).setX(ballPosX + 200);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!balls.get(balls.size() - 1).running) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    // 뗄 때
                    for (int i = 0; i < balls.size(); i++) {
                        final int finalI = i;
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                balls.get(finalI).setVel(dX, dY);
                                balls.get(finalI).startThread();
                            }
                        }, 200);

                    }
                    break;
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    // 누르면서 움직일 때
                    dY = event.getY() - ballPosY - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());
                    dX = event.getX() - ballPosX;

                    break;
            }
        }
        return true;
    }
}
