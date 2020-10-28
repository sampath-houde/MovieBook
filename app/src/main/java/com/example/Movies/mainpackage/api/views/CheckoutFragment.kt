/*

package com.example.Movies.mainpackage.api.views

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.Movies.R
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_checkout.*
import kotlinx.android.synthetic.main.fragment_checkout.view.*
import java.lang.reflect.Type
import kotlin.properties.Delegates

class CheckoutFragment : Fragment() {

    val args: CheckoutFragmentArgs by navArgs()
    private lateinit var movieName: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var movieTickets: TextView

    private lateinit var data2: String  //MoviePoster
    private lateinit var data1: String  //MovieName
    private var data3: Int = 0  //MovieTickets

    private lateinit var sessionId: String //CurrentLoginUser

    private var positionOfLoggedInUser by Delegates.notNull<Int>() //PositionOfLoggedInUserInList

    private lateinit var user_data: ArrayList<UserDataBase>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_checkout, container, false)

        movieName = view.findViewById(R.id.movieName)
        moviePoster = view.findViewById(R.id.movieImage)
        movieTickets = view.findViewById(R.id.ticketCount)

        getDataFromIntent()

        setDataFromIntent()

        view.btn_no.setOnClickListener {
            Toast.makeText(context, "Booking Cancelled", Toast.LENGTH_SHORT).show()
            view?.findNavController()!!.navigate(R.id.movieListFragment)
        }

        view.btn_yes.setOnClickListener {
            getCurrentUserSessionIdFromIntent()
            getUserList()
            getPositionOfLoggedInUser()
            setBookedTikcetsInfoInLoggedInUser()
            Toast.makeText(context, "Tickets Booked Successfully", Toast.LENGTH_SHORT).show()
            view?.findNavController()!!.navigate(R.id.userBookingsFragment)
        }
        return view
    }

    private fun updateUserList() {
        val sharedPreferences: SharedPreferences = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val editor = sharedPreferences.edit()
        val gson: Gson = Gson()
        val json: String = gson.toJson(user_data)
        editor.putString("activity", json)
        editor.apply()
    }

    private fun setBookedTikcetsInfoInLoggedInUser() {
        val userBookedMovieInfo = UserDataBase.Movie_booked()
        userBookedMovieInfo.movie_name = data1
        if(data2==null)
        {
            userBookedMovieInfo.movie_poster = "R.drawable.ic_baseline_broken_image2_24"
        }
        else {
            userBookedMovieInfo.movie_poster = "https://image.tmdb.org/t/p/w500/" + data2
        }
        userBookedMovieInfo.movie_tikcets = data3.toString()

        user_data.get(positionOfLoggedInUser).movie_booked.add(userBookedMovieInfo)

        updateUserList()
    }

    private fun getPositionOfLoggedInUser() {
        for(i in 0 until user_data.size)
        {
            if(sessionId == user_data.get(i).user_email)
            {
                positionOfLoggedInUser = i
            }
        }
    }

    private fun setDataFromIntent() {

        if(data2 == null)
        {
            moviePoster.setImageResource(R.drawable.ic_baseline_broken_image2_24)
        }
        else {
            Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + data2)
                .into(moviePoster)
        }

        movieName.setText(data1)

        movieTickets.setText(data3.toString())
    }

    private fun getDataFromIntent() {
        data2 = args.moviePoster!!
        data1 = args.movieName!!
        data3 = args.ticketCount
    }

    private fun getCurrentUserSessionIdFromIntent() {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", MODE_PRIVATE)
        sessionId = sharedPreferences?.getString("userSessionId", null).toString()
    }

    fun getUserList() {
        val sharedPreferences2: SharedPreferences = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if(json==null){
            user_data=ArrayList<UserDataBase>()
        }else{

            user_data = gson.fromJson(json, type)
        }
    }
}



override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_checkout)

    movieName = findViewById(R.id.movieName)
    moviePoster = findViewById(R.id.movieImage)
    movieTickets = findViewById(R.id.ticketCount)

    getDataFromIntent()

    setDataFromIntent()

    btn_no.setOnClickListener {
        Toast.makeText(applicationContext, "Booking Cancelled", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MovieTrendingFragment::class.java)
        startActivity(intent)
    }

    btn_yes.setOnClickListener {

        getCurrentUserSessionIdFromIntent()
        getUserList()
        getPositionOfLoggedInUser()
        setBookedTikcetsInfoInLoggedInUser()
        Toast.makeText(applicationContext, "Tickets Booked Successfully", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, UserBookingsFragment::class.java)
        startActivity(intent)
    }

}
*/
