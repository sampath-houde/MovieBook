package com.example.Movies.login_register.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.Movies.R
import com.example.Movies.databinding.FragmentLoginBinding
import com.example.Movies.login_register.views.loginRegisterViewModels.LoginViewModel
import com.example.Movies.mainpackage.api.MainActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.delay
import org.xml.sax.Parser


@Suppress("DEPRECATION")
class LoginFragment : Fragment() {

    private lateinit var emailText: EditText
    private lateinit var passwordText: EditText
    private lateinit var loginViewModel: LoginViewModel
    private var fragmentLoginBinding: FragmentLoginBinding? = null
    private lateinit var SuccessDialog: SweetAlertDialog
    private lateinit var ErrorDialog: SweetAlertDialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        fragmentLoginBinding = binding
        val view = binding.root
        emailText = binding.emailEditText
        passwordText = binding.passwordEditText
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding.btnSignup.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnLogin.setOnClickListener {
                if (validateCheck() == 2) {
                    val loginStatus = loginViewModel.loginSuccessfull(
                        emailText.text.toString(),
                        passwordText.text.toString()
                    )
                    actionOncheckLoginStatus(loginStatus)
                }
        }


        binding.btnForgot.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_forgotPassword1Fragment)
        }
        return view
    }

    private fun actionOncheckLoginStatus(loginStatus: Int) {
        if (loginStatus == 0) {
            Snackbar.make(
                fragmentLoginBinding?.btnLogin!!,
                "User doesn't exist",
                Snackbar.LENGTH_LONG
            )
                .setAction("Register")
                {
                    view?.findNavController()!!
                        .navigate(R.id.action_loginFragment_to_registerFragment)
                }.show()
        } else if (loginStatus == 1) {
            SuccessDialog = SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            SuccessDialog.setTitleText("Success!").hideConfirmButton().show()
            if (fragmentLoginBinding!!.materialCheckBox.isChecked == true) {
                loginViewModel.loginStatus = true
            } else {
                loginViewModel.loginStatus == false
            }
            loginViewModel.setUserSessionIdAndLoginStatus(emailText.text.toString())
            Handler().postDelayed({
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }, 1000)

        } else if (loginStatus == -1) {
            ErrorDialog = SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            ErrorDialog.setTitleText("Oops...").setContentText("Invalid Combination").show()
            emailText.setText("")
            passwordText.setText("")
        }
    }

    override fun onResume() {
        super.onResume()
        emailText.setText("")
        passwordText.setText("")
    }

    @SuppressLint("ResourceAsColor")
    private fun validateCheck(): Int {
        var i = 0
        if (emailText.text.toString().isEmpty()) {
            YoYo.with(Techniques.Shake).duration(700).playOn(fragmentLoginBinding!!.emailEditText)
            fragmentLoginBinding!!.emailEditText.error = "Enter Email"
        } else if ((emailText.text.toString().contains("@") && emailText.text.toString()
                .contains(".com")) == false
        ) {
            YoYo.with(Techniques.Shake).duration(700).playOn(fragmentLoginBinding!!.emailEditText)
            fragmentLoginBinding!!.emailEditText.error = "Enter valid Email"
        } else {
            i++
        }

        if (passwordText.text.toString().isEmpty()) {
            YoYo.with(Techniques.Shake).duration(700)
                .playOn(fragmentLoginBinding!!.passwordEditText)
            fragmentLoginBinding!!.passwordEditText.error = "Enter Password"
        } else {
            i++
        }
        return i;
    }

    override fun onDestroyView() {
        fragmentLoginBinding = null
        super.onDestroyView()
    }

}


/*private fun loginSuccessfull() {
    userEmpty_list = loginViewModel.getUserList()
    for (i in 0 until userEmpty_list.size) {
        if (userEmpty_list.get(i).user_email != emailText.text.toString()) {
            Snackbar.make(btn_login, "User doesn't exist", Snackbar.LENGTH_LONG)
                .setAction("Register")
                {
                    view?.findNavController()!!
                        .navigate(R.id.action_loginFragment_to_registerFragment)
                }.show()
        } else if (userEmpty_list.get(i).user_password == passwordText.text.toString()) {
            Toast.makeText(context, "Login Successfull", Toast.LENGTH_SHORT)
                .show()
                if (fragmentLoginBinding?.materialCheckBox?.isChecked == true) {
                    loginViewModel.loginStatus = true
                } else {
                    loginViewModel.loginStatus == false
                }
            loginViewModel.setUserSessionIdAndLoginStatus(userEmpty_list.get(i).user_email)
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        } else {
            Snackbar.make(btn_login, "Invalid Combination", Snackbar.LENGTH_LONG).show()
        }
    }
}*/

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

/*override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
setContentView(R.layout.fragment_login)
passwordText = findViewById(R.id.passwordEditText)
emailText = findViewById(R.id.emailEditText)

btn_signup.setOnClickListener {
    val intent = Intent(this, RegisterFragment::class.java)
    startActivity(intent)
}

btn_login.setOnClickListener {
    val i = validateCheck()
    if(i==2){loginSuccessfull()}
}

btn_forgot.setOnClickListener {
    val intent = Intent(this, ForgotPassword1Fragment::class.java)
    startActivity(intent)
}
}
*/