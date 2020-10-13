package com.example.Movies

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.Movies.login_register.views.LoginActivity
import com.example.Movies.mainpackage.api.views.MovieList
import kotlin.math.log

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginStatus = getUserSessionId()

        Handler().postDelayed({
            if(loginStatus == false) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent2 = Intent(this, MovieList::class.java)
                startActivity(intent2)
            }
        }, 2000)


    }

    private fun getUserSessionId() : Boolean{
        val loginPreference = getSharedPreferences("LoginSession", MODE_PRIVATE)
        val a = loginPreference.getBoolean("userLoginStatus", false)
        return a
    }
}