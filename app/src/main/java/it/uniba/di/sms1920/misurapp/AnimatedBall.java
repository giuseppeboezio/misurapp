package it.uniba.di.sms1920.misurapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import android.os.Handler;

import androidx.appcompat.widget.AppCompatImageView;

import com.google.android.material.canvas.CanvasCompat;

public class AnimatedBall extends AppCompatImageView {

    private Context mContext;

    int x = -1;

    int y = -1;

    private int xVelocity = 10;

    private int yVelocity = 5;

    private Handler h;

    private final int FRAME_RATE = 30;


    public AnimatedBall(Context context, AttributeSet attrs)  {

        super(context, attrs);

        mContext = context;

        h = new Handler();

    }

    private Runnable r = new Runnable() {

        @Override

        public void run() {

            invalidate();

        }

    };

    protected void onDraw(Canvas c) {

        BitmapDrawable ball = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.baloon, null);

        if (x<0 && y <0) {

            x = this.getWidth()/2;

            y = this.getHeight()/2;

        } else {

            x += xVelocity;

            y += yVelocity;

            if ((x > this.getWidth() - ball.getBitmap().getWidth()) || (x < 0)) {

                xVelocity = xVelocity*-1;

            }

            if ((y > this.getHeight() - ball.getBitmap().getHeight()) || (y < 0)) {

                yVelocity = yVelocity*-1;

            }

        }

        c.drawBitmap(ball.getBitmap(), x, y, null);

        h.postDelayed(r, FRAME_RATE);

    }
}
