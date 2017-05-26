package com.iot.lee_pc.seatplacement;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class resultActivity extends AppCompatActivity {
    private RecyclerView _recyclerView;
    SQLiteDatabase db;
    String name;
    String seat;
    int recordCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Setseatnumber();

        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        ArrayList<Info> infos = loadData();

        infoadapter infoadapter = new infoadapter(infos);
        _recyclerView.setAdapter(infoadapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        _recyclerView.setLayoutManager(layoutManager);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());




    }

    public void Setseatnumber() {

        int max = 0;

        createDatabase("Student");

        String SQL = "select _id from  StudentInformation";//레코드 개수 찾기
        Cursor count = db.rawQuery(SQL, null);
        recordCount = count.getCount();
        count.close();

        for (int preferences = 1; preferences < 4; preferences++) { //1~3지망까지 비교

            for (int i = 1; i <= recordCount; i++) { //비교할 인원     i = 아이디 ,  stu_seat = 순위좌석 번호 ,  money_seatnum = 순위좌석 경매가격 ,  seat_final = 최종 확인 좌석

                String stu1 = " select _id,  preference" + preferences + ", batting" + preferences + ", SeatPlace from StudentInformation  where _id=" + i;
                Cursor c1 = db.rawQuery(stu1, null);
                c1.moveToNext();
                String stu_no = c1.getString(0);
                String stu_seat = c1.getString(1);
                String stu_money = c1.getString(2);
                String stu_set_seat = c1.getString(3);

                if (parseInt(stu_set_seat) == 100) { //확정 자리가 비어있으면
                    for (int j = 1; j <= recordCount; j++) {
                        if (i != j) {
                            String stu2 = " select _id ,  preference" + preferences + ", batting" + preferences + ", SeatPlace from StudentInformation where _id=" + j;
                            Cursor c2 = db.rawQuery(stu2, null);
                            c2.moveToNext();
                            String stu_no_compare = c2.getString(0);
                            String stu_seat_compare = c2.getString(1);
                            String stu_money_compare = c2.getString(2);


                            if (stu_seat.equals(stu_seat_compare)) { //선택한 자리가 같을 시
                                int _seat_money = parseInt(stu_money); //비교 하는 사람의 입찰금을 저장
                                int _seat_money_compare = parseInt(stu_money_compare); //비교 당하는 사람의 입찰금을 저장

                                if (_seat_money > _seat_money_compare) { //금액이 더욱 크면 확정 자리에 금액을 넣음
                                    isSeatempty(stu_seat, stu_no);
                                } else if (_seat_money < _seat_money_compare && max < _seat_money_compare) { //작으면 다른 사람의 금액을 넣음 ,max보다 큰 값이 들어가야 들어감
                                    max = _seat_money_compare;
                                    db.execSQL("update StudentInformation set SeatPlace= 100 where _id = " + stu_no);
                                }
                            } else {//선택한 자리가 다를 시 최종 자리 seat_final 에 자리번호가 들어간다
                                isSeatempty(stu_seat, stu_no);
                            }
                            c2.close();
                        }
                    }
                }
                c1.close();
            }
            if (preferences == 3) {
                // randomSeat(recordCount);
////////////////////////////////////////////////////
    randomseatSelect(recordCount);
/*                       String Record = "select _id from  StudentInformation where  SeatPlace = 100";//레코드 개수 찾기
                      Cursor _count = db.rawQuery(Record, null);
                     int nullseatCount = _count.getCount();  //빈자리 개수
               int a[] = new int[nullseatCount]; //int형 배열 선언 빈자리의 a개수
                //     _count.close();
                int b[] = new int[recordCount];

                Cursor saveCursor = db.rawQuery("select SeatPlace from StudentInformation ", null);
                for(int i = 0 ; i < recordCount ; i ++ )
                {
                    saveCursor.moveToNext();
                    b[i]=saveCursor.getInt(0);
                }
                for (int i = 0; i < nullseatCount; i++) {
                    a[i] = (int) (Math.random() * recordCount + 1);
                    for (int j = 0; j < recordCount; j++) //중복제거를 위한 for문
                    {

                        if (a[i] == b[j]) {

                            i--;
                            break;
                        }
                    }
                }


                for (int i = 0; i < recordCount; i++) { //랜덤
//i가 되는 자리가 비었을때 100 , 일 대 랜덤의 배열을 하나씩 검사하며 같은 것이 없으면 넣는다
                    for (int j = 0; j < nullseatCount; j++) { //사용자

                        if (100 == b[i]) { //자리가 비어있으면
                            db.execSQL("update StudentInformation set SeatPlace = " + a[j] + " where _id=" + (i+1));
                        }
                        else
                        {
                            break;
                        }


                    }
                }


            }         String Record = "select _id from  StudentInformation where  SeatPlace = 100";//레코드 개수 찾기
            Cursor _count = db.rawQuery(Record, null);
            int nullseatCount = _count.getCount();  //빈자리 개수
            int a[] = new int[nullseatCount]; //int형 배열 선언 빈자리의 a개수
            //     _count.close();
            int b[] = new int[recordCount];

            Cursor saveCursor = db.rawQuery("select SeatPlace from StudentInformation ", null);
            for(int i = 0 ; i < recordCount ; i ++ )
            {
                saveCursor.moveToNext();
                b[i]=saveCursor.getInt(0);
            }
            for (int i = 0; i < nullseatCount; i++) {
                a[i] = (int) (Math.random() * recordCount + 1);
                for (int j = 0; j < recordCount; j++) //중복제거를 위한 for문
                {

                    if (a[i] == b[j]) {

                        i--;
                        break;
                    }
                }
            }


            for (int i = 0; i < recordCount; i++) { //랜덤
//i가 되는 자리가 비었을때 100 , 일 대 랜덤의 배열을 하나씩 검사하며 같은 것이 없으면 넣는다
                for (int j = 0; j < nullseatCount; j++) { //사용자

                    if (100 == b[i]) { //자리가 비어있으면
                        db.execSQL("update StudentInformation set SeatPlace = " + a[j] + " where _id=" + (i+1));
                    }
                    else
                    {
                        break;
                    }


                }
            }


       */ }

        }
    }

    private void isSeatempty(String stu_seat, String stu_no) {
        Cursor cursor = db.rawQuery(" select count(*) as elreadySeat from StudentInformation where SeatPlace = " + stu_seat, null);
        cursor.moveToNext();
        if (cursor.getInt(0) == 0) {
            db.execSQL("update StudentInformation set SeatPlace = " + stu_seat + " where _id = " + stu_no);
        }
        cursor.close();
    }

    private void createDatabase(String dbName) { //db생성
        try {  //오류검출시도
            db = openOrCreateDatabase(
                    dbName,
                    Activity.MODE_PRIVATE,
                    null);
        } catch (Exception e) { //오류검출
            e.printStackTrace();
        }
    }

    private ArrayList<Info> loadData() {
        ArrayList<Info> infos = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("select name,SeatPlace from StudentInformation ", null);
            recordCount = cursor.getCount();
            for (int i = 0; i < recordCount; i++) {
                cursor.moveToNext();
                name = cursor.getString(0);
                seat = cursor.getString(1);
                Info info = new Info();
                info.setName(name);
                info.setSeat(seat);
                infos.add(info);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "조회에러", Toast.LENGTH_SHORT).show();
        }
        return infos;
    }


    private void randomseatSelect (int recordCount)
    {


        String Record = "select _id from  StudentInformation where  SeatPlace = 100";//레코드 개수 찾기
        Cursor saveCursor = db.rawQuery("select SeatPlace from StudentInformation ", null);

        Cursor _count = db.rawQuery(Record, null);
        int nullseatCount = _count.getCount();  //빈자리 개수
        int a[] = new int[nullseatCount]; //int형 배열 선언 빈자리의 a개수
        int b[] = new int[recordCount];

      for(int i = 0 ; i < recordCount ; i ++ )
        {
            saveCursor.moveToNext();
            b[i]=saveCursor.getInt(0);
        }
        for (int i = 0; i < nullseatCount; i++) {
            a[i] = (int) (Math.random() * recordCount + 1);
            for (int j = 0; j < recordCount; j++) //중복제거를 위한 for문
            {

                if (a[i] == b[j]) {

                    i--;
                    break;
                }
            }
        }


        for (int i = 0; i < recordCount; i++) { //랜덤
//i가 되는 자리가 비었을때 100 , 일 대 랜덤의 배열을 하나씩 검사하며 같은 것이 없으면 넣는다
            for (int j = 0; j < nullseatCount; j++) { //사용자

                if (100 == b[i]) { //자리가 비어있으면
                    db.execSQL("update StudentInformation set SeatPlace = " + a[j] + " where _id=" + (i+1));
                    break;
                }
                else
                {
                    break;
                }


            }
        }



    }





    }






