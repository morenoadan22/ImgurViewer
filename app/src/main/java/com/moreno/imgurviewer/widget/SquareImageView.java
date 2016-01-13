package com.moreno.imgurviewer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by adan on 1/12/16.
 * <br />
 * <br />
 * Custom View class that re-sizes the content into specified dimensions
 */
public class SquareImageView extends ImageView {

    public SquareImageView (Context context) {
        super(context);
    }

    public SquareImageView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView (Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}
