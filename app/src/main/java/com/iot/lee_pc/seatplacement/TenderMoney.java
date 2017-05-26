package com.iot.lee_pc.seatplacement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by pack on 2017-05-23.
 */

public class TenderMoney extends AppCompatActivity {

    String name;
    String price;
    private RecyclerView _recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tendermoney);


        _recycle = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<Info> infos = tendermoney();

        Tenderadapter tenderadapter = new Tenderadapter(infos);
        _recycle.setAdapter(tenderadapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        _recycle.setLayoutManager(layoutManager);
        _recycle.setItemAnimator(new DefaultItemAnimator());


    }


    SQLiteDatabase db;
    String SQL = "select _id from  StudentInformation";//레코드 개수 찾기
    Cursor count = db.rawQuery(SQL, null);
    int recordCount = count.getCount();


    private ArrayList<Info> tendermoney() {
        ArrayList<Info> infos = new ArrayList<>();


        for (int i = 0; i < 3; i++) //우선순위 개수 만큼 반복
        {
            for (int j = 0; j < recordCount; j++) { //사람 수 만큼 반복
                // 우선순위를 비교할 때 같은 사람이 있으면 금액과 자리를 출력
                //String stu1 = " select _name , batting"+i+" from StudentInformation where preference"+i+"= "+(j+1); //동일한 좌석을 가진 학생 수 출력을 위한 SQL문
                String stu1 = " select _id from StudentInformation where preference" + i + "= " + (j + 1); //동일한 좌석을 가진 학생 수 출력을 위한 SQL문
                Cursor c1 = db.rawQuery(stu1, null);
                int preferenceCount = c1.getCount();
                c1.close();

                String equlPreference = " select _name , batting" + i + " from StudentInformation where preference" + i + "= " + (j + 1); //동일한 좌석을 가진 학생 수 출력을 위한 SQL문 ,
                //j+1 의 좌석의 사람 이름과 배팅금액을 불러옴
                Cursor cursor = db.rawQuery(equlPreference, null);

                for (int k = 0; k < preferenceCount; k++) //동일 좌석을 지원한 수 만큼 반복
                {
                    cursor.moveToNext();
                    name = cursor.getString(0);
                    price = cursor.getString(1);
                    Info info = new Info();
                    info.setName(name);
                    info.setSeat(price);
                    infos.add(info);


                }
                cursor.close();

            }
        }
        return infos;
    }
}


