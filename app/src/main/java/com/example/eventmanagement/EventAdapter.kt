package com.example.eventmanagement

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView


class EventAdapter(var events:ArrayList<Event>) : RecyclerView.Adapter<EventAdapter.ViewHolder>(){
    private var onClickListener: OnClickListener? = null


    // A function to bind the onclickListener.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: Event)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.event,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventAdapter.ViewHolder, position: Int) {
        holder.btn.text= events[position].name
        holder.btn.tag = events[position].num
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, events[position] )
            }
        }
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
         val btn:Button
        init {
            btn= view.findViewById(R.id.eventBtn)

        }
    }

    override fun getItemCount(): Int {
        return events.size
    }
}