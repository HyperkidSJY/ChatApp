package com.hyper.chatapp.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.hyper.chatapp.models.Chats
import javax.inject.Inject


class ChatRepository @Inject constructor(private val firebaseDatabase: DatabaseReference) {



    fun getChats(senderRoom: String, callback: (List<Chats>) -> Unit)  {
        firebaseDatabase.child("chats").child(senderRoom).child("message")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val chats = mutableListOf<Chats>()
                    for (i in snapshot.children) {
                        val message = i.getValue(Chats::class.java)
                        chats.add(message!!)
                    }
                    callback(chats)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            }
            )
    }

    fun addMessage(chatObject: Chats, senderRoom: String, receiverRoom: String) {
        firebaseDatabase.child("chats").child(senderRoom).child("message").push()
            .setValue(chatObject).addOnSuccessListener {
                firebaseDatabase.child("chats").child(receiverRoom).child("message").push()
                    .setValue(chatObject)
            }
    }

}