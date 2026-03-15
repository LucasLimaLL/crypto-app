# Cryptogram Android App

Mobile application responsible for the player experience in the cryptogram game. Manages the game board, letter editing, word confirmation, undo/redo, hints, and interaction with the backend to retrieve puzzles and save progress.

---

## Architecture — Vertical Slices

Each product feature lives in its own **vertical slice** under `feature/`, containing three sub-packages:

```
feature/<name>/
├── ui/        — Fragment + ViewModel (Android lifecycle-aware)
├── domain/    — Use-cases and domain models (pure Java, no Android deps)
└── data/      — Repository interface + implementation (network / database)
```

This boundary keeps features independently developable and testable. Swapping a data-layer implementation (e.g., replacing the stub with a Retrofit call) never touches the UI or domain layers.

**Included slices:**
| Slice | Description |
|-------|-------------|
| `auth` | Login / authentication flow |
| `home` | Main game-board screen |

---

## Tech Stack

| Concern | Choice |
|---------|--------|
| Language | Java 21 (records, sealed interfaces, pattern matching) |
| Build | Gradle 8.9 · Kotlin DSL (`build.gradle.kts`) · Version Catalog |
| UI | AndroidX · Material 3 · ViewBinding |
| Navigation | Navigation Component (single-Activity) |
| State | ViewModel + LiveData |
| Min SDK | 24 (Android 7.0) |
| Target SDK | 35 (Android 15) |
| Tests | JUnit 4 · Mockito 5 · Espresso · Architecture Components Core Testing |

---

## Prerequisites

- **Android Studio** Ladybug (2024.2) or later
- **JDK 21** (Temurin or equivalent)
- Android SDK with API 35 platform installed

> The first build downloads Gradle 8.9 automatically via the included wrapper.

---

## Setup

```bash
# 1. Clone the repository
git clone https://github.com/LucasLimaLL/crypto-app.git
cd crypto-app

# 2. (Optional) Set your local SDK path — never commit this file
echo "sdk.dir=/path/to/your/Android/Sdk" > local.properties

# 3. Open in Android Studio (File → Open) or build from the terminal:
./gradlew assembleDebug
```

---

## Build Variants

| Variant | Description |
|---------|-------------|
| `debug` | Minification off, `.debug` app-ID suffix, debuggable |
| `release` | R8 minification + resource shrinking, ProGuard rules applied |

```bash
# Debug APK
./gradlew assembleDebug

# Release APK (requires a signing config — see build.gradle.kts)
./gradlew assembleRelease
```

---

## Running Tests

```bash
# Unit tests (runs on the JVM — fast)
./gradlew test

# Instrumented tests (requires a connected device or emulator)
./gradlew connectedAndroidTest
```

---

## Project Structure

```
crypto-app/
├── gradle/
│   ├── libs.versions.toml          # Central version catalog
│   └── wrapper/                    # Gradle wrapper (commit this)
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/br/com/lucaslima/cryptogram/
│       │   │   ├── CryptogramApplication.java
│       │   │   ├── MainActivity.java
│       │   │   └── feature/
│       │   │       ├── auth/
│       │   │       │   ├── ui/     (LoginFragment, LoginViewModel)
│       │   │       │   ├── domain/ (LoginUseCase, LoginResult)
│       │   │       │   └── data/   (AuthRepository, AuthRepositoryImpl)
│       │   │       └── home/
│       │   │           ├── ui/     (HomeFragment, HomeViewModel)
│       │   │           ├── domain/ (GetPuzzleUseCase, Puzzle, PuzzleResult)
│       │   │           └── data/   (PuzzleRepository, PuzzleRepositoryImpl)
│       │   └── res/
│       │       ├── layout/         (activity_main, fragment_home, fragment_login)
│       │       ├── navigation/     (nav_graph.xml)
│       │       ├── values/         (strings, colors, themes)
│       │       ├── values-night/   (dark-mode theme overrides)
│       │       └── xml/            (network_security_config)
│       ├── test/                   # Unit tests (HomeViewModelTest, LoginViewModelTest)
│       └── androidTest/            # Instrumented tests (MainActivityTest)
├── build.gradle.kts                # Root build script
├── settings.gradle.kts
└── gradle.properties
```

---

## Adding a New Feature Slice

1. Create `app/src/main/java/br/com/lucaslima/cryptogram/feature/<name>/` with `ui/`, `domain/`, and `data/` sub-packages.
2. Add your Fragment and ViewModel to `ui/`, use-cases and records to `domain/`, and a repository interface + impl to `data/`.
3. Register the Fragment in `res/navigation/nav_graph.xml`.
4. Add a corresponding unit test under `src/test/`.

---

## License

This project is proprietary. All rights reserved.

