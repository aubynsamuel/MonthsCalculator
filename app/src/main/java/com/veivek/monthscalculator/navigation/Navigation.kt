package com.veivek.monthscalculator.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.retain.retain
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.veivek.monthscalculator.MonthCalculatorScreen
import com.veivek.monthscalculator.PensionCalculatorScreen

@Composable
fun Navigation() {
    val backStack = retain { NavBackStack<NavKey>(AppRoutes.MonthsCalculator) }
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Months", "Pension")
    val icons = listOf(Icons.Default.DateRange, Icons.Default.Calculate)

    Column {
        NavDisplay(
            onBack = { backStack.removeLastOrNull() },
            backStack = backStack,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .weight(1f),
            entryProvider = entryProvider {
                entry<AppRoutes.MonthsCalculator> {
                    MonthCalculatorScreen()
                }

                entry<AppRoutes.PensionCalculator> {
                    PensionCalculatorScreen()
                }
            }
        )

        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(icons[index], contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = {
                        when (item) {
                            "Months" -> backStack.navigate(AppRoutes.MonthsCalculator)
                            "Pension" -> backStack.navigate(AppRoutes.PensionCalculator)
                        }
                        selectedItem = index
                    }
                )
            }
        }
    }
}
