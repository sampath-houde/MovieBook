package com.example.Movies.mainpackage.api.views

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.example.Movies.R
import com.example.Movies.databinding.FragmentUserprofileBinding
import com.example.Movies.login_register.views.LoginRegisterActivity
import com.example.Movies.mainpackage.api.viewModel.UserProfileViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.google.android.material.textfield.TextInputLayout

@Suppress("DEPRECATION")
class UserProfileFragment : Fragment() {

    private lateinit var user_data: ArrayList<UserDataBase>
    private lateinit var profile_name: TextView
    private lateinit var profile_email: TextView
    private lateinit var profile_number: TextView
    private lateinit var nameLayout: TextInputLayout
    private lateinit var emailLayout: TextInputLayout
    private lateinit var phoneLayout: TextInputLayout
    private var currentLoggedUser: Int = 0
    private var userprofileBinding: FragmentUserprofileBinding? = null
    private lateinit var userProfileViewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentUserprofileBinding.inflate(inflater, container, false)
        userprofileBinding = binding
        val view = binding.root

        profile_name = binding.nameEdit
        profile_email = binding.emailEdit
        profile_number = binding.phoneEdit
        nameLayout = binding.nameEditTextLayout
        emailLayout = binding.emailEditTextLayout
        phoneLayout = binding.phoneEditTextLayout
        userProfileViewModel = ViewModelProviders.of(this).get(UserProfileViewModel::class.java)

        user_data = userProfileViewModel.getUserList()

        currentLoggedUser = userProfileViewModel.getCurrentUserSessionId()

        fetchDetailsOfCurrentUser()

        /*binding.btnBookings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.userBookingsFragment)
        }

        binding.btnWishlist.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.favouritesFragment)
        }*/

        binding.btnLogout.setOnClickListener {
            Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
            userProfileViewModel.setUserLoginStatus()
            val intent = Intent(context, LoginRegisterActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


        binding.btnUpdate.setOnClickListener {
            updateDataOfTheUser()
        }
        return view
    }

    private fun updateDataOfTheUser() {
        var flag = 0
        if (profile_name.text.toString().isEmpty()) {
            nameLayout.error = "*Enter Name"
        } else {
            user_data.get(currentLoggedUser).user_name = profile_name.text.toString()
            flag++
        }

        if (profile_email.text.toString().isEmpty() || !profile_email.text.toString()
                .contains("@") || !profile_email.text.toString().contains(".com")
        ) {
            emailLayout.error = "*Enter valid Email Id"
        } else {
            user_data.get(currentLoggedUser).user_email = profile_email.text.toString()
            flag++
        }

        if (profile_email.text.toString()
                .isEmpty() || profile_number.text.toString().length != 10
        ) {
            emailLayout.error = "*Enter valid Email Id"
        } else {
            user_data.get(currentLoggedUser).user_phone = profile_number.text.toString()
            flag++
        }

        if (flag == 3) {
            Toast.makeText(context, "Updated User Data", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(userprofileBinding!!.root).navigate(R.id.movieListFragment)
            userProfileViewModel.updateUserList(user_data)
        }
    }


    private fun fetchDetailsOfCurrentUser() {
        profile_name.text = user_data.get(currentLoggedUser).user_name
        profile_email.text = user_data.get(currentLoggedUser).user_email
        profile_number.text = user_data.get(currentLoggedUser).user_phone
        userprofileBinding?.headerName?.text = user_data.get(currentLoggedUser).user_name
        userprofileBinding?.headerUserName?.text =
            user_data.get(currentLoggedUser).user_name.toLowerCase().replace(" ", "_")
        userprofileBinding?.wishlistNumber?.text =
            user_data.get(currentLoggedUser).movie_favourites.size.toString()
        userprofileBinding?.bookingNumber?.text =
            user_data.get(currentLoggedUser).movie_booked.size.toString()
    }

}

    /*fun updateUserList(){
        val sharedPreferences = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val editor = sharedPreferences.edit()
        val gson: Gson = Gson()
        val json: String = gson.toJson(user_data)
        editor.putString("activity", json)
        editor.apply()
    }*/

    /*private fun setUserLoginStatus() {
        val loginStatus = context?.getSharedPreferences("LoginSession", MODE_PRIVATE)!!
        val editor: SharedPreferences.Editor = loginStatus.edit()
        editor.putBoolean("userLoginStatus", false)
        editor.apply()
    }*/

    /*fun getUserList() {
        val sharedPreferences2 = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if(json==null){
            user_data=ArrayList<UserDataBase>()
        }else{

            user_data = gson.fromJson(json, type)
        }
    }*/

    /*private fun getCurrentUserSessionId() {
        val sessionId: String
        val sharedPreferences3 = activity?.getSharedPreferences("LoginSession", MODE_PRIVATE)!!
        sessionId = sharedPreferences3.getString("userSessionId", null).toString()
        for(i in 0 until user_data.size) {
            if(sessionId == user_data.get(i).user_email) {
                currentLoggedUser = i;
                break
            }
        }
    }*/

    /*override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_userprofile)

    profile_name = findViewById(R.id.profileName)
    profile_email = findViewById(R.id.profileEmail)
    profile_number = findViewById(R.id.profileNumber)

    getCurrentUserSessionId()

    getUserList()

    fetchDetailsOfCurrentUser()

    myBookings.setOnClickListener {
        val intent = Intent(this, UserBookingsFragment::class.java)
        startActivity(intent)
    }

    btn_logout.setOnClickListener {
        Toast.makeText(applicationContext, "Logout Successful", Toast.LENGTH_SHORT).show()
        setUserLoginStatus()
        val intentLogout = Intent(this, LoginFragment::class.java)
        startActivity(intentLogout)
    }
}
*/