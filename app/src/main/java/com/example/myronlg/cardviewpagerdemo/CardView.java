package com.example.myronlg.cardviewpagerdemo;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by myron.lg on 2015/8/15.
 */
public class CardView extends FrameLayout {

    private View dimView;
    private ImageView imageView;

    public CardView(Context context) {
        super(context);
        init();
    }

    public CardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        dimView = new View(getContext());
        dimView.setBackgroundColor(Color.BLACK);
        dimView.setAlpha(0);
        imageView = new ImageView(getContext());
        dimView.setLayerType(LAYER_TYPE_HARDWARE, null);
        imageView.setLayerType(LAYER_TYPE_HARDWARE, null);
        addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(dimView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    }

    public void setImage(int resId){
        imageView.setImageResource(resId);
    }

    public void setDim(float dim){
//        dimView.setBackgroundColor(Color.argb((int) (255 * dim), 0, 0, 0));
        dimView.setAlpha(dim);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
