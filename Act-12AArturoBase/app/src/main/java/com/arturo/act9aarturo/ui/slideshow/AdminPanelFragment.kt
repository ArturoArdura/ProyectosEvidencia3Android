package com.arturo.act9aarturo.ui.slideshow

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.arturo.act9aarturo.adapters.AlienAdapter
import com.arturo.act9aarturo.adapters.OrderAdapter
import com.arturo.act9aarturo.data.SupabaseClient
import com.arturo.act9aarturo.databinding.FragmentSlideshowBinding
import com.arturo.act9aarturo.models.Alien
import com.arturo.act9aarturo.models.Order
import com.arturo.act9aarturo.models.OrderWithDetails
import com.google.android.material.tabs.TabLayout
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.launch

class AdminPanelFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()
        setupAliensList()
        setupOrdersList()
    }

    private fun setupTabs() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    binding.sectionAliens.visibility = View.VISIBLE
                    binding.sectionOrders.visibility = View.GONE
                    fetchAliens()
                } else {
                    binding.sectionAliens.visibility = View.GONE
                    binding.sectionOrders.visibility = View.VISIBLE
                    fetchOrders()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    // --- SECTION: ALIENS (CRUD) ---

    private fun setupAliensList() {
        binding.aliensRecyclerView.layoutManager = LinearLayoutManager(context)
        fetchAliens()

        binding.btnAddAlien.setOnClickListener {
            showAlienDialog(null)
        }
    }

    private fun fetchAliens() {
        lifecycleScope.launch {
            try {
                val aliens = SupabaseClient.client.from("aliens").select().decodeList<Alien>()
                binding.aliensRecyclerView.adapter = AlienAdapter(
                    aliens,
                    onEdit = { alien -> showAlienDialog(alien) },
                    onDelete = { alien -> deleteAlien(alien) }
                )
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading aliens: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showAlienDialog(alien: Alien?) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(if (alien == null) "New Alien" else "Edit Alien")

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(50, 40, 50, 10)

        val nameInput = EditText(context)
        nameInput.hint = "Name (e.g., Birdperson)"
        nameInput.setText(alien?.name ?: "")
        layout.addView(nameInput)

        val speciesInput = EditText(context)
        speciesInput.hint = "Species (e.g., Avian)"
        speciesInput.setText(alien?.species ?: "")
        layout.addView(speciesInput)

        builder.setView(layout)

        builder.setPositiveButton("Save") { _, _ ->
            val name = nameInput.text.toString()
            val species = speciesInput.text.toString()
            if (name.isNotEmpty() && species.isNotEmpty()) {
                saveAlien(alien, name, species)
            }
        }
        builder.setNegativeButton("Cancel", null)
        builder.show()
    }

    private fun saveAlien(alien: Alien?, name: String, species: String) {
        lifecycleScope.launch {
            try {
                if (alien == null) {
                    // Create
                    val newAlien = Alien(name = name, species = species)
                    SupabaseClient.client.from("aliens").insert(newAlien)
                } else {
                    // Update
                    val updatedAlien = alien.copy(name = name, species = species)
                    SupabaseClient.client.from("aliens").update(updatedAlien) {
                        filter {
                            Alien::id eq alien.id
                        }
                    }
                }
                fetchAliens()
            } catch (e: Exception) {
                Toast.makeText(context, "Error saving alien: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteAlien(alien: Alien) {
        lifecycleScope.launch {
            try {
                SupabaseClient.client.from("aliens").delete {
                    filter {
                        Alien::id eq alien.id
                    }
                }
                fetchAliens()
            } catch (e: Exception) {
                Toast.makeText(context, "Error deleting alien", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // --- SECTION: ORDERS (RELATION) ---

    private fun setupOrdersList() {
        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(context)
        
        binding.btnCreateOrder.setOnClickListener {
            createRandomOrder()
        }
    }

    private fun fetchOrders() {
        lifecycleScope.launch {
            try {
                // CAMBIO IMPORTANTE: Pedimos todos los campos (*) de las tablas relacionadas
                // para evitar errores de deserialización porque faltaban campos obligatorios (como species o id).
                val columns = "id, aliens(*), rewards(*)"
                val orders = SupabaseClient.client.from("orders").select(columns = Columns.raw(columns)).decodeList<OrderWithDetails>()
                
                if (orders.isEmpty()) {
                    Toast.makeText(context, "No orders found", Toast.LENGTH_SHORT).show()
                }
                
                binding.ordersRecyclerView.adapter = OrderAdapter(orders)
            } catch (e: Exception) {
                 Toast.makeText(context, "Error loading orders: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createRandomOrder() {
        lifecycleScope.launch {
            try {
                // Intentamos obtener un alien y reward reales para no fallar por Foreign Key
                val aliens = SupabaseClient.client.from("aliens").select().decodeList<Alien>()
                val rewards = SupabaseClient.client.from("rewards").select().decodeList<com.arturo.act9aarturo.models.Reward>()

                if (aliens.isNotEmpty() && rewards.isNotEmpty()) {
                    val randomAlien = aliens.random()
                    val randomReward = rewards.random()
                    
                    val order = Order(alien_id = randomAlien.id, reward_id = randomReward.id)
                    SupabaseClient.client.from("orders").insert(order)
                    fetchOrders()
                    Toast.makeText(context, "Order Created: ${randomAlien.name} -> ${randomReward.title}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Create manual Aliens/Rewards first!", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, "Error creating order: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}