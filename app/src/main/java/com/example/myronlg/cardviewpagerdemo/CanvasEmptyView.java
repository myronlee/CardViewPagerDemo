package com.example.myronlg.cardviewpagerdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by myron.lg on 2015/8/19.
 */
public class CanvasEmptyView extends View {
    public boolean draw = true;

    public CanvasEmptyView(Context context) {
        super(context);
    }

    public CanvasEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (draw) {
            canvas.drawColor(Color.CYAN);
        }
    }
}
