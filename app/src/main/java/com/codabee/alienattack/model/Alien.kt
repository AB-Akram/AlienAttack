package com.codabee.alienattack.model

import androidx.annotation.DrawableRes
import com.codabee.alienattack.R

data class Alien(
    @DrawableRes val image: Int = R.drawable.alien0,
    val timeToAttack: Int = 600,
    val distance: Int = 600,
    val position: Float = 1.0F,
    val isActive: Boolean = false
)
