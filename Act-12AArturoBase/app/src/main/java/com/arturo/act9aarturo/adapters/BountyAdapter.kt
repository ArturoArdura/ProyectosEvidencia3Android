package com.arturo.act9aarturo.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arturo.act9aarturo.R
import com.arturo.act9aarturo.data.model.Bounty

class BountyAdapter(
    private val bounties: List<Bounty>,
    private val onAcceptClick: (Bounty) -> Unit
) : RecyclerView.Adapter<BountyAdapter.BountyViewHolder>() {

    class BountyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: TextView = view.findViewById(R.id.bounty_icon)
        val title: TextView = view.findViewById(R.id.bounty_title)
        val reward: TextView = view.findViewById(R.id.bounty_reward)
        val btnAccept: Button = view.findViewById(R.id.btn_accept_bounty)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BountyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bounty, parent, false)
        return BountyViewHolder(view)
    }

    override fun onBindViewHolder(holder: BountyViewHolder, position: Int) {
        val bounty = bounties[position]
        
        holder.icon.text = bounty.icon_res
        holder.title.text = bounty.title
        holder.reward.text = "+${bounty.reward_points} PTS"

        if (bounty.isCompleted) {
            holder.btnAccept.text = "DONE"
            holder.btnAccept.isEnabled = false
            holder.btnAccept.setBackgroundColor(Color.DKGRAY)
        } else {
            holder.btnAccept.text = "ACCEPT"
            holder.btnAccept.isEnabled = true
            // El color original se maneja en el XML, pero aquí podríamos restaurarlo si fuera necesario
        }
        
        holder.btnAccept.setOnClickListener {
            onAcceptClick(bounty)
            bounty.isCompleted = true // Actualización local optimista
            notifyItemChanged(position)
        }
    }

    override fun getItemCount() = bounties.size
}