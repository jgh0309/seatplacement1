package com.iot.lee_pc.seatplacement;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class drawingRect extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawing_rect);


        ViewEx viewEx = new ViewEx(this);
        setContentView(viewEx);
    }

    public class ViewEx extends View
    {
        public ViewEx(Context context)
        {
            super(context);


        }
        public void onDraw(Canvas canvas)
        {
            canvas.drawColor(Color.BLACK);

            Paint MyPaint = new Paint();
            MyPaint.setColor(Color.RED);
            MyPaint.setStyle(Paint.Style.FILL);

            int left = 50; // initial start position of rectangles (50 pixels from left)
            int top = 50; // 50 pixels from the top
            int width = 150;
            int height = 150;
            int count = 0 ;
            for (int row = 0; row < 5; row++) {
                MyPaint.setColor(Color.BLUE);
                canvas.drawRect(left, top, left+width, top+height, MyPaint);

                for(int col = 0; col < 5; col++) { // draw 4 columns
                    count ++;

                    if(count % 3 == 0)
                    {

                        MyPaint.setColor(Color.RED);
                        canvas.drawRect(left, top, left+width, top+height, MyPaint);

                    }
                    else
                    {
                        MyPaint.setColor(Color.BLUE);
                        canvas.drawRect(left, top, left + width, top + height, MyPaint);
                    }
                    left = (left + width + 10);


                }
                left = 50;
                top = top + height + 10;



                // move to new row by changing the top co-ordinate
            }
        }
    }
}
