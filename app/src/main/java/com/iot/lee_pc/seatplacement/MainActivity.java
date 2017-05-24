package com.iot.lee_pc.seatplacement;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
{
    SQLiteDatabase db;

    String a;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase("Student");
    //    deleteTable();
        createTable("StudentInformation");

        Button button = (Button) findViewById(R.id.startBTN);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(
                        getApplicationContext(),
                        userActivity.class
                );
                startActivity(intent);
            }
        });
    }
    public  void createDatabase(String dbName){ //db생성
        try {  //오류검출시도
            db = openOrCreateDatabase(
                    dbName,
                    Activity.MODE_PRIVATE,
                    null);
        }
        catch(Exception e){ //오류검출
            e.printStackTrace();
        }

    }

    public  void createTable(String tableName){ //tb생성
        try {
            db.execSQL("create table if not exists " + tableName +
                    " ( " +
                    " _id integer PRIMARY KEY autoincrement, " +
                    " name text, " +
                    " preference1 integer, " +
                    " preference2 integer, " +
                    " preference3 integer, " +
                    " batting1 integer, " +
                    " batting2 integer, " +
                    " batting3 integer, " +
                    " SeatPlace integer )"
            );
        } catch(Exception e){
            e.printStackTrace();
        }
    }

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
}
