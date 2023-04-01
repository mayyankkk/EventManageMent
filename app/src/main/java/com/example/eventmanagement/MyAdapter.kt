package com.example.eventmanagement

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//Adapter for Discussion forum
class MyAdapter(var messages: ArrayList<MSG>,var context:Activity) :RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val hName: TextView
        val hMsg: TextView
        init {
            hName=itemView.findViewById(R.id.name)
            hMsg=itemView.findViewById(R.id.msg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem= messages[position]
        holder.hMsg.text=messages[position].msg
        holder.hName.text= messages[position].name + " : "
    }
}