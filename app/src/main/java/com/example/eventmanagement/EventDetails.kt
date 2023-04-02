package com.example.eventmanagement

import android.app.Dialog
import android.content.Intent

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.eventmanagement.databinding.ActivityEventDetailsBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File
import java.io.IOException

class EventDetails : AppCompatActivity() {
    private lateinit var binding: ActivityEventDetailsBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var storageReference: StorageReference
    private  var mProgressDialog:Dialog?=null
    companion object{
        const val  KEY1="com.example.eventmanagement.EventDetails.KEY1"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tag=intent.getStringExtra("KEY")
        showProgressBar()
        storageReference=FirebaseStorage.getInstance().reference.child("Event_Images/${tag}.jpg")

        databaseReference=FirebaseDatabase.getInstance().reference.child("Event_Details")

        if (tag != null) {
            setDetails(tag)
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

    private fun setDetails(tag:String){
        databaseReference.child(tag).addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.eventTxt.text=snapshot.value.toString()
                binding.eventName.text=tag
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Database Erron",error.details)
            }

        })

        try {
            val file= File.createTempFile("tempFile",".jpg")
            storageReference.getFile(file).addOnSuccessListener {
                val bitmap=BitmapFactory.decodeFile(file.absolutePath)
                binding.eventImage.setImageBitmap(bitmap)
                dismissProgressBar()
            }

        }catch (e:IOException){
            e.printStackTrace()
        }

    }

    private fun showProgressBar(){
        mProgressDialog= Dialog(this@EventDetails)
        mProgressDialog!!.setContentView(R.layout.progress_bar)
        mProgressDialog!!.show()
    }

    private fun dismissProgressBar(){
        mProgressDialog?.dismiss()
    }


}