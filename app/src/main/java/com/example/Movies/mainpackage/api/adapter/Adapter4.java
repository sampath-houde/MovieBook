package com.example.Movies.mainpackage.api.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Movies.R;
import com.example.Movies.databinding.MyViewBinding;
import com.example.Movies.userDataBase.UserDataBase;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Adapter4 extends RecyclerView.Adapter<Adapter4.ViewHolder> {

    private ArrayList<UserDataBase> user_data;

    Context context;
    ArrayList<UserDataBase.Movie_Favourites> movieFavourites;
    MyViewBinding binding;

    public Adapter4(Context c, ArrayList<UserDataBase.Movie_Favourites> movieFavouritesList) {
        this.context = c;
        this.movieFavourites = movieFavouritesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = MyViewBinding.inflate(inflater, parent, false);
        return new Adapter4.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.moviename.setText(movieFavourites.get(position).movie_name);
        String s1 = movieFavourites.get(position).movie_Poster;
        Glide.with(context).
                load("https://image.tmdb.org/t/p/w500/" + s1)
                .into(holder.img);
        holder.movieType.setText(movieFavourites.get(position).movie_showType);
        holder.wishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialAlertDialogBuilder(context)
                        .setMessage("Are you sure you want to remove this from your wishlist")
                        .setPositiveButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setNegativeButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                movieFavourites.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, movieFavourites.size());
                                removeMovieObject(movieFavourites);
                            }
                        }).show();
            }
        });

        int rating = (int) Math.round(Double.parseDouble(movieFavourites.get(position).getMovie_Rating()));

        switch (rating) {
            case 1: holder.movierating.setRating((float) 0.5);break;
            case 2: holder.movierating.setRating((float) 1.0);break;
            case 3: holder.movierating.setRating((float) 1.5);break;
            case 4: holder.movierating.setRating(2);break;
            case 5: holder.movierating.setRating((float) 2.5);break;
            case 6: holder.movierating.setRating(3);break;
            case 7: holder.movierating.setRating((float) 3.5);break;
            case 8: holder.movierating.setRating(4);break;
            case 9: holder.movierating.setRating((float) 4.5);break;
            case 10: holder.movierating.setRating(5);break;
            default: holder.movierating.setRating(3);
        }
    }

    @Override
    public int getItemCount() {
        return movieFavourites.size();
    }

    private void updateMovieBookedList(ArrayList<UserDataBase> updatedList) {
        SharedPreferences sharedPref = context.getSharedPreferences("Main", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(updatedList);
        edit.putString("activity", json);
        edit.apply();
    }

    private void removeMovieObject(ArrayList<UserDataBase.Movie_Favourites> movie_favourites) {
        getUserList();
        int pos = getPositionOfUserLoggedInUser();
        user_data.get(pos).setMovie_favourites(movie_favourites);
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

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView moviename, movieType;
        ImageView img;
        RatingBar movierating;
        ImageButton wishlist;

        public ViewHolder(MyViewBinding binding) {
            super(binding.getRoot());
            moviename = binding.movieName;
            img = binding.imageView1;
            movieType = binding.adult;
            wishlist = binding.btnFavicon;
            movierating = binding.movieRating;

            wishlist.setImageResource(R.drawable.ic_baseline_favorite_24_red);
        }
    }
}


