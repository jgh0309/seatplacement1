package com.iot.lee_pc.seatplacement;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class comparetest extends AppCompatActivity
{
    SQLiteDatabase db;
    int max=0;

    @Before
    public void setUp() throws Exception
    {
    }

    @Test
    public void 배열에_배치되지_않은_요소에_랜덤하게_숫자를_넣어준다() throws Exception
    {
        int[] ary = new int[]{1, 2, 100, 4, 100};
        createRandomArray(2, ary);
        assertNotEquals(100, ary[2]);
        assertNotEquals(100, ary[4]);
    }

    @Test
    public void 이미_배치된_자리이면_배치하지_않는다() throws Exception
    {
        int[] ary = new int[]{1, 2, 100, 4, 5};
        isSeatempty(2, 3, ary);
        assertEquals(100, ary[2]);
    }

    @Test
    public void 비어있는_자리를_발견하면_1을_반환한다() throws Exception
    {
        int[] ary = new int[]{1, 2, 100, 4, 5};
        searchEmpty(ary);
        assertEquals(1, searchEmpty(ary));
    }

    @Test
    public void 지망순위와_자리가_같으면_금액으로_비교한다() throws Exception
    {
        int[] ary = new int[]{100, 100};
        int[] stu1 = new int[]{1,1000};
        int[] stu2 = new int[]{2,2000};

        ifpreferenceequal(stu1,stu2,ary);
        assertEquals(100, ary[0]);
        assertEquals(2, ary[1]);
    }




    private void ifpreferenceequal(int[] stu1, int[] stu2,int[] ary){
            int _seat_money = stu1[1]; //비교 하는 사람의 입찰금을 저장
            int _seat_money_compare = stu2[1]; //비교 당하는 사람의 입찰금을 저장

            if (_seat_money > _seat_money_compare)
            { //금액이 더욱 크면 확정 자리에 금액을 넣음
                ary[stu1[0]]=stu1[1];
            } else if (_seat_money < _seat_money_compare && max < _seat_money_compare)
            { //작으면 다른 사람의 금액을 넣음 ,max보다 큰 값이 들어가야 들어감
                max = _seat_money_compare;
                ary[stu1[0]] = 100;   //본인보다 큰 금액을 배팅한 사람이 들어갈 수 있게 자리를 포기함.
            }
    }

    private int[] createRandomArray(int nullseatCount, int[] ary)
    {
        int a[] = new int[nullseatCount];
        int b[] = ary;

        for (int i = 0; i < nullseatCount; i++)
        {
            a[i] = (int) (Math.random() * ary.length + 1);
            for (int j = 0; j < ary.length; j++) //중복제거를 위한 for문
            {
                if (a[i] == b[j])
                {
                    i--;
                    break;
                }
            }
        }
        for (int i = 0; i < ary.length; i++)
        {
            for (int j = 0; j < nullseatCount; j++)
            {
                if (100 == b[i])
                {
                    b[i] = a[j];
                } else break;
            }
        }
        return ary;
    }

    private void isSeatempty(int stu_seat, int stu_no, int[] ary)
    {
        int count = 0;
        for (int i = 0; i < ary.length; i++) if (ary[i] == stu_seat) count++;
        if (count == 0)
        {
            ary[stu_no] = stu_seat;
        }
    }

    private int searchEmpty(int[] ary)
    {
        for (int i = 0; i < ary.length; i++)
         {
            if (ary[i] == 100)
            {
                return 1;
            }

         }
        return 0;
    }
}
