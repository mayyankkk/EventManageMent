package com.example.eventmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eventmanagement.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.eventsBtn.setOnClickListener {
            val intent = Intent(this, EventList::class.java)
            startActivity(intent)
        }
        binding.discussForumBtn.setOnClickListener {
            val intent = Intent(this, DiscussionForumActivity::class.java)
            startActivity(intent)
        }
        binding.logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent= Intent(this,SignInActivity::class.java)
            startActivity(intent)
        }
    }
}