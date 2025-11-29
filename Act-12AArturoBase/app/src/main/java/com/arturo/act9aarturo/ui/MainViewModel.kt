package com.arturo.act9aarturo.ui

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arturo.act9aarturo.data.model.Character
import com.arturo.act9aarturo.data.model.DimensionLog
import com.arturo.act9aarturo.data.model.Reward
import com.arturo.act9aarturo.data.model.Venue
import com.arturo.act9aarturo.data.model.Bounty
import com.arturo.act9aarturo.data.repository.NeonRepository
import com.arturo.act9aarturo.data.supabase.SupabaseManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel(private val repository: NeonRepository) : ViewModel() {

    enum class AppState { PORTAL_MODE, DIMENSION_MODE }

    private val _appState = MutableLiveData<AppState>(AppState.PORTAL_MODE)
    val appState: LiveData<AppState> = _appState

    private val _mascot = MutableLiveData<Character?>()
    val mascot: LiveData<Character?> = _mascot

    private val _currentVenue = MutableLiveData<Venue?>()
    val currentVenue: LiveData<Venue?> = _currentVenue

    private val _venueDeals = MutableLiveData<List<Reward>>()
    val venueDeals: LiveData<List<Reward>> = _venueDeals

    private val _userPoints = MutableLiveData<Int>()
    val userPoints: LiveData<Int> = _userPoints
    
    private val _lastWonReward = MutableLiveData<Reward?>()
    val lastWonReward: LiveData<Reward?> = _lastWonReward
    
    private val _myRewards = MutableLiveData<List<Reward>>()
    val myRewards: LiveData<List<Reward>> = _myRewards
    
    private val _visitHistory = MutableLiveData<List<DimensionLog>>()
    val visitHistory: LiveData<List<DimensionLog>> = _visitHistory
    
    private val _bounties = MutableLiveData<List<Bounty>>()
    val bounties: LiveData<List<Bounty>> = _bounties

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadInitialData()
    }

    fun loadInitialData() {
        viewModelScope.launch {
            _isLoading.value = true
            _mascot.value = repository.getRandomMascot()
            
            // Cargar Bounties
            try {
                _bounties.value = SupabaseManager.getBounties()
            } catch (e: Exception) { /* Fallback */ }

            if (repository.hasSpun()) {
                val venue = repository.getCurrentVenue()
                _currentVenue.value = venue
                _userPoints.value = repository.getUserBalance()
                _appState.value = AppState.DIMENSION_MODE
                venue?.let { loadVenueDeals(it.name) }
            }
            _isLoading.value = false
        }
    }

    fun checkDailyLogin(prefs: SharedPreferences) {
        val lastLoginTime = prefs.getLong("last_login", 0)
        val streak = prefs.getInt("streak", 0)
        
        val currentTime = System.currentTimeMillis()
        val oneDayMillis = 24 * 60 * 60 * 1000L
        
        // Si ha pasado más de un día pero menos de dos (Racha continúa)
        if (currentTime - lastLoginTime in oneDayMillis..(oneDayMillis * 2)) {
            val newStreak = streak + 1
            val bonus = 100 * newStreak
            
            prefs.edit().putLong("last_login", currentTime).putInt("streak", newStreak).apply()
            
            // Dar puntos (Simulado en repositorio: restar negativo es sumar)
            repository.redeemPoints(-bonus) 
            _userPoints.value = repository.getUserBalance()
            
            _lastWonReward.value = Reward(
                title = "DAILY STREAK: $newStreak DAYS!", 
                description = "You got schwifty! +$bonus Flurbos", 
                points = bonus
            )
        } 
        // Si es el primer login o se rompió la racha
        else if (currentTime - lastLoginTime > oneDayMillis * 2 || lastLoginTime == 0L) {
            prefs.edit().putLong("last_login", currentTime).putInt("streak", 1).apply()
            repository.redeemPoints(-100)
            _userPoints.value = repository.getUserBalance()
            
            _lastWonReward.value = Reward(
                title = "DAILY BONUS", 
                description = "Welcome back! +100 Flurbos", 
                points = 100
            )
        }
    }

    fun activatePortal() {
        if (repository.hasSpun()) return

        viewModelScope.launch {
            _isLoading.value = true
            delay(2500)
            
            try {
                val (reward, venue) = repository.travelToDimension()
                
                // Log Visita
                val log = DimensionLog(
                    venue_name = venue.name,
                    visit_date = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date()),
                    points_earned = reward.points
                )
                SupabaseManager.logVisit(log)

                _lastWonReward.value = reward
                _currentVenue.value = venue
                _userPoints.value = repository.getUserBalance()
                _mascot.value = repository.getRandomMascot()
                loadVenueDeals(venue.name)
                
                _appState.value = AppState.DIMENSION_MODE
                refreshRewards()
            } catch (e: Exception) {
                // Error handling
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    private suspend fun loadVenueDeals(venueName: String) {
        _venueDeals.value = SupabaseManager.getVenueDeals(venueName)
    }
    
    fun spendPoints(amount: Int) {
        if (repository.redeemPoints(amount)) {
            _userPoints.value = repository.getUserBalance()
        }
    }
    
    fun refreshRewards() {
        viewModelScope.launch {
             _isLoading.value = true
             _myRewards.value = repository.getMyRewards()
             _visitHistory.value = SupabaseManager.getVisitHistory()
             _isLoading.value = false
        }
    }
}