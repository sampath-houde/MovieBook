package com.example.Movies.login_register.views.loginRegisterViewModels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.example.Movies.R
import com.example.Movies.login_register.views.ForgotPassword1FragmentDirections
import com.example.Movies.userDataBase.UserDataBase
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ForgotPassword1ViewModel(application: Application) : AndroidViewModel(application) {


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

    fun checkSucessfull(emailText: String, phoneText: String): Int {
        var k = -1
        val useremptyList = getUserList()
        for (i in 0 until useremptyList.size) {
            if (useremptyList.get(i).user_email != emailText || useremptyList.get(i).user_phone != phoneText) {
                k = 0
                break
            } else if (useremptyList.get(i).user_phone == phoneText && useremptyList.get(i).user_email == emailText) {
                k = 1
                break
            } else {
                break
            }
        }
        return k
    }

}