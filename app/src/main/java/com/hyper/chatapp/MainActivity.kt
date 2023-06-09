package com.hyper.chatapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hyper.chatapp.adapters.UserAdapter
import com.hyper.chatapp.databinding.ActivityMainBinding
import com.hyper.chatapp.viewmodels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var authViewModel: AuthViewModel
    lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        setUpUserRecyclerView()
        setUpViewModel()
    }


    private fun setUpUserRecyclerView(){
        binding.rvUser.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter(this)
        binding.rvUser.adapter = userAdapter
    }

    private fun setUpViewModel(){
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        authViewModel.getUsers()
        authViewModel.userList.observe(this) {
            if(it != null){
                userAdapter.setList(it)
                userAdapter.notifyDataSetChanged()
            }
        }
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){
            authViewModel.logOut()
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }




}