package com.example.eventmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.example.eventmanagement.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignUpBinding
    lateinit var auth: FirebaseAuth
    lateinit var fStore: FirebaseFirestore
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent=Intent(this,MainActivity::class.java)

            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fStore= FirebaseFirestore.getInstance()

        auth= Firebase.auth


        binding.signUpBtn.setOnClickListener {
            if(binding.etSignUpEmail.text.toString()=="" || binding.etSignUpPass.text.toString()==""|| binding.etSignUpName.text.toString()==""){
                Toast.makeText(this,"Enter valid email/password/name",Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(binding.etSignUpEmail.text.toString(), binding.etSignUpPass.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            val intent=Intent(this,MainActivity::class.java)
                            val df= fStore.collection("Users").document(auth.currentUser?.uid!!)
                            val userInfo= Admin(binding.etSignUpEmail.text.toString(),false,binding.etSignUpName.text.toString())
                            df.set(userInfo)
                            startActivity(intent)
                            finish()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            }

        }

    }
}