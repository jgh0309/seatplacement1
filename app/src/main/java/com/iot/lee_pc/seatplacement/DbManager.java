package com.iot.lee_pc.seatplacement;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;


public class DbManager extends AppCompatActivity
{
    public void deleteTable(){
        try{
            db.execSQL(
                    " delete from StudentInformation"
            );
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    SQLiteDatabase db;
}
