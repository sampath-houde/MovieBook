package com.example.Movies.mainpackage.api.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.Movies.mainpackage.api.ApiInterface.Api_Url
import com.example.Movies.mainpackage.api.views.MovieSearchFragment
import org.json.JSONObject

class MovieSearchViewModel(application: Application) : AndroidViewModel(application){

    fun getSearchMoviesList(searchQuery: String, context: MovieSearchFragment) {

        val url = Api_Url.SEARCH_URL

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url + searchQuery, null,
            com.android.volley.Response.Listener { response: JSONObject ->
                if (response != null) {
                    context.setDataToRecycler2(response)
                }
            }, com.android.volley.Response.ErrorListener {
                context.onFailedApiCall()
            })

        val requestQueue: RequestQueue = Volley.newRequestQueue(getApplication())
        requestQueue.add(jsonObjectRequest)

        /*val getOMDBapi2: OMDBapi = RetrofitInstance.getService()
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

        })*/
    }
}