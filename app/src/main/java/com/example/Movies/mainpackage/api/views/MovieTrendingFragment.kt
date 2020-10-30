package com.example.Movies.mainpackage.api.views

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movies.R
import com.example.Movies.databinding.FragmentMovietrendingBinding
import com.example.Movies.login_register.views.LoginRegisterActivity
import com.example.Movies.mainpackage.api.ApiInterface.OMDBapi
import com.example.Movies.mainpackage.api.ApiInterface.RetrofitInstance
import com.example.Movies.mainpackage.api.MainActivity
import com.example.Movies.mainpackage.api.adapter.MyAdapter
import com.example.Movies.mainpackage.api.model.MovieTrending
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class MovieTrendingFragment : Fragment() {

    private lateinit var drawerLayoutManager: DrawerLayout
    private lateinit var navigationView: NavigationView
    private var results = listOf<MovieTrending.Result>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter
    private lateinit var toolbar: Toolbar
    private var fragmentMovietrendingBinding: FragmentMovietrendingBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMovietrendingBinding.inflate(inflater, container, false)
        fragmentMovietrendingBinding = binding
        val view = binding.root

        toolbar = binding.myToolbar
        drawerLayoutManager = binding.drawerlayout

        recyclerView = binding.RecyclerView

        Handler().postDelayed({
            getTrendingMoviesList()

            toolBarMenu()
            setupNavigationDrawer()

            navigationView = binding.navigationView

            navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {

                when (it.itemId) {

                    R.id.Search -> {
                        Navigation.findNavController(view).navigate(R.id.movieSearchFragment)
                    }

                    R.id.logout -> {
                        Toast.makeText(this.context, "Logout Successful", Toast.LENGTH_SHORT).show()
                        setUserLoginStatus()
                        val intent = Intent(context, LoginRegisterActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }

                    R.id.profileSection -> {
                        Navigation.findNavController(view).navigate(R.id.userProfileFragment)
                    }

                    R.id.Favourites -> {
                        Navigation.findNavController(view).navigate(R.id.favouritesFragment)
                    }

                    R.id.myBookings -> {
                        Navigation.findNavController(view).navigate(R.id.userBookingsFragment)
                    }

                }
                drawerLayoutManager.closeDrawer(GravityCompat.START)
                return@OnNavigationItemSelectedListener true;
            })
        }, 1500)

        binding.progressCardView.visibility = View.VISIBLE

        return view
    }

    override fun onDestroyView() {
        fragmentMovietrendingBinding = null
        super.onDestroyView()
    }

    private fun setUserLoginStatus() {
        val loginStatus = context?.getSharedPreferences("LoginSession", MODE_PRIVATE)!!
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
                fragmentMovietrendingBinding?.progressCardView?.visibility = View.INVISIBLE
                if (response != null) {
                    results = response.body().results
                    setDataToRecycler(response.body())
                }
            }

            override fun onFailure(call: Call<MovieTrending>?, t: Throwable?) {
                fragmentMovietrendingBinding?.progressCardView?.visibility = View.INVISIBLE
                fragmentMovietrendingBinding?.connectionNotAvailable?.visibility = View.VISIBLE
                fragmentMovietrendingBinding?.textConnection?.visibility = View.VISIBLE
            }
        })
    }

    override fun onResume() {
        super.onResume()

    }

    private fun setDataToRecycler(body: MovieTrending) {
        myAdapter = MyAdapter(context, body.results)
        myAdapter.notifyDataSetChanged()

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)

    }

    fun setupNavigationDrawer() {


        val toggle = ActionBarDrawerToggle(
            activity,
            drawerLayoutManager,
            toolbar,
            R.string.openDrawer,
            R.string.drawerClose
        )

        drawerLayoutManager.addDrawerListener(toggle)
        toggle.syncState()
    }

    fun toolBarMenu() {
        toolbar.setTitle("Movie Trending")
        (activity as MainActivity?)!!.setSupportActionBar(toolbar)
    }


    /*private fun onFilter(text: String) {
        var results2 = ArrayList<MovieTrending.Result>()

        if (text != null) {
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
    }*/

}


/*@SuppressLint("ResourceType")
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_movietrending)

    toolBarMenu()
    setupNavigationDrawer()

    navigationView = findViewById(R.id.navigationView)

    navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {

        when(it.itemId) {

            R.id.Home -> {

            }

            R.id.Search -> {
                val intent = Intent(this, MovieSearchFragment::class.java)
                startActivity(intent)
            }

            R.id.logout -> {
                Toast.makeText(applicationContext, "Logout Successful", Toast.LENGTH_SHORT).show()
                setUserLoginStatus()
                val intentLogout = Intent(this,LoginFragment::class.java)
                startActivity(intentLogout)
            }

            R.id.profileSection -> {
                val intentProfile = Intent(this, UserProfileFragment::class.java)
                startActivity(intentProfile)
            }

        }

        drawerLayoutManager.closeDrawer(GravityCompat.START)
        return@OnNavigationItemSelectedListener true;
    })

    recyclerView = findViewById(R.id.RecyclerView)

    Handler().postDelayed({
        getTrendingMoviesList()

        *//*fab.setOnClickListener {

            }*//*

        }, 1500)

        textView3.visibility = View.INVISIBLE
    }*/







