package com.example.Movies.mainpackage.api.views

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.Movies.R
import com.example.Movies.login_register.views.RegisterActivity
import com.example.Movies.userDataBase.UserDataBase
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import java.lang.reflect.Type

class UserProfile : AppCompatActivity() {

    private lateinit var user_data: ArrayList<UserDataBase>
    private lateinit var profile_name: TextView
    private lateinit var profile_email: TextView
    private lateinit var profile_number: TextView
    private lateinit var sessionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        profile_name = findViewById(R.id.profileName)
        profile_email = findViewById(R.id.profileEmail)
        profile_number = findViewById(R.id.profileNumber)

        getCurrentUserSessionId()

        getUserList()

        fetchDetailsOfCurrentUser()

        myBookings.setOnClickListener {
            val intent = Intent(this, UserBookings::class.java)
            startActivity(intent)
        }
    }

    fun getUserList() {
        val sharedPreferences2: SharedPreferences = getSharedPreferences("Main", MODE_PRIVATE)
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
        val sharedPreferences2 = getSharedPreferences("LoginSession", MODE_PRIVATE)
        sessionId = sharedPreferences2.getString("userSessionId", null).toString()
    }

}