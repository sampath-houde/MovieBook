package com.example.Movies.userDataBase

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import kotlin.properties.Delegates


class UserDataBase() : Serializable{

    public lateinit var user_name: String
    public lateinit var user_email: String
    public lateinit var user_phone: String
    public lateinit var user_password: String
    public  var movie_booked = ArrayList<Movie_booked>()


    class Movie_booked : Serializable{
        public lateinit var movie_name: String
        public lateinit var movie_tikcets: String
        public lateinit var movie_poster: String
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