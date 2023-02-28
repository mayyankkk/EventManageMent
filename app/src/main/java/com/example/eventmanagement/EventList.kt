package com.example.eventmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.eventmanagement.databinding.ActivityEventListBinding

class EventList : AppCompatActivity() {
    companion object{
        const val KEY="com.example.eventmanagement.EventList.KEY"
    }
    private lateinit var binding: ActivityEventListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.arduinoBtn.setOnClickListener {
            val extra= binding.arduinoBtn.tag.toString()
            startEventDetails(extra)
        }
        binding.autocadBtn.setOnClickListener {
            val extra= binding.autocadBtn.tag.toString()
            startEventDetails(extra)
        }
        binding.event3.setOnClickListener {
            val extra= binding.event3.tag.toString()
            startEventDetails(extra)
        }
        binding.event4.setOnClickListener {
            val extra= binding.event4.tag.toString()
            startEventDetails(extra)
        }

    }

    private fun startEventDetails(extra: String){
        val intent= Intent(this,EventDetails:: class.java)
        intent.putExtra("KEY",extra)
        startActivity(intent)
    }
}