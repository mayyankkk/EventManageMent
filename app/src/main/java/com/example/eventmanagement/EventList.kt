package com.example.eventmanagement

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventmanagement.databinding.ActivityEventListBinding
import com.google.firebase.database.*

class EventList : AppCompatActivity() {
    companion object {
        const val KEY = "com.example.eventmanagement.EventList.KEY"
    }

    private lateinit var binding: ActivityEventListBinding
    private lateinit var eventAdapter: EventAdapter
    private lateinit var events: ArrayList<Event>
    private lateinit var dbReference: DatabaseReference
    private var mProgressDialog: Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!isInternetConnected()){
            Toast.makeText(this,"Please Connect To the internet",Toast.LENGTH_SHORT).show()
            finish()
        }


        showProgressBar()
        dbReference = FirebaseDatabase.getInstance().reference.child("Event_List")
        events = arrayListOf()

        dbReference.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                events.clear()
                dismissProgressBar()
                for (index in snapshot.children) {
                    val event = Event(
                        index.child("name").value.toString(),
                        index.child("num").value.toString()
                    )
                    events.add(event)
                }
                eventAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EventList, "Database error", Toast.LENGTH_SHORT).show()
            }

        })




        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(this)
        eventAdapter = EventAdapter(events)
        binding.recyclerViewEvents.adapter = eventAdapter


        eventAdapter.setOnClickListener(object : EventAdapter.OnClickListener {
            override fun onClick(position: Int, model: Event) {
                startEventDetails(model.name)
            }

        })

    }

    public fun startEventDetails(extra: String) {
        val intent = Intent(this, EventDetails::class.java)
        intent.putExtra("KEY", extra)
        startActivity(intent)
    }

    private fun showProgressBar() {
        mProgressDialog = Dialog(this@EventList)
        mProgressDialog!!.setContentView(R.layout.progress_bar)
        mProgressDialog!!.show()
    }
    private fun isInternetConnected(): Boolean {
        val cm: ConnectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
    private fun dismissProgressBar() {
        mProgressDialog?.dismiss()
    }

}