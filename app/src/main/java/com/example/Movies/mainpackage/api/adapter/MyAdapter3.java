package com.example.Movies.mainpackage.api.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Movies.R;
import com.example.Movies.mainpackage.api.model.MovieSearchList;
import com.example.Movies.mainpackage.api.views.MovieDescription;
import com.example.Movies.userDataBase.UserDataBase;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {


    Context context;
    ArrayList<UserDataBase.Movie_booked> movieBookedList;

    public MyAdapter3(Context c, ArrayList<UserDataBase.Movie_booked> movieBookedList) {
        this.context = c;
        this.movieBookedList = movieBookedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_bookings_view, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.moviename.setText(movieBookedList.get(position).movie_name);
        Glide.with(context).
                load(movieBookedList.get(position).movie_poster)
                .into(holder.img);
        holder.movieTickets.setText(movieBookedList.get(position).movie_tikcets);

    }

    @Override
    public int getItemCount() {
        return movieBookedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView moviename, movieTickets;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviename = itemView.findViewById(R.id.movieName);
            img = itemView.findViewById(R.id.movieImage);
            movieTickets = itemView.findViewById(R.id.ticketCount);
        }
    }
}
