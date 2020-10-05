package com.example.Movies.mainpackage.api.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movies.R
import com.example.Movies.mainpackage.api.adapter.MyAdapter2
import com.example.Movies.mainpackage.api.ApiInterface.OMDBapi
import com.example.Movies.mainpackage.api.ApiInterface.RetrofitInstance
import com.example.Movies.mainpackage.api.model.MovieSearchList
import kotlinx.android.synthetic.main.activity_movie_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class MovieSearch : AppCompatActivity() {

    private lateinit var progrressCardView: CardView

    private lateinit var progressBar: ProgressBar

    private lateinit var progressTextView: TextView

    private lateinit var proressLayout: ConstraintLayout

    private lateinit var fadeIn: Animation

    private var results = listOf<MovieSearchList.Result>()

    private lateinit var recyclerView: RecyclerView

    private lateinit var myAdapter: MyAdapter2

    /*private var textQuery: TextView = findViewById(R.id.getSearchQuery)
    private var searchButton: ImageView = findViewById(R.id.searchButton)*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_search)

        progressTextView = findViewById(R.id.connecting)
        proressLayout = findViewById(R.id.progressConstraint)
        progressBar = findViewById(R.id.progressBar)
        progrressCardView = findViewById(R.id.progressCardView)
        recyclerView = findViewById(R.id.recyclerView2)


        searchButton.setOnClickListener {

            progrressCardView.visibility = View.VISIBLE
            Handler().postDelayed({

                val searchQuery = getSearchQuery.text.toString()
                getSearchMoviesList(searchQuery)
                getSearchQuery.setText("")

            }, 2000)

        }

    }

    private fun setDataToRecycler2(body: MovieSearchList) {
        myAdapter = MyAdapter2(this, body.results2)
        val intent = Intent(this, MovieDescription::class.java)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    private fun getSearchMoviesList(searchQuery: String) {

        if(searchQuery.trim() == "")
        {
            Toast.makeText(applicationContext, "Enter Movie Name", Toast.LENGTH_SHORT).show()
        }
        else {
            val getOMDBapi2: OMDBapi = RetrofitInstance.getService()
            val call2: Call<MovieSearchList>? = getOMDBapi2.getSearchMovie(searchQuery)


            call2?.enqueue(object : Callback<MovieSearchList> {
                override fun onResponse(
                    call2: Call<MovieSearchList>?,
                    response2: Response<MovieSearchList>?,
                ) {
                    progrressCardView.visibility = View.INVISIBLE
                    if(response2!!.body().totalResults2 == 0)
                    {
                        Toast.makeText(applicationContext, "No Movie Found", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        setDataToRecycler2(response2!!.body())
                    }
                }

                override fun onFailure(call2: Call<MovieSearchList>?, t: Throwable?) {
                    Toast.makeText(applicationContext, "Connection Error!!", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }
    }
}

