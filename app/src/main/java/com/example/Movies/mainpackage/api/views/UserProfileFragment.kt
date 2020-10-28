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
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.Movies.R
import com.example.Movies.login_register.views.LoginFragment
import com.example.Movies.login_register.views.LoginRegisterActivity
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_userprofile.*
import kotlinx.android.synthetic.main.fragment_userprofile.view.*
import java.lang.reflect.Type

class UserProfileFragment : Fragment() {

    private lateinit var user_data: ArrayList<UserDataBase>
    private lateinit var profile_name: TextView
    private lateinit var profile_email: TextView
    private lateinit var profile_number: TextView
    private lateinit var sessionId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_userprofile, container, false)

        profile_name = view.findViewById(R.id.profileName)
        profile_email = view.findViewById(R.id.profileEmail)
        profile_number = view.findViewById(R.id.profileNumber)

        getCurrentUserSessionId()

        getUserList()

        fetchDetailsOfCurrentUser()

        view.myBookings.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.userBookingsFragment)
        }

        view.btn_logout.setOnClickListener {
            Toast.makeText(context, "Logout Successful", Toast.LENGTH_SHORT).show()
            setUserLoginStatus()
            val intent = Intent(context, LoginRegisterActivity::class.java)
            startActivity(intent)
        }

        view.my_toolbar.setTitle("Profile")
        view.my_toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        view.my_toolbar.setNavigationOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }

    private fun setUserLoginStatus() {
        val loginStatus = context?.getSharedPreferences("LoginSession", MODE_PRIVATE)!!
        val editor: SharedPreferences.Editor = loginStatus.edit()
        editor.putBoolean("userLoginStatus", false)
        editor.apply()
    }

    fun getUserList() {
        val sharedPreferences2 = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if(json==null){
            user_data=ArrayList<UserDataBase>()
        }else{

            user_data = gson.fromJson(json, type)
        }
    }

    private fun fetchDetailsOfCurrentUser() {
        for (i in 0 until user_data.size) {
            if (user_data.get(i).user_email == sessionId) {
                profile_name.text = user_data.get(i).user_name
                profile_email.text = user_data.get(i).user_email
                profile_number.text = user_data.get(i).user_phone
            }
        }
    }

    private fun getCurrentUserSessionId() {
        val sharedPreferences2 = activity?.getSharedPreferences("LoginSession", MODE_PRIVATE)!!
        sessionId = sharedPreferences2.getString("userSessionId", null).toString()
    }

}


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