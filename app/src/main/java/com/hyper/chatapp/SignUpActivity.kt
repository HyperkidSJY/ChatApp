package com.hyper.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hyper.chatapp.databinding.ActivitySignUpBinding
import com.hyper.chatapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    lateinit var authViewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)


        binding.btnSignUp.setOnClickListener {
            when {
                binding.etName.text.isNullOrEmpty() -> {
                    Toast.makeText(this@SignUpActivity, "Please enter Name", Toast.LENGTH_SHORT)
                        .show()
                }

                binding.etEmail.text.isNullOrEmpty() -> {
                    Toast.makeText(this@SignUpActivity, "Please enter Email", Toast.LENGTH_SHORT)
                        .show()
                }

                binding.etPassword.text.isNullOrEmpty() -> {
                    Toast.makeText(this@SignUpActivity, "Please enter Password", Toast.LENGTH_SHORT)
                        .show()
                }

                else -> {
                    val name = binding.etName.text.toString()
                    val email = binding.etEmail.text.toString()
                    val password = binding.etPassword.text.toString()
                    authViewModel.register(name,email, password)
                }
            }
        }

        authViewModel.resultLiveData.observe(this, Observer { user ->
            if (user != null) {
                val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                finish()
                startActivity(intent)
            } else {
                Toast.makeText(this@SignUpActivity, "Some Error Occurred", Toast.LENGTH_SHORT)
                    .show()
            }
        })

    }
}