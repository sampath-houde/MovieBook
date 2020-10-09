package com.example.Movies.mainpackage.api.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.Movies.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_movie_description.*
import kotlinx.android.synthetic.main.activity_movie_description.progressCardView
import kotlin.properties.Delegates

@Suppress("DEPRECATION")
class MovieDescription : AppCompatActivity() {

    lateinit var data1: String //movieName
    lateinit var data2: String //moviePlot
    lateinit var data4: String //moviePoster
    lateinit var data5: String //movieReleaseDate
    lateinit var data7: String //movieCount
    var tickets = 0//no of tickets

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_description)
        val imageview: ImageView = findViewById(R.id.imageView3) as ImageView
        val moviename: TextView = findViewById(R.id.movieName)
        val movierating: TextView = findViewById(R.id.rating2)
        val moviedescription: TextView = findViewById(R.id.movieDescription)

        Handler().postDelayed({
            imageCardView.visibility = View.VISIBLE
            movieNameCardView.visibility = View.VISIBLE
            plotCardView.visibility = View.VISIBLE
            progressCardView.visibility = View.INVISIBLE
            bookingCard.visibility = View.VISIBLE
            // Set Data
            rating5.setText("N/A")
            rating3.setText(data7)
            val randomTicketGenerator = (0..30).random()
            ticketsCount.setText(randomTicketGenerator.toString())
            moviename.setText(data1)
            movierating.setText("N/A")
            releaseDate2.setText(data5)
            moviedescription.setText(data2)

            if(data4=="1") {
                imageView3.setImageResource(R.drawable.ic_baseline_broken_image_24)
            } else
            {
                var s1 = ""
                for (i in 1 until data4.length) {
                    s1 = s1 + data4[i]
                }
                Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + s1)
                    .into(imageview)
            }

            bookbutton.setOnClickListener {
                tickets++
                if(tickets <= randomTicketGenerator) {
                    Snackbar.make(bookbutton, "Tickets Added to Cart: "+tickets, Snackbar.LENGTH_SHORT).show()
                }
                else {
                    Snackbar.make(bookbutton,"Limit Exceeded", Snackbar.LENGTH_SHORT).show()
                    tickets = randomTicketGenerator
                }
            }

            checkout.setOnClickListener {
                Toast.makeText(applicationContext, "Tickets added to Cart", Toast.LENGTH_SHORT).show()
                val intentBooking = Intent(this, CheckoutActivity::class.java)
                intentBooking.putExtra("movieName", data1)
                intentBooking.putExtra("moviePoster", data4)
                intentBooking.putExtra("TicketCount", tickets)
                startActivity(intentBooking)
            }


        }, 500)

        plotCardView.visibility = View.INVISIBLE
        bookingCard.visibility = View.INVISIBLE
        movieNameCardView.visibility = View.INVISIBLE
        imageCardView.visibility = View.INVISIBLE

        // Get Data
        data5 = intent.getStringExtra("movieDate")!!
        data7 = intent.getStringExtra("movieVotes")!!
        data1 = intent.getStringExtra("movieName")!!
        data2 = intent.getStringExtra("movieDescription")!!
        data4 = intent.getStringExtra("moviePoster")!!

    }
}
