package com.example.Movies.login_register.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.Movies.R
import com.example.Movies.databinding.FragmentForgotpassword1Binding
import com.example.Movies.login_register.views.loginRegisterViewModels.ForgotPassword1ViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_forgotpassword1.*

@Suppress("DEPRECATION")
class ForgotPassword1Fragment : Fragment() {

    private var fragmentForgotpassword1Binding: FragmentForgotpassword1Binding? = null
    private lateinit var phoneText: TextView
    private lateinit var emailText: TextView
    private lateinit var forgotPassword1ViewModel: ForgotPassword1ViewModel
    private lateinit var userEmpty_list: ArrayList<UserDataBase>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentForgotpassword1Binding.inflate(inflater, container, false)
        fragmentForgotpassword1Binding = binding
        val view = binding.root
        forgotPassword1ViewModel =
            ViewModelProviders.of(this).get(ForgotPassword1ViewModel::class.java)

        phoneText = binding.phoneEditText
        emailText = binding.emailEditText


        binding.btnNext.setOnClickListener {
            if (checkFields() == 2) {
                val status = forgotPassword1ViewModel.checkSucessfull(
                    emailText.text.toString(),
                    phoneText.text.toString()
                )
                actionOnCheckUserValidStatus(status)
            }
        }

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }

    private fun actionOnCheckUserValidStatus(status: Int) {
        if (status == 0) {
            Snackbar.make(btn_next, "User doesn't exist", Snackbar.LENGTH_LONG)
                .setAction("Register")
                {
                    view?.findNavController()!!
                        .navigate(R.id.action_forgotPassword1Fragment_to_registerFragment)
                }.show()
        } else if(status == 1) {
            Toast.makeText(context, "User Found", Toast.LENGTH_SHORT)
                .show()
            val action =
                ForgotPassword1FragmentDirections.actionForgotPassword1FragmentToForgotPassword2Fragment(0)
            view?.findNavController()!!.navigate(action)
        } else {
            Snackbar.make(btn_next, "Invalid Combination", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun checkFields(): Int {
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

    override fun onDestroyView() {
        fragmentForgotpassword1Binding = null
        super.onDestroyView()
    }
}

/*fun getUserList(): ArrayList<UserDataBase> {
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
    }*/

/*private fun checkSucessfull(useremptyList: ArrayList<UserDataBase>) {
        for (i in 0 until useremptyList.size) {
            if (useremptyList.get(i).user_email != emailText.text.toString() || useremptyList.get(i).user_phone != phoneText.text.toString()) {
                Snackbar.make(btn_next, "User doesn't exist", Snackbar.LENGTH_LONG)
                    .setAction("Register")
                    {
                        view?.findNavController()!!
                            .navigate(R.id.action_forgotPassword1Fragment_to_registerFragment)
                    }.show()
            } else if (useremptyList.get(i).user_phone == phoneText.text.toString() && useremptyList.get(
                    i
                ).user_email == emailText.text.toString()
            ) {
                Toast.makeText(context, "User Found", Toast.LENGTH_SHORT)
                    .show()
                val action =
                    ForgotPassword1FragmentDirections.actionForgotPassword1FragmentToForgotPassword2Fragment()
                view?.findNavController()!!.navigate(action)
            } else {
                Snackbar.make(btn_next, "Invalid Combination", Snackbar.LENGTH_SHORT).show()
            }
        }
    }*/

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
