package com.example.Movies.mainpackage.api.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.Movies.mainpackage.api.ApiInterface.OMDBapi
import com.example.Movies.mainpackage.api.ApiInterface.RetrofitInstance
import com.example.Movies.mainpackage.api.model.MovieSearchList
import com.example.Movies.mainpackage.api.views.MovieSearchFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieSearchViewModel(application: Application) : AndroidViewModel(application){

    fun getSearchMoviesList(searchQuery: String, movieSearchFragment: MovieSearchFragment) {

        val getOMDBapi2: OMDBapi = RetrofitInstance.getService()
        val call2: Call<MovieSearchList>? = getOMDBapi2.getSearchMovie(searchQuery)

        call2?.enqueue(object : Callback<MovieSearchList> {
            override fun onResponse(
                call2: Call<MovieSearchList>?,
                response2: Response<MovieSearchList>?,
            ) {
                if (response2!!.body().totalResults2 == 0) {
                    Toast.makeText(getApplication(), "No Movie Found", Toast.LENGTH_SHORT).show()
                } else {
                    movieSearchFragment.setDataToRecycler2(response2.body())
                }
            }

            override fun onFailure(call2: Call<MovieSearchList>?, t: Throwable?) {
                movieSearchFragment.onFailedApiCall()
            }

        })
    }
}