package com.iot.lee_pc.seatplacement;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class resultActivity extends AppCompatActivity
{
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Setseatnumber();
        executeRawQueryName_Seatplace();


        FragmentManager fragmentManager = getSupportFragmentManager();
        final ListFragment listFragment = (ListFragment) fragmentManager.findFragmentById(R.id.listfragment);

        listFragment.setiu(new ListFragment.iualbum()
        {
            @Override
            public void onclick(int position)
            {
                Toast.makeText(listFragment.getContext(), "아이유의 " + position + "번째 앨범입니다.", Toast.LENGTH_SHORT);
            }
        });
    }

    public void Setseatnumber() {

        int _idnum = 0;
        int max = 0;

        createDatabase("Student");
        String SQL = "select _id from  StudentInformation";//레코드 개수 찾기
        Cursor count = db.rawQuery(SQL,null);
        int recordCount = count.getCount();
        count.close();

        for (int seatnum = 1; seatnum < 4; seatnum++) { //지망 개수

            for (int i = 1; i <= recordCount; i++) { //비교할 인원
                // i = 아이디
                //stu_seat seatnum = 순위좌석 번호
                //money_seatnum = 순위좌석 경매가격
                //seat_final = 최종 확인 좌석


                String stu1 = " select _id,  preference" + seatnum + ", batting" + seatnum + ", SeatPlace from StudentInformation  where _id="+i;
                Cursor c1 = db.rawQuery(stu1, null);
                c1.moveToNext();
                String stu_no = c1.getString(0);
                String stu_seat = c1.getString(1);
                String stu_money = c1.getString(2);
                String stu_set_seat = c1.getString(3);

                if (Integer.parseInt(stu_set_seat) == 100) { //확정 자리가 비어있으면
                    for (int j = 1; j <= recordCount; j++)
                    {
                        if (i != j)
                        {
                            String stu2 = " select _id ,  preference" + seatnum + ", batting" + seatnum + ", SeatPlace from StudentInformation where _id=" + j;
                            Cursor c2 = db.rawQuery(stu2, null);
                            //           int recordCount_compare = c2.getCount();

                            //        c1.moveToFirst();
                            c2.moveToNext();
                            String stu_no_compare = c2.getString(0);
                            String stu_seat_compare = c2.getString(1);
                            String stu_money_compare = c2.getString(2);
                            //         String stu_set_seat_compare = c2.getString(2);


                            if (stu_seat.equals(stu_seat_compare))
                            { //선택한 자리가 같을 시
                                int _seat_money = Integer.parseInt(stu_money); //비교 하는 사람의 입찰금을 저장
                                int _seat_money_compare = Integer.parseInt(stu_money_compare); //비교 당하는 사람의 입찰금을 저장

                                if (_seat_money > _seat_money_compare)
                                { //금액이 더욱 크면 확정 자리에 금액을 넣음

                                    db.execSQL("update StudentInformation set SeatPlace = " + stu_seat + " where _id = " + stu_no);
                                } else if (_seat_money < _seat_money_compare && max < _seat_money_compare)
                                { //작으면 다른 사람의 금액을 넣음 ,max보다 큰 값이 들어가야 들어감
                                    max = _seat_money_compare;
                                    db.execSQL("update StudentInformation set SeatPlace= " + stu_seat_compare + " where _id = " + stu_no_compare);
                                }
                            } else
                            {//선택한 자리가 다를 시 최종 자리 seat_final 에 자리번호가 들어간다
                                db.execSQL("update StudentInformation set SeatPlace = " + stu_seat + " where _id = " + stu_no);
                            }
                            c2.close();
                        }
                    }
                }
            c1.close();
            }
        }
    }

    public void createDatabase(String dbName) { //db생성
        try {  //오류검출시도
            db = openOrCreateDatabase(
                    dbName,
                    Activity.MODE_PRIVATE,
                    null);
        } catch (Exception e) { //오류검출
            e.printStackTrace();
        }
    }

    private  void executeRawQueryName_Seatplace() {        //select
        try {
            Cursor cursor = db.rawQuery(
                    "select name,SeatPlace from StudentInformation ", null
            );
            int a= cursor.getCount();
            for(int i=0; i<a; i++)
            {
                cursor.moveToNext();
                String b = cursor.getString(0);
                String c = cursor.getString(1);
                Toast.makeText(getApplicationContext(), b, Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), c, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"조회에러",Toast.LENGTH_SHORT).show();
        }
    }
}
