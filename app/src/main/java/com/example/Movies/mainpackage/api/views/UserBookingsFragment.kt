package com.example.Movies.mainpackage.api.views

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movies.R
import com.example.Movies.databinding.FragmentUserbookingsBinding
import com.example.Movies.mainpackage.api.adapter.MyAdapter3
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_userbookings.*
import kotlinx.android.synthetic.main.fragment_userbookings.view.*
import java.lang.reflect.Type

class UserBookingsFragment : Fragment() {

    private lateinit var myAdapter3: MyAdapter3

    private lateinit var recyclerView: RecyclerView

    private lateinit var sessionId: String

    private var fragmentUserbookingsBinding: FragmentUserbookingsBinding? = null

    private var positionOfCurrentLoggedIn: Int = 0

    private lateinit var  userBookedMovieInfo: ArrayList<UserDataBase>

    private lateinit var  newUserBookedMovieInfo: ArrayList<UserDataBase.Movie_booked>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserbookingsBinding.inflate(inflater, container, false)
        fragmentUserbookingsBinding = binding
        val view = binding.root

        recyclerView = binding.recyclerView3


        getCurrentUserSessionId()
        userBookedMovieInfo = getMovieBookedList()

        for(i in 0 until userBookedMovieInfo.size)
        {
            if(sessionId == userBookedMovieInfo.get(i).user_email)
            {
                positionOfCurrentLoggedIn = i
            }
        }

        binding.myToolbar.setTitle("Bookings")
        binding.myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.myToolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        newUserBookedMovieInfo = userBookedMovieInfo.get(positionOfCurrentLoggedIn).movie_booked

        setDataToRecycle3(newUserBookedMovieInfo)

        return view
    }

    private fun getMovieBookedList(): ArrayList<UserDataBase> {
        val user_data: ArrayList<UserDataBase>
        val sharedPreferences2: SharedPreferences = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if(json==null){
            user_data=ArrayList<UserDataBase>()
        }else{

            user_data = gson.fromJson(json, type)
        }
        return user_data
    }

    private fun setDataToRecycle3(userMovieBookedList: ArrayList<UserDataBase.Movie_booked>) {
        myAdapter3 = MyAdapter3(context, userMovieBookedList)
        recyclerView.adapter = myAdapter3
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun getCurrentUserSessionId() {
        val sharedPreferences2 = context?.getSharedPreferences("LoginSession", MODE_PRIVATE)!!
        sessionId = sharedPreferences2.getString("userSessionId", null).toString()
    }
}


/*override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_userbookings)

    recyclerView = findViewById(R.id.recyclerView3)

    userBookedMovieInfo = getMovieBookedList()

    getCurrentUserSessionId()

    for(i in 0 until userBookedMovieInfo.size)
    {
        if(sessionId == userBookedMovieInfo.get(i).user_email)
        {
            positionOfCurrentLoggedIn = i
        }
    }

    newUserBookedMovieInfo = userBookedMovieInfo.get(positionOfCurrentLoggedIn).movie_booked

    setDataToRecycle3(newUserBookedMovieInfo)

    returnHome.setOnClickListener {
        val intent = Intent(this, MovieTrendingFragment::class.java)
        startActivity(intent)
    }
}
*/