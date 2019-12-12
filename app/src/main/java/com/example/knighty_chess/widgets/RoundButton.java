package com.example.knighty_chess.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;

import com.example.knighty_chess.R;

public class RoundButton extends androidx.appcompat.widget.AppCompatButton {

    public RoundButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context);
    }

    public RoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context);
    }

    protected void initViews(Context context) {
        this.setTextSize(getDimenValueInDp(context.getResources(), R.dimen.button_text_size));
        this.setLineSpacing(context.getResources().getDimension(R.dimen.button_line_spacing), 1);
        this.setAllCaps(false);
        int top_bottom_padding = getDimenValueInPixels(context.getResources(), R.dimen.btn_top_bottom_padding);
        int side_padding = getDimenValueInPixels(context.getResources(), R.dimen.btn_side_padding);
        this.setPadding(side_padding, top_bottom_padding, side_padding, top_bottom_padding);
        this.setGravity(Gravity.CENTER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.setTextColor(context.getResources().getColorStateList(R.color.colorWhite, context.getTheme()));
        }
        else {
            this.setTextColor(context.getResources().getColorStateList(R.color.colorWhite));
        }

        this.setBackground(context.getDrawable(R.drawable.button_state_selector));
        this.setMaxLines(1);
        this.setStateListAnimator(null);
        this.setHeight(getDimenValueInPixels(context.getResources(), R.dimen.button_height));
    }

    private int getDimenValueInDp(Resources res, int dimenId) {
        return (int) (res.getDimension(dimenId) / res.getDisplayMetrics().density);
    }

    private int getDimenValueInPixels(Resources res, int dimenId) {
        int valueInDp = getDimenValueInDp(res, dimenId);
        final float scale = res.getDisplayMetrics().density;
        return (int) (valueInDp * scale + 0.5f);
    }
}
