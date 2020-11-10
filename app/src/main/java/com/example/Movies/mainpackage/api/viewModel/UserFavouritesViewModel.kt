package com.example.Movies.mainpackage.api.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.Movies.mainpackage.api.views.FavouritesFragment
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class UserFavouritesViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var userBookedMovieInfo: ArrayList<UserDataBase>
    private lateinit var  newUserBookedMovieInfo: ArrayList<UserDataBase.Movie_Favourites>

    private fun getMovieFavourites(): ArrayList<UserDataBase> {
        val user_data: ArrayList<UserDataBase>
        val sharedPreferences2: SharedPreferences = getApplication<Application>().getSharedPreferences("Main",
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
        return user_data
    }

    fun getPositionOfCurrentLoggedInUser(): Int {
        var positionOfCurrentLoggedIn = -1
        userBookedMovieInfo = getMovieFavourites()
        val sessionId = getCurrentUserSessionId()
        userBookedMovieInfo = getMovieFavourites()
        for(i in 0 until userBookedMovieInfo.size)
        {
            if(sessionId == userBookedMovieInfo.get(i).user_email)
            {
                positionOfCurrentLoggedIn = i
            }
        }
        return positionOfCurrentLoggedIn
    }

    private fun getCurrentUserSessionId(): String {
        val sessionId: String
        val sharedPreferences2 = getApplication<Application>().getSharedPreferences("LoginSession", Context.MODE_PRIVATE)!!
        sessionId = sharedPreferences2.getString("userSessionId", null).toString()
        return sessionId
    }

    fun setDataToRecycler3Adapter(favouritesFragment: FavouritesFragment) {
        newUserBookedMovieInfo = userBookedMovieInfo.get(getPositionOfCurrentLoggedInUser()).movie_favourites
        favouritesFragment.setDataToRecycler(newUserBookedMovieInfo)
    }

}