package com.example.Movies.mainpackage.api.views

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movies.R
import com.example.Movies.login_register.views.LoginActivity
import com.example.Movies.mainpackage.api.ApiInterface.OMDBapi
import com.example.Movies.mainpackage.api.ApiInterface.RetrofitInstance
import com.example.Movies.mainpackage.api.adapter.MyAdapter
import com.example.Movies.mainpackage.api.model.MovieTrending
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.movie_list2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

@Suppress("DEPRECATION")
class MovieList : AppCompatActivity(){

    private lateinit var drawerLayoutManager: DrawerLayout
    private lateinit var navigationView: NavigationView
    private var results = listOf<MovieTrending.Result>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var fab: FloatingActionButton


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_list2)

        toolBarMenu()
        setupNavigationDrawer()

        navigationView = findViewById(R.id.navigationView)

        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {

            when(it.itemId) {

                R.id.Home -> {

                }

                R.id.Search -> {
                    val intent = Intent(this, MovieSearch::class.java)
                    startActivity(intent)
                }

                R.id.logout -> {
                    Toast.makeText(applicationContext, "Logout Successful", Toast.LENGTH_SHORT).show()
                    setUserLoginStatus()
                    val intentLogout = Intent(this,LoginActivity::class.java)
                    startActivity(intentLogout)
                }

                R.id.profileSection -> {
                    val intentProfile = Intent(this, UserProfile::class.java)
                    startActivity(intentProfile)
                }

            }

            drawerLayoutManager.closeDrawer(GravityCompat.START)
            return@OnNavigationItemSelectedListener true;
        })

        recyclerView = findViewById(R.id.RecyclerView)

        Handler().postDelayed({
            getTrendingMoviesList()

            /*fab.setOnClickListener {

            }*/

        }, 1500)

        textView3.visibility = View.INVISIBLE
    }

    private fun setUserLoginStatus() {
        val loginStatus: SharedPreferences = getSharedPreferences("LoginSession", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = loginStatus.edit()
        editor.putBoolean("userLoginStatus", false)
        editor.apply()
    }


    private fun getTrendingMoviesList() {

        val getOMDBapi: OMDBapi = RetrofitInstance.getService()
        val call: Call<MovieTrending> = getOMDBapi.trendingMovieList


        call.enqueue(object : Callback<MovieTrending> {

            override fun onResponse(
                call: Call<MovieTrending>?,
                response: Response<MovieTrending>?,
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
                textView3.visibility = View.INVISIBLE
                connectionNotAvailable.visibility = View.VISIBLE
                textConnection.visibility = View.VISIBLE
            }
        })
    }


    private fun setDataToRecycler(body: MovieTrending) {
        myAdapter = MyAdapter(this, body.results)
        val intent = Intent(this, MovieDescription::class.java)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

    }

    fun setupNavigationDrawer()
    {
        drawerLayoutManager = findViewById(R.id.drawerlayout)

        val toggle = ActionBarDrawerToggle(this,
            drawerLayoutManager,
            my_toolbar,
            R.string.openDrawer,
            R.string.drawerClose)

        drawerLayoutManager.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun toolBarMenu(){
        my_toolbar.setTitle("Movie List")
        setSupportActionBar(my_toolbar)
    }

    private fun onFilter(text: String) {
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






