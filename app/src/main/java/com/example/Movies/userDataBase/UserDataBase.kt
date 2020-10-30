package com.example.Movies.userDataBase

import android.os.Bundle
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.io.StringReader
import kotlin.properties.Delegates


class UserDataBase() : Serializable {

    lateinit var user_name: String
    lateinit var user_email: String
    lateinit var user_phone: String
    lateinit var user_password: String
    var movie_booked = ArrayList<Movie_booked>()
    var movie_favourites = ArrayList<Movie_Favourites>();

    class Movie_booked : Serializable {
        lateinit var movie_name: String
        lateinit var movie_tikcets: String
        lateinit var movie_poster: String
    }

    class Movie_Favourites : Serializable {
        lateinit var movie_Rating: String
        var id: Int = -1
        var wishlist: Boolean = false
        lateinit var movie_name: String
        lateinit var movie_date: String
        lateinit var movie_showType: String
        lateinit var movie_Votes: String
        lateinit var movie_Poster: String
        lateinit var movie_Description: String
    }
    /* private var user_name: String=""
         get() = field
         set(value){field=value}

      private var user_email: String =""
      get() = field
      set(value){field=value}

      private var user_phone: String = ""
          get() = field
          set(value) {field=value}

      private  var user_password: String =""
      get()=field
      set(value){field=value}*/


    /*public fun getUser_name(): String {
        return user_name
    }

    public fun setuser_name(user_name:String){this.user_name = user_name}

    private fun getUser_email(): String {
        return user_email
    }
    private fun setUser_email(user_email:String){this.user_email = user_email}



    fun getUser_phone(): String{
        return user_phone
    }
    fun setUser_phone(user_phone:String){this.user_phone = user_phone}


    fun getUser_password(): String {
        return user_password
    }
    fun setUser_password(user_password:String){this.user_password = user_password}*/
}
