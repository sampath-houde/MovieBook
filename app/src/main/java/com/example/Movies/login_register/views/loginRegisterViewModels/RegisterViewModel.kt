package com.example.Movies.login_register.views.loginRegisterViewModels

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.Movies.R
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson

class RegisterViewModel(application: Application) : AndroidViewModel(application) {


     var userEmpty_list = ArrayList<UserDataBase>()

    fun setAndAddAllData(
        user_name: String,
        user_email: String,
        user_phone: String,
        user_password: String
    ): Boolean {
        val userData = UserDataBase()
        userData.user_name = user_name
        userData.user_phone = user_phone
        userData.user_email = user_email
        userData.user_password = user_password
        userEmpty_list.add(userData)

        setUserList()
        return true
    }

    fun setUserList(){
        val sharedPreferences = getApplication<Application>().getSharedPreferences("Main", Context.MODE_PRIVATE)!!
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String = gson.toJson(userEmpty_list)
        editor.putString("activity", json)
        editor.apply()
    }
}
