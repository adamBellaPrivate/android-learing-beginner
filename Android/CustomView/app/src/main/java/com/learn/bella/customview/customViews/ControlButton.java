package com.learn.bella.customview.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.learn.bella.customview.R;

/**
 * Created by adambella on 1/19/18.
 */

public class ControlButton extends FrameLayout {
    private View rootView;
    private Button countStepperButton;
    private TextView counterLabel;
    private int counter = 0;

    public ControlButton(Context context, AttributeSet attrs) {
        super(context,attrs);
        //Attribute
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ControlButton, 0, 0);

        try {
            int bg = a.getColor(R.styleable.ControlButton_counterBackgroundColor, Color.RED);

        } finally {
            a.recycle();
        }

        //private Paint bgPaint = new Paint();
        //bgPaint.setStyle(Style.FILL);
        //bgPaint.setColor()


        init(context);
    }

    public void init(Context context){
        rootView = inflate(context, R.layout.control_button, this);
        countStepperButton = rootView.findViewById(R.id.button);
        counterLabel = rootView.findViewById(R.id.textView);

        countStepperButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCounter();
            }
        });
    }

    private void updateCounter(){
        counter += 1;
        counterLabel.setText(String.valueOf(counter));
        invalidate();
        requestLayout();
    }
}
