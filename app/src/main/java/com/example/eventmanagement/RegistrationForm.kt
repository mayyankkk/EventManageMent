package com.example.eventmanagement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.eventmanagement.EventDetails.Companion.KEY1
import com.example.eventmanagement.databinding.ActivityRegistrationFormBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

const val TAG = "MAYANK"

class RegistrationForm : AppCompatActivity(), PaymentResultListener {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: ActivityRegistrationFormBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationFormBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitBtn.setOnClickListener {
            if (binding.etEmail1.text.toString() != "" && binding.etName1.text.toString() != "" && binding.etCollegeName1.text.toString() != "" && binding.etRegNo1.text.toString() != "") {
                makepayment()
            } else {
                Toast.makeText(this, "Check Name/Reg No./College Name", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveInFirebase() {
        val eventName= intent.getStringExtra(KEY1).toString()
        val name = binding.etName1.text.toString()
        val regNo = binding.etRegNo1.text.toString().trim()
        val collegeName = binding.etCollegeName1.text.toString()
        val eventData = EventData(eventName, name, collegeName, regNo)
        databaseReference = FirebaseDatabase.getInstance().getReference("Events")
        databaseReference.child(eventName).child(regNo).setValue(eventData)
            .addOnSuccessListener {
                Toast.makeText(this, "You're registered for the $eventName", Toast.LENGTH_LONG)
                    .show()
            }
            .addOnFailureListener {
                Log.d(TAG, "some error occurred")
            }
    }

    private fun makepayment() {
        val co = Checkout()
        try {
            val options = JSONObject()
            options.put("name", "EventManager")
            options.put("description", "Pay 500")
            //You can omit the image option to fetch the image from the dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put("amount", "5000")//pass amount in currency subunits
            val prefill = JSONObject()
            prefill.put("email", "")
            prefill.put("contact", "")
            options.put("prefill", prefill)
            co.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success!", Toast.LENGTH_LONG).show()
        saveInFirebase()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_LONG).show()
    }
}
