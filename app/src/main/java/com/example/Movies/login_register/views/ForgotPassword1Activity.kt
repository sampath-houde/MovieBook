package com.example.Movies.login_register.views

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.Movies.R
import com.example.Movies.mainpackage.api.views.MovieList
import com.example.Movies.userDataBase.UserDataBase
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_forgotpassword1.*
import kotlinx.android.synthetic.main.activity_forgotpassword1.emailInputLayout
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.reflect.Type

class ForgotPassword1Activity : AppCompatActivity() {

    private lateinit var phoneText: TextView
    private lateinit var emailText: TextView
    private lateinit var userEmpty_list: ArrayList<UserDataBase>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgotpassword1)

        phoneText = findViewById(R.id.phoneEditText)
        emailText = findViewById(R.id.emailEditText)


        btn_next.setOnClickListener {
            userEmpty_list = getUserList()
            val errors: Int = checkUserValidity()
            if (errors == 2) {
                checkSucessfull(userEmpty_list)
            }
        }

    }

    private fun checkSucessfull(useremptyList: ArrayList<UserDataBase>) {
        for (i in 0 until useremptyList.size) {
            if (useremptyList.get(i).user_email != emailText.text.toString() || useremptyList.get(i).user_phone != phoneText.text.toString()) {
                Snackbar.make(btn_next, "User doesn't exist", Snackbar.LENGTH_LONG)
                    .setAction("Register")
                    {
                        val intent = Intent(this, RegisterActivity::class.java)
                        startActivity(intent)
                    }.show()
            } else if (useremptyList.get(i).user_phone == phoneText.text.toString() && useremptyList.get(i).user_email == emailText.text.toString()) {
                Toast.makeText(applicationContext, "User Found", Toast.LENGTH_SHORT)
                    .show()
                val intent = Intent(this, ForgotPasswordActivity2::class.java)
                intent.putExtra("key", i)
                startActivity(intent)
            } else {
                Snackbar.make(btn_next, "Invalid Combination", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUserValidity(): Int {

        var i = 0

        if (phoneText.text.toString().isEmpty()) {
            phoneEditTextLayout.error = "*Enter Contact Number"
        } else if (phoneText.text.toString().length != 10) {
            phoneEditTextLayout.error = "Invalid Contact Number"
            phoneText.text = ""
        } else {
            i++
        }

        if (emailText.text.toString().isEmpty()) {
            emailInputLayout.error = "*Enter Email Id"
        } else if (!(emailText.text.toString().contains("@") && emailText.text.toString()
                .contains(".com"))
        ) {
            emailInputLayout.error = "Enter valid email id"
            emailText.text = ""
        } else {
            i++
        }
        return i
    }


    fun getUserList(): ArrayList<UserDataBase> {
        val database: ArrayList<UserDataBase>
        val sharedPreferences2: SharedPreferences = getSharedPreferences("Main", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if (json == null) {
            database = ArrayList<UserDataBase>()
        } else {

            database = gson.fromJson(json, type)
        }
        return database
    }
}