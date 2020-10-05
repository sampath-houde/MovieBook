package com.example.Movies.login_register.views

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.Movies.R
import com.example.Movies.login_register.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.reflect.Type


class LoginActivity : AppCompatActivity() {

    private lateinit var emailText:EditText

    private lateinit var  user_list: List<UserDataBase>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailText = findViewById(R.id.emailEditText)

        btn_signup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            validateCheck()
        }


    }

    private fun validateCheck() {
        val sharedPreferences = getSharedPreferences("MovieFinder", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("userData", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase?>?>() {}.type
        user_list = gson.fromJson(json, type)



    }
}