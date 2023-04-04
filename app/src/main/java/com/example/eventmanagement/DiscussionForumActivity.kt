package com.example.eventmanagement

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventmanagement.databinding.ActivityDiscussionForumBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DiscussionForumActivity : AppCompatActivity() {
    private lateinit var binding:ActivityDiscussionForumBinding
    lateinit var messages: ArrayList<MSG>
    private lateinit var myAdapter:MyAdapter
    private var mProgressDialog:Dialog?=null
    private lateinit var databaseReference:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityDiscussionForumBinding.inflate(layoutInflater)

        if(!isInternetConnected()){
            Toast.makeText(this,"Please Connect To the internet",Toast.LENGTH_SHORT).show()
            finish()
        }

        setContentView(binding.root)
        val email=Firebase.auth.currentUser?.email.toString()
        val user= email.dropLast(10)
        Log.i("MAYANK",user)
        showProgressBar()
        databaseReference=FirebaseDatabase.getInstance().reference.child("Messages")


        messages= arrayListOf()



        databaseReference.addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.i("Mayank",snapshot.toString())
                messages.clear()
                dismissProgressBar()
                for(index in snapshot.children){
                    val msg= MSG(index.child("name").value.toString(),index.child("msg").value.toString())
                    Log.i("Mayank",msg.toString())
                    messages.add(msg)

                }
                myAdapter.notifyDataSetChanged()
                binding.etmessage.text.clear()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        binding.recyclerViewDiscuss.layoutManager=LinearLayoutManager(this)
        (binding.recyclerViewDiscuss.layoutManager as LinearLayoutManager).stackFromEnd = true;
        myAdapter= MyAdapter(messages,this@DiscussionForumActivity)
        binding.recyclerViewDiscuss.adapter=myAdapter


        binding.send.setOnClickListener {
            if(binding.etmessage.text.toString()!=""){
                val msg=MSG(user,binding.etmessage.text.toString())
                writeNewMessage(msg)
            }
            binding.recyclerViewDiscuss.smoothScrollToPosition(myAdapter.itemCount)

        }




    }
    private fun isInternetConnected(): Boolean {
        val cm: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
    private fun showProgressBar(){
        mProgressDialog= Dialog(this@DiscussionForumActivity)
        mProgressDialog!!.setContentView(R.layout.progress_bar)
        mProgressDialog!!.show()
    }

    private fun dismissProgressBar(){
        mProgressDialog?.dismiss()
    }

     private fun writeNewMessage(msg:MSG){
         databaseReference.push().setValue(msg)
     }
}