package com.example.Movies.mainpackage.api.views

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.Movies.R
import com.example.Movies.databinding.FragmentFavouritesBinding
import com.example.Movies.mainpackage.api.adapter.Adapter4
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_favourites.view.*
import java.lang.reflect.Type

class FavouritesFragment : Fragment(){

    lateinit var recyclerView: RecyclerView

    private lateinit var myAdapter: Adapter4

    private lateinit var sessionId: String

    private var positionOfCurrentLoggedIn: Int = 0

    private lateinit var  user_data: ArrayList<UserDataBase>

    private var fragmentFavouritesBinding: FragmentFavouritesBinding? = null

    private lateinit var  newUserFavourites: ArrayList<UserDataBase.Movie_Favourites>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        fragmentFavouritesBinding = binding
        val view = binding.root

        recyclerView = binding.RecyclerView4
        getCurrentUserSessionId()

        user_data = getMovieFavourites()

        for(i in 0 until user_data.size)
        {
            if(sessionId == user_data.get(i).user_email)
            {
                positionOfCurrentLoggedIn = i
            }
        }

        binding.myToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.myToolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
        binding.myToolbar.setTitle("Favourites")

        newUserFavourites = user_data.get(positionOfCurrentLoggedIn).movie_favourites
        setDataToRecycler(newUserFavourites)
        return view
    }

    override fun onDestroyView() {
        fragmentFavouritesBinding = null
        super.onDestroyView()
    }

    private fun setDataToRecycler(newUserFavourites: ArrayList<UserDataBase.Movie_Favourites>) {
        myAdapter = Adapter4(context, newUserFavourites)
        myAdapter.notifyDataSetChanged()

        recyclerView.adapter = myAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun getMovieFavourites(): ArrayList<UserDataBase> {
        val user_data: ArrayList<UserDataBase>
        val sharedPreferences2: SharedPreferences = context?.getSharedPreferences("Main",
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
        return user_data
    }

    private fun getCurrentUserSessionId() {
        val sharedPreferences2 = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)!!
        sessionId = sharedPreferences2.getString("userSessionId", null).toString()
    }
}
