package com.example.Movies.mainpackage.api.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.Movies.R
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_movie_description.*
import java.lang.reflect.Type
import kotlin.properties.Delegates

class CheckoutActivity : AppCompatActivity() {

    private lateinit var movieName: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var movieTickets: TextView

    private lateinit var data2: String  //MoviePoster
    private lateinit var data1: String  //MovieName
    private var data3: Int = 0  //MovieTickets

    private lateinit var sessionId: String //CurrentLoginUser

    private var positionOfLoggedInUser by Delegates.notNull<Int>() //PositionOfLoggedInUserInList

    private lateinit var user_data: ArrayList<UserDataBase>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        movieName = findViewById(R.id.movieName)
        moviePoster = findViewById(R.id.movieImage)
        movieTickets = findViewById(R.id.ticketCount)

        getDataFromIntent()

        setDataFromIntent()

        btn_no.setOnClickListener {
            Toast.makeText(applicationContext, "Booking Cancelled", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MovieList::class.java)
            startActivity(intent)
        }

        btn_yes.setOnClickListener {

            getCurrentUserSessionIdFromIntent()
            getUserList()
            getPositionOfLoggedInUser()
            setBookedTikcetsInfoInLoggedInUser()
            Toast.makeText(applicationContext, "Tickets Booked Successfully", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, UserBookings::class.java)
            startActivity(intent)
        }

    }

    private fun updateUserList() {
        val sharedPreferences: SharedPreferences = getSharedPreferences("Main", MODE_PRIVATE)
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
        data2 = intent.getStringExtra("moviePoster")!!
        data1 = intent.getStringExtra("movieName")!!
        data3 = intent.getIntExtra("TicketCount", -1)
    }

    private fun getCurrentUserSessionIdFromIntent() {
        val sharedPreferences2 = getSharedPreferences("LoginSession", MODE_PRIVATE)
        sessionId = sharedPreferences2.getString("userSessionId", null).toString()
    }

    fun getUserList() {
        val sharedPreferences2: SharedPreferences = getSharedPreferences("Main", MODE_PRIVATE)
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