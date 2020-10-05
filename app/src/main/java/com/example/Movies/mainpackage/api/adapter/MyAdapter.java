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
import com.example.Movies.mainpackage.api.model.MovieTrending;
import com.example.Movies.R;
import com.example.Movies.mainpackage.api.views.MovieDescription;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    Context context;
    List<MovieTrending.Result> movieList;
    List<MovieSearchList.Result> movieList2;

    public MyAdapter(Context c, List<MovieTrending.Result> movieList) {
        this.context = c;
        this.movieList = movieList;
    }
    public void upDateData(List<MovieTrending.Result> updatedList) {
        this.movieList = updatedList;
    }

    public void upDateDataList2(List<MovieSearchList.Result> updatedList2) {
        this.movieList2 = updatedList2;
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

        if (movieList.get(position).getOriginalTitle() == null) {
            holder.moviename.setText(movieList.get(position).getOriginalName());
        } else {
            holder.moviename.setText(movieList.get(position).getOriginalTitle());
        }

        holder.movierating.setText(movieList.get(position).getVoteAverage().toString());

        if(movieList.get(position).getAdult() != null) {
            if (movieList.get(position).getAdult()) {
                holder.movieAdult.setText("A Rated");
            } else {
                holder.movieAdult.setText("U/A Rated");
            }
        } else
        {
            holder.movieAdult.setText("N/A");
        }
        String str = movieList.get(position).getPosterPath();
        String s1 = "";
        for (int i = 1; i < str.length(); i++) {
            s1 = s1 + str.charAt(i);
        }
        Glide.with(context).
                load("https://image.tmdb.org/t/p/w500/" + s1)
                .into(holder.img);

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MovieDescription.class);
                if (movieList.get(position).getOriginalTitle() == null) {
                    intent.putExtra("movieName", movieList.get(position).getOriginalName());
                } else {
                    intent.putExtra("movieName", movieList.get(position).getOriginalTitle());
                }

                if(movieList.get(position).getReleaseDate() == null)
                {
                    intent.putExtra("movieDate", movieList.get(position).getFirstAirDate());
                }
                else {
                    intent.putExtra("movieDate", movieList.get(position).getReleaseDate());
                }
                intent.putExtra("movieShowType", movieList.get(position).getMediaType());
                intent.putExtra("movieVotes", movieList.get(position).getVoteCount().toString());
                intent.putExtra("moviePoster", movieList.get(position).getPosterPath());
                intent.putExtra("movieRating", movieList.get(position).getVoteAverage().toString());
                intent.putExtra("movieDescription", movieList.get(position).getOverview());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView moviename, movierating, movieAdult;
            ImageView img;
            ConstraintLayout mainLayout;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                moviename = itemView.findViewById(R.id.movieName);
                movierating = itemView.findViewById(R.id.movieRating);
                img = itemView.findViewById(R.id.imageView1);
                movieAdult = itemView.findViewById(R.id.adult);
                mainLayout = itemView.findViewById(R.id.mainLayout);
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