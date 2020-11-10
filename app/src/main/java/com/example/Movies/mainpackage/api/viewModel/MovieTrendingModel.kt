package com.example.Movies.mainpackage.api.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.Movies.mainpackage.api.ApiInterface.OMDBapi
import com.example.Movies.mainpackage.api.ApiInterface.RetrofitInstance
import com.example.Movies.mainpackage.api.model.MovieTrending
import com.example.Movies.mainpackage.api.views.MovieTrendingFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieTrendingModel(application: Application) : AndroidViewModel(application) {


     fun setUserLoginStatus() {
        val loginStatus = getApplication<Application>().getSharedPreferences("LoginSession", Context.MODE_PRIVATE)!!
        val editor: SharedPreferences.Editor = loginStatus.edit()
        editor.putBoolean("userLoginStatus", false)
        editor.apply()
    }

    fun getTrendingMoviesList(context: MovieTrendingFragment) {
        val getOMDBapi: OMDBapi = RetrofitInstance.getService()
        val call: Call<MovieTrending> = getOMDBapi.trendingMovieList


        call.enqueue(object : Callback<MovieTrending> {

            override fun onResponse(
                call: Call<MovieTrending>?,
                response: Response<MovieTrending>?,
            ) {
                if (response != null) {
                    context.setListFromApiToRecyclerAdapter(response.body())
                }
            }

            override fun onFailure(call: Call<MovieTrending>?, t: Throwable?) {
                context.onFailureApiCall()
            }
        })
    }


}