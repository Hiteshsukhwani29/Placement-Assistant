package com.hitesh.genie.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hitesh.placementassistant.OffCampusModel
import com.hitesh.placementassistant.R

class OnCampusAdapter(private val mList: List<OffCampusModel>) : RecyclerView.Adapter<OnCampusAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_job, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val list : OffCampusModel = mList[position]
        holder.companyname.text = list.companyname
        holder.payrange.text = list.payrange
        holder.skillrequired.text = list.skills
        holder.eligibility.text = list.eligibility
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val companyname: TextView = itemView.findViewById(R.id.tv_companyname)
        val payrange: TextView = itemView.findViewById(R.id.tv_payrange)
        val skillrequired: TextView = itemView.findViewById(R.id.tv_skillsrequired)
        val eligibility: TextView = itemView.findViewById(R.id.tv_eligibility)
    }
}