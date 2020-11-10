package com.example.Movies.mainpackage.api.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MovieDescriptionViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var user_data: ArrayList<UserDataBase>
    private var positionOfLoggedInUser = -1

    fun getCurrentUserSessionIdFromIntent(): String {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        val sessionId = sharedPreferences?.getString("userSessionId", null).toString()
        return sessionId
    }

    fun getUserList() {
        val sharedPreferences2: SharedPreferences = getApplication<Application>().getSharedPreferences(
            "Main",
            Context.MODE_PRIVATE
        )!!
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if(json==null){
            user_data=ArrayList<UserDataBase>()
        }else{
            user_data = gson.fromJson(json, type)
        }
    }

    fun setBookedTikcetsInfoInLoggedInUser(data1: String, data4: String, tickets: Int) {
        val userBookedMovieInfo = UserDataBase.Movie_booked()
        userBookedMovieInfo.movie_name = data1
        userBookedMovieInfo.movie_poster = "https://image.tmdb.org/t/p/w500/" + data4
        userBookedMovieInfo.movie_tikcets = tickets.toString()

        user_data.get(positionOfLoggedInUser).movie_booked.add(userBookedMovieInfo)

        updateUserList()
    }

    fun updateUserList() {
        val sharedPreferences: SharedPreferences = getApplication<Application>().getSharedPreferences(
            "Main",
            Context.MODE_PRIVATE
        )!!
        val editor = sharedPreferences.edit()
        val gson: Gson = Gson()
        val json: String = gson.toJson(user_data)
        editor.putString("activity", json)
        editor.apply()
    }

    fun getPositionOfLoggedInUser(): Int {

        val sessionId = getCurrentUserSessionIdFromIntent()
        for(i in 0 until user_data.size)
        {
            if(sessionId == user_data.get(i).user_email)
            {
                positionOfLoggedInUser = i
                break
            }
        }
        return positionOfLoggedInUser
    }

}