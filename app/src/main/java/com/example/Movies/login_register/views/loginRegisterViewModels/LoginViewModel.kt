package com.example.Movies.login_register.views.loginRegisterViewModels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var loginStatus: Boolean = false

    fun getUserList(): ArrayList<UserDataBase> {
        val database: ArrayList<UserDataBase>
        val sharedPreferences2 = getApplication<Application>().getSharedPreferences("Main", Context.MODE_PRIVATE)!!
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

    fun setUserSessionIdAndLoginStatus(userEmail: String) {
        val sharedPreferences2 = getApplication<Application>().getSharedPreferences("LoginSession", Context.MODE_PRIVATE)!!
        val editor2: SharedPreferences.Editor = sharedPreferences2.edit()
        editor2.putString("userSessionId", userEmail)
        editor2.putBoolean("userLoginStatus", loginStatus)
        editor2.apply()
    }

    fun loginSuccessfull(emailText: String, passwordText: String): Int {
        var k = -1
        val userEmpty_list = getUserList()
        for (i in 0 until userEmpty_list.size) {
            if (userEmpty_list.get(i).user_email != emailText) {
                k=0
                break
            } else if (userEmpty_list.get(i).user_password == passwordText) {
                k=1
                break
            } else {
                break
            }
        }
        return k
    }

}