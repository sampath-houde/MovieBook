package com.example.Movies.mainpackage.api.viewModel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class UserProfileViewModel(application: Application) : AndroidViewModel(application) {

    lateinit var user_data: ArrayList<UserDataBase>

    fun getUserList(): ArrayList<UserDataBase> {
        val sharedPreferences2 =
            getApplication<Application>().getSharedPreferences("Main", Context.MODE_PRIVATE)!!
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if (json == null) {
            user_data = ArrayList<UserDataBase>()
        } else {

            user_data = gson.fromJson(json, type)
        }
        return user_data
    }

    fun setUserLoginStatus() {
        val loginStatus = getApplication<Application>().getSharedPreferences(
            "LoginSession",
            Context.MODE_PRIVATE
        )!!
        val editor: SharedPreferences.Editor = loginStatus.edit()
        editor.putBoolean("userLoginStatus", false)
        editor.apply()
    }

    fun updateUserList(user_data: ArrayList<UserDataBase>) {
        val sharedPreferences =
            getApplication<Application>().getSharedPreferences("Main", Context.MODE_PRIVATE)!!
        val editor = sharedPreferences.edit()
        val gson: Gson = Gson()
        val json: String = gson.toJson(user_data)
        editor.putString("activity", json)
        editor.apply()
    }

    fun getCurrentUserSessionId(): Int {
        var currentLoggedInUser = -1
        val sessionId: String
        val sharedPreferences3 = getApplication<Application>().getSharedPreferences(
            "LoginSession",
            Context.MODE_PRIVATE
        )!!
        sessionId = sharedPreferences3.getString("userSessionId", null).toString()
        for (i in 0 until user_data.size) {
            if (sessionId == user_data.get(i).user_email) {
                currentLoggedInUser = i
                break
            }
        }
        return currentLoggedInUser
    }

}