package com.example.Movies.mainpackage.api.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.Movies.R
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlin.properties.Delegates

class CheckoutActivity : AppCompatActivity() {

    private lateinit var movieName: TextView
    private lateinit var moviePoster: ImageView
    private lateinit var movieTickets: TextView

    private lateinit var data2: String  //MoviePoster
    private lateinit var data1: String  //MovieName
    private var data3 by Delegates.notNull<Int>()  //MovieTickets

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
}