package com.example.chick.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class GifView extends View {

    private Movie mMovie;
    private InputStream mStream;
    private long mMoviestart;
    private float mMovieWidth;
    private float mMovieHeight;
    private long mMovieDuration;
    private float scale = 1;

    public GifView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    public GifView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GifView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public GifView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension((int)mMovieWidth, (int)mMovieHeight);
    }

    public float getMovieWidth() {
        return mMovieWidth;
    }

    public float getMovieHeight() {
        return mMovieHeight;
    }

    public long getMovieDuration() {
        return mMovieDuration;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mMovie != null) {
            canvas.drawColor(Color.TRANSPARENT);
            super.onDraw(canvas);
            final long now = SystemClock.uptimeMillis();
            if (mMoviestart == 0) {
                mMoviestart = now;
            }
            final int relTime = (int) ((now - mMoviestart) % mMovie.duration());
            mMovie.setTime(relTime);
            canvas.save();
            canvas.scale(scale, scale);
            mMovie.draw(canvas, 0, 0);
            canvas.restore();
            this.invalidate();
        }
    }

    public void setMovie(Movie mMovie) {
        this.mMovie = mMovie;
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(dm);
        scale = dm.widthPixels * 1F / mMovie.width();
        mMovieWidth = mMovie.width()*scale;
        mMovieHeight = mMovie.height()*scale;
        mMovieDuration = mMovie.duration();
        this.setMinimumHeight((int) mMovieHeight);
    }
}