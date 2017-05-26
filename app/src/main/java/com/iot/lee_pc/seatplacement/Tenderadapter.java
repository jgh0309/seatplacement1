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
    private ArrayList<Tender> _tenders;

    public Tenderadapter(ArrayList<Tender> tenderArrayList, ArrayList<Tender> tenders)
    {
        this._tenders = tenders;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.priceview, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Tender tender = _tenders.get(position);
        holder.nameText.setText(tender.getName());
        holder.priceText.setText(tender.getPrice());
        holder.seatText.setText(tender.getTenderseat());
    }

    @Override
    public int getItemCount()
    {
        return _tenders.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View priceview)
        {
            super(priceview);

            nameText = (TextView) priceview.findViewById(R.id.nameview);
            priceText = (TextView) priceview.findViewById(R.id.priceview);
            seatText = (TextView) priceview.findViewById(R.id.seatView);
        }

        TextView nameText;
        TextView priceText;
        TextView seatText;
    }
}
