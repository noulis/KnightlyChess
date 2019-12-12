package com.example.knighty_chess.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.knighty_chess.R;

public class RoundSpinnerWithHint extends ConstraintLayout {

    private Context context;
    private OnClickListener listener;
    private TextView hint;
    private Spinner spinner;
    private String defaultHintText= null;

    public RoundSpinnerWithHint(Context context) {
        super(context);
        this.context = context;
        initializeViews(context);
    }

    public RoundSpinnerWithHint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initializeViews(context);
        initAttributes(context,attrs);
    }

    public RoundSpinnerWithHint(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initializeViews(context);
        initAttributes(context,attrs);
    }

    /*****************
     * Inflate views *
     *****************/

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        hint = this.findViewById(R.id.hintText);
        spinner = this.findViewById(R.id.spinner);
        spinner.setFocusable(true);

        if (defaultHintText!=null){
            hint.setText(defaultHintText);
        }
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.properties_tab, this);
        }
    }

    private void initAttributes(Context context, AttributeSet attrs) {
        TypedArray customAttributes = context.obtainStyledAttributes(attrs, R.styleable.MaterialSpinnerWithHint, 0, 0);

        if (customAttributes.hasValue(R.styleable.MaterialSpinnerWithHint_hintText)) {
            defaultHintText = customAttributes.getString(R.styleable.MaterialSpinnerWithHint_hintText);
        }

        customAttributes.recycle();
    }

    /************
     * Listener *
     ************/

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (listener!=null && event.getAction()==MotionEvent.ACTION_UP &&
                isPointInsideView(event.getRawX(), event.getRawY(), this))
            listener.onClick(this);
        return super.dispatchTouchEvent(event);
    }

    /******************************
     * Public setters and getters *
     ******************************/

    public void setOnItemSelectedListener(@NonNull AdapterView.OnItemSelectedListener listener){
        spinner.setOnItemSelectedListener(listener);
    }

    public void setAdapterAndSpinnerOptions(@NonNull Integer[] spinnerOptions){
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(context, R.layout.my_material_spinner_style, spinnerOptions);
        spinner.setAdapter(adapter);
    }

    public void setHintText(@NonNull String hintText){
        hint.setText(hintText);
    }

    public void setCustomOnClickListener (OnClickListener listener) {
        this.listener = listener;
    }

    public int getSelectedItemPosition(){
        return spinner.getSelectedItemPosition();
    }

    public void setSelectedItemPosition(int position){
        spinner.setSelection(position);
    }

    public String getSelectedItem(){
        return spinner.getSelectedItem().toString();
    }

    public void setSelectedItem(String itemValue){
        int itemCount = spinner.getCount();

        if (itemCount<=0){
            return;
        }

        for (int i =0; i<itemCount ; i++){
            if (spinner.getItemAtPosition(i).equals(itemValue)){
                spinner.setSelection(i);
                return;
            }
        }
    }

    /**
     * Function that checks if given point is inside view
     *
     * @param x x-dimension of point
     * @param y y-dimension of point
     * @return true if point is inside view, false if not
     */
    private boolean isPointInsideView (float x, float y, View view){
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int viewX = location[0];
        int viewY = location[1];
        return (( x > viewX && x < (viewX + view.getWidth())) && ( y > viewY && y < (viewY + view.getHeight())));
    }

}
