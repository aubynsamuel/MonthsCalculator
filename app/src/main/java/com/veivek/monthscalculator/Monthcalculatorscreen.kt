package com.veivek.monthscalculator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthCalculatorScreen() {
    var startDate by remember { mutableStateOf(YearMonth.now()) }
    var endDate by remember { mutableStateOf(YearMonth.now()) }
    var showStartPicker by remember { mutableStateOf(false) }
    var showEndPicker by remember { mutableStateOf(false) }

    val monthsBetween = calculateMonthsBetween(startDate, endDate)


    Scaffold(topBar = {
        TopAppBar(title = { Text("Months Calculator") })
    }) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1a1a2e),
                            Color(0xFF0f3460)
                        )
                    )
                )
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
                    color = Color(0xFFe94560),
                    letterSpacing = 1.5.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Calculate months between dates",
                    fontSize = 14.sp,
                    color = Color(0xFFbababa),
                    letterSpacing = 0.5.sp
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Start Date Card
                DateCard(
                    label = "START DATE",
                    date = startDate,
                    accentColor = Color(0xFF16c79a),
                    onClick = { showStartPicker = true }
                )

                Spacer(modifier = Modifier.height(20.dp))

                // End Date Card
                DateCard(
                    label = "END DATE",
                    date = endDate,
                    accentColor = Color(0xFFf39c12),
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

@Composable
fun DateCard(
    label: String,
    date: YearMonth,
    accentColor: Color,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF2a2a40))
            .clickable(onClick = onClick)
            .padding(20.dp)
    ) {
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = accentColor,
            letterSpacing = 1.2.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = date.format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH)),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = date.year.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF8a8a9e)
            )
        }
    }
}

@Composable
fun ResultCard(months: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFe94560),
                        Color(0xFFf39c12)
                    )
                )
            )
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "TOTAL MONTHS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White.copy(alpha = 0.8f),
            letterSpacing = 1.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = months.toString(),
            fontSize = 72.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            textAlign = TextAlign.Center
        )

        Text(
            text = if (months == 1) "month" else "months",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.9f),
            letterSpacing = 0.5.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthYearPicker(
    currentDate: YearMonth,
    onDateSelected: (YearMonth) -> Unit,
    onDismiss: () -> Unit,
) {
    var selectedYear by remember { mutableIntStateOf(currentDate.year) }
    var selectedMonth by remember { mutableIntStateOf(currentDate.monthValue) }

    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF2a2a40),
        title = {
            Text(
                "Select Date",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                // Year Selector
                Text(
                    "Year",
                    color = Color(0xFF16c79a),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { selectedYear-- },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1a1a2e)
                        )
                    ) {
                        Text("−", fontSize = 24.sp)
                    }

                    Text(
                        selectedYear.toString(),
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Button(
                        onClick = { selectedYear++ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1a1a2e)
                        )
                    ) {
                        Text("+", fontSize = 24.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Month Selector
                Text(
                    "Month",
                    color = Color(0xFFf39c12),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column {
                    months.chunked(3).forEach { monthRow ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            monthRow.forEachIndexed { _, month ->
                                val monthIndex = months.indexOf(month) + 1

                                Button(
                                    onClick = { selectedMonth = monthIndex },
                                    modifier = Modifier.weight(1f),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = if (selectedMonth == monthIndex)
                                            Color(0xFFf39c12)
                                        else
                                            Color(0xFF1a1a2e)
                                    )
                                ) {
                                    Text(
                                        month.take(3),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(YearMonth.of(selectedYear, selectedMonth))
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFe94560)
                )
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Color(0xFF8a8a9e)
                )
            ) {
                Text("Cancel")
            }
        }
    )
}

fun calculateMonthsBetween(start: YearMonth, end: YearMonth): Int {
    val (earlier, later) = if (start.isBefore(end)) start to end else end to start

    val yearDiff = later.year - earlier.year
    val monthDiff = later.monthValue - earlier.monthValue

    // Total months inclusive (add 1 to include both start and end months)
    return yearDiff * 12 + monthDiff + 1
}