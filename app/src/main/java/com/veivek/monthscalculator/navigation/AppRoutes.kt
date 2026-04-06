package com.veivek.monthscalculator.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class AppRoutes : NavKey {
    @Serializable
    object PensionCalculator : NavKey

    @Serializable
    object MonthsCalculator : NavKey
}