package com.example.Movies.mainpackage.api.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Movies.R;
import com.example.Movies.userDataBase.UserDataBase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {

    private ArrayList<UserDataBase> user_data;

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
        holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieBookedList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, movieBookedList.size());
                removeMovieObject(movieBookedList);
            }
        });

    }

    private void updateMovieBookedList(ArrayList<UserDataBase> updatedList) {
        SharedPreferences sharedPref = context.getSharedPreferences("Main", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(updatedList);
        edit.putString("activity", json);
        edit.apply();
    }

    private void removeMovieObject(ArrayList<UserDataBase.Movie_booked> movieBookedList) {
        getUserList();
        int pos = getPositionOfUserLoggedInUser();
        user_data.get(pos).setMovie_booked(movieBookedList);
        updateMovieBookedList(user_data);
    }

    private int getPositionOfUserLoggedInUser() {
        getUserList();
        int position = 0;
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("LoginSession", Context.MODE_PRIVATE);
        String sessionId = sharedPreferences2.getString("userSessionId", null).toString();
        for(int i=0; i<user_data.size(); i++){
            if(sessionId == user_data.get(i).user_email){
                position = i;
            }
        }
        return position;
    }

    private void getUserList() {
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("Main", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("activity", null);
        Type type = new TypeToken<ArrayList<UserDataBase>>() {}.getType();
        if(json==null){
            user_data = new ArrayList<UserDataBase>();
        }else{
            user_data = gson.fromJson(json, type);
        }
    }

    @Override
    public int getItemCount() {
        return movieBookedList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView moviename, movieTickets;
        ImageView img;
        Button cancel_btn;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviename = itemView.findViewById(R.id.movieName);
            img = itemView.findViewById(R.id.movieImage);
            movieTickets = itemView.findViewById(R.id.ticketCount);
            cancel_btn = itemView.findViewById(R.id.btn_cancel);
        }
    }
}
