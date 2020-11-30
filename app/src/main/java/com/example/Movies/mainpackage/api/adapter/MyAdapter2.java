package com.example.Movies.mainpackage.api.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.Movies.databinding.MyViewBinding;
import com.example.Movies.mainpackage.api.model.MovieSearchList;
import com.example.Movies.R;
import com.example.Movies.mainpackage.api.views.MovieDescriptionFragment;

import java.util.List;

import static android.content.ContentValues.TAG;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {


    Context context;
    List<MovieSearchList.Result> movieList;
    MyViewBinding binding;
    long DURATION = 250;
    private boolean on_attach = true;

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

        FromLeftToRight(holder.cardView, 2);

        if(movieList.get(position).getOriginalTitle() == null)
        {
            holder.moviename.setText(movieList.get(position).getOriginalName());
        }
        else
        {
            holder.moviename.setText(movieList.get(position).getOriginalTitle());
        }

        holder.btn_fav.setVisibility(View.INVISIBLE);

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
                /*if(movieList.get(position).getPosterPath() != null)
                {*/
                    bundle.putString("moviePoster", movieList.get(position).getPosterPath());
                /*}
                else
                {
                    bundle.putString("moviePoster", "1");
                }*/
                bundle.putString("movieDescription", movieList.get(position).getOverview());
                bundle.putString("movieDate", movieList.get(position).getReleaseDate());
                bundle.putString("movieRating", movieList.get(position).getVoteAverage().toString());
                bundle.putString("movieVotes", movieList.get(position).getVoteCount().toString());
                bundle.putString("movieShowType", "Movie");
                MovieDescriptionFragment frag = new MovieDescriptionFragment();
                Navigation.findNavController(v).navigate(R.id.action_movieSearchFragment_to_movieDescriptionFragment, bundle);

            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.d(TAG, "onScrollStateChanged: Called " + newState);
                on_attach = false;
                super.onScrollStateChanged(recyclerView, newState);
            }
        });

        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setAnimation(View itemView, int i) {
        if(!on_attach){
            i = -1;
        }
        boolean isNotFirstItem = i == -1;
        i++;
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animator = ObjectAnimator.ofFloat(itemView, "alpha", 0.f, 0.5f, 1.0f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animator.setStartDelay(isNotFirstItem ? DURATION / 2 : (i * DURATION / 3));
        animator.setDuration(500);
        animatorSet.play(animator);
        animator.start();
    }


    private void FromLeftToRight(View itemView, int i) {
        if(!on_attach){
            i = -1;
        }
        boolean not_first_item = i == -1;
        i = i + 1;
        itemView.setTranslationX(-400f);
        itemView.setAlpha(0.f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(itemView, "translationX", -400f, 0);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(itemView, "alpha", 1.f);
        ObjectAnimator.ofFloat(itemView, "alpha", 0.f).start();
        animatorTranslateY.setStartDelay(not_first_item ? DURATION : (i * DURATION));
        animatorTranslateY.setDuration((not_first_item ? 2 : 1) * DURATION);
        animatorSet.playTogether(animatorTranslateY, animatorAlpha);
        animatorSet.start();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView moviename;
        RatingBar movierating;
        ImageView img;
        ConstraintLayout mainLayout;
        ImageButton btn_fav;
        CardView cardView;

        public ViewHolder(MyViewBinding binding) {
            super(binding.getRoot());
            moviename = binding.movieName;
            movierating = binding.movieRating;
            img = binding.imageView1;
            mainLayout = binding.mainLayout;
            btn_fav = binding.btnFavicon;
            cardView = binding.view;
        }
    }
}
