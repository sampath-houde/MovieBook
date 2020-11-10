package com.example.Movies.login_register.views.loginRegisterViewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ForgotPassword2ViewModel(application: Application) : AndroidViewModel(application) {

    fun getUserList(): ArrayList<UserDataBase> {
        val database: ArrayList<UserDataBase>
        val sharedPreferences2 =
            getApplication<Application>().getSharedPreferences("Main", Context.MODE_PRIVATE)!!
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

    fun updateUserList(user_emptyList: ArrayList<UserDataBase>) {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("Main", Context.MODE_PRIVATE)!!
        val editor = sharedPreferences.edit()
        val gson: Gson = Gson()
        val json: String = gson.toJson(user_emptyList)
        editor.putString("activity", json)
        editor.apply()
    }

    fun updatePassword(key: Int, password2: String): Boolean {
        val user_emptyList = getUserList()
        user_emptyList.get(key).user_password = password2
        updateUserList(user_emptyList)
        return true
    }

}
