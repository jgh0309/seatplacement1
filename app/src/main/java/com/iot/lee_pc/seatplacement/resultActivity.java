package com.iot.lee_pc.seatplacement;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class resultActivity extends AppCompatActivity {
    private RecyclerView _recyclerView;
    private RecyclerView detailView;
    SQLiteDatabase db;
    String name;
    String seat;
    String price;
    String tenderseat;
    int recordCount;
    int totalseat;
    private ArrayList<RecycleData> recycleDatas = new ArrayList<>();
    private ArrayList<Tender> _tenders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        totalseat = intent.getIntExtra("totalseat", 0);

        Setseatnumber();

        _recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        detailView = (RecyclerView) findViewById(R.id.detailview);

        ArrayList<Info> infos = loadData();
        ArrayList<Tender> tenders = loadDetail();
        InsertData(totalseat);



        infoadapter infoadapter = new infoadapter(recycleDatas, infos);
        Tenderadapter tenderadapter = new Tenderadapter(_tenders, tenders);
        _recyclerView.setAdapter(infoadapter);
        detailView.setAdapter(tenderadapter);

        RecyclerView.LayoutManager gridlayoutManager = new GridLayoutManager(getApplicationContext(), 5);
        _recyclerView.setLayoutManager(gridlayoutManager);
        _recyclerView.setItemAnimator(new DefaultItemAnimator());

        RecyclerView.LayoutManager gridlayoutManager1 = new GridLayoutManager(getApplicationContext(),recordCount);
        detailView.setLayoutManager(gridlayoutManager1);
        detailView.setItemAnimator(new DefaultItemAnimator());

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
                    a[i] = (int) (Math.random() * totalseat + 1);
                    for (int j = 0; j < recordCount; j++) //중복제거를 위한 for문
                    {

                        if (a[i] == b[j]) {

                            i--;
                            break;
                        }
                    }
                }

                int seatcount = 0;
                for (int i = 0; i < recordCount; i++) { //랜덤
//i가 되는 자리가 비었을때 100 , 일 대 랜덤의 배열을 하나씩 검사하며 같은 것이 없으면 넣는다
                    for (int j = 0; j < nullseatCount; j++) { //사용자

                        if (100 == b[i]) { //자리가 비어있으면
                            db.execSQL("update StudentInformation set SeatPlace = " + a[seatcount] + " where _id=" + (i+1));
                            seatcount ++;
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

    private ArrayList<Tender> loadDetail(){
        ArrayList<Tender> tenders = new ArrayList<>();
        for (int i = 0; i < 3; i++) //우선순위 개수 만큼 반복
        {
            for (int j = 0; j < recordCount; j++) { //사람 수 만큼 반복
                // 우선순위를 비교할 때 같은 사람이 있으면 금액과 자리를 출력
                //String stu1 = " select _name , batting"+i+" from StudentInformation where preference"+i+"= "+(j+1); //동일한 좌석을 가진 학생 수 출력을 위한 SQL문
                String stu1 = " select _id from StudentInformation where preference" + (i+1) + "= " + (j + 1); //동일한 좌석을 가진 학생 수 출력을 위한 SQL문
                Cursor c1 = db.rawQuery(stu1, null);
                int preferenceCount = c1.getCount();
                c1.close();

                String equlPreference = " select name , batting" + (i+1) + ", SeatPlace from StudentInformation where preference" + (i+1) + "= " + (j + 1) +
                        " order by SeatPlace;"; //동일한 좌석을 가진 학생 수 출력을 위한 SQL문 ,
                //j+1 의 좌석의 사람 이름과 배팅금액을 불러옴
                Cursor cursor = db.rawQuery(equlPreference, null);

                for (int k = 0; k < preferenceCount; k++) //동일 좌석을 지원한 수 만큼 반복
                {
                    cursor.moveToNext();
                    name = cursor.getString(0);
                    price = cursor.getString(1);
                    tenderseat = cursor.getString(2);
                    Tender tender = new Tender();
                    tender.setName(name);
                    tender.setPrice(price);
                    tender.setTenderseat(tenderseat);
                    tenders.add(tender);
                }
                cursor.close();
            }
        }
        return tenders;
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

    public void InsertData(int i) {    // 앨범객체 생성해서 arraylist에 넣어줌.
        for (int j = 0; j < i; j++) {

            RecycleData recycleData = new RecycleData();
            recycleData.setSeatnumber(j);
            recycleDatas.add(recycleData);
        }
    }
}






