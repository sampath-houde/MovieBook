package com.example.Movies.login_register.views

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.Movies.R
import com.example.Movies.userDataBase.UserDataBase
import com.example.Movies.mainpackage.api.views.MovieListFragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.reflect.Type


class LoginFragment : Fragment() {

    private lateinit var emailText:EditText
    private lateinit var passwordText: EditText

    private lateinit var userEmpty_list: ArrayList<UserDataBase>

    private  var loginStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)
        passwordText = findViewById(R.id.passwordEditText)
        emailText = findViewById(R.id.emailEditText)

        btn_signup.setOnClickListener {
            val intent = Intent(this, RegisterFragment::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {
            val i = validateCheck()
            if(i==2){loginSuccessfull()}
        }

        btn_forgot.setOnClickListener {
            val intent = Intent(this, ForgotPassword1Fragment::class.java)
            startActivity(intent)
        }
    }

    private fun loginSuccessfull() {
        getUserList()
            for (i in 0 until userEmpty_list.size) {
                if (userEmpty_list.get(i).user_email != emailText.text.toString()) {
                    Snackbar.make(btn_login, "User doesn't exist", Snackbar.LENGTH_LONG)
                        .setAction("Register")
                        {
                            val intent = Intent(this, RegisterFragment::class.java)
                            startActivity(intent)
                        }.show()
                } else if (userEmpty_list.get(i).user_password == passwordText.text.toString()) {
                    Toast.makeText(applicationContext, "Login Successfull", Toast.LENGTH_SHORT)
                        .show()
                    setUserSessionIdAndLoginStatus()
                    val intentMovieList = Intent(this, MovieListFragment::class.java)
                    startActivity(intentMovieList)
                } else {
                    Snackbar.make(btn_login, "Invalid Combination", Snackbar.LENGTH_LONG).show()
                }
            }
        }


    private fun setUserSessionIdAndLoginStatus() {
        if(materialCheckBox.isChecked == true){loginStatus = true} else{loginStatus == false}
        val sharedPreferences2: SharedPreferences =getSharedPreferences("LoginSession", MODE_PRIVATE)
        val editor2: SharedPreferences.Editor = sharedPreferences2.edit()
        editor2.putString("userSessionId", emailText.text.toString())
        editor2.putBoolean("userLoginStatus", loginStatus)
        editor2.apply()
    }

    @SuppressLint("ResourceAsColor")
    private fun validateCheck() : Int{
        var i = 0

        if(emailText.text.toString().isEmpty()) {
            emailInputLayout.error = "*Enter Email Id"
        }
        else if ((emailText.text.toString().contains("@") && emailText.text.toString().contains(".com")) == false) {
            emailInputLayout.error = "Enter valid email id"
        }
        else{i++}

        if(passwordText.text.toString().isEmpty())
        {
            passwordInputLayout.error = "*Enter Password"
        } else{i++}

        return i;
    }

    fun getUserList() {
        val sharedPreferences2: SharedPreferences = getSharedPreferences("Main", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if(json==null){
            userEmpty_list=ArrayList<UserDataBase>()
        }else{

            userEmpty_list = gson.fromJson(json, type)
        }
    }

}