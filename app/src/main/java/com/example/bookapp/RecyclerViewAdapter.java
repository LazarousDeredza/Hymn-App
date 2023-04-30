package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable{
    Context mContext;
    ArrayList<Hymns> lstHymn;
    ArrayList<Hymns> hymnsArrayListFull;



    public RecyclerViewAdapter(ArrayList<Hymns> lstHymn) {
        this.lstHymn=new ArrayList<>(lstHymn);
        this.hymnsArrayListFull = lstHymn;
    }


    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hymn, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
       Hymns hymns=lstHymn.get(position);


        holder.tv_title.setText(hymns.getTitle());
        holder.tv_number.setText(String.valueOf(hymns.getNumber()));


    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_title,hymn_content;
        private TextView tv_number;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // implement onClick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if(pos != RecyclerView.NO_POSITION) {
                        Hymns clickedDataItem = lstHymn.get(pos);

                        Intent i = new Intent(v.getContext(), Details.class);
                        // send hymns title and contents through recyclerview to detail activity
                        i.putExtra("titleOfStory", clickedDataItem.getTitle());
                        i.putExtra("contentOfStory", clickedDataItem.getContent());
                        v.getContext().startActivity(i);

                    }
                }
            });

            tv_title=(TextView) itemView.findViewById(R.id.hymn_title);
            tv_number=(TextView) itemView.findViewById(R.id.hymn_number);
            hymn_content=(TextView) itemView.findViewById(R.id.contentOfStory);
        }
    }

    @Override
    public int getItemCount() {
        return lstHymn.size();
    }

    public Filter getFilter() {
        return hymnFilter;
    }
    private final Filter hymnFilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<Hymns> filteredList=new ArrayList<>();
            if(constraint==null || constraint.length()==0){
                filteredList.addAll(hymnsArrayListFull);
            }else{
                String filterPattern=constraint.toString().toLowerCase().trim();
                for(Hymns item:hymnsArrayListFull){
                    if(item.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                        Log.e("found "+ item.number, item.getTitle());
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredList;
            results.count=filteredList.size();
            return results;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
        lstHymn.clear();
        lstHymn.addAll((Collection<? extends Hymns>) results.values);
        notifyDataSetChanged();
        }
    };

}
