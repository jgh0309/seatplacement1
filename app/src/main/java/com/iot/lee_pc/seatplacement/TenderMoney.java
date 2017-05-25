package com.iot.lee_pc.seatplacement;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by pack on 2017-05-23.
 */

public class TenderMoney {
    SQLiteDatabase db;

    public void tendermoney(){
        int _sum=0;
        int select_number = 0; // 버튼으로자리 번호 클릭시 자리를 검색하여 그 자리를 신청한 학생들의 이름과 금액을 보여줌



        String stu1 = " select stu_name ,  seat_money from StudentInformation where seat_number="+ select_number;
        Cursor c1 = db.rawQuery(stu1, null);
        int recordCount = c1.getCount();

        for(int i=0;i<recordCount;i++)
        {
            c1.moveToNext();
            String stu_name = c1.getString(0);
            String seat_money = c1.getString(1);
            String  seat_number= c1.getString(2);
//출력 하면 됨
        _sum = _sum +Integer.parseInt(seat_money);
        c1.close();
        }


    }


}
