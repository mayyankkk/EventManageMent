package com.example.eventmanagement

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventmanagement.databinding.ActivityRegistrationBinding
import com.google.firebase.database.*

class Registration : AppCompatActivity() {
    private lateinit var binding:ActivityRegistrationBinding
    lateinit var events: ArrayList<Event>
    private lateinit var db:DatabaseReference
    private lateinit var  regAdapter:EventAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        events= arrayListOf()

        db= FirebaseDatabase.getInstance().reference.child("Event_List")
        db.addValueEventListener(object :ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                events.clear()
                for(index in snapshot.children){
                    val event=Event(index.child("name").value.toString(),
                    index.child("num").value.toString()
                    )
                    events.add(event)
                }
                regAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.recyclerViewReg.layoutManager=LinearLayoutManager(this)
        regAdapter= EventAdapter(events)
        binding.recyclerViewReg.adapter= regAdapter

        regAdapter.setOnClickListener(object :EventAdapter.OnClickListener{
            override fun onClick(position: Int, model: Event) {
                startActivityDetails(model.name)
            }

        })
    }

    private fun startActivityDetails(name: String) {
        val intent= Intent(this,Registrations::class.java)
        intent.putExtra("EventName",name)
        startActivity(intent)
    }
}