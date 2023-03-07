package com.example.eventmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast
import com.example.eventmanagement.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignUpBinding
    lateinit var auth: FirebaseAuth
    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                            val user = auth.currentUser
                            val intent=Intent(this,MainActivity::class.java)
                            startActivity(intent)
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