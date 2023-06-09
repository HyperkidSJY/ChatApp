package com.hyper.chatapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hyper.chatapp.models.Chats
import com.hyper.chatapp.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel@Inject constructor(private val chatRepository: ChatRepository) : ViewModel() {

    private val _chatList = MutableLiveData<List<Chats>>()
    val chatList : LiveData<List<Chats>> = _chatList

    fun getChats(senderRoom : String){
        chatRepository.getChats(senderRoom) { chats ->
            for (i in chats.indices) {
                _chatList.value = chats
            }
        }
    }

    fun addMessage(chatObject: Chats, senderRoom: String, receiverRoom: String){
        chatRepository.addMessage(chatObject,senderRoom, receiverRoom)
    }

}