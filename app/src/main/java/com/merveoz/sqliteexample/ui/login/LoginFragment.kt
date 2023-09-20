package com.merveoz.sqliteexample.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaCodec.CryptoInfo.Pattern
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.merveoz.sqliteexample.R
import com.merveoz.sqliteexample.common.viewBinding
import com.merveoz.sqliteexample.databinding.FragmentLoginBinding


class LoginFragment : Fragment(R.layout.fragment_login) {

    private val binding by viewBinding (FragmentLoginBinding::bind)

    private lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPref = requireActivity().getSharedPreferences("AppSettings", Context.MODE_PRIVATE)

        val isLogin = sharedPref.getBoolean("isLogin", false) //isLogin keyword'ünü default false atadık, login başarılı gerçekleşince bunu true olarak değiştireceğiz

        if(isLogin){
            findNavController().navigate(R.id.loginToDailyNotes)
        }

        with(binding) {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                if(checkFields(email, password)) {
                    sharedPref.edit().putBoolean("isLogin", true).apply() //veri set etme işlemi.
                    findNavController().navigate(R.id.loginToDailyNotes)
                }
            }
        }
    }
    //verileri kontrol etmek için
    private fun checkFields(email: String, password: String): Boolean {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                binding.tilEmail.error = "E-mail is not valid!"
                false
            }
            password.isEmpty() -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "Password is empty!"
                false
            }
            password.length < 6 -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "Password should be at least 6 character"
                false
            }
            else -> true
        }
    }
}