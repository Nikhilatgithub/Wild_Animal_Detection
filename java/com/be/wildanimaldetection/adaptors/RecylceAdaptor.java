package com.be.wildanimaldetection.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.be.wildanimaldetection.R;
import java.util.ArrayList;



public class RecylceAdaptor extends RecyclerView.Adapter<RecylceAdaptor.recyclerholder> {

    ArrayList<ReclycleGetSet> list,listall;

    public Context context;



    public class recyclerholder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView msg;


        public recyclerholder (View v){

            super(v);

            imageView=v.findViewById(R.id.home_list_img);
            msg=v.findViewById(R.id.home_list_msg);

        }

    }

    public RecylceAdaptor(Context mycontext, ArrayList <ReclycleGetSet> listitems){

        list=listitems;
        listall=new ArrayList<>(list);
        context=mycontext;

    }
    @NonNull
    @Override
    public recyclerholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item,parent,false);

        return new recyclerholder(v);

    }


    @Override
    public void onBindViewHolder(@NonNull final recyclerholder holder, final int position) {
        final ReclycleGetSet currentitem=list.get(position);

        holder.imageView.setImageBitmap(currentitem.getImageresource());
        holder.msg.setText(currentitem.getText());



    }

    @Override
    public int getItemCount() {
        return list.size();
    }






}

