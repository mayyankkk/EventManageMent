package com.example.eventmanagement

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.eventmanagement.databinding.ActivityAddNewEventBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class AddNewEvent : AppCompatActivity() {
    private val SELECT_PICTURE= 200
    lateinit var binding: ActivityAddNewEventBinding
    lateinit var storageRef:StorageReference
    private var mProgressDialog: Dialog? = null
    private var imageUri:Uri?=null
    lateinit var db:DatabaseReference
    private var counter:Int=0
    var flag=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNewEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storageRef=FirebaseStorage.getInstance().reference
        counter=0
        db=FirebaseDatabase.getInstance().reference


        binding.uploadImage.setOnClickListener {
            selectImage()
        }


        binding.submitBtn.setOnClickListener {
            val name= binding.eventName.text.trim()
            val txt=binding.eventTxt.text.trim()
            if(name!="" && txt!="" && imageUri!=null){

                checkDB(name.toString())
            }
        }
    }

    private fun uploadImage(name:String) {

        val imageRef= storageRef.child("Event_Images").child("${name}.jpg")
        imageRef.putFile(imageUri!!).addOnSuccessListener {
            Toast.makeText(this,"Upload Success",Toast.LENGTH_SHORT).show()
            binding.image.setImageURI(null)
            dismissProgressBar()
        }
            .addOnFailureListener{
                Log.i("Mayank","Image Failed")
            }
    }

    private fun selectImage() {
        val intent=Intent()
        intent.type="image/*"
        intent.action=Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode== RESULT_OK){
            if(requestCode==SELECT_PICTURE){
                imageUri=data?.data
                binding.image.setImageURI(imageUri)
                binding.image.visibility= View.VISIBLE
            }
        }
    }
    private fun showProgressBar() {
        mProgressDialog = Dialog(this@AddNewEvent)
        mProgressDialog!!.setContentView(R.layout.progress_bar)
        mProgressDialog!!.show()
    }
    private fun dismissProgressBar() {
        mProgressDialog?.dismiss()
    }


    private fun submit(){
        db.child("Event_List").child(counter.toString()).child("name").setValue(binding.eventName.text.toString())
        db.child("Event_Details").child(binding.eventName.text.toString()).setValue(binding.eventTxt.text.toString())
        uploadImage(binding.eventName.text.toString())
        counter=0
        flag=false
    }

    private fun showDialog(){
        val dialog= AlertDialog.Builder(this)
        dialog.setMessage("Do you want to update the Event?")
        dialog.setTitle("Alert!")
        dialog.setPositiveButton("Yes",DialogInterface.OnClickListener{ _, _ ->
            submit()
        })
        dialog.setNegativeButton("No",DialogInterface.OnClickListener{_,_ ->
            finish()
        })
        dialog.show()
    }

    private fun checkDB(name:String){
        db.child("Event_List").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                showProgressBar()
                for(index in snapshot.children){
                    counter++
                    if(index.child("name").value.toString()==name){
                        showDialog()
                        flag=true
                        break
                    }
                }
                if(!flag){
                    counter++
                    submit()
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}