package com.example.eventmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eventmanagement.databinding.ActivityAdminActivityBinding
import com.google.firebase.auth.FirebaseAuth

class AdminActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addEvent.setOnClickListener {  }

        binding.registration.setOnClickListener {  }

        binding.updateEvent.setOnClickListener {  }

        binding.deleteEvent.setOnClickListener {  }

        binding.logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent= Intent(this,AdminLoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}