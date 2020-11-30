package com.example.Movies.mainpackage.api.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Movies.R;
import com.example.Movies.databinding.UserBookingsViewBinding;
import com.example.Movies.userDataBase.UserDataBase;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.ViewHolder> {

    private ArrayList<UserDataBase> user_data;

    Context context;
    ArrayList<UserDataBase.Movie_booked> movieBookedList;
    UserBookingsViewBinding binding;

    public MyAdapter3(Context c, ArrayList<UserDataBase.Movie_booked> movieBookedList) {
        this.context = c;
        this.movieBookedList = movieBookedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = UserBookingsViewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.moviename.setText(movieBookedList.get(position).movie_name);
        if(movieBookedList.get(position).movie_poster == null){
            holder.img.setImageResource(R.drawable.ic_baseline_broken_image_24);
        }
        else {
            Glide.with(context).
                    load(movieBookedList.get(position).movie_poster)
                    .into(holder.img);
        }
        holder.movieTickets.setText(movieBookedList.get(position).movie_tikcets);
        holder.cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layout.setBackgroundColor(Color.parseColor("#FFFF4444"));
                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setCancelText("No")
                        .setConfirmText("Yes")
                        .showCancelButton(true)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("Cancelled!")
                                        .setContentText("Your booking has been cancelled!")
                                        .setConfirmText("OK")
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                movieBookedList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, movieBookedList.size());
                                removeMovieObject(movieBookedList);
                            }
                        })
                        .show();
                holder.layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                /*new MaterialAlertDialogBuilder(context)
                        .setTitle("Cancel Ticket")
                        .setMessage("Do you want to cancel the ticket")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                holder.layout.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            }
                        })
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                movieBookedList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, movieBookedList.size());
                                removeMovieObject(movieBookedList);
                            }
                        }).show();*/
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
        String sessionId = sharedPreferences2.getString("userSessionId", null);
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
        ConstraintLayout layout;

        public ViewHolder(UserBookingsViewBinding binding) {
            super(binding.getRoot());
            moviename = binding.movieName;
            img = binding.movieImage;
            movieTickets = binding.ticketCount;
            cancel_btn = binding.btnCancel;
            layout = binding.constraintLayout;
        }
    }
}
