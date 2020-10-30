package com.example.Movies.mainpackage.api.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.SharedPreferencesKt;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Movies.databinding.MyViewBinding;
import com.example.Movies.mainpackage.api.model.MovieSearchList;
import com.example.Movies.mainpackage.api.model.MovieTrending;
import com.example.Movies.R;
import com.example.Movies.userDataBase.UserDataBase;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    Context context;
    List<MovieTrending.Result> movieList;
    private String sessionId;
    private ArrayList<UserDataBase> user_data;
    UserDataBase.Movie_Favourites user_favourites = new UserDataBase.Movie_Favourites();
    MyViewBinding binding;

    public MyAdapter(Context c, List<MovieTrending.Result> movieList) {
        this.context = c;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
         binding = MyViewBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        if (movieList.get(position).getOriginalTitle() == null) {
            holder.moviename.setText(movieList.get(position).getOriginalName());
        } else {
            holder.moviename.setText(movieList.get(position).getOriginalTitle());
        }

        int rating = (int) Math.round(movieList.get(position).getVoteAverage());

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

        if (movieList.get(position).getAdult() != null) {
            if (!movieList.get(position).getAdult()) {
                holder.movieAdult.setText("U/A Rated");
            } else {
                holder.movieAdult.setText("A Rated");
            }
        } else {
            holder.movieAdult.setText("A Rated");
        }
        String str = movieList.get(position).getPosterPath();
        String s1 = "";
        for (int i = 1; i < str.length(); i++) {
            s1 = s1 + str.charAt(i);
        }
        Glide.with(context).
                load("https://image.tmdb.org/t/p/w500/" + s1)
                .into(holder.img);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (movieList.get(position).getOriginalTitle() == null) {
                    bundle.putString("movieName", movieList.get(position).getOriginalName());
                } else {
                    bundle.putString("movieName", movieList.get(position).getOriginalTitle());
                }

                if (movieList.get(position).getReleaseDate() == null) {
                    bundle.putString("movieDate", movieList.get(position).getFirstAirDate());
                } else {
                    bundle.putString("movieDate", movieList.get(position).getReleaseDate());
                }
                bundle.putString("movieShowType", movieList.get(position).getMediaType());
                bundle.putString("movieVotes", movieList.get(position).getVoteCount().toString());
                bundle.putString("moviePoster", movieList.get(position).getPosterPath());
                bundle.putString("movieRating", movieList.get(position).getVoteAverage().toString());
                bundle.putString("movieDescription", movieList.get(position).getOverview());
                Navigation.findNavController(v).navigate(R.id.movieDescriptionFragment, bundle);
            }
        });

        getCurrentUserSessionIdFromIntent();
        getUserList();

        if(user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().isEmpty())
        {
            holder.btn_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
        }
        else {
            for (int i=0; i<user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().size(); i++)
                if (user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().get(i).getId() == position) {
                    holder.btn_fav.setImageResource(R.drawable.ic_baseline_favorite_24_red);
                }
        }

        holder.btn_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().size() == 0) {
                    Snackbar.make(holder.btn_fav, "Added to Wishlist", Snackbar.LENGTH_SHORT).show();
                    //holder.btn_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                    addItemToFavouriteList(position);
                } else {
                    if (checkIfMovieIsPresentInFavourites(position)) {
                        Snackbar.make(v, "Removed from Wishlist", Snackbar.LENGTH_SHORT).show();
                        //holder.btn_fav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                        removeitemFromFavouriteList(position);
                    } else {
                        Snackbar.make(v, "Added to Wishlist", Snackbar.LENGTH_SHORT).show();
                        //holder.btn_fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                        addItemToFavouriteList(position);
                    }
                }
            }

        });


    }

    private Boolean checkIfMovieIsPresentInFavourites(int position) {
        int z=0;
        for (int i = 0; i < user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().size(); i++)
        {
            if(user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().get(i).getId() == position) {
                z = 1;
                break;
            }
            else
                z=0;
        }
        return z==1;
    }

    private void removeitemFromFavouriteList(int position) {
        //user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().remove(position);

        for(int i=0; i<user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().size(); i++)
        {
            if(user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().get(i).getId() == position)
            {
                user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().remove(i);
                break;
            }
        }
        notifyDataSetChanged();

        updateUserList();
    }

    private void addItemToFavouriteList(int position) {
        user_favourites.setId(position);
        if (movieList.get(position).getOriginalTitle() == null) {
            user_favourites.movie_name = movieList.get(position).getOriginalName();
        } else {
            user_favourites.movie_name = movieList.get(position).getOriginalTitle();
        }

        if (movieList.get(position).getReleaseDate() == null) {
            user_favourites.movie_date = movieList.get(position).getFirstAirDate();
        } else {
            user_favourites.movie_date = movieList.get(position).getReleaseDate();
        }

        user_favourites.movie_showType = movieList.get(position).getMediaType();
        user_favourites.movie_Votes = movieList.get(position).getVoteCount().toString();
        user_favourites.movie_Poster = movieList.get(position).getPosterPath();
        user_favourites.movie_Rating = movieList.get(position).getVoteAverage().toString();
        user_favourites.movie_Description = movieList.get(position).getOverview();
        user_data.get(getPositionOfLoggedInUser()).getMovie_favourites().add(user_favourites);
        notifyDataSetChanged();

        updateUserList();

    }

    void updateUserList() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("Main", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user_data);
        editor.putString("activity", json);
        editor.apply();
    }

    private void getCurrentUserSessionIdFromIntent() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("LoginSession", Context.MODE_PRIVATE);
        sessionId = sharedPreferences.getString("userSessionId", null);
    }

    private int getPositionOfLoggedInUser() {
        int position = 0;
        for (int i = 0; i < user_data.size(); i++) {
            if (sessionId == user_data.get(i).user_email) {
                position = i;
            }
        }
        return position;
    }

    void getUserList() {
        SharedPreferences sharedPreferences2 = context.getSharedPreferences("Main", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("activity", null);
        Type type = new TypeToken<ArrayList<UserDataBase>>() {
        }.getType();
        if (json == null) {
            user_data = new ArrayList<UserDataBase>();
        } else {
            user_data = gson.fromJson(json, type);
        }
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView moviename, movieAdult;
        ImageView img;
        RatingBar movierating;
        ImageButton btn_fav;
        ConstraintLayout mainLayout;
        CardView view;

        public ViewHolder(MyViewBinding binding) {
            super(binding.getRoot());
            moviename = binding.movieName;
            movierating = binding.movieRating;
            img = binding.imageView1;
            movieAdult = binding.adult;
            btn_fav = binding.btnFavicon;
            mainLayout = binding.mainLayout;
            view = binding.view;

        }
    }
}

//@Override
//    public Filter getFilter() {
//        return filter;
//    }
//
//    Filter filter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence constraint) {
//            List<MovieTrending.Result> filteredList = new ArrayList<>();
//
//            if (constraint.toString().isEmpty()) {
//                filteredList.addAll(movieList);
//            } else {
//                for (int i = 0; i < movieList.size(); i++) {
//                    if (movieList.get(i).getOriginalTitle() != null) {
//                        if (movieList.get(i).getOriginalTitle().toString().toLowerCase().contains(constraint)) {
//                            filteredList.add(movieList.get(i));
//                        }
//                    } else if (movieList.get(i).getOriginalName() != null) {
//                        if (movieList.get(i).getOriginalName().toString().toLowerCase().contains(constraint)) {
//                            filteredList.add(movieList.get(i));
//                        }
//                    }
//                }
//            }
//
//            FilterResults filterResults = new FilterResults();
//            filterResults.values = filteredList;
//
//            return filterResults;
//        }
//
//
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            movieList.clear();
//            movieList.addAll((Collection<? extends MovieTrending.Result>) results.values);
//            notifyDataSetChanged();
//        }
//
//    };