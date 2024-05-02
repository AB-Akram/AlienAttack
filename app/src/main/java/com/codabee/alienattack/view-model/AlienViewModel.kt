package com.codabee.alienattack.`view-model`

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codabee.alienattack.R
import com.codabee.alienattack.model.Alien
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlienViewModel : ViewModel() {
    private val _uiState =
        MutableStateFlow(Alien()) //A mutable StateFlow that provides a setter for value
    var state: StateFlow<Alien> = _uiState.asStateFlow()

    //SetupCoroutine
    var alienCoroutine: Job = Job()

    //OnStart
    fun onStart() {
        setAlienActivity(true)
        //launch my coroutine
        viewModelScope.launch {
            alienCoroutine = launch {
                attack()
            }
        }
    }

    //OnCancel
    fun onCancel() {
        alienCoroutine.cancel()
        setAlienActivity(false)
    }

    //OnReset
    fun onReset() {
        _uiState.update {
            it.copy(
                image = R.drawable.alien0,
                isActive = false,
                distance = 600,
                position = 1.0F
            )
        }
    }

    //UpdatePosition
    fun updatePosition(): Float {
        val currentFloat = _uiState.value.distance.toFloat()
        val totalFloat = _uiState.value.timeToAttack.toFloat()
        val position = currentFloat / totalFloat
        if (position == 0.0F) return 1.1F
        return position
    }

    //UpdateImage
    fun updateImage(current: Int): Int {
        return when (current) {
            R.drawable.alien0 -> R.drawable.alien1
            R.drawable.alien1 -> R.drawable.alien2
            R.drawable.alien2 -> R.drawable.alien3
            R.drawable.alien3 -> R.drawable.alien4
            R.drawable.alien4 -> R.drawable.alien5
            R.drawable.alien5 -> R.drawable.alien6
            R.drawable.alien6 -> R.drawable.alien7
            R.drawable.alien7 -> R.drawable.alien8
            else -> R.drawable.alien0
        }
    }

    //UpdateActivity
    fun setAlienActivity(boolean: Boolean) {
        _uiState.update { it.copy(isActive = boolean) } //copy function will create a new object with copies of all values from the current object
    }

    //Suspend Attack
    suspend fun attack() {
        while (_uiState.value.distance > 0) {
            delay(100) //1s
            val newImage = updateImage(_uiState.value.image)
            _uiState.update {
                it.copy(
                    image = newImage,
                    distance = _uiState.value.distance - 10,
                    position = updatePosition()
                )
            }
        }
    }
}
