package com.example.Movies.mainpackage.api.views

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.Movies.R
import com.example.Movies.databinding.FragmentUserbookingsBinding
import com.example.Movies.mainpackage.api.adapter.MyAdapter3
import com.example.Movies.mainpackage.api.viewModel.UserBookingsViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@Suppress("DEPRECATION")
class UserBookingsFragment : Fragment() {

    private lateinit var myAdapter3: MyAdapter3

    private lateinit var recyclerView: RecyclerView

    private lateinit var sessionId: String

    private lateinit var progressBar: SweetAlertDialog

    private var fragmentUserbookingsBinding: FragmentUserbookingsBinding? = null

    private var positionOfCurrentLoggedIn: Int = 0

    private lateinit var  userBookedMovieInfo: ArrayList<UserDataBase>

    private lateinit var  newUserBookedMovieInfo: ArrayList<UserDataBase.Movie_booked>

    private lateinit var userBookingsViewModel: UserBookingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserbookingsBinding.inflate(inflater, container, false)
        fragmentUserbookingsBinding = binding
        val view = binding.root
        userBookingsViewModel = ViewModelProviders.of(this).get(UserBookingsViewModel::class.java)

        recyclerView = binding.recyclerView3

        userBookingsViewModel.getPositionOfCurrentLoggedInUser()
        userBookingsViewModel.setDataToRecycler3Adapter(this)

        return view
    }

     fun setDataToRecycle3(userMovieBookedList: ArrayList<UserDataBase.Movie_booked>) {
         progressBar = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
         progressBar.hideConfirmButton().setTitleText("Loading...").show()
         Handler().postDelayed({
             myAdapter3 = MyAdapter3(context, userMovieBookedList)
             recyclerView.adapter = myAdapter3
             recyclerView.layoutManager = LinearLayoutManager(context)
             progressBar.hide()
         }, 400)

    }

}

/*private fun getCurrentUserSessionId() {
        val sharedPreferences2 = context?.getSharedPreferences("LoginSession", MODE_PRIVATE)!!
        sessionId = sharedPreferences2.getString("userSessionId", null).toString()
    }*/

/*private fun getMovieBookedList(): ArrayList<UserDataBase> {
        val user_data: ArrayList<UserDataBase>
        val sharedPreferences2: SharedPreferences = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if(json==null){
            user_data=ArrayList<UserDataBase>()
        }else{

            user_data = gson.fromJson(json, type)
        }
        return user_data
    }*/

/*override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_userbookings)

    recyclerView = findViewById(R.id.recyclerView3)

    userBookedMovieInfo = getMovieBookedList()

    getCurrentUserSessionId()

    for(i in 0 until userBookedMovieInfo.size)
    {
        if(sessionId == userBookedMovieInfo.get(i).user_email)
        {
            positionOfCurrentLoggedIn = i
        }
    }

    newUserBookedMovieInfo = userBookedMovieInfo.get(positionOfCurrentLoggedIn).movie_booked

    setDataToRecycle3(newUserBookedMovieInfo)

    returnHome.setOnClickListener {
        val intent = Intent(this, MovieTrendingFragment::class.java)
        startActivity(intent)
    }
}
*/