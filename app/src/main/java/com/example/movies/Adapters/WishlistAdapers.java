package com.example.movies.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.Domain.Wish;
import com.example.movies.R;

import java.util.ArrayList;

public class WishlistAdapers extends RecyclerView.Adapter<WishlistAdapers.ViewHolder>{
    Context context;
    ArrayList<Wish> arrayList;

    public WishlistAdapers(Context context, ArrayList<Wish> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public WishlistAdapers.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.wish_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull WishlistAdapers.ViewHolder holder, int position) {
        Wish wish = arrayList.get(position);
        holder.mName.setText(wish.getMovieName());
        holder.mYear.setText(wish.getmYear());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.movieNameSpace);
            mYear = itemView.findViewById(R.id.movieYearSpace);
        };
    }
}
