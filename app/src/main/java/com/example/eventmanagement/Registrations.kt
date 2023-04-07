package com.example.eventmanagement

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eventmanagement.databinding.ActivityRegistrationsBinding
import com.google.firebase.database.*

class Registrations : AppCompatActivity() {
    lateinit var reg: ArrayList<EventData>
    lateinit var binding:ActivityRegistrationsBinding
    lateinit var db:DatabaseReference
    lateinit var regAdapter: RegAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRegistrationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val name=intent.getStringExtra("EventName").toString()
        reg= arrayListOf()
        db=FirebaseDatabase.getInstance().reference.child("Events").child(name)
        db.addValueEventListener(object: ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                reg.clear()
                for(index in snapshot.children){
                    val event=EventData(index.child("eventName").value.toString(),
                        index.child("name").value.toString(),
                        index.child("collegeName").value.toString(),
                        index.child("regNo").value.toString()
                    )
                    reg.add(event)
                }
                regAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.recyclerViewRegs.layoutManager=LinearLayoutManager(this)
        regAdapter=RegAdapter(reg)
        binding.recyclerViewRegs.adapter=regAdapter
    }
}