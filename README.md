# Reader App

Reader is a Kotlin Android application for discovering books, saving them to a personal library, tracking reading progress, and reviewing simple reading statistics. It uses Jetpack Compose for the UI, Firebase for authentication and cloud persistence, and the Google Books API for book search and metadata.

## Latest Release

Download the latest release from GitHub: [Reader App v1.1.0](https://github.com/ahmedazizi3/ReaderApp/releases/tag/v1.1.0).

## Demo

[![Watch Demo](https://img.youtube.com/vi/mA0T5VA4jAY/0.jpg)](https://youtube.com/shorts/mA0T5VA4jAY)

## Problem It Solves

Book discovery and reading progress often end up split between search results, notes apps, screenshots, and memory. Reader brings those flows into one mobile app:

- Search for books from Google Books.
- View detailed book information, including authors, description, categories, page count, publish date, and cover image.
- Save books to a Firebase-backed personal reading list.
- Separate books into `Currently reading` and `Reading List` sections.
- Update notes, ratings, started date, and finished date.
- View basic personal reading statistics.
- Keep each user's saved books scoped to their Firebase account.

## Features

- Email/password sign up and login with Firebase Authentication.
- Cloud Firestore storage for saved books and reading metadata.
- User-specific Firestore queries so each account sees its own library.
- Google Books search with optional API key support.
- Book details screen with save/unsave behavior.
- Update screen for notes, rating, started reading, finished reading, and delete actions.
- Stats screen for reading progress summaries.
- Jetpack Compose UI built with Material 3.
- Safer local configuration: `google-services.json`, `local.properties`, and signing files are ignored by Git.

## Screenshots

Screenshots are stored in the [`Reader Application`](Reader%20Application) directory.

| Login | Home | Search |
| --- | --- | --- |
| <img src="Reader%20Application/photo_1_2025-07-07_22-03-25.jpg" width="220" alt="Login screen" /> | <img src="Reader%20Application/photo_2_2025-07-07_22-03-25.jpg" width="220" alt="Home screen" /> | <img src="Reader%20Application/photo_3_2025-07-07_22-03-25.jpg" width="220" alt="Search screen" /> |

| Book Details | Update Reading | Statistics |
| --- | --- | --- |
| <img src="Reader%20Application/photo_4_2025-07-07_22-03-25.jpg" width="220" alt="Book details screen" /> | <img src="Reader%20Application/photo_6_2025-07-07_22-03-25.jpg" width="220" alt="Update reading screen" /> | <img src="Reader%20Application/photo_8_2025-07-07_22-03-25.jpg" width="220" alt="Statistics screen" /> |

## Tech Stack

| Area | Technology |
| --- | --- |
| Language | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Architecture | MVVM-style screens, ViewModels, repositories |
| Navigation | Navigation Compose |
| Dependency Injection | Hilt, KSP |
| Authentication | Firebase Authentication |
| Database | Cloud Firestore |
| Analytics | Firebase Analytics |
| Networking | Retrofit, OkHttp, Gson converter |
| Async | Kotlin Coroutines |
| Images | Coil Compose |
| Build | Gradle, Android Gradle Plugin 9.2.1, Kotlin 2.3.21 |

## Project Configuration

- Package/application ID: `azizi.ahmed.reader`
- Minimum SDK: `29`
- Target SDK: `35`
- Compile SDK: `36`
- Java/Kotlin JVM target: `11`
- Compose BOM: `2026.05.00`
- Firebase BOM: `34.13.0`

## Project Structure

```text
app/src/main/java/azizi/ahmed/reader/
+-- MainActivity.kt
+-- ReaderApplication.kt
`-- packages/
    +-- components/      # Reusable Compose UI components
    +-- data/            # Resource wrappers and shared data helpers
    +-- di/              # Hilt dependency graph
    +-- model/           # Google Books and Firestore models
    +-- navigation/      # Navigation graph and route definitions
    +-- network/         # Google Books API service
    +-- repository/      # Books API and Firestore repositories
    `-- screens/         # Login, sign up, home, search, details, update, stats
```

## Getting Started

### Prerequisites

- Android Studio with the bundled JDK.
- Android SDK Platform 36 installed.
- A Firebase project.
- An Android emulator or physical Android device.
- Internet access for Gradle dependencies, Firebase, and Google Books search.

### Firebase Setup

1. Create or open a Firebase project.
2. Register an Android app with this package name:

   ```text
   azizi.ahmed.reader
   ```

3. Enable Firebase Authentication with the email/password provider.
4. Enable Cloud Firestore.
5. Download `google-services.json`.
6. Place it in:

   ```text
   app/google-services.json
   ```

`app/google-services.json` is intentionally ignored by Git because it is environment-specific Firebase configuration.

### Google Books API Key

The app can search Google Books without an API key, but unauthenticated requests can hit quota limits faster and return HTTP `429`.

To use an API key, add this line to `local.properties`:

```properties
BOOKS_API_KEY=your_google_books_api_key_here
```

`local.properties` is ignored by Git, so the key stays local to your machine.

### Run In Android Studio

1. Open the project in Android Studio.
2. Let Gradle sync finish.
3. Confirm `app/google-services.json` exists locally.
4. Select an emulator or device.
5. Run the `app` configuration.

### Run From The Command Line

Build a debug APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

Install on a connected device:

```powershell
.\gradlew.bat :app:installDebug
```

Run unit tests:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

On macOS or Linux, use `./gradlew` instead of `.\gradlew.bat`.

## Local Files That Should Not Be Committed

The repository is configured to ignore sensitive or machine-specific files:

- `app/google-services.json`
- `local.properties`
- `*.jks`
- `*.keystore`
- `*.p12`
- `*.pfx`
- Gradle and IDE build/cache folders

## Current Notes

- The app uses client-side user scoping for Firestore reads and writes. For production, configure Firestore security rules so users can only access documents where `user_id` matches their authenticated UID.
- `android.disallowKotlinSourceSets=false` is currently set in `gradle.properties` to keep KSP/Hilt generated sources working with AGP 9 built-in Kotlin.
- Release signing is not configured in this repository. Keep signing keys outside Git.

## Verification

The current project has been verified with:

```powershell
.\gradlew.bat :app:assembleDebug --console=plain
.\gradlew.bat :app:testDebugUnitTest --console=plain
```
