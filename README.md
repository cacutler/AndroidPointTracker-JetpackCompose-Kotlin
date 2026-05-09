# Card Game Point Tracker (Android)

A lightweight, intuitive point tracking application for card games on Android. Keep score across multiple players and rounds with ease. All data is stored locally on your device.

## Features

- **Multiple Games**: Create and manage multiple games simultaneously with dedicated tracking for each
- **Multi-Player Support**: Track scores for unlimited players per game
- **Round Management**: Organize gameplay into rounds with individual score entries per player
- **Score History**: View detailed round-by-round score history for each game
- **Flexible Scoring**: Support for both "highest score wins" and "lowest score wins" game modes
- **Game Status**: Categorize games as active or completed for better organization
- **Local Storage**: All game data is stored locally on your device using SQLite—no internet connection required
- **Intuitive UI**: Clean, Material 3 design built with Jetpack Compose for a modern Android experience

## Getting Started

### Requirements

- Android 7.0 (API 24) or higher
- Android Studio (latest version recommended)
- JDK 11 or higher

### Building the App

1. **Clone or download the project**
   ```bash
   git clone git@github.com:cacutler/AndroidPointTracker-JetpackCompose-Kotlin.git
   cd CardGamePointTracker
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing Android Studio project"
   - Navigate to the project directory

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run on an emulator or device**
   - Connect an Android device via USB or launch an Android emulator
   - Click "Run" in Android Studio or use:
     ```bash
     ./gradlew installDebug
     ```

### Using the App

1. **Create a Game**
   - Tap the "+" button on the main screen
   - Enter the game name
   - Select game type (highest or lowest score wins)
   - Add player names

2. **Track Scores**
   - Tap on a game to enter the score tracking screen
   - Enter points for each player in each round
   - Progress through multiple rounds as your game continues

3. **View History**
   - Access the round history to see detailed score progression
   - Review past games to see final standings and player performance

4. **Manage Games**
   - Mark games as complete when finished
   - View active and completed games on the main screen
   - Delete games you no longer need

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Database**: Room (SQLite wrapper)
- **Architecture**: MVVM (Model-View-ViewModel) with Repository pattern
- **Navigation**: Jetpack Compose Navigation
- **Lifecycle Management**: AndroidX Lifecycle
- **Build System**: Gradle with Kotlin DSL
- **Minimum API Level**: 24 (Android 7.0)
- **Target API Level**: 37 (Android 15)

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/cacutler/cardgamepointtracker/
│   │   │   ├── data/              # Room entities and DAOs
│   │   │   ├── ui/                # UI management and logic
│   │   │   │   ├── screens/       # Composable screens
│   │   │   │   ├── viewmodels/    # ViewModels for state management
│   │   │   │   └── theme/         # Material 3 theme configuration
│   │   │   ├── navigation/        # Navigation graph
│   │   │   ├── repository/        # Data access layer
│   │   │   └── MainActivity.kt    # Starting point for the app
│   │   └── res/                   # Resources (strings, colors, etc.)
│   ├── test/                      # Unit tests
│   └── androidTest/               # Instrumentation tests
└── build.gradle.kts               # Module-level build configuration
```

## Beta Testing

The app is currently in **closed beta testing**. Your feedback helps improve the experience before the production release!

### How to Join Beta Testing

1. **Contact the Developer**
   - Email: **calexcutler@gmail.com**
   - Include your Android device model and OS version along with the google account email you want to use to download the app
   - Let us know how you plan to use the app

2. **Install the Beta**
   - You'll receive instructions to install the beta version
   - Feel free to test various game types and scenarios

3. **Provide Feedback**
   - Report any bugs or issues you encounter
   - Suggest features or improvements
   - Share your experience and how you're using the app

### Known Limitations / Roadmap

- Data is stored locally only (cloud sync planned for future releases)
- Export/import functionality coming soon
- Additional game statistics and analytics planned

## Testing

The project includes both unit tests and instrumentation tests:

```bash
# Run unit tests
./gradlew test
# Run instrumentation tests on device/emulator
./gradlew connectedAndroidTest
```

## Support & Feedback

- 📧 Email: calexcutler@gmail.com
- For bug reports, feature requests, or general feedback, please reach out via email

## Contributing

Beta testers are welcome! Please reach out via email to discuss how you'd like to help.