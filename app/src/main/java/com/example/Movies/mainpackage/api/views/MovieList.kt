package com.example.Movies.mainpackage.api.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movies.R
import com.example.Movies.mainpackage.api.adapter.MyAdapter
import com.example.Movies.mainpackage.api.ApiInterface.OMDBapi
import com.example.Movies.mainpackage.api.ApiInterface.RetrofitInstance
import com.example.Movies.mainpackage.api.model.MovieTrending
import kotlinx.android.synthetic.main.movie_list2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class MovieList : AppCompatActivity(){

    private var results = listOf<MovieTrending.Result>()

    private lateinit var recyclerView: RecyclerView

    private lateinit var myAdapter: MyAdapter


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_list2)

        recyclerView = findViewById(R.id.RecyclerView)

        Handler().postDelayed({
            getTrendingMoviesList()


            fab.setOnClickListener {
                val intent = Intent(this, MovieSearch::class.java)
                startActivity(intent)
            }

        }, 1500)

        textView3.visibility = View.INVISIBLE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search, menu)
        val item: MenuItem? = menu?.findItem(R.id.actionSearch)
        val searchView: SearchView = item!!.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d("Search Value", newText.toString())
                onFilter(newText.toString())
                return true
            }

        })
        return super.onCreateOptionsMenu(menu)
    }

    private fun getTrendingMoviesList() {



        val getOMDBapi: OMDBapi = RetrofitInstance.getService()
        val call: Call<MovieTrending> = getOMDBapi.trendingMovieList


        call.enqueue(object : Callback<MovieTrending> {

            override fun onResponse(
                call: Call<MovieTrending>?,
                response: Response<MovieTrending>?
            ) {
                progressCardView.visibility = View.INVISIBLE
                textView3.visibility = View.VISIBLE
                if (response != null) {
                    results = response.body().results
                    setDataToRecycler(response.body())
                }
            }

            override fun onFailure(call: Call<MovieTrending>?, t: Throwable?) {
                progressCardView.visibility = View.INVISIBLE
                textView3.visibility = View.VISIBLE
                Toast.makeText(applicationContext, "Connection not Available", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }


    private fun setDataToRecycler(body: MovieTrending) {
        myAdapter = MyAdapter(this, body.results)
        val intent = Intent(this, MovieDescription::class.java)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }


    private fun onFilter(text:String) {
        var results2 = ArrayList<MovieTrending.Result>()

        if(text!=null)
        {
            for (i in 0 until results.size) {
                if (results.get(i).getOriginalTitle() != null) {
                    if (results.get(i).getOriginalTitle().toLowerCase()
                            .contains(text.toString().toLowerCase())
                    ) {
                        results2.add(results.get(i))
                    }
                } else if (results.get(i).getOriginalName() != null) {
                    if (results.get(i).getOriginalName().toLowerCase()
                            .contains(text.toString().toLowerCase())
                    ) {
                        results2.add(results.get(i))
                    }
                }
            }
            myAdapter.upDateData(results2)
            myAdapter.notifyDataSetChanged()
        }
    }

}

