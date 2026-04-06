averageOfBestThreeYears = int(
    input("Enter your average of best 3 years salary (per annum): ")
)
totalMonthsContributed = int(input("Enter your total months contributed: "))

eligibleMonths = 180
baseEligiblePercentage = 37.5


# SSNIT Pension calculator
def calculatePension(averageOfBestThreeYears, totalMonthsContributed):
    extraMonthsContributed = totalMonthsContributed - eligibleMonths

    if extraMonthsContributed >= 0:
        additionalAccumulatedPercentage = extraMonthsContributed * (1.125 / 12)
        totalPercentage = baseEligiblePercentage + additionalAccumulatedPercentage
        # totalEligiblePercentage is known as pension right
        totalEligiblePercentage = min(60, totalPercentage)
        pensionPay = (totalEligiblePercentage * (averageOfBestThreeYears / 12)) / 100
        print(f"Your pension pay will be ", "{:.1f}".format(pensionPay), "GHS")
    else:
        print("You do not qualify for pension")


calculatePension(averageOfBestThreeYears, totalMonthsContributed)
