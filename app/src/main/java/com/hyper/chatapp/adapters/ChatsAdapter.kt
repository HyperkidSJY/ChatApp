package com.hyper.chatapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.hyper.chatapp.databinding.RecieveMessageListBinding
import com.hyper.chatapp.databinding.SendMessageItemBinding
import com.hyper.chatapp.models.Chats
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ChatsAdapter(val context : Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        val ITEM_SENT = 1
        val ITEM_RECEIVE = 2

    class SendViewHolder(binding: SendMessageItemBinding)
        : RecyclerView.ViewHolder(binding.root){
            val sendMsg = binding.tvSendMsg
            val messageTime = binding.messageTime
    }
    class ReceiveViewHolder(binding: RecieveMessageListBinding)
        : RecyclerView.ViewHolder(binding.root){
        val receiveMsg = binding.tvReceiveMsg
        val messageTime = binding.messageTime
    }

    private var chatList : List<Chats>? = null
    fun setList(chatList: List<Chats>){
        this.chatList = chatList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1){
            SendViewHolder(
                SendMessageItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }else{
            ReceiveViewHolder(
                RecieveMessageListBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentChat = chatList?.get(position)
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentChat?.senderuid)){
            ITEM_SENT
        }else{
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int {
        return if(chatList == null) 0
        else{
            chatList?.size!!
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentChat = chatList?.get(position)
        if(holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            holder.sendMsg.text = currentChat?.message
            holder.messageTime.text = currentChat?.timestamp
        }else{
            val viewHolder = holder as ReceiveViewHolder
            holder.receiveMsg.text = currentChat?.message
            holder.messageTime.text = currentChat?.timestamp
        }
    }




}