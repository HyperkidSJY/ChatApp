package com.hyper.chatapp.viewmodels


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthResult
import com.hyper.chatapp.models.User
import com.hyper.chatapp.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {

    val resultLiveData = MutableLiveData<AuthResult?>()

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> = _userList

    fun login(email: String, password: String) {
        authRepository.login(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    resultLiveData.value = task.result
                } else {
                    resultLiveData.value = null
                }
            }
    }

    fun register(name: String, email: String, password: String) {
        authRepository.signup(name, email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    resultLiveData.value = task.result
                    authRepository.addUserToDatabase(name, email, task.result.user?.uid!!)
                } else {
                    resultLiveData.value = null
                }
            }
    }

    fun logOut() {
        authRepository.logout()
    }

    fun getUsers() {
        authRepository.getUsers { users ->
            for (i in users.indices) {
                    _userList.value = users
            }
        }
    }
}