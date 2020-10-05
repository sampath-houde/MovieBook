package com.example.Movies.login_register.views

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.Movies.R
import com.example.Movies.login_register.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.reflect.Type

class RegisterActivity : AppCompatActivity() {

    private lateinit var btn_register: Button
    private lateinit var user_name: EditText
    private lateinit var user_email: EditText
    private lateinit var user_phone: EditText
    private lateinit var user_password: EditText
    private var userEmpty_list = ArrayList<UserDataBase>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register = findViewById(R.id.btn_register)
        user_name= findViewById(R.id.nameEditText)
        user_email = findViewById(R.id.emailEditText)
        user_phone = findViewById(R.id.phoneEditText)
        user_password = findViewById(R.id.passwordEditText)

        btn_register.setOnClickListener {
            val check = checkErrors()
            if(check == 4)
            {
                setAndAddAllData()
            }
        }
    }

    private fun checkErrors() : Int {

        var i = 0

        if (user_name.text.toString().isEmpty()) {
            nameEditTextLayout.error = "*Enter Name"
        } else {i++}

        if (user_email.text.toString().isEmpty()) {
            emailEditTextLayout.error = "*Enter email id"
        } else if ((user_email.text.toString().contains("@") && user_email.text.toString().contains(".com")) == false) {
            emailEditTextLayout.error = "Invalid email id"
            user_email.setText("")
        } else{i++}

        if(user_phone.text.toString().isEmpty())
        {
            phoneEditTextLayout.error = "*Enter Phone Number"
        } else if(user_phone.text.toString().length != 10)
        {
            phoneEditTextLayout.error = "Enter valid phone number"
            user_phone.setText("")
        } else {i++}

        if(user_password.text.toString().isEmpty())
        {
            passwordEditTextLayout.error = "*Enter Password"
        } else {i++}

        return i;
    }

    //Function to add all data
    private fun setAndAddAllData() {
        val userData = UserDataBase()
        userData.user_name = user_name.text.toString()
        userData.user_phone = user_phone.text.toString()
        userData.user_email = user_email.text.toString()
        userData.user_password = user_password.text.toString()

        userEmpty_list.add(userData)

        val sharedPreferences: SharedPreferences = getSharedPreferences("MovieFinder", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()

        val gson: Gson = Gson()
        var json: String = gson.toJson(userEmpty_list)
        editor.putString("userData", json)
        editor.apply()

        val sharedPreferences2 = getSharedPreferences("MovieFinder", MODE_PRIVATE)
        json = sharedPreferences2.getString("userData", null)!!
        val type: Type = object : TypeToken<ArrayList<UserDataBase?>?>() {}.type
        userEmpty_list = gson.fromJson(json, type)
        
        Log.d("List of User", userEmpty_list.get(0))
    }
}