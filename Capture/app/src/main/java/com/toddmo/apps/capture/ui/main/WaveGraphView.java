package com.toddmo.apps.capture.ui.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import java.time.LocalTime;
import java.util.LinkedList;

public class WaveGraphView extends SurfaceView {
    private final String LOG_TAG = WaveGraphView.class.getCanonicalName();

    private LinkedList<Float> mDataQueue = new LinkedList<Float>();
    private final int kPointPrecision = 4;
    private final int kFontSize = 50;
    private int mMaxQueueSize = 0;


    private boolean mReady = false;
    private int mFormat = -1;
    private int mWidth = -1;
    private int mHeight = -1;
    private int mUpdateInterval = 50; // in millisecond
    private boolean mUpdateNeeded = false;
    private Object mLock = new Object();

    private Handler mHandler = new Handler();
    private Runnable mUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            synchronized (mLock) {
                if (mReady && mUpdateNeeded) {
                    Log.d(LOG_TAG, "draw begin");
                    Canvas canvas = getHolder().lockCanvas();
                    canvas.drawColor(0xffffffff);
                    Paint paint = new Paint();
                    paint.setColor(0xff0000ff);
                    int c = 0;
                    for (int i =0;i<mDataQueue.size();i++) {
                        Float data = mDataQueue.get(i);
                        int x = mWidth - i*kPointPrecision;
                        int y = 0;
                        float factor = 3;
                        data *= factor;
                        float p = (data + 1.0f) * (mHeight - kFontSize) / 2;
                        y = (int)p;
//                        Log.v(LOG_TAG, "data = " + data + ", p = "+p+", (x, y) = " + x + ", " + y);
                        canvas.drawRect(new Rect((int)x, (int)y, x+kPointPrecision, y+kPointPrecision), paint);
                    }
                    paint.setColor(0xffff0000);
                    paint.setTextSize((float)kFontSize);
                    paint.setTextAlign(Paint.Align.CENTER);
                    LocalTime time = LocalTime.now();
                    canvas.drawText(time.toString(), mWidth / 2, mHeight - kFontSize, paint);
                    getHolder().unlockCanvasAndPost(canvas);
                    Log.d(LOG_TAG, "draw end");
                    mUpdateNeeded = false;
                }
            }
            mHandler.postDelayed(mUpdateRunnable, mUpdateInterval);
        }
    };

    public void redraw() {
        synchronized (mLock) {
            mUpdateNeeded = true;
        }
    }

    public WaveGraphView(Context context, AttributeSet attrs) {
        super(context, attrs);

        SurfaceHolder holder = this.getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
                Log.d(LOG_TAG, "surfaceCreated");
            }

            @Override
            public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
                Log.d(LOG_TAG, "surfaceChanged " + format + ", " + width + ", " + height);
                mFormat = format;
                mWidth = width;
                mHeight = height;
                mReady = true;
                mMaxQueueSize = mWidth / kPointPrecision;
                redraw();
            }

            @Override
            public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
                Log.d(LOG_TAG, "surfaceDestroyed");
                mReady = false;
            }
        });
        mHandler.post(mUpdateRunnable);
    }

    public void pushData(float data) {
        synchronized (mLock) {
            mDataQueue.push(data);
            while (mDataQueue.size() > mMaxQueueSize) {
                mDataQueue.pollLast();
            }
        }
        Log.v(LOG_TAG, "pushData (" + data + ") current size: " + mDataQueue.size() + ", first value "+ mDataQueue.get(0));

        redraw();
    }
}
