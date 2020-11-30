package com.example.Movies.mainpackage.api

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.Movies.R
import com.example.Movies.login_register.views.LoginRegisterActivity
import com.example.Movies.mainpackage.api.views.*
import com.gauravk.bubblenavigation.BubbleNavigationConstraintView
import com.gauravk.bubblenavigation.BubbleNavigationLinearView
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener
import com.google.android.material.navigation.NavigationView
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import com.yarolegovich.slidingrootnav.transform.CompositeTransformation
import kotlinx.android.synthetic.main.fragment_movietrending.*

class MainActivity : AppCompatActivity() {

    private lateinit var bubbleNavigationConstraintView: BubbleNavigationConstraintView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bubbleNavigationConstraintView = findViewById(R.id.bubbleNavigationBar)
        bubbleNavigationConstraintView.setNavigationChangeListener { view, position ->

            when (position) {
                0 -> {
                    Navigation.findNavController(this, R.id.fragment2)
                        .navigate(R.id.movieListFragment)
                }

                1 -> {
                    Navigation.findNavController(this, R.id.fragment2)
                        .navigate(R.id.movieSearchFragment)

                }

                2 -> {
                    Navigation.findNavController(this, R.id.fragment2)
                        .navigate(R.id.favouritesFragment)
                }

                3 -> {
                    Navigation.findNavController(this, R.id.fragment2)
                        .navigate(R.id.userBookingsFragment)
                }

                4 -> {
                    Navigation.findNavController(this, R.id.fragment2)
                        .navigate(R.id.userProfileFragment)
                }
            }

        }

    }

    private var pressedTime: Long = 0

    /*override fun onBackPressed() {

            val fragmentManager = supportFragmentManager.findFragmentById(R.id.fragment2)
        if(fragmentManager is MovieSearchFragment || fragmentManager is MovieDescriptionFragment
            || fragmentManager is FavouritesFragment || fragmentManager is UserProfileFragment
            || fragmentManager is UserBookingsFragment) {
            Navigation.findNavController(this, R.id.fragment2).navigate(R.id.movieListFragment)
        } else {
            super.onBackPressed()
        }
    }*/

}


