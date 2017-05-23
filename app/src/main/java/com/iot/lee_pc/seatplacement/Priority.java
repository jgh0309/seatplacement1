package com.iot.lee_pc.seatplacement;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by pack on 2017-05-22.
 */

public class Priority extends AppCompatActivity {

    SQLiteDatabase db;


    private void Setseatnumber() {

        int _idnum = 0;
        int max = 0;
        //   String studentnum = "select stu_seat"+(k+1)+" from "+ _idnum;
        //Map<String,String>map = new HashMap<String,String>();
        createDatabase("Student");

        for (int seatnum = 0; seatnum < 3; seatnum++) {
            for (int i = 0; i < 5; i++) { //비교할 인원
                //_idnum = 아이디
                //stu_seat seatnum = 순위좌석 번호
                //money_seatnum = 순위좌석 경매가격
                //seat_final = 최종 확인 좌석


                String stu1 = " select " + _idnum + ",  stu_seat" + seatnum + ", money_tender" + seatnum + ", seat_final from StudentInformation";
                Cursor c1 = db.rawQuery(stu1, null);
                int recordCount = c1.getCount();

                c1.moveToNext();
                String stu_no = c1.getString(0);
                String stu_seat = c1.getString(1);
                String stu_money = c1.getString(2);
                String stu_set_seat = c1.getString(3);

                if (Integer.parseInt(stu_set_seat) == 0) { //확정 자리가 비어있으면
                    for (int j = i + 1; j < 6; j++) {
                        String stu2 = " select " + _idnum + ",  stu_seat" + seatnum + ", money_tender" + seatnum + ", seat_final from StudentInformation";
                        Cursor c2 = db.rawQuery(stu2, null);
                        int recordCount_compare = c2.getCount();

                        c1.moveToNext();
                        String stu_no_compare = c2.getString(0);
                        String stu_seat_compare = c2.getString(1);
                        String stu_money_compare = c2.getString(2);
                        String stu_set_seat_compare = c2.getString(2);

                        if (stu_seat.equals(stu_seat_compare)) { //선택한 자리가 같을 시
                            int _seat_money = Integer.parseInt(stu_money); //비교 하는 사람의 입찰금을 저장
                            int _seat_money_compare = Integer.parseInt(stu_money_compare); //비교 당하는 사람의 입찰금을 저장

                            if (_seat_money > _seat_money_compare) { //금액이 더욱 크면 확정 자리에 금액을 넣음

                                db.execSQL("update StudentInformation set seat_final = " + stu_seat + " where stu_name = " + stu_no);
                            } else if (_seat_money < _seat_money_compare && max < _seat_money_compare) { //작으면 다른 사람의 금액을 넣음 ,max보다 큰 값이 들어가야 들어감
                                max = _seat_money_compare;
                                db.execSQL("update StudentInformation set seat_final= " + stu_seat_compare + " where stu_name = " + stu_no_compare);
                            }
                        } else {//선택한 자리가 다를 시 최종 자리 seat_final 에 자리번호가 들어간다
                            db.execSQL("update StudentInformation set seat_final = " + stu_seat + " where stu_name = " + stu_no);

                        }


                    }


                }
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
}



