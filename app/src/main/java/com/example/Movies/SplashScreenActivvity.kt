package com.example.Movies

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import androidx.navigation.Navigation
import com.example.Movies.login_register.views.LoginRegisterActivity
import com.example.Movies.mainpackage.api.MainActivity
import com.example.Movies.mainpackage.api.views.MovieTrendingFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_splashscreen.*

@Suppress("DEPRECATION")
class SplashScreenActivvity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        supportActionBar?.hide()
        Handler().postDelayed({
            val loginStatus = getUserSessionId()
            if(loginStatus == false) {
                val intent = Intent(this, LoginRegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
            else if(loginStatus)
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 2000)

    }
    private fun getUserSessionId() : Boolean {
        val loginPreference = getSharedPreferences("LoginSession", MODE_PRIVATE)
        val a = loginPreference.getBoolean("userLoginStatus", false)
        return a
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