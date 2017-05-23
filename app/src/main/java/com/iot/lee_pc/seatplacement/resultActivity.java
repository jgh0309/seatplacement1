package com.iot.lee_pc.seatplacement;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class resultActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


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
}
