package com.hyper.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hyper.chatapp.databinding.ActivityLoginBinding
import com.hyper.chatapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        binding.btnLogIn.setOnClickListener {
            when {
                binding.etEmail.text.isNullOrEmpty() -> {
                    Toast.makeText(this@LoginActivity, "Please enter Email", Toast.LENGTH_SHORT)
                        .show()
                }

                binding.etPassword.text.isNullOrEmpty() -> {
                    Toast.makeText(this@LoginActivity, "Please enter Password", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()
                    authViewModel.login(email, password)
                }
            }

        }

        binding.btnSignUp.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }

        authViewModel.resultLiveData.observe(this) { user ->
            if (user != null) {
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                finish()
                startActivity(intent)
            } else {
                Toast.makeText(this@LoginActivity, "User does not exist", Toast.LENGTH_SHORT).show()
            }
        }

    }
}