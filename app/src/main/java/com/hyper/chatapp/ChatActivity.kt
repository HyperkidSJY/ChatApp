package com.hyper.chatapp


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.hyper.chatapp.adapters.ChatsAdapter
import com.hyper.chatapp.databinding.ActivityChatBinding
import com.hyper.chatapp.models.Chats
import com.hyper.chatapp.viewmodels.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@AndroidEntryPoint
class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    var receiverRoom: String? = null
    var senderRoom: String? = null
    lateinit var chatsAdapter: ChatsAdapter
    lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("name")
        supportActionBar?.title = name


        val receiveruid = intent.getStringExtra("uid")
        val senderuid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiveruid + senderuid
        receiverRoom = senderuid + receiveruid


        setUpRecyclerView()
        setUpViewModel()


        binding.btnSent.setOnClickListener {
            if(!binding.messageBox.text.isNullOrEmpty()){
                val message = binding.messageBox.text.toString()
                val chatObject = Chats(message, senderuid!!, unixTime(Timestamp(System.currentTimeMillis())))
                chatViewModel.addMessage(chatObject,senderRoom!!,receiverRoom!!)
                binding.messageBox.setText("")
            }
        }

    }


    private fun setUpRecyclerView(){
        binding.rvChat.layoutManager = LinearLayoutManager(this)
        chatsAdapter = ChatsAdapter(this)
        binding.rvChat.adapter = chatsAdapter
    }

    private fun setUpViewModel(){
        chatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        chatViewModel.getChats(senderRoom!!)

        chatViewModel.chatList.observe(this, Observer {
            chatsAdapter.setList(it)
            chatsAdapter.notifyDataSetChanged()
        })

    }

    private fun unixTime(timex: Timestamp) : String{
        val date = Date(timex.time)
        val sdf = SimpleDateFormat("HH:mm" , Locale.UK)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}
