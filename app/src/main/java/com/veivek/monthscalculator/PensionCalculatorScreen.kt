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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.core.layout.WindowWidthSizeClass
import com.veivek.monthscalculator.components.PensionResultCard
import com.veivek.monthscalculator.utils.PensionResult
import com.veivek.monthscalculator.utils.calculatePension

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun PensionCalculatorScreen() {
    var salaryInput by remember { mutableStateOf("") }
    var monthsInput by remember { mutableStateOf("") }

    val salary = salaryInput.toDoubleOrNull() ?: 0.0
    val months = monthsInput.toIntOrNull() ?: 0

    val pensionResult = remember(salary, months) { calculatePension(salary, months) }

    // Adaptive Info
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val isExpanded =
        adaptiveInfo.windowSizeClass.windowWidthSizeClass != WindowWidthSizeClass.COMPACT

    val scaffoldNavigator = rememberSupportingPaneScaffoldNavigator()

    Box {
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Pension Calculator") })
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { paddingValues ->
            SupportingPaneScaffold(
                directive = scaffoldNavigator.scaffoldDirective,
                value = scaffoldNavigator.scaffoldValue,
                mainPane = {
                    AnimatedPane {
                        PensionMainContent(
                            salaryInput = salaryInput,
                            onSalaryChange = { salaryInput = it },
                            monthsInput = monthsInput,
                            onMonthsChange = { monthsInput = it },
                            isExpanded = isExpanded,
                            showResult = monthsInput.isNotEmpty() && salaryInput.isNotEmpty(),
                            pensionResult = pensionResult
                        )
                    }
                },
                supportingPane = {
                    AnimatedPane {
                        PensionResultPane(pensionResult)
                    }
                },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )
        }
    }
}

@Composable
fun PensionMainContent(
    salaryInput: String,
    onSalaryChange: (String) -> Unit,
    monthsInput: String,
    onMonthsChange: (String) -> Unit,
    isExpanded: Boolean,
    showResult: Boolean,
    pensionResult: PensionResult,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isExpanded) {
            Text(
                text = "Pension Calculator",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Estimate your SSNIT pension",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                letterSpacing = 0.5.sp
            )

            Spacer(modifier = Modifier.height(48.dp))
        }

        OutlinedTextField(
            value = salaryInput,
            onValueChange = onSalaryChange,
            label = { Text("Average of Best 3 Years Salary (Annual)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            prefix = { Text("GHS ") },
            shape = RoundedCornerShape(20.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = monthsInput,
            onValueChange = onMonthsChange,
            label = { Text("Total Months Contributed") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(20.dp)
        )

        if (!isExpanded && showResult) {
            Spacer(modifier = Modifier.height(40.dp))
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(800)) +
                        expandVertically(animationSpec = spring(stiffness = Spring.StiffnessLow))
            ) {
                PensionResultCard(pensionResult)
            }
        }
    }
}

@Composable
fun PensionResultPane(result: com.veivek.monthscalculator.utils.PensionResult) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        PensionResultCard(result)
    }
}
