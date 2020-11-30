package com.example.Movies.login_register.views

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.Movies.R
import com.example.Movies.databinding.FragmentRegisterBinding
import com.example.Movies.login_register.views.loginRegisterViewModels.RegisterViewModel
import com.example.Movies.userDataBase.UserDataBase
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_register.*

@Suppress("DEPRECATION")
class RegisterFragment : Fragment() {

    private var fragmentRegisterBinding: FragmentRegisterBinding? = null
    private lateinit var btn_register: Button
    private lateinit var user_name: EditText
    private lateinit var user_email: EditText
    private lateinit var user_phone: EditText
    private lateinit var user_password: EditText
    private lateinit var registerViewModel: RegisterViewModel
    private var userEmpty_list = ArrayList<UserDataBase>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegisterBinding.inflate(inflater, container, false)
        fragmentRegisterBinding = binding
        user_name = binding.nameEditText
        user_email = binding.emailEditText
        user_phone = binding.phoneEditText
        user_password = binding.passwordEditText
        val view = binding.root
        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)

        binding.btnRegister.setOnClickListener {
            if (checkErrors() == 4) {
                val boolean = registerViewModel.setAndAddAllData(
                    user_name.text.toString(), user_email.text.toString(),
                    user_phone.text.toString(), user_password.text.toString()
                )
                if (boolean) {
                    view.findNavController().navigate(R.id.loginFragment)
                    Toast.makeText(context, "Registered Successfully", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnLogin.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        binding.btnback.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }

        return view
    }

    override fun onDestroyView() {
        fragmentRegisterBinding = null
        super.onDestroyView()
    }

    private fun checkErrors(): Int {

        var i = 0

        if (user_name.text.toString().isEmpty()) {
            user_name.error = "Enter name"
        } else {
            i++
        }

        if (user_email.text.toString().isEmpty()) {
            user_email.error = "Enter Email"
        } else if ((user_email.text.toString().contains("@") && user_email.text.toString()
                .contains(".com")) == false
        ) {
            user_email.setText("")
            user_email.error = "Enter valid email"
        } else {
            i++
        }

        if (user_phone.text.toString().isEmpty()) {
            user_phone.error = "Enter phone number"
        } else if (user_phone.text.toString().length != 10) {
            user_phone.error = "Enter valid phone number"
            user_phone.setText("")
        } else {i++}

            if (user_password.text.toString().isEmpty()) {
                user_password.error = "Enter password"
        } else if (user_password.text.contains(" ")) {
                user_password.error = "No spaces allowed"
                user_password.setText("")
        } else {i++}

        return i;
    }
}


/*//Function to add all data
    fun setAndAddAllData() {
        val userData = UserDataBase()
        userData.user_name = user_name.toString()
        userData.user_phone = user_phone.toString()
        userData.user_email = user_email.toString()
        userData.user_password = user_password.toString()

        userEmpty_list.add(userData)
        setUserList()
    }

    fun setUserList(){
        val sharedPreferences = context?.getSharedPreferences("Main", MODE_PRIVATE)!!
        val editor = sharedPreferences.edit()
        val gson: Gson = Gson()
        val json: String = gson.toJson(userEmpty_list)
        editor.putString("activity", json)
        editor.apply()
    }*/
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
