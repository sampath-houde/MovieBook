package com.example.Movies.mainpackage.api.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Movies.databinding.MyViewBinding;
import com.example.Movies.mainpackage.api.model.MovieSearchList;
import com.example.Movies.R;
import com.example.Movies.mainpackage.api.views.MovieDescriptionFragment;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {


    Context context;
    List<MovieSearchList.Result> movieList;
    MyViewBinding binding;

    public MyAdapter2(Context c, List<MovieSearchList.Result> movieList)
    {
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

        if(movieList.get(position).getOriginalTitle() == null)
        {
            holder.moviename.setText(movieList.get(position).getOriginalName());
        }
        else
        {
            holder.moviename.setText(movieList.get(position).getOriginalTitle());
        }

        holder.btn_fav.setVisibility(View.INVISIBLE);

        holder.movierating.setText(movieList.get(position).getVoteAverage().toString());
        String str = movieList.get(position).getPosterPath();
        String s1="";
        if(str == null)
        {
            holder.img.setImageResource(R.drawable.ic_baseline_broken_image_24);
        }
        else {
            for (int i = 1; i < str.length(); i++) {
                s1 = s1 + str.charAt(i);
            }
            Glide.with(context).
                    load("https://image.tmdb.org/t/p/w500/" + s1)
                    .into(holder.img);
        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, MovieDescriptionFragment.class);
                Bundle bundle = new Bundle();
                if(movieList.get(position).getOriginalTitle() == null)
                {
                    bundle.putString("movieName", movieList.get(position).getOriginalName());
                }
                else
                {
                    bundle.putString("movieName", movieList.get(position).getOriginalTitle());
                }
                if(movieList.get(position).getPosterPath() != null)
                {
                    bundle.putString("moviePoster", movieList.get(position).getPosterPath());
                }
                else
                {
                    bundle.putString("moviePoster", "1");
                }
                bundle.putString("movieDescription", movieList.get(position).getOverview());
                bundle.putString("movieDate", movieList.get(position).getReleaseDate());
                bundle.putString("movieVotes", movieList.get(position).getVoteCount().toString());
                MovieDescriptionFragment frag = new MovieDescriptionFragment();
                Navigation.findNavController(v).navigate(R.id.action_movieSearchFragment_to_movieDescriptionFragment, bundle);

            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView moviename, movierating;
        ImageView img;
        ConstraintLayout mainLayout;
        ImageButton btn_fav;

        public ViewHolder(MyViewBinding binding) {
            super(binding.getRoot());
            moviename = binding.movieName;
            movierating = binding.movieRating;
            img = binding.imageView1;
            mainLayout = binding.mainLayout;
            btn_fav = binding.btnFavicon;
        }
    }
}
