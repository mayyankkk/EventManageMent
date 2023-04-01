package com.example.eventmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventmanagement.databinding.ActivityEventListBinding
import com.google.firebase.database.*

class EventList : AppCompatActivity() {
    companion object{
        const val KEY="com.example.eventmanagement.EventList.KEY"
    }
    private lateinit var binding: ActivityEventListBinding
    private lateinit var eventAdapter: EventAdapter
    private lateinit var events: ArrayList<Event>
    private lateinit var dbReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEventListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbReference=FirebaseDatabase.getInstance().reference.child("Event_List")
        events= arrayListOf()

        dbReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                events.clear()
                for(index in snapshot.children){
                    val event= Event(index.child("name").value.toString(),index.child("num").value.toString())
                    events.add(event)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EventList,"Database error",Toast.LENGTH_SHORT) .show()
            }

        })




        binding.recyclerViewEvents.layoutManager=LinearLayoutManager(this)
        eventAdapter= EventAdapter(events)
        binding.recyclerViewEvents.adapter=eventAdapter


        eventAdapter.setOnClickListener(object : EventAdapter.OnClickListener {
            override fun onClick(position: Int, model: Event) {
                startEventDetails(model.name)
            }

        })

    }

    public fun startEventDetails(extra: String){
        val intent= Intent(this,EventDetails:: class.java)
        intent.putExtra("KEY",extra)
        startActivity(intent)
    }


}