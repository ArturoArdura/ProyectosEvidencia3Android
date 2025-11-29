package com.arturo.act9aarturo.ui.gallery

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.arturo.act9aarturo.R
import com.arturo.act9aarturo.adapters.InventoryAdapter
import com.arturo.act9aarturo.databinding.FragmentGalleryBinding
import com.arturo.act9aarturo.ui.MainViewModel
import com.arturo.act9aarturo.ui.ViewModelFactory

class MyRewardsFragment : Fragment(R.layout.fragment_gallery) {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGalleryBinding.bind(view)

        val factory = ViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]

        setupRecyclerView()
        setupObservers()

        // Cargar datos al entrar
        viewModel.refreshRewards()
    }

    private fun setupRecyclerView() {
        // Usamos GridLayoutManager para el inventario tipo mochila
        binding.rewardsRecyclerView.layoutManager = GridLayoutManager(context, 3)
    }

    private fun setupObservers() {
        // 1. Observar lista de recompensas (ahora items de inventario)
        viewModel.myRewards.observe(viewLifecycleOwner) { rewards ->
            if (rewards.isEmpty()) {
                Toast.makeText(context, "Jerry's Backpack is empty.", Toast.LENGTH_SHORT).show()
            }
            // Usamos el nuevo InventoryAdapter
            binding.rewardsRecyclerView.adapter = InventoryAdapter(rewards) { item ->
                Toast.makeText(context, "Used item: ${item.title}", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Observar estado de carga para ocultar el spinner
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingSpinner.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}