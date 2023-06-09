package com.hyper.chatapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.hyper.chatapp.ChatActivity
import com.hyper.chatapp.databinding.UserListItemBinding
import com.hyper.chatapp.models.User

class UserAdapter(private  val context : Context)
    : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

        class ViewHolder(binding: UserListItemBinding)
            : RecyclerView.ViewHolder(binding.root){
                val name = binding.tvName
        }


    private var list: List<User>? = null
    fun setList(list : List<User>){
        this.list = list
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context),parent,false
            )
        )
    }



    override fun getItemCount(): Int {
        return if(list == null) 0
        else{
            list?.size!!
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentUser = list?.get(position)
        holder.name.text = currentUser?.name

        holder.itemView.setOnClickListener{
            val intent = Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentUser?.name)
            intent.putExtra("uid",currentUser?.uid)
            context.startActivity(intent)
        }
    }
}