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

public class userActivity extends AppCompatActivity
{
    SQLiteDatabase db;

    String a;
    int b;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final EditText editName = (EditText) findViewById(R.id.editName);
        final EditText edit1st_preference = (EditText) findViewById(R.id.edit1st_preference);
        final EditText edit2nd_preference = (EditText) findViewById(R.id.edit2nd_preference);
        final EditText edit3rd_preference = (EditText) findViewById(R.id.edit3rd_preference);
        final EditText edit1st_batting = (EditText) findViewById(R.id.edit1st_batting);
        final EditText edit2nd_batting = (EditText) findViewById(R.id.edit2nd_batting);
        final EditText edit3rd_batting = (EditText) findViewById(R.id.edit3rd_batting);

        createDatabase("Student");      //Main에서 생성한 db 가져오기(open)

        Button button = (Button) findViewById(R.id.nextBTN);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               insertStudentInfo(editName.getText().toString(),edit1st_preference.getText().toString(),edit2nd_preference.getText().toString(),
                       edit3rd_preference.getText().toString(),edit1st_batting.getText().toString(),edit2nd_batting.getText().toString(),edit3rd_batting.getText().toString());
                Toast.makeText(getApplicationContext(), a , Toast.LENGTH_SHORT).show();

                editName.setText("");
                edit1st_preference.setText("");
                edit2nd_preference.setText("");
                edit3rd_preference.setText("");
                edit1st_batting.setText("");
                edit2nd_batting.setText("");
                edit3rd_batting.setText("");
            }
        });

        Button button1 = (Button) findViewById(R.id.resultBTN);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(
                        getApplicationContext(),
                        resultActivity.class
                );
                startActivity(intent);
            }
        });
    }

    public void insertStudentInfo(String editName ,String edit1st_preference ,String edit2nd_preference ,String edit3rd_preference ,
                                  String edit1st_batting, String edit2nd_batting, String edit3rd_batting){
        try{
            db.execSQL(
//                    "insert into StudentInformation" +
//                            " (name, preference1, preference2, preference3, batting1, batting2, batting3, SeatPlace)" +
//                            " values ( '" + editName +"', "+ edit1st_preference +", "+ edit2nd_preference +", "+ edit3rd_preference +
//                            ", "+ edit1st_batting +", "+ edit2nd_batting +", "+ edit3rd_batting +", 0 );"
                    "insert into StudentInformation" +
                            " (name, preference1, preference2, preference3, batting1, batting2, batting3, SeatPlace)" +
                            " values ( 'PARK', 3, 1, 2, 4500, 2000, 3000, 100 );");
            db.execSQL(
                    "insert into StudentInformation" +
                            " (name, preference1, preference2, preference3, batting1, batting2, batting3, SeatPlace)" +
                            " values ( 'TAE', 4, 3, 1, 4400, 2100, 3200, 100 );");
            db.execSQL(
                    "insert into StudentInformation" +
                            " (name, preference1, preference2, preference3, batting1, batting2, batting3, SeatPlace)" +
                            " values ( 'HUNG', 6, 3, 2, 4200, 1000, 2000, 100 );");
            db.execSQL(
                    "insert into StudentInformation" +
                            " (name, preference1, preference2, preference3, batting1, batting2, batting3, SeatPlace)" +
                            " values ( 'KIM', 1, 5, 2, 3800, 2000, 3000, 100 );");
            db.execSQL(
                    "insert into StudentInformation" +
                            " (name, preference1, preference2, preference3, batting1, batting2, batting3, SeatPlace)" +
                            " values ( 'JUNG', 5, 4, 2, 3500, 2000, 3000, 100 );");
            db.execSQL(
                    "insert into StudentInformation" +
                            " (name, preference1, preference2, preference3, batting1, batting2, batting3, SeatPlace)" +
                            " values ( 'LEE', 1, 6, 2, 3500, 2000, 3000, 100 );");
            a="삽입성공";
        }catch(Exception e)
        {
            e.printStackTrace();
            a="삽입실패";
        }
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

}
