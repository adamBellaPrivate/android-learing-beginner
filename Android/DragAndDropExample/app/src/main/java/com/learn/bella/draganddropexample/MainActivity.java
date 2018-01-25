package com.learn.bella.draganddropexample;

import android.content.ClipData;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button targetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.button2).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.button3).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.button4).setOnTouchListener(new MyTouchListener());
        findViewById(R.id.firstRow).setOnDragListener(new MyDragListener());
        findViewById(R.id.secondRow).setOnDragListener(new MyDragListener());
        findViewById(R.id.targetRow).setOnDragListener(new MyDragListener());
        targetButton = findViewById(R.id.targetButton);
    }

    // This defines your touch listener
    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            }else {
                return false;
            }
        }
    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {

            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    boolean result = isViewOverlapping(v,targetButton);

                    if (result) {
                        View view = (View) event.getLocalState();
                        ColorDrawable bg1 = (ColorDrawable) view.getBackground();
                        int colorId1 = bg1.getColor();

                        ColorDrawable bg2 = (ColorDrawable)targetButton.getBackground();
                        int colorId2 = bg2.getColor();

                        Toast.makeText(getApplicationContext(),colorId1 == colorId2 ? "Colors match" : "Colors don't match",Toast.LENGTH_SHORT).show();
                    }

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    View view = (View) event.getLocalState();
                    view.setVisibility(View.VISIBLE);
                default:
                    break;
            }
            return true;
        }
    }

    private boolean isViewOverlapping(View firstView, View secondView) {

        final int[] location = new int[2];

        firstView.getLocationOnScreen(location);
        Rect rect1 = new Rect(location[0], location[1],location[0] + firstView.getWidth(), location[1] + firstView.getHeight());

        secondView.getLocationOnScreen(location);
        Rect rect2 = new Rect(location[0], location[1],location[0] + secondView.getWidth(), location[1] + secondView.getHeight());

        Log.d("Button size", rect1.toString());
        Log.d("Target size", rect2.toString());

        return rect1.intersect(rect2);
    }
}
