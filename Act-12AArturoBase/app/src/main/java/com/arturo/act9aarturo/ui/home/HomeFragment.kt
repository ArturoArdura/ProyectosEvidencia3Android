package com.arturo.act9aarturo.ui.home

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arturo.act9aarturo.R
import com.arturo.act9aarturo.adapters.BountyAdapter
import com.arturo.act9aarturo.adapters.RewardAdapter
import com.arturo.act9aarturo.data.model.Reward
import com.arturo.act9aarturo.databinding.FragmentHomeBinding
import com.arturo.act9aarturo.ui.MainViewModel
import com.arturo.act9aarturo.ui.ViewModelFactory
import com.bumptech.glide.Glide

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainViewModel
    private var portalAnimator: ObjectAnimator? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        
        val factory = ViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)[MainViewModel::class.java]

        // Check Daily Login / Streak
        val prefs = requireContext().getSharedPreferences("neon_perks_prefs", android.content.Context.MODE_PRIVATE)
        viewModel.checkDailyLogin(prefs)

        setupUI()
        setupObservers()
    }

    private fun setupUI() {
        // Animación suave del portal (idle)
        portalAnimator = ObjectAnimator.ofFloat(binding.imgPortal, "rotation", 0f, 360f).apply {
            duration = 8000
            repeatCount = ObjectAnimator.INFINITE
            interpolator = LinearInterpolator()
            start()
        }

        binding.btnOpenPortal.setOnClickListener {
            spinThePortal()
        }
        
        // Configurar el RecyclerView para los Deals
        binding.recyclerDeals.layoutManager = LinearLayoutManager(context)
        
        // Configurar el RecyclerView para Bounties (Horizontal)
        binding.recyclerBounties.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun spinThePortal() {
        // Acelerar la animación
        portalAnimator?.cancel()
        portalAnimator = ObjectAnimator.ofFloat(binding.imgPortal, "rotation", 0f, 3600f).apply {
            duration = 2000 // 2 segundos de giro rápido
            interpolator = LinearInterpolator()
            start()
        }
        
        viewModel.activatePortal()
    }

    private fun setupObservers() {
        // Control de Estados (Portal vs Dimensión)
        viewModel.appState.observe(viewLifecycleOwner) { state ->
            if (state == MainViewModel.AppState.PORTAL_MODE) {
                binding.groupPortalMode.visibility = View.VISIBLE
                binding.groupDimensionMode.visibility = View.GONE
                binding.txtMascotDialog.text = "Morty! We gotta spin the portal to find a dimension with rewards!"
            } else {
                binding.groupPortalMode.visibility = View.GONE
                binding.groupDimensionMode.visibility = View.VISIBLE
                portalAnimator?.cancel() // Detener animación
            }
        }

        viewModel.mascot.observe(viewLifecycleOwner) { character ->
            character?.let {
                Glide.with(this).load(it.image).circleCrop().into(binding.imgMascot)
                // Mensaje contextual
                if (viewModel.appState.value == MainViewModel.AppState.DIMENSION_MODE) {
                    binding.txtMascotDialog.text = "Welcome to this dimension! Don't touch anything."
                }
            }
        }
        
        viewModel.currentVenue.observe(viewLifecycleOwner) { venue ->
            venue?.let {
                binding.txtVenueName.text = it.name
                binding.txtVenueDesc.text = it.description
            }
        }
        
        viewModel.userPoints.observe(viewLifecycleOwner) { points ->
            binding.txtBalanceValue.text = points.toString()
        }

        // OBSERVAMOS LOS DEALS (OFERTAS) Y LLENAMOS LA LISTA
        viewModel.venueDeals.observe(viewLifecycleOwner) { deals ->
            binding.recyclerDeals.adapter = RewardAdapter(deals) { deal ->
                // Al hacer clic, intentamos comprar
                if (viewModel.userPoints.value ?: 0 >= deal.points) {
                    viewModel.spendPoints(deal.points)
                    
                    // NUEVO: Al comprar, también añadimos al inventario
                    // Para simplificar, llamamos al VM (debería tener una funcion para esto)
                    // Pero como spendPoints solo actualiza el saldo local y remoto,
                    // aquí podríamos necesitar una llamada adicional para guardar en 'inventory'.
                    // Idealmente viewModel.spendPoints debería manejar esto.
                    
                    Toast.makeText(context, "Purchased: ${deal.title}", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Not enough Flurbos!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        
        // OBSERVAMOS LAS BOUNTIES (MISIONES)
        viewModel.bounties.observe(viewLifecycleOwner) { bounties ->
            binding.recyclerBounties.adapter = BountyAdapter(bounties) { bounty ->
                Toast.makeText(context, "Accepted mission: ${bounty.title}", Toast.LENGTH_SHORT).show()
                // Aquí podríamos actualizar el estado en Supabase
            }
        }

        viewModel.lastWonReward.observe(viewLifecycleOwner) { reward ->
            reward?.let {
                showArrivalDialog(it)
            }
        }
        
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.loadingSpinner.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnOpenPortal.isEnabled = !isLoading
        }
    }
    
    private fun showArrivalDialog(reward: Reward) {
        android.app.AlertDialog.Builder(requireContext())
            .setTitle("DIMENSION REACHED!")
            .setMessage("You landed safely.\n\n${reward.title}\n${reward.description}")
            .setPositiveButton("Let's Party", null)
            .show()
    }
}