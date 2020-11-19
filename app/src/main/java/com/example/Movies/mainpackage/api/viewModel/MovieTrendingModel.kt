package com.example.Movies.mainpackage.api.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.Movies.mainpackage.api.ApiInterface.Api_Url
import com.example.Movies.mainpackage.api.views.MovieTrendingFragment
import org.json.JSONObject

class MovieTrendingModel(application: Application) : AndroidViewModel(application) {


     fun setUserLoginStatus() {
        val loginStatus = getApplication<Application>().getSharedPreferences("LoginSession", Context.MODE_PRIVATE)!!
        val editor: SharedPreferences.Editor = loginStatus.edit()
        editor.putBoolean("userLoginStatus", false)
        editor.apply()
    }

    fun getTrendingMoviesList(context: MovieTrendingFragment) {
        val url = Api_Url.TRENDING_URL

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            com.android.volley.Response.Listener { response: JSONObject ->
                if (response != null) {
                    context.setListFromApiToRecyclerAdapter(response)
                }
            }, Response.ErrorListener {
                context.onFailureApiCall()
            })

        val queue: RequestQueue? = Volley.newRequestQueue(getApplication())
        queue!!.add(jsonObjectRequest)

        /*val getOMDBapi: OMDBapi = RetrofitInstance.getService()
        val call: Call<MovieTrending> = getOMDBapi.trendingMovieList

        call.enqueue(object : Callback<MovieTrending> {

            override fun onResponse(
                call: Call<MovieTrending>?,
                response: Response<MovieTrending>?,
            ) {
                if (response != null) {
                    context.setListFromApiToRecyclerAdapter(response.)
                }
            }

            override fun onFailure(call: Call<MovieTrending>?, t: Throwable?) {
                context.onFailureApiCall()
            }
        })
    }*/
    }


}