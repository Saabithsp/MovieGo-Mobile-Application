package com.example.movies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Domain.GenresItem;
import com.example.movies.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryEachFilmListAdapter extends RecyclerView.Adapter<CategoryEachFilmListAdapter.viewHolder> {
    List<String> items;
    Context context;

    public CategoryEachFilmListAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryEachFilmListAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category,parent,false);
        return new viewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryEachFilmListAdapter.viewHolder holder, int position) {
        holder.titleTxt.setText(items.get(position));


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        TextView titleTxt;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt=itemView.findViewById(R.id.TitleTxt);

        }
    }
}
