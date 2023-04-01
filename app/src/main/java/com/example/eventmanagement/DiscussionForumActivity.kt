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
    private lateinit var databaseReference:DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDiscussionForumBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val email=Firebase.auth.currentUser?.email.toString()
        val user= email.dropLast(10)
        Log.i("MAYANK",user)

        databaseReference=FirebaseDatabase.getInstance().reference.child("Messages")


        messages= arrayListOf()



        databaseReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("Mayank",snapshot.toString())
                messages.clear()
                for(index in snapshot.children){
                    val msg= MSG(index.child("name").value.toString(),index.child("msg").value.toString())
                    Log.i("Mayank",msg.toString())
                    messages.add(msg)

                }
                binding.etmessage.text.clear()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        binding.recyclerViewDiscuss.layoutManager=LinearLayoutManager(this)
        myAdapter= MyAdapter(messages,this@DiscussionForumActivity)
        binding.recyclerViewDiscuss.adapter=myAdapter


        binding.send.setOnClickListener {
            if(binding.etmessage.text.toString()!=""){
                val msg=MSG(user,binding.etmessage.text.toString())
                writeNewMessage(msg)
            }

        }




    }

     private fun writeNewMessage(msg:MSG){
         databaseReference.push().setValue(msg)
     }
}