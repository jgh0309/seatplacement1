package com.iot.lee_pc.seatplacement;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    SQLiteDatabase db;
    int editSeat;
    int editman;
    EditText editTotalSeat;
    EditText editTotalMan;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createDatabase("Student");
        dropTable();
        createTable("StudentInformation");

        editTotalSeat = (EditText) findViewById(R.id.totalSeat);
        editTotalMan = (EditText) findViewById(R.id.totalman);


        Button button = (Button) findViewById(R.id.startBTN);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(editTotalSeat.getText().toString().equals("")||editTotalMan.getText().toString().equals(""))
                {
                    if(editTotalSeat.getText().toString().equals("")&&editTotalMan.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "총 좌석수, 참여 인원수를 설정해주세요.", Toast.LENGTH_SHORT).show();
                    else
                    {
                        if (editTotalSeat.getText().toString().equals(""))Toast.makeText(getApplicationContext(), "총 좌석수를 설정해주세요.", Toast.LENGTH_SHORT).show();
                        else Toast.makeText(getApplicationContext(), "참여 인원수를 설정해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    editSeat = Integer.parseInt(editTotalSeat.getText().toString());
                    editman = Integer.parseInt(editTotalMan.getText().toString());
                    Intent intent = new Intent(getApplicationContext(), userActivity.class);
                    Content total = new Content(editSeat, editman);
                    intent.putExtra("total", total);
                    startActivity(intent);
                }
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

    public void dropTable(){
        try{
            db.execSQL(
                    " drop table StudentInformation"
            );
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
