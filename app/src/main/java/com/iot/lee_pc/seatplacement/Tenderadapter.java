package com.iot.lee_pc.seatplacement;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pack on 2017-05-26.
 */

public class Tenderadapter extends RecyclerView.Adapter<Tenderadapter.ViewHolder> {
    private ArrayList<Info> _albums;

    public Tenderadapter(ArrayList<Info> albums)
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
        holder.priceText.setText(info.getSeat());
    }

    @Override
    public int getItemCount()
    {
        return _albums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View priceview)
        {
            super(priceview);

            nameText = (TextView) priceview.findViewById(R.id.nameview);
            priceText = (TextView) priceview.findViewById(R.id.priceview);
        }

        TextView nameText;
        TextView priceText;
    }
}
