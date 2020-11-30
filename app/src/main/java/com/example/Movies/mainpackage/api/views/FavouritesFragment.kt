package com.example.Movies.mainpackage.api.views

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
import com.example.Movies.databinding.FragmentFavouritesBinding
import com.example.Movies.mainpackage.api.adapter.Adapter4
import com.example.Movies.mainpackage.api.viewModel.UserFavouritesViewModel
import com.example.Movies.userDataBase.UserDataBase

@Suppress("DEPRECATION")
class FavouritesFragment : Fragment(){

    lateinit var recyclerView: RecyclerView

    private lateinit var myAdapter: Adapter4

    private lateinit var sessionId: String

    private lateinit var progressBar: SweetAlertDialog

    private var positionOfCurrentLoggedIn: Int = 0

    private lateinit var  user_data: ArrayList<UserDataBase>

    private var fragmentFavouritesBinding: FragmentFavouritesBinding? = null

    private lateinit var  newUserFavourites: ArrayList<UserDataBase.Movie_Favourites>

    private lateinit var userFavouritesViewModel: UserFavouritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        fragmentFavouritesBinding = binding
        val view= binding.root
        userFavouritesViewModel = ViewModelProviders.of(this).get(UserFavouritesViewModel::class.java)

        recyclerView = binding.RecyclerView4


        userFavouritesViewModel.getPositionOfCurrentLoggedInUser()
        userFavouritesViewModel.setDataToRecycler3Adapter(this)

        return view
    }

    fun setDataToRecycler(newUserFavourites: ArrayList<UserDataBase.Movie_Favourites>) {
        progressBar = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        progressBar.hideConfirmButton().setTitleText("Loading...").show()
        Handler().postDelayed({
            myAdapter = Adapter4(context, newUserFavourites)
            myAdapter.notifyDataSetChanged()

            recyclerView.adapter = myAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)
            progressBar.hide()
        }, 400)

    }

    override fun onDestroyView() {
        fragmentFavouritesBinding = null
        super.onDestroyView()
    }
}

/*private fun getMovieFavourites(): ArrayList<UserDataBase> {
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
    }*/

/*private fun getCurrentUserSessionId() {
    val sharedPreferences2 = context?.getSharedPreferences("LoginSession", Context.MODE_PRIVATE)!!
    sessionId = sharedPreferences2.getString("userSessionId", null).toString()
}*/