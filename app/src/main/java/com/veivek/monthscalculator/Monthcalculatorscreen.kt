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
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberSupportingPaneScaffoldNavigator
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
import androidx.window.core.layout.WindowWidthSizeClass
import com.veivek.monthscalculator.components.DateCard
import com.veivek.monthscalculator.components.MonthYearPicker
import com.veivek.monthscalculator.components.ResultCard
import com.veivek.monthscalculator.utils.calculateMonthsBetween
import java.time.YearMonth

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MonthCalculatorScreen() {
    var startDate by remember { mutableStateOf(YearMonth.now()) }
    var endDate by remember { mutableStateOf(YearMonth.now()) }
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val monthsBetween = remember(startDate, endDate) { calculateMonthsBetween(startDate, endDate) }

    // Adaptive Info
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val isExpanded =
        adaptiveInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT

    val scaffoldNavigator = rememberSupportingPaneScaffoldNavigator()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Months Calculator") })
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            SupportingPaneScaffold(
                directive = scaffoldNavigator.scaffoldDirective,
                value = scaffoldNavigator.scaffoldValue,
                mainPane = {
                    AnimatedPane {
                        MainContent(
                            startDate = startDate,
                            endDate = endDate,
                            onStartPickerClick = { showStartPicker = true },
                            onEndPickerClick = { showEndPicker = true },
                            isExpanded = isExpanded,
                            monthsBetween = monthsBetween // Pass for compact mode only
                        )
                    }
                },
                supportingPane = {
                    AnimatedPane {
                        ResultPane(monthsBetween)
                    }
                },
                modifier = Modifier.fillMaxSize()
            )

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

@Composable
fun MainContent(
    startDate: YearMonth,
    endDate: YearMonth,
    onStartPickerClick: () -> Unit,
    onEndPickerClick: () -> Unit,
    isExpanded: Boolean,
    monthsBetween: Int,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Only show title in compact mode to save space in landscape
        if (!isExpanded) {
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
        }

        // Start Date Card
        DateCard(
            label = "START DATE",
            date = startDate,
            onClick = onStartPickerClick
        )

        Spacer(modifier = Modifier.height(20.dp))

        // End Date Card
        DateCard(
            label = "END DATE",
            date = endDate,
            onClick = onEndPickerClick
        )

        // Only show ResultCard here in Compact mode
        if (!isExpanded) {
            Spacer(modifier = Modifier.height(40.dp))
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800)) +
                        expandVertically(animationSpec = spring(stiffness = Spring.StiffnessLow))
            ) {
                ResultCard(monthsBetween)
            }
        }
    }
}

@Composable
fun ResultPane(monthsBetween: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        ResultCard(monthsBetween)
    }
}
