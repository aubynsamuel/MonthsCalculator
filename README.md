# Calculator 360

Calculator 360 is an Android app with two simple calculators:

- **Months Calculator** — counts the months between two selected dates, including both the start and end months.
- **Pension Calculator** — estimates a monthly Ghana SSNIT pension from the average annual salary for your best three years and the total number of months contributed.

The app uses an adaptive layout that works on phones and larger screens.

## Requirements

- Android Studio
- JDK 17
- Android SDK 36
- An emulator or device running Android 8.0 (API 26) or later

## Run the app

1. Clone the repository.
2. Open the project in Android Studio.
3. Let Gradle finish syncing.
4. Select an emulator or connected Android device.
5. Click **Run**.

You can also build a debug APK from the command line:

```bash
./gradlew assembleDebug
```

On Windows, use:

```powershell
.\gradlew.bat assembleDebug
```

The APK will be created in `app/build/outputs/apk/debug/`.

## Pension calculation

The estimate follows the formula implemented in the app:

- A minimum of 180 contribution months is required.
- The base pension right is 37.5%.
- Each additional contribution month adds 0.09375%.
- The pension right is capped at 60%.

This calculator provides an estimate only. Confirm official pension figures and eligibility with SSNIT.

## Built with

- Kotlin
- Jetpack Compose
- Material 3
- Navigation 3
- Material 3 Adaptive layouts

## Tests

Run the unit tests with:

```bash
./gradlew test
```

Run the instrumented tests on a connected device or emulator with:

```bash
./gradlew connectedAndroidTest
```
