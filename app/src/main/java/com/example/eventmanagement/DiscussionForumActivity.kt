package com.example.eventmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventmanagement.databinding.ActivityDiscussionForumBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase

class DiscussionForumActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDiscussionForumBinding
    lateinit var messages: ArrayList<MSG>
    private lateinit var myAdapter:MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiscussionForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email=Firebase.auth.currentUser?.email.toString()
        val user= email.dropLast(10)
        Log.i("MAYANK",user)
        binding.send.setOnClickListener {
            if(binding.etmessage.text.toString()!=""){
                val msg=MSG(user,binding.etmessage.text.toString())
                FirebaseDatabase.getInstance().reference.child("Messages").push().setValue(msg)
            }
            binding.etmessage.text.clear()
        }

        binding.recyclerViewDiscuss.layoutManager=LinearLayoutManager(this)
        messages= arrayListOf<MSG>()

        val databaseReference=FirebaseDatabase.getInstance().reference.child("Messages")
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messages.clear()
                for(index in snapshot.children){
                    val n=index.child("name").value
                    val s=index.child("msg").value
                    if(n!=null && s!=null){
                        val msg=MSG(n.toString(),s.toString())
                        messages.add(msg)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

         myAdapter= MyAdapter(messages,this)
        binding.recyclerViewDiscuss.adapter=myAdapter


    }
}