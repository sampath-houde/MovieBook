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
import com.example.Movies.mainpackage.api.model.MovieSearchList;
import com.example.Movies.R;
import com.example.Movies.mainpackage.api.views.MovieDescriptionFragment;

import java.util.List;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {


    Context context;
    List<MovieSearchList.Result> movieList;

    public MyAdapter2(Context c, List<MovieSearchList.Result> movieList)
    {
        this.context = c;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_view, parent, false);
        return new ViewHolder(view);
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
                Intent intent = new Intent(context, MovieDescriptionFragment.class);
                if(movieList.get(position).getOriginalTitle() == null)
                {
                    intent.putExtra("movieName", movieList.get(position).getOriginalName());
                }
                else
                {
                    intent.putExtra("movieName", movieList.get(position).getOriginalTitle());
                }
                if(movieList.get(position).getPosterPath() != null)
                {
                    intent.putExtra("moviePoster", movieList.get(position).getPosterPath());
                }
                else
                {
                    intent.putExtra("moviePoster", "1");
                }
                intent.putExtra("movieDescription", movieList.get(position).getOverview());
                intent.putExtra("movieDate", movieList.get(position).getReleaseDate());
                intent.putExtra("movieVotes", movieList.get(position).getVoteCount().toString());
                context.startActivity(intent);

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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            moviename = itemView.findViewById(R.id.movieName);
            movierating = itemView.findViewById(R.id.movieRating);
            img = itemView.findViewById(R.id.imageView1);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
