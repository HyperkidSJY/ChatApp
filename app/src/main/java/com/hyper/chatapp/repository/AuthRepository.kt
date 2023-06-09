package com.hyper.chatapp.repository

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.hyper.chatapp.models.Chats
import com.hyper.chatapp.models.User
import javax.inject.Inject


class AuthRepository@Inject constructor(
    private val firebaseAuth: FirebaseAuth,private val firebaseDatabase: DatabaseReference) {

    fun login(email: String, password: String): Task<AuthResult> {
        return firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    fun signup(name: String,email: String, password: String): Task<AuthResult> {
        return firebaseAuth.createUserWithEmailAndPassword(email, password)
    }

    fun logout() {
        firebaseAuth.signOut()
    }

    fun addUserToDatabase(name: String, email: String, uid: String) {
        firebaseDatabase.child("user").child(uid)
            .setValue(User(name, email, uid))
    }

    fun getUsers(callback: (List<User>) -> Unit) {
        firebaseDatabase.child("user")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val users = mutableListOf<User>()
                    for (i in snapshot.children) {
                        val curUser = i.getValue(User::class.java)
                        if(firebaseAuth.currentUser?.uid != curUser?.uid){
                            users.add(curUser!!)
                        }
                    }

                    callback(users)
                }

                override fun onCancelled(error: DatabaseError) {

                }
            }
        )
    }

}