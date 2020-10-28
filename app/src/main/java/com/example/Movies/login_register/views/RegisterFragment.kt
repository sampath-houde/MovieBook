package com.example.Movies.login_register.views

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.Movies.R
import com.example.Movies.databinding.FragmentRegisterBinding
import com.example.Movies.userDataBase.UserDataBase
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.fragment_register.view.*

class RegisterFragment : Fragment() {

    private var fragmentRegisterBinding: FragmentRegisterBinding? = null
    private lateinit var btn_register: Button
    private lateinit var user_name: EditText
    private lateinit var user_email: EditText
    private lateinit var user_phone: EditText
    private lateinit var user_password: EditText
    private var userEmpty_list = ArrayList<UserDataBase>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        fragmentRegisterBinding = binding
        val view = binding.root

        btn_register = binding.btnRegister
        user_name= binding.nameEditText
        user_email = binding.emailEditText
        user_phone = binding.phoneEditText
        user_password = binding.passwordEditText

        btn_register.setOnClickListener {
            val check = checkErrors()
            if(check == 4)
            {
                setAndAddAllData()
            }
        }

        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.btnBack.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }

    override fun onDestroyView() {
        fragmentRegisterBinding = null
        super.onDestroyView()
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
        } else if(user_password.text.contains(" ")){
            passwordEditTextLayout.error = "No spaces allowed"
        } else {i++}

        return i;
    }

    //Function to add all data
    public fun setAndAddAllData() {
        val userData = UserDataBase()
        userData.user_name = user_name.text.toString()
        userData.user_phone = user_phone.text.toString()
        userData.user_email = user_email.text.toString()
        userData.user_password = user_password.text.toString()

        userEmpty_list.add(userData)
        setUserList()

        view?.findNavController()!!.navigate(R.id.loginFragment)
    }

    fun setUserList(){
        val sharedPreferences = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val editor = sharedPreferences.edit()
        val gson: Gson = Gson()
        val json: String = gson.toJson(userEmpty_list)
        editor.putString("activity", json)
        editor.apply()
    }
}


/*override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.fragment_register)

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

    btn_login.setOnClickListener {
        val intent = Intent(this, LoginFragment::class.java)
        startActivity(intent)
    }
}*/
