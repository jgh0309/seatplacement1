package com.iot.lee_pc.seatplacement;

import android.support.v7.app.AppCompatActivity;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class comparetest extends AppCompatActivity
{

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
        int seat = 100;
        int[] stu1 = new int[]{1000, 2000, 1, 2};
        seat = ifpreferenceequal(stu1, seat);
        assertEquals(2, seat);
    }

    @Test
    public void 이미_배치된_자리이면_배치하지_않는다() throws Exception
    {
        int[] ary = new int[]{1, 2, 100, 4, 5};
        isSeatempty(2, 3, ary);
        assertEquals(100, ary[2]);
    }

    @Test
    public void 배열에_배치되지_않은_요소에_랜덤하게_숫자를_넣어준다() throws Exception
    {
        int[] ary = new int[]{1, 2, 100, 4, 100};
        createRandomArray(2, ary);
        assertNotEquals(100, ary[2]);
        assertNotEquals(100, ary[4]);
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

    private void isSeatempty(int stu_seat, int stu_no, int[] ary)
    {
        int count = 0;
        for (int i = 0; i < ary.length; i++) if (ary[i] == stu_seat) count++;
        if (count == 0)
        {
            ary[stu_no] = stu_seat;
        }
    }

    private int ifpreferenceequal(int[] stu, int seat)
    {
        int max = 0;
        for (int i = 0; i < 2; i++)
        {
            int _seat_money = stu[i];
            for (int j = 0; j < 2; j++)
            {
                if (i != j)
                {
                    int _seat_money_compare = stu[j];
                    if (_seat_money > _seat_money_compare)
                    { //금액이 더욱 크면 확정 자리에 금액을 넣음
                        seat = stu[i + 2];
                    } else if (_seat_money < _seat_money_compare && max < _seat_money_compare)
                    { //작으면 다른 사람의 금액을 넣음 ,max보다 큰 값이 들어가야 들어감
                        max = _seat_money_compare;
                        seat = 100;   //본인보다 큰 금액을 배팅한 사람이 들어갈 수 있게 자리를 포기함.
                    } else seat = stu[i + 2];
                }
            }
        }
        return seat;
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

}
