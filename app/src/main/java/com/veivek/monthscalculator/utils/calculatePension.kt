package com.veivek.monthscalculator.utils

data class PensionResult(
    val pensionPay: Double,
    val pensionRight: Double,
    val isEligible: Boolean
)

const val ELIGIBLE_MONTHS = 180
const val BASE_ELIGIBLE_PERCENTAGE = 37.5
const val ADDITIONAL_PERCENT_PER_MONTH = 1.125 / 12

fun calculatePension(averageOfBestThreeYears: Double, totalMonthsContributed: Int): PensionResult {
    val extraMonthsContributed = totalMonthsContributed - ELIGIBLE_MONTHS

    if (extraMonthsContributed >= 0) {
        val additionalAccumulatedPercentage = extraMonthsContributed * ADDITIONAL_PERCENT_PER_MONTH
        val totalPercentage = BASE_ELIGIBLE_PERCENTAGE + additionalAccumulatedPercentage
        
        // totalEligiblePercentage is known as pension right
        val pensionRight = minOf(60.0, totalPercentage)
        val pensionPay = (pensionRight * (averageOfBestThreeYears / 12)) / 100
        
        return PensionResult(
            pensionPay = pensionPay,
            pensionRight = pensionRight,
            isEligible = true
        )
    } else {
        return PensionResult(
            pensionPay = 0.0,
            pensionRight = 0.0,
            isEligible = false
        )
    }
}
