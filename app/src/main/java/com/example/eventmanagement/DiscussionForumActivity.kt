package com.example.eventmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.eventmanagement.databinding.ActivityDiscussionForumBinding

class DiscussionForumActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDiscussionForumBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiscussionForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}