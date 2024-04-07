package com.example.bitfit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitfit.R

class HealthAdapter(healths1: Context, private val healths: MutableList<DisplayHealth>): RecyclerView.Adapter<HealthAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateView: TextView = itemView.findViewById(R.id.date)
        val cView : TextView = itemView.findViewById(R.id.cname)
        val sView: TextView = itemView.findViewById(R.id.sname)
        val cnView : TextView = itemView.findViewById(R.id.no_calories)
        val snView : TextView = itemView.findViewById(R.id.no_sleep)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Implement your onCreateViewHolder logic here
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val healthView = inflater.inflate(R.layout.health_view, parent, false)
        return ViewHolder(healthView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Implement your onBindViewHolder logic here
        val health = healths[position]
        holder.dateView.text = health.date
        holder.cnView.text = health.no_calories.toString()
        holder.snView.text = health.no_sleep.toString()

    }

    override fun getItemCount(): Int {
        return healths.size
    }
}