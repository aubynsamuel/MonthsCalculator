package com.veivek.monthscalculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.veivek.monthscalculator.components.DateCard
import com.veivek.monthscalculator.components.MonthYearPicker
import com.veivek.monthscalculator.components.ResultCard
import com.veivek.monthscalculator.utils.calculateMonthsBetween
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthCalculatorScreen() {
    var startDate by remember { mutableStateOf(YearMonth.now()) }
    var endDate by remember { mutableStateOf(YearMonth.now()) }
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val monthsBetween = remember(startDate, endDate) { calculateMonthsBetween(startDate, endDate) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Months Calculator") })
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Title
                Text(
                    text = "Month Calculator",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Calculate months between dates",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Start Date Card
                DateCard(
                    label = "START DATE",
                    date = startDate,
                    onClick = { showStartPicker = true }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // End Date Card
                DateCard(
                    label = "END DATE",
                    date = endDate,
                    onClick = { showEndPicker = true }
                )

                Spacer(modifier = Modifier.height(40.dp))

                // Result Display
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(800)) +
                            expandVertically(animationSpec = spring(stiffness = Spring.StiffnessLow))
                ) {
                    ResultCard(monthsBetween)
                }
            }

            // Date Pickers
            if (showStartPicker) {
                MonthYearPicker(
                    currentDate = startDate,
                    onDateSelected = {
                        startDate = it
                        showStartPicker = false
                    },
                    onDismiss = { showStartPicker = false }
                )
            }

            if (showEndPicker) {
                MonthYearPicker(
                    currentDate = endDate,
                    onDateSelected = {
                        endDate = it
                        showEndPicker = false
                    },
                    onDismiss = { showEndPicker = false }
                )
            }
        }
    }
}
