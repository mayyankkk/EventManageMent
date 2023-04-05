package com.example.eventmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.eventmanagement.databinding.ActivityAdminLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class AdminLoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityAdminLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var fStore: FirebaseFirestore
    lateinit var df: CollectionReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fStore = FirebaseFirestore.getInstance()
        auth = Firebase.auth
        df = fStore.collection("Users")

        binding.signInBtn.setOnClickListener {
            val email=binding.etName.text.toString()
            val password=binding.etPass.text.toString()
            if(email!="" && password!=""){
                Log.i("Mayank",email)
                checkAdmin(email,password)
            }
        }


    }

    override fun onStart() {
        super.onStart()
        if(auth.currentUser!=null){
            startActivity(Intent(this,AdminActivity::class.java))
            finish()
        }
    }

    private fun checkAdmin(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                check(auth.currentUser!!.uid)
            } else {
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun check(uid:String){

        df.document(uid).get().addOnSuccessListener {
           val doc= it.toObject<Admin>()!!
            Log.i("Mayank",doc.toString())
            if(doc.isAdmin){
                startActivity(Intent(this,AdminActivity::class.java))
                finish()
            }
        }
    }
}