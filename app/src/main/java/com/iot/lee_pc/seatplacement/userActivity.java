package com.iot.lee_pc.seatplacement;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class userActivity extends AppCompatActivity
{
    SQLiteDatabase db;
    int totalseat;
    int totalman;
    int totalcount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        createDatabase("Student");      //Main에서 생성한 db 가져오기(open)

        final EditText editName = (EditText) findViewById(R.id.editName);
        final EditText edit1st_preference = (EditText) findViewById(R.id.edit1st_preference);
        final EditText edit2nd_preference = (EditText) findViewById(R.id.edit2nd_preference);
        final EditText edit3rd_preference = (EditText) findViewById(R.id.edit3rd_preference);
        final EditText edit1st_batting = (EditText) findViewById(R.id.edit1st_batting);
        final EditText edit2nd_batting = (EditText) findViewById(R.id.edit2nd_batting);
        final EditText edit3rd_batting = (EditText) findViewById(R.id.edit3rd_batting);
        final TextView textView = (TextView) findViewById(R.id.textView3);

        Intent intent = getIntent();
        Content total = (Content) intent.getSerializableExtra("total");
        totalseat = total.getTotalseat();
        totalman = total.getTotalman();
        textView.setText("총 " + totalseat + " 개의 좌석이 있습니다.");

        final Button button = (Button) findViewById(R.id.nextBTN);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {   //edittext에 null이 있는지 확인
                if(editName.getText().toString().equals("")||edit1st_preference.getText().toString().equals("")||edit2nd_preference.getText().toString().equals("")
                        ||edit3rd_preference.getText().toString().equals("") ||edit1st_batting.getText().toString().equals("")||edit2nd_batting.getText().toString().equals("") ||edit3rd_batting.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "입력하지 않은 항목이 있습니다.", Toast.LENGTH_SHORT).show();
                else {
                    final int preference1 = Integer.parseInt(edit1st_preference.getText().toString());
                    final int preference2 = Integer.parseInt(edit1st_preference.getText().toString());
                    final int preference3 = Integer.parseInt(edit1st_preference.getText().toString());
                    if ((preference1 <= totalseat) && (preference2 <= totalseat) && (preference3 <= totalseat))
                    {
                        insertStudentInfo(editName.getText().toString(), edit1st_preference.getText().toString(), edit2nd_preference.getText().toString(),
                                edit3rd_preference.getText().toString(), edit1st_batting.getText().toString(), edit2nd_batting.getText().toString(), edit3rd_batting.getText().toString());
                        editName.setText("");
                        edit1st_preference.setText("");
                        edit2nd_preference.setText("");
                        edit3rd_preference.setText("");
                        edit1st_batting.setText("");
                        edit2nd_batting.setText("");
                        edit3rd_batting.setText("");
                        totalcount++;
                        textView.setText("총 " + (totalseat-totalcount) + " 개의 좌석이 있습니다.");
                    } else
                        Toast.makeText(getApplicationContext(), "최대 좌석수 이하로 선택하세요.", Toast.LENGTH_SHORT).show();

                    if (totalcount == totalman)
                    {
                        Toast.makeText(getApplicationContext(), "참여인원 모두가 선택하였습니다.\n결과보기 버튼을 클릭해주세요.", Toast.LENGTH_SHORT).show();
                        button.setVisibility(View.INVISIBLE);
                    }
                }
            }
        });

        Button button1 = (Button) findViewById(R.id.resultBTN);
        button1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (totalcount != totalman)
                    Toast.makeText(getApplicationContext(), "아직 모든 인원이 선택하지 않았습니다.\n모두 참여할 때까지 기다려주세요.", Toast.LENGTH_SHORT).show();
                else
                {
                    Intent intent = new Intent(
                            getApplicationContext(),
                            resultActivity.class
                    );
                    startActivity(intent);
                }
            }
        });

        Button button2 = (Button) findViewById(R.id.buttonDrawing);
        button2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(
                        getApplicationContext(),
                        drawingRect.class
                );
                startActivity(intent);
            }
        });
    }

    public void insertStudentInfo(String editName, String edit1st_preference, String edit2nd_preference, String edit3rd_preference,
                                  String edit1st_batting, String edit2nd_batting, String edit3rd_batting)
    {
        try
        {
            db.execSQL(

                    "insert into StudentInformation" +
                            " (name, preference1, preference2, preference3, batting1, batting2, batting3, SeatPlace)" +
                            " values ( '" + editName + "', " + edit1st_preference + ", " + edit2nd_preference + ", " + edit3rd_preference +
                            ", " + edit1st_batting + ", " + edit2nd_batting + ", " + edit3rd_batting + ", 100 );"
                    /*"insert into StudentInformation" +
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
                            " values ( 'LEE', 1, 6, 2, 3500, 2000, 3000, 100 );"*/);
            Toast.makeText(getApplicationContext(), "삽입성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "삽입실패", Toast.LENGTH_SHORT).show();
        }
    }

    public void createDatabase(String dbName)
    { //db생성
        try
        {  //오류검출시도
            db = openOrCreateDatabase(
                    dbName,
                    Activity.MODE_PRIVATE,
                    null);
        } catch (Exception e)
        { //오류검출
            e.printStackTrace();
        }

    }


}
