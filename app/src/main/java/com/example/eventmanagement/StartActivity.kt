package com.example.eventmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eventmanagement.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.adminBtn.setOnClickListener{
            startActivity(Intent(this,AdminLoginActivity::class.java))
        }
        binding.userBtn.setOnClickListener {
            startActivity(Intent(this,SignInActivity::class.java))
        }
    }
}