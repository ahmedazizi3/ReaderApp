# Reader App

Reader is an Android book tracking application built with Kotlin and Jetpack Compose. It helps users discover books, save them to a personal reading list, update reading status, and review simple reading statistics from one mobile experience.

## What Problem It Solves

Keeping track of books across search results, reading progress, favorites, and finished titles can get messy quickly. Reader brings those actions into one app:

- Search books using Google Books data.
- View book details such as title, authors, publication date, categories, page count, description, and cover image.
- Save books to a personal list.
- Mark books by reading status, such as currently reading or not yet started.
- Track personal reading stats.
- Keep user data connected to an authenticated Firebase account.

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose, Material 3
- **Architecture:** MVVM-style screens with ViewModels and repositories
- **Navigation:** Jetpack Navigation Compose
- **Dependency Injection:** Hilt with KSP
- **Backend Services:** Firebase Authentication, Cloud Firestore, Firebase Analytics
- **Networking:** Retrofit, OkHttp, Gson converter
- **Async:** Kotlin Coroutines
- **Images:** Coil Compose
- **Build:** Gradle, Android Gradle Plugin, Kotlin Gradle Plugin

## Screenshots

Screenshots are stored in the [`Reader Application`](Reader%20Application) directory.

| Login | Home | Search |
| --- | --- | --- |
| <img src="Reader%20Application/photo_1_2025-07-07_22-03-25.jpg" width="220" alt="Login screen" /> | <img src="Reader%20Application/photo_2_2025-07-07_22-03-25.jpg" width="220" alt="Home screen with reading list" /> | <img src="Reader%20Application/photo_3_2025-07-07_22-03-25.jpg" width="220" alt="Search books screen" /> |

| Book Details | Reading Stats | More App Screens |
| --- | --- | --- |
| <img src="Reader%20Application/photo_4_2025-07-07_22-03-25.jpg" width="220" alt="Book details screen" /> | <img src="Reader%20Application/photo_8_2025-07-07_22-03-25.jpg" width="220" alt="Reading statistics screen" /> | <img src="Reader%20Application/photo_6_2025-07-07_22-03-25.jpg" width="220" alt="Reader app screen" /> |

## How To Run It

### Prerequisites

- Android Studio
- Android SDK with the project compile SDK installed
- JDK compatible with the Android Gradle Plugin, preferably the JDK bundled with Android Studio
- A Firebase project configured for Android

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/ahmedazizi3/ReaderApp.git
   cd ReaderApp
   ```

2. Add Firebase configuration:

   - Open your Firebase project.
   - Register an Android app using this package name:

     ```text
     azizi.ahmed.reader
     ```

   - Download `google-services.json`.
   - Place it here:

     ```text
     app/google-services.json
     ```

   This file is intentionally ignored by Git so private/local Firebase configuration is not pushed to GitHub.

3. Open the project in Android Studio.

4. Sync Gradle.

5. Run the app on an emulator or physical Android device.

### Run From The Command Line

Build a debug APK:

```bash
./gradlew assembleDebug
```

Install on a connected device or emulator:

```bash
./gradlew installDebug
```

On Windows PowerShell, use:

```powershell
.\gradlew.bat assembleDebug
.\gradlew.bat installDebug
```

## Notes

- `app/google-services.json` is required locally for Firebase, but it should not be committed.
- The app uses internet access for book search and Firebase operations.
- Make sure Firebase Authentication and Cloud Firestore are enabled in your Firebase project.
