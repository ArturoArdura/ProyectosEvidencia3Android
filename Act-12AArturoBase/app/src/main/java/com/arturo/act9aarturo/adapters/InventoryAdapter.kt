package com.arturo.act9aarturo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arturo.act9aarturo.R
import com.arturo.act9aarturo.data.model.Reward

class InventoryAdapter(
    private val items: List<Reward>,
    private val onUseClick: (Reward) -> Unit
) : RecyclerView.Adapter<InventoryAdapter.InventoryViewHolder>() {

    class InventoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: TextView = view.findViewById(R.id.item_icon)
        val name: TextView = view.findViewById(R.id.item_name)
        val status: TextView = view.findViewById(R.id.item_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InventoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inventory_grid, parent, false)
        return InventoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: InventoryViewHolder, position: Int) {
        val item = items[position]
        holder.name.text = item.title
        
        // Lógica visual simple
        holder.icon.text = if (item.title.contains("Snack")) "🍔" else "🔮"
        
        holder.itemView.setOnClickListener { onUseClick(item) }
    }

    override fun getItemCount() = items.size
}