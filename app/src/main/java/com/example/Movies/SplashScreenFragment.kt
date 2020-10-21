package com.example.Movies

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.Movies.login_register.views.LoginFragment
import com.example.Movies.mainpackage.api.views.MovieListFragment

@Suppress("DEPRECATION")
class SplashScreenFragment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginStatus = getUserSessionId()

        Handler().postDelayed({
            if(loginStatus == false) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val intent2 = Intent(this, MainActivity::class.java)
                startActivity(intent2)
            }
        }, 2000)


    }

    private fun getUserSessionId() : Boolean{
        val loginPreference = getSharedPreferences("LoginSession", MODE_PRIVATE)
        val a = loginPreference?.getBoolean("userLoginStatus", false)
        return a!!
    }

    /*override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splashscreen, container, false)

        val loginStatus = getUserSessionId()

        Handler().postDelayed({
            if(loginStatus == false) {
                val intent = Intent(this, LoginFragment::class.java)
                startActivity(intent)
            } else {
                val intent2 = Intent(this, MovieListFragment::class.java)
                startActivity(intent2)
            }
        }, 2000)

        return view
    }*/
}