package com.example.Movies.mainpackage.api.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.Movies.R
import com.example.Movies.databinding.FragmentMoviedescriptionBinding
import com.example.Movies.mainpackage.api.viewModel.MovieDescriptionViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.gauravk.bubblenavigation.BubbleToggleView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_moviedescription.*
import java.lang.reflect.Type

@Suppress("DEPRECATION")
class MovieDescriptionFragment : Fragment() {

    lateinit var movieDescriptionViewModel: MovieDescriptionViewModel
    val args: MovieDescriptionFragmentArgs by navArgs()
    lateinit var data1: String //movieName
    lateinit var data2: String //moviePlot
    lateinit var data6: String //movieRating
    lateinit var data4: String //moviePoster
    lateinit var data5: String //movieReleaseDate
    lateinit var data7: String //movieCount
    var tickets: Int//no of tickets

    init {
        tickets = 0
    }

    private var positionOfLoggedInUser = 0 //PositionOfLoggedInUserInList

    private var fragmentMoviedescriptionBinding: FragmentMoviedescriptionBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviedescriptionBinding.inflate(inflater, container, false)
        fragmentMoviedescriptionBinding = binding
        val view = binding.root
        movieDescriptionViewModel = ViewModelProviders.of(this).get(MovieDescriptionViewModel::class.java)

        val randomTicketGenerator = (1..30).random()

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        Handler().postDelayed({
            binding.imageCardView.visibility = View.VISIBLE
            binding.movieNameCardView.visibility = View.VISIBLE
            binding.plotCardView.visibility = View.VISIBLE
            binding.bookingCard.visibility = View.VISIBLE
            binding.materialRatingBar.visibility = View.VISIBLE
            // Set Data
            binding.rating3.setText(data7)
            binding.movieName.setText(data1)
            binding.releaseDate2.setText(data5)
            binding.movieDescription.setText(data2)
            binding.rating2.setText(data6)

            val rating = Math.round(data6.toDouble()).toInt()

            when (rating) {
                1 -> binding.materialRatingBar.setRating(0.5.toFloat())
                2 -> binding.materialRatingBar.setRating(1.0.toFloat())
                3 -> binding.materialRatingBar.setRating(1.5.toFloat())
                4 -> binding.materialRatingBar.setRating(2f)
                5 -> binding.materialRatingBar.setRating(2.5.toFloat())
                6 -> binding.materialRatingBar.setRating(3f)
                7 -> binding.materialRatingBar.setRating(3.5.toFloat())
                8 -> binding.materialRatingBar.setRating(4f)
                9 -> binding.materialRatingBar.setRating(4.5.toFloat())
                10 -> binding.materialRatingBar.setRating(5f)
                else -> binding.materialRatingBar.setRating(3f)
            }

            if (data4 == null) {
                binding.imageView3.setImageResource(R.drawable.ic_baseline_broken_image_24)
            } else {
                var s1 = ""
                for (i in 1 until data4.length) {
                    s1 = s1 + data4[i]
                }
                Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + s1)
                    .into(binding.imageView3)
            }

            binding.btnPlus.setOnClickListener {
                tickets++
                if (tickets <= randomTicketGenerator) {
                    binding.ticketsCount.setText(tickets.toString())
                } else {
                    Snackbar.make(btn_plus, "No Tickets Left", Snackbar.LENGTH_SHORT).show()
                    tickets = randomTicketGenerator
                }
            }

            binding.btnMinus.setOnClickListener {
                tickets--
                if (tickets <= randomTicketGenerator) {
                    binding.ticketsCount.setText(tickets.toString())
                } else {
                    Snackbar.make(btn_plus, "Invaid Input", Snackbar.LENGTH_SHORT).show()
                    tickets = 0
                }
            }

            binding.btnBookNow.setOnClickListener {
                if (tickets != 0) {
                    Snackbar.make(btn_bookNow, "Tickets Booked", Snackbar.LENGTH_SHORT).show()

                    movieDescriptionViewModel.getUserList()
                    movieDescriptionViewModel.getCurrentUserSessionIdFromIntent()
                    positionOfLoggedInUser = movieDescriptionViewModel.getPositionOfLoggedInUser()
                    /*val bubbleNavigationBooking  = requireActivity().findViewById<BubbleToggleView>(R.id.bubbleToggleView4)
                    val bubbleNavigationHome = requireActivity().findViewById<BubbleToggleView>(R.id.bubbleToggleView1)
*/
                    movieDescriptionViewModel.setBookedTikcetsInfoInLoggedInUser(data1, data4, tickets)
                    val action =
                        MovieDescriptionFragmentDirections.actionMovieDescriptionFragmentToUserBookingsFragment()
                    view.findNavController().navigate(action)

                } else {
                    Toast.makeText(context, "No Tickets Booked", Toast.LENGTH_SHORT)
                        .show()
                }
            }



        }, 500)

        binding.plotCardView.visibility = View.INVISIBLE
        binding.bookingCard.visibility = View.INVISIBLE
        binding.movieNameCardView.visibility = View.INVISIBLE
        binding.imageCardView.visibility = View.INVISIBLE
        binding.materialRatingBar.visibility = View.INVISIBLE

        // Get Data
        data5 = args.movieDate!!
        data7 = args.movieVotes!!
        data1 = args.movieName!!
        data2 = args.movieDescription!!
        data4 = args.moviePoster!!
        data6 = args.movieRating!!

        return view
    }

    override fun onPause() {
        super.onPause()
        tickets = 0
    }

    override fun onDestroyView() {
        fragmentMoviedescriptionBinding = null
        super.onDestroyView()
    }
}

/*private fun getCurrentUserSessionIdFromIntent() {
        val sharedPreferences = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)
        sessionId = sharedPreferences?.getString("userSessionId", null).toString()
    }*/

/*private fun setBookedTikcetsInfoInLoggedInUser() {
    val userBookedMovieInfo = UserDataBase.Movie_booked()
    userBookedMovieInfo.movie_name = data1
    userBookedMovieInfo.movie_poster = "https://image.tmdb.org/t/p/w500/" + data4
    userBookedMovieInfo.movie_tikcets = tickets.toString()

    user_data.get(positionOfLoggedInUser).movie_booked.add(userBookedMovieInfo)

    movieDescriptionViewModel.updateUserList()
}*/

/*private fun updateUserList() {
    val sharedPreferences: SharedPreferences = context?.getSharedPreferences(
        "Main",
        Context.MODE_PRIVATE
    )!!
    val editor = sharedPreferences.edit()
    val gson: Gson = Gson()
    val json: String = gson.toJson(user_data)
    editor.putString("activity", json)
    editor.apply()
}*/

/*private fun getPositionOfLoggedInUser() {
    for(i in 0 until user_data.size)
    {
        if(sessionId == user_data.get(i).user_email)
        {
            positionOfLoggedInUser = i
        }
    }
}*/

/*fun getUserList() {
    val sharedPreferences2: SharedPreferences = context?.getSharedPreferences(
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
}*/

/*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_moviedescription)
        val imageview: ImageView = findViewById(R.id.imageView3) as ImageView
        val moviename: TextView = findViewById(R.id.movieName)
        val movierating: TextView = findViewById(R.id.rating2)
        val moviedescription: TextView = findViewById(R.id.movieDescription)
        val randomTicketGenerator = (1..30).random()

        Handler().postDelayed({
            imageCardView.visibility = View.VISIBLE
            movieNameCardView.visibility = View.VISIBLE
            plotCardView.visibility = View.VISIBLE
            progressCardView.visibility = View.INVISIBLE
            bookingCard.visibility = View.VISIBLE
            // Set Data
            rating5.setText("N/A")
            rating3.setText(data7)
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

            btn_plus.setOnClickListener {
                tickets++
                if(tickets <= randomTicketGenerator) {
                    ticketsCount.setText(tickets.toString())
                }
                else {
                    Snackbar.make(btn_plus,"No Tickets Left", Snackbar.LENGTH_SHORT).show()
                    tickets = randomTicketGenerator
                }
            }

            btn_minus.setOnClickListener {
                tickets--
                if(tickets <= randomTicketGenerator) {
                    ticketsCount.setText(tickets.toString())
                }
                else
                {
                    Snackbar.make(btn_plus,"Invaid Input", Snackbar.LENGTH_SHORT).show()
                    tickets = 0
                }
            }

            btn_bookNow.setOnClickListener {
                if(tickets == 0)
                {
                    Snackbar.make(btn_bookNow, "No Tickets Booked", Snackbar.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(applicationContext, "Tickets added to Cart", Toast.LENGTH_SHORT)
                        .show()
                    val intentBooking = Intent(this, CheckoutFragment::class.java)
                    intentBooking.putExtra("movieName", data1)
                    intentBooking.putExtra("moviePoster", data4)
                    intentBooking.putExtra("TicketCount", tickets)
                    startActivity(intentBooking)
                }
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

    }*/
