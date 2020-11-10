package com.example.Movies.mainpackage.api.views

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movies.R
import com.example.Movies.databinding.FragmentMoviesearchBinding
import com.example.Movies.mainpackage.api.adapter.MyAdapter2
import com.example.Movies.mainpackage.api.model.MovieSearchList
import com.example.Movies.mainpackage.api.viewModel.MovieSearchViewModel

@Suppress("DEPRECATION")
class MovieSearchFragment : Fragment() {

    private lateinit var progrressCardView: CardView
    private lateinit var progressBar: ProgressBar
    private lateinit var progressTextView: TextView
    private lateinit var proressLayout: ConstraintLayout
    private lateinit var recyclerView: RecyclerView
    private var fragmentMoviesearchBinding: FragmentMoviesearchBinding? = null
    private lateinit var movieSearchViewModel: MovieSearchViewModel
    private lateinit var myAdapter: MyAdapter2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMoviesearchBinding.inflate(inflater, container, false)
        fragmentMoviesearchBinding = binding
        val view = binding.root
        movieSearchViewModel = ViewModelProviders.of(this).get(MovieSearchViewModel::class.java)

        progressTextView = binding.connecting
        proressLayout = binding.progressConstraint
        progressBar = binding.progressBar
        progrressCardView = binding.progressCardView
        recyclerView = binding.recyclerView2

        binding.searchButton.setOnClickListener {

            if (binding.searchEditText.text.toString().trim().isEmpty()) {
                Toast.makeText(context, "Enter Movie Name", Toast.LENGTH_SHORT).show()
            } else {
                progrressCardView.visibility = View.VISIBLE
                Handler().postDelayed({

                    val searchQuery = binding.searchEditText.text.toString()
                    movieSearchViewModel.getSearchMoviesList(searchQuery, this)
                    binding.searchEditText.setText("")

                }, 2000)
            }

        }

        binding.myToolbar.setTitle("Search Movie")
        binding.myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.myToolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }

    fun onFailedApiCall() {
        Toast.makeText(context, "Connection Error!!", Toast.LENGTH_SHORT)
            .show()
        progrressCardView.visibility = View.INVISIBLE
    }

    fun setDataToRecycler2(body: MovieSearchList) {
        progrressCardView.visibility = View.INVISIBLE

        myAdapter = MyAdapter2(context, body.results2)
        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onDestroyView() {
        fragmentMoviesearchBinding = null
        super.onDestroyView()
    }

}

/*fun getSearchMoviesList(searchQuery: String) {

       val getOMDBapi2: OMDBapi = RetrofitInstance.getService()
       val call2: Call<MovieSearchList>? = getOMDBapi2.getSearchMovie(searchQuery)


       call2?.enqueue(object : Callback<MovieSearchList> {
           override fun onResponse(
               call2: Call<MovieSearchList>?,
               response2: Response<MovieSearchList>?,
           ) {
               progrressCardView.visibility = View.INVISIBLE
               if(response2!!.body().totalResults2 == 0)
               {
                   Toast.makeText(context, "No Movie Found", Toast.LENGTH_SHORT).show()
               }
               else
               {
                   setDataToRecycler2(response2!!.body())
               }
           }

           override fun onFailure(call2: Call<MovieSearchList>?, t: Throwable?) {
               Toast.makeText(context, "Connection Error!!", Toast.LENGTH_SHORT)
                   .show()
           }

       })
   }*/


/*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_moviesearch)

        progressTextView = findViewById(R.id.connecting)
        proressLayout = findViewById(R.id.progressConstraint)
        progressBar = findViewById(R.id.progressBar)
        progrressCardView = findViewById(R.id.progressCardView)
        recyclerView = findViewById(R.id.recyclerView2)


        search_button.setOnClickListener {

            progrressCardView.visibility = View.VISIBLE
            Handler().postDelayed({

                val searchQuery = searchEditText.text.toString()
                getSearchMoviesList(searchQuery)
                searchEditText.setText("")

            }, 2000)

        }

    }*/

