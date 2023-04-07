package com.example.eventmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RegAdapter(var events: ArrayList<EventData>) : RecyclerView.Adapter<RegAdapter.ViewHolder>() {

    private var onClickListener: OnClickListener? = null


    // A function to bind the onclickListener.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: EventData)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegAdapter.ViewHolder {
        val view=LayoutInflater.from(parent.context)
            .inflate(R.layout.reg,parent,false)
        return ViewHolder(view)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val tv1:TextView
        val tv2:TextView
        val tv3:TextView
        val tv4:TextView


        init {
            tv1=view.findViewById(R.id.tv1)
            tv2=view.findViewById(R.id.tv2)
            tv3=view.findViewById(R.id.tv3)
            tv4=view.findViewById(R.id.tv4)
        }
    }

    override fun onBindViewHolder(holder: RegAdapter.ViewHolder, position: Int) {
        holder.tv1.text= "Reg. No:${events[position].RegNo}"
        holder.tv2.text="Name: ${events[position].name}"
        holder.tv3.text="College Name: ${events[position].collegeName}"
        holder.tv4.text="Event Name: ${events[position].eventName}"
        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, events[position] )
            }
        }

    }

    override fun getItemCount(): Int {
        return events.size
    }

}
