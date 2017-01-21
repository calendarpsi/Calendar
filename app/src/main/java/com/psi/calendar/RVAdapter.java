package com.psi.calendar;


import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView className;
        TextView classTime;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            className = (TextView)itemView.findViewById(R.id.claseName);
            classTime = (TextView)itemView.findViewById(R.id.claseTime);
        }
    }

    List<Clase> Clases;

    RVAdapter(List<Clase> Clases){
        this.Clases = Clases;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup,false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.className.setText(Clases.get(i).name);
        personViewHolder.classTime.setText(Clases.get(i).time);
        personViewHolder.cv.setCardBackgroundColor(Color.parseColor(Clases.get(i).color));
        personViewHolder.className.setTextSize(Clases.get(i).size);
    }

    @Override
    public int getItemCount() {
        return Clases.size();
    }
}