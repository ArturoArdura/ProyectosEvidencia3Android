package com.arturo.act9aarturo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arturo.act9aarturo.R
import com.arturo.act9aarturo.data.model.Reward

class RewardAdapter(
    private val rewards: List<Reward>,
    private val onClaimClick: (Reward) -> Unit
) : RecyclerView.Adapter<RewardAdapter.RewardViewHolder>() {

    class RewardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.reward_title)
        val description: TextView = view.findViewById(R.id.reward_points) // Using existing ID for description for now
        val btnClaim: Button = view.findViewById(R.id.btn_claim)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RewardViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reward, parent, false)
        return RewardViewHolder(view)
    }

    override fun onBindViewHolder(holder: RewardViewHolder, position: Int) {
        val reward = rewards[position]
        holder.title.text = reward.title
        // Display description instead of points or both
        holder.description.text = reward.description
        
        holder.btnClaim.isEnabled = !reward.is_redeemed
        holder.btnClaim.text = if (reward.is_redeemed) "USED" else "CLAIM"
        
        holder.btnClaim.setOnClickListener {
            onClaimClick(reward)
        }
    }

    override fun getItemCount() = rewards.size
}