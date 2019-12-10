package com.example.knighty_chess;

import android.content.Context;
import androidx.cardview.widget.CardView;
import android.util.AttributeSet;

public class SquareCardView extends CardView {

    public SquareCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int ignoredHeightMeasureSpec) {
        int newHeightMeasureSpec = widthMeasureSpec;
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }

}