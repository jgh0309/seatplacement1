package com.iot.lee_pc.seatplacement;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

public class infoadapter extends RecyclerView.Adapter<infoadapter.ViewHolder>
{
    private ArrayList<RecycleData> totalseats;
    private ArrayList<Info> infos;
    int manCount;

    public infoadapter(ArrayList<RecycleData> totalseats, ArrayList<Info> infos)
    {
        this.totalseats = totalseats;
        this.infos = infos;
        manCount = infos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        RecycleData seat = totalseats.get(position);
        int seatnumber = seat.getSeatnumber();
        for(int i=0; i<manCount;i++) {
            if(seatnumber == Integer.parseInt(infos.get(i).getSeat())) {
                holder.button.setBackgroundColor(Color.BLUE);
                holder.button.setText(infos.get(i).getName());
                System.out.println(infos.get(i).getName());
            }
        }

    }

    @Override
    public int getItemCount()
    {
        return totalseats.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View itemView)
        {
            super(itemView);

            button = (Button) itemView.findViewById(R.id.button);

//            nameText = (TextView) itemView.findViewById(R.id.nameview);
//            seatText = (TextView) itemView.findViewById(R.id.seatview);
        }

        Button button;
    }
}
