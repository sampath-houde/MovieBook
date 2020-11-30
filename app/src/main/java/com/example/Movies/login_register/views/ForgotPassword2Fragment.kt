package com.example.Movies.login_register.views

import android.content.Context.MODE_PRIVATE
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
import androidx.navigation.fragment.navArgs
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.Movies.R
import com.example.Movies.databinding.FragmentForgotpassword2Binding
import com.example.Movies.login_register.views.loginRegisterViewModels.ForgotPassword2ViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_forgotpassword2.*
import java.lang.reflect.Type

@Suppress("DEPRECATION")
class ForgotPassword2Fragment : Fragment() {

    val args : ForgotPassword2FragmentArgs by navArgs()
    private lateinit var userEmpty_list: ArrayList<UserDataBase>
    private var fragmentForgotpassword2Binding: FragmentForgotpassword2Binding? = null
    private lateinit var forgotpassword2ViewModel: ForgotPassword2ViewModel
    private lateinit var password1: TextView
    private lateinit var password2: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentForgotpassword2Binding.inflate(inflater, container, false)
        fragmentForgotpassword2Binding = binding
        val view = binding.root
        forgotpassword2ViewModel = ViewModelProviders.of(this).get(ForgotPassword2ViewModel::class.java)

        password1 = binding.passwordEditText
        password2 = binding.passwordEditText2

        binding.btnNext.setOnClickListener {
            val boolean = checkConditions()
            if(boolean) {
                val boolean = forgotpassword2ViewModel.updatePassword(args.key, password2.text.toString())
                if(boolean) {
                    Toast.makeText(context, "Password Updated", Toast.LENGTH_SHORT).show()
                    view.findNavController().navigate(R.id.loginFragment)
                }
            }
        }

        binding.btnback.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }

    private fun checkConditions() : Boolean {
        var o =0
        if(password1.text.toString().isEmpty())
        {
            YoYo.with(Techniques.Shake).playOn(password1)
            password1.error = "Enter password"
        } else {o++}

        if(password2.text.toString().isEmpty()){
            YoYo.with(Techniques.Shake).playOn(password2)
            password2.error = "Enter password"
        } else {o++}

        if(password2.text.toString() != password1.text.toString()){
            Toast.makeText(context, "Password doesn't match", Toast.LENGTH_SHORT).show()
            password1.text = ""
            password2.text = ""
        } else {o++}

        if(o==3) {
            return password1.text.toString() == password2.text.toString()
        }
        else
            return false
    }

    override fun onDestroyView() {
        fragmentForgotpassword2Binding = null
        super.onDestroyView()
    }

}

/*override fun onCreate(savedInstanceState: Bundle?) {
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
    }*/

/*private fun updatePassword(key: Int) {
        userEmpty_list.get(key).user_password = password2.text.toString()
        updateUserList()
        Toast.makeText(context, "Password Updated", Toast.LENGTH_SHORT).show()
        view?.findNavController()!!.navigate(R.id.loginFragment)
    }*/

/*fun getUserList() {
        val sharedPreferences2 = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val gson = Gson()
        val json = sharedPreferences2.getString("activity", null)
        val type: Type = object : TypeToken<ArrayList<UserDataBase>>() {}.type
        if(json==null){
            userEmpty_list=ArrayList<UserDataBase>()
        }else{

            userEmpty_list = gson.fromJson(json, type)
        }
    }*/

/*fun updateUserList(){
    val sharedPreferences = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
    val editor = sharedPreferences.edit()
    val gson: Gson = Gson()
    val json: String = gson.toJson(userEmpty_list)
    editor.putString("activity", json)
    editor.apply()
}*/