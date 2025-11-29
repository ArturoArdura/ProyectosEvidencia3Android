package com.arturo.act9aarturo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arturo.act9aarturo.R
import com.arturo.act9aarturo.models.Alien

class AlienAdapter(
    private val aliens: List<Alien>,
    private val onEdit: (Alien) -> Unit,
    private val onDelete: (Alien) -> Unit
) : RecyclerView.Adapter<AlienAdapter.AlienViewHolder>() {

    class AlienViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.alien_name)
        val species: TextView = view.findViewById(R.id.alien_species)
        val btnEdit: ImageButton = view.findViewById(R.id.btn_edit_alien)
        val btnDelete: ImageButton = view.findViewById(R.id.btn_delete_alien)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlienViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_alien, parent, false)
        return AlienViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlienViewHolder, position: Int) {
        val alien = aliens[position]
        holder.name.text = alien.name
        holder.species.text = alien.species
        
        holder.btnEdit.setOnClickListener { onEdit(alien) }
        holder.btnDelete.setOnClickListener { onDelete(alien) }
    }

    override fun getItemCount() = aliens.size
}