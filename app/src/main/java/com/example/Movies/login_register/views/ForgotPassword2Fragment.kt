package com.example.Movies.login_register.views

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.Movies.R
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_forgotpassword2.*
import java.lang.reflect.Type

class ForgotPassword2Fragment : Fragment() {

    private lateinit var userEmpty_list: ArrayList<UserDataBase>

    private lateinit var password1: TextView
    private lateinit var password2: TextView
    private  var i: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_forgotpassword2)


        password1 = findViewById(R.id.passwordEditText)
        password2 = findViewById(R.id.passwordEditText2)

        i = intent.getIntExtra("key", -1)


        btn_create.setOnClickListener {
            getUserList()
            val boolean = checkConditions()
            if(boolean) {
                updatePassword()
            }
        }
    }

    private fun updatePassword() {
        userEmpty_list.get(i).user_password = password2.text.toString()
        updateUserList()
        Toast.makeText(applicationContext, "Password Updated", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginFragment::class.java)
        startActivity(intent)
    }

    private fun checkConditions() : Boolean {
        var o =0
        if(password1.text.toString().isEmpty())
        {
            passwordInputLayout.error = "Enter Password"
        } else {o++}

        if(password2.text.toString().isEmpty()){
            passwordInputLayout2.error = "Enter Password"
        } else {o++}

        if(password2.text.toString() != password1.text.toString()){
            Toast.makeText(applicationContext, "Password doesn't match", Toast.LENGTH_SHORT).show()
            password1.text = ""
            password2.text = ""
        } else {o++}

        if(o==3) {
            return password1.text.toString() == password2.text.toString()
        }
        else return false
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

    fun updateUserList(){
        val sharedPreferences: SharedPreferences = getSharedPreferences("Main", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson: Gson = Gson()
        val json: String = gson.toJson(userEmpty_list)
        editor.putString("activity", json)
        editor.apply()
    }

}