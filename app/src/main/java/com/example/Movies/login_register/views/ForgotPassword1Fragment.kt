package com.example.Movies.login_register.views

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.Movies.R
import com.example.Movies.databinding.FragmentForgotpassword1Binding
import com.example.Movies.userDataBase.UserDataBase
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_forgotpassword1.*
import kotlinx.android.synthetic.main.fragment_forgotpassword1.view.*
import java.lang.reflect.Type

class ForgotPassword1Fragment : Fragment() {

    private var fragmentForgotpassword1Binding: FragmentForgotpassword1Binding? = null
    private lateinit var phoneText: TextView
    private lateinit var emailText: TextView
    private lateinit var userEmpty_list: ArrayList<UserDataBase>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentForgotpassword1Binding.inflate(inflater, container, false)
        fragmentForgotpassword1Binding = binding
        val view = binding.root

        phoneText = binding.phoneEditText
        emailText = binding.emailEditText


        binding.btnNext.setOnClickListener {
            userEmpty_list = getUserList()
            val errors: Int = checkUserValidity()
            if (errors == 2) {
                checkSucessfull(userEmpty_list)
            }
        }

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }

    override fun onDestroyView() {
        fragmentForgotpassword1Binding = null
        super.onDestroyView()
    }

    private fun checkSucessfull(useremptyList: ArrayList<UserDataBase>) {
        for (i in 0 until useremptyList.size) {
            if (useremptyList.get(i).user_email != emailText.text.toString() || useremptyList.get(i).user_phone != phoneText.text.toString()) {
                Snackbar.make(btn_next, "User doesn't exist", Snackbar.LENGTH_LONG)
                    .setAction("Register")
                    {
                        view?.findNavController()!!.navigate(R.id.action_forgotPassword1Fragment_to_registerFragment)
                    }.show()
            } else if (useremptyList.get(i).user_phone == phoneText.text.toString() && useremptyList.get(i).user_email == emailText.text.toString()) {
                Toast.makeText(context, "User Found", Toast.LENGTH_SHORT)
                    .show()
                val action = ForgotPassword1FragmentDirections.actionForgotPassword1FragmentToForgotPassword2Fragment(i)
                view?.findNavController()!!.navigate(action)
               /* val intent = Intent(this, ForgotPassword2Fragment::class.java)
                intent.putExtra("key", i)
                startActivity(intent)*/
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
        val sharedPreferences2 = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
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

/*override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_forgotpassword1)

    phoneText = findViewById(R.id.phoneEditText)
    emailText = findViewById(R.id.emailEditText)


    btn_next.setOnClickListener {
        userEmpty_list = getUserList()
        val errors: Int = checkUserValidity()
        if (errors == 2) {
            checkSucessfull(userEmpty_list)
        }
    }

}*/
