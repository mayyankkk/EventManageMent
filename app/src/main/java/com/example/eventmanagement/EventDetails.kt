package com.example.eventmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eventmanagement.EventList.Companion.KEY
import com.example.eventmanagement.databinding.ActivityEventDetailsBinding

class EventDetails : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailsBinding
    companion object{
        const val  KEY1="com.example.eventmanagement.EventDetails.KEY1"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tag=intent.getStringExtra("KEY")
        when(tag){
            "Arduino Effect"->{
                binding.eventTxt.setText(R.string.arduino_effect_text)
                binding.eventImage.setImageResource(R.drawable.arduino)
                binding.eventName.setText(R.string.arduino_effect)
            }
            "Auto Cad"->{
                binding.eventImage.setImageResource(R.drawable.autocad)
                binding.eventName.setText(R.string.auto_cad)
                binding.eventTxt.setText(R.string.auto_cad_text)
            }
            "Blind Coding"->{
                binding.eventImage.setImageResource(R.drawable.blinccoding)
                binding.eventName.setText(R.string.event_3)
                binding.eventTxt.setText(R.string.blind_coding_text)
            }
            "Boat Making"->{
                binding.eventName.setText(R.string.event_4)
                binding.eventImage.setImageResource(R.drawable.boatmaking)
                binding.eventTxt.setText(R.string.boat_making_text)
            }
        }

        binding.registerBtn.setOnClickListener {
            val intent= Intent(this,RegistrationForm:: class.java)
            intent.putExtra(KEY1, tag)
            startActivity(intent)
        }
        binding.discussBtn.setOnClickListener {
            val intent= Intent(this,DiscussionForumActivity:: class.java)
            intent.putExtra(KEY1, tag)
            startActivity(intent)
        }
    }



}