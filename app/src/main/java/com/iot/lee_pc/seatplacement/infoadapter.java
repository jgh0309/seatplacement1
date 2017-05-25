package com.iot.lee_pc.seatplacement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class infoadapter extends RecyclerView.Adapter<infoadapter.ViewHolder>
{
    private ArrayList<Info> _albums;

    public infoadapter(ArrayList<Info> albums)
    {
        _albums = albums;
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
        Info info = _albums.get(position);
        holder.nameText.setText(info.getName());
        holder.seatText.setText(info.getSeat());
    }

    @Override
    public int getItemCount()
    {
        return _albums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View itemView)
        {
            super(itemView);

            nameText = (TextView) itemView.findViewById(R.id.nameview);
            seatText = (TextView) itemView.findViewById(R.id.seatview);
        }

        TextView nameText;
        TextView seatText;
    }
}
