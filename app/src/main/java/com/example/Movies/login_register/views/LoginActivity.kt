package com.example.Movies.login_register.views

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.Movies.R
import com.example.Movies.login_register.userDataBase.UserDataBase
import com.example.Movies.mainpackage.api.views.MovieList
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.reflect.Type


class LoginActivity : AppCompatActivity() {

    private lateinit var emailText:EditText
    private lateinit var passwordText: EditText

    private lateinit var  user_list: ArrayList<UserDataBase>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        passwordText = findViewById(R.id.passwordEditText)
        emailText = findViewById(R.id.emailEditText)

        btn_signup.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val i = validateCheck()
            if(i==2){loginSuccessfull()}
        }


    }

    private fun loginSuccessfull() {
            for (i in 0 until user_list.size) {
                if(user_list.get(i).user_email != emailText.text.toString())
                {
                    Snackbar.make(btn_login, "User doesn't exist", Snackbar.LENGTH_LONG).setAction("Register")
                    {
                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent)
                    }.show()
                }else if(user_list.get(i).user_password == passwordText.text.toString()){
                    val intentMovieList = Intent(this, MovieList::class.java)
                    startActivity(intentMovieList)
                } else {
                    Snackbar.make(btn_login, "Invalid Combination", Snackbar.LENGTH_LONG).show()
                }
            }
    }

    @SuppressLint("ResourceAsColor")
    private fun validateCheck() : Int{
        val sharedPreferences = getSharedPreferences("MovieFinder", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("userData", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase?>?>() {}.type
        user_list = gson.fromJson(json, type)

        var i = 0

        if(emailText.text.toString().isEmpty()) {
            emailInputLayout.error = "*Enter Email Id"
        } else if ((emailText.text.toString().contains("@") && emailText.text.toString().contains(".com")) == false) {
            emailInputLayout.error = "Enter valid email id"
        } else{i++}

        if(passwordText.text.toString().isEmpty())
        {
            passwordInputLayout.error = "*Enter Password"
        } else{i++}

        return i;
    }
}