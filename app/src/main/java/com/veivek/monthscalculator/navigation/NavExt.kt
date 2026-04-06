package com.veivek.monthscalculator.navigation

import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

/**
 * Navigate to a new screen.
 * If the target screen is already at the top of the stack, replace it instead of adding.
 *
 * @param targetScreen The destination route
 */
fun NavBackStack<NavKey>.navigate(targetScreen: NavKey) {
    if (this.last() != targetScreen) {
        this.add(targetScreen)
    } else {
        this[this.lastIndex] = targetScreen
    }
}

/**
 * Navigate to a new screen and remove the current screen from backstack.
 * Useful for replacing the current screen without adding to navigation history.
 *
 * Example: Login -> Home (remove Login from stack)
 *
 * @param targetScreen The destination route
 */
fun NavBackStack<NavKey>.navigateAndRemoveCurrent(targetScreen: NavKey) {
    this[this.lastIndex] = targetScreen
}

/**
 * Navigate to a screen and clear all other screens from the backstack.
 * Useful for "reset to home" or "logout" scenarios.
 *
 * Example: Any Screen -> Home (clear entire stack)
 *
 * @param targetScreen The destination route (typically your home/main screen)
 */
fun NavBackStack<NavKey>.navigateAndRemoveAllOther(targetScreen: NavKey) {
    this.add(targetScreen)
    this.removeAll { navKey -> navKey != targetScreen }
}

/**
 * Clear backstack up to (and including) the target screen.
 * Useful for "back to X" navigation patterns.
 *
 * Example: Screen A -> B -> C -> D, clearUpTo(B) results in: A -> B
 *
 * @param targetScreen The screen to navigate back to
 */
fun NavBackStack<NavKey>.clearUpTo(targetScreen: NavKey) {
    val targetIndex = this.indexOf(targetScreen)
    if (targetIndex != -1) {
        this.removeAll { navKey -> this.indexOf(navKey) > targetIndex }
    }
}

/**
 * Pop the backstack if there are multiple entries, otherwise stay on current screen.
 * Prevents accidentally exiting the app when on the root screen.
 *
 * Use this in "Back" button handlers.
 */
fun NavBackStack<NavKey>.popOrStay() {
    if (this.size > 1) {
        this.removeLastOrNull()
    }
}