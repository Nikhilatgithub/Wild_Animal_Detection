package com.be.wildanimaldetection.adaptors.broadcast_adpr;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.be.wildanimaldetection.R;

import java.util.ArrayList;



public class RecylceAdaptorB extends RecyclerView.Adapter<RecylceAdaptorB.recyclerholder> {

    ArrayList<ReclycleGetSetB> list,listall;

    public Context context;



    public class recyclerholder extends RecyclerView.ViewHolder{


        public TextView msg;
        public ImageView imageView;
        public CardView cardView;
        public recyclerholder (View v){

            super(v);
            imageView=v.findViewById(R.id.relogo);
            msg=v.findViewById(R.id.retitle);
            cardView=v.findViewById(R.id.recard);
            cardView.setCardBackgroundColor(Color.TRANSPARENT);
            cardView.setCardElevation(0);
        }

    }

    public RecylceAdaptorB(Context mycontext, ArrayList <ReclycleGetSetB> listitems){

        list=listitems;
        listall=new ArrayList<>(list);
        context=mycontext;

    }
    @NonNull
    @Override
    public recyclerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_custum,parent,false);

        return new recyclerholder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull final recyclerholder holder, final int position) {
        final ReclycleGetSetB currentitem=list.get(position);

        holder.imageView.setImageResource(currentitem.getImageresource());
        holder.msg.setText(currentitem.getText());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }






}

