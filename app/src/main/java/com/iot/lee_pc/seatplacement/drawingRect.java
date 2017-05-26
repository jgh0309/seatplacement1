package com.iot.lee_pc.seatplacement;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Arrays;

public class drawingRect extends AppCompatActivity
{

    SQLiteDatabase db;
    int recordCount;

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
            createDatabase("Student");



            canvas.drawColor(Color.BLACK);

            Paint MyPaint = new Paint();
            MyPaint.setColor(Color.RED);
            MyPaint.setStyle(Paint.Style.FILL);

            int left = 50; // initial start seatnumber of rectangles (50 pixels from left)
            int top = 50; // 50 pixels from the top
            int width = 150;
            int height = 150;
            int count = 0 ;
            for (int row = 0; row < 5; row++) {

                MyPaint.setColor(Color.BLUE);
                canvas.drawRect(left, top, left+width, top+height, MyPaint);
                Cursor colorcursor = db.rawQuery("select SeatPlace from StudentInformation",null);
                int seatcount = colorcursor.getCount();
                int  array[] = new int[25];
                for (int i=0;i<seatcount;i++)
                {
                    array[i]=colorcursor.getInt(0);
                }
                Arrays.sort(array);

                for(int col = 0; col < 5; col++) { // draw 4 columns

                    count ++;
     //           colorcursor.moveToNext();
    //            int seatnumber =colorcursor.getInt(0);
                    if(count ==array[count])
                    //if(count % 3 == 0)
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
    public  void createDatabase(String dbName){ //db생성
        try {  //오류검출시도
            SQLiteDatabase db = openOrCreateDatabase(
                    dbName,
                    Activity.MODE_PRIVATE,
                    null);
        }
        catch(Exception e){ //오류검출
            e.printStackTrace();
        }

    }
}
