package com.example.Movies.mainpackage.api.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movies.R
import com.example.Movies.mainpackage.api.adapter.MyAdapter3
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_user_bookings.*
import java.lang.reflect.Type

class UserBookings : AppCompatActivity() {

    private lateinit var myAdapter3: MyAdapter3

    private lateinit var recyclerView: RecyclerView

    private lateinit var sessionId: String

    private var positionOfCurrentLoggedIn: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_bookings)

        recyclerView = findViewById(R.id.recyclerView3)

        val userBookedMovieInfo = getMovieBookedList()

        getCurrentUserSessionId()

        for(i in 0 until userBookedMovieInfo.size)
        {
            if(sessionId == userBookedMovieInfo.get(i).user_email)
            {
                positionOfCurrentLoggedIn = i
            }
        }

        val newUserBookedMovieInfo = userBookedMovieInfo.get(positionOfCurrentLoggedIn).movie_booked

        setDataToRecycle3(newUserBookedMovieInfo)

        returnHome.setOnClickListener {
            val intent = Intent(this, MovieList::class.java)
            startActivity(intent)
        }

    }

    private fun getMovieBookedList(): ArrayList<UserDataBase> {
        val user_data: ArrayList<UserDataBase>
        val sharedPreferences2: SharedPreferences = getSharedPreferences("Main", MODE_PRIVATE)
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
        myAdapter3 = MyAdapter3(this, userMovieBookedList)
        val intent = Intent(this, MovieDescription::class.java)
        recyclerView.adapter = myAdapter3
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getCurrentUserSessionId() {
        val sharedPreferences2 = getSharedPreferences("LoginSession", MODE_PRIVATE)
        sessionId = sharedPreferences2.getString("userSessionId", null).toString()
    }
}