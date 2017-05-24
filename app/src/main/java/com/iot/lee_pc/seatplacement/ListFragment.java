package com.iot.lee_pc.seatplacement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by laby on 2017-05-23.
 */

public class ListFragment extends Fragment
{

    //region iu
    private iualbum _iu;

    public iualbum getiu()
    {
        return _iu;
    }

    public void setiu(iualbum iu)
    {
        if (_iu != iu)
            _iu = iu;
    }
//endregion

    private String[] _albums;
    resultActivity _resultActivity = new resultActivity();

    public ListFragment()
    {

        _albums = new String[5];
        _albums[0] = "나만몰랐던 이야기";
        _albums[1] = "좋은날";
        _albums[2] = "분홍신";
        _albums[3] = "밤편지";
        _albums[4] = "팔레트";

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        ViewGroup fragment = (ViewGroup) inflater.inflate(R.layout.listview, container, false);
        final ListView listview = (ListView) fragment.findViewById(R.id.listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, _albums);        //만들어진 ui사용
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {             //리스너 > 인터페이스 > 인터페이스를 이벤트핸들러로 사용
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                _iu.onclick(position);

            }
        });

        return fragment;
    }

    interface iualbum
    {
        void onclick(int position);
    }
}
