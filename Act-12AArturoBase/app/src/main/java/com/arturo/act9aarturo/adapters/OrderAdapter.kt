package com.arturo.act9aarturo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arturo.act9aarturo.R
import com.arturo.act9aarturo.models.OrderWithDetails

class OrderAdapter(
    private val orders: List<OrderWithDetails>
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val id: TextView = view.findViewById(R.id.order_id)
        val desc: TextView = view.findViewById(R.id.order_desc)
        val points: TextView = view.findViewById(R.id.order_points)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.id.text = "#${order.id}"
        
        val alienName = order.aliens?.name ?: "Unknown Alien"
        val rewardTitle = order.rewards?.title ?: "Unknown Item"
        
        holder.desc.text = "$alienName ordered $rewardTitle"
        holder.points.text = "Cost: ${order.rewards?.points ?: 0} pts"
    }

    override fun getItemCount() = orders.size
}