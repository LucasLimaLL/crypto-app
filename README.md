# Cryptogram Android App

[![Build](https://img.shields.io/badge/build-passing-brightgreen)](http://localhost:9000/dashboard?id=criptograma-app)
[![Quality Gate](https://img.shields.io/badge/quality%20gate-passing-brightgreen)](http://localhost:9000/dashboard?id=criptograma-app)
[![Coverage](https://img.shields.io/badge/coverage-97%25-brightgreen)](http://localhost:9000/dashboard?id=criptograma-app)

Android application for the Criptograma game. Manages login, puzzle selection, and game-board interaction. Consumes `crypto-shared` for domain logic.

---

## Prerequisites

| Tool | Version |
|------|---------|
| Android Studio | Ladybug (2024.2) or later |
| JDK | 21 (Temurin or equivalent; Android AGP does not support Java 25 yet) |
| Android SDK | API 35 platform installed |
| Gradle | 8.9 (via wrapper — no install required) |
| Docker | 24+ (for SonarQube) |
| `crypto-shared` | Published to `mavenLocal` — see [crypto-shared setup](../crypto-shared/README.md) |

---

## Local Setup

```bash
# 1. Clone
git clone https://github.com/LucasLimaLL/crypto-app.git
cd crypto-app

# 2. Publish crypto-shared to local Maven (required before first build)
cd ../crypto-shared && ./gradlew publishToMavenLocal && cd ../crypto-app

# 3. Set local SDK path (never commit this file)
echo "sdk.dir=C:\\Users\\<you>\\AppData\\Local\\Android\\Sdk" > local.properties

# 4. Run unit tests (Windows PowerShell — use project's local Gradle cache)
.\gradlew testDebugUnitTest --no-daemon --offline -g .gradle-user-home

# 5. Run Checkstyle
.\gradlew checkstyleMain --no-daemon --offline -g .gradle-user-home

# 6. Generate coverage report
.\gradlew jacocoDebugTestReport --no-daemon --offline -g .gradle-user-home

# 7. Run SonarQube analysis (requires SonarQube at http://localhost:9000)
$env:SONAR_TOKEN="<your-token>"; .\gradlew sonar --no-daemon -g .gradle-user-home

# 8. Build debug APK
.\gradlew assembleDebug --no-daemon --offline -g .gradle-user-home
```

> **Note**: Run all Gradle commands on Windows PowerShell. WSL2 cannot execute Android build tools (`aapt.exe`, `dx.bat`) because they are Windows PE binaries.

---

## Build Variants

| Variant | Description |
|---------|-------------|
| `debug` | Minification off, `.debug` app-ID suffix, coverage instrumentation enabled |
| `release` | R8 minification + resource shrinking, ProGuard rules applied |

---

## Project Structure

```
crypto-app/
├── gradle/
│   ├── libs.versions.toml          # Central version catalog
│   └── wrapper/                    # Gradle wrapper (committed)
├── config/
│   └── checkstyle/                 # Checkstyle rules (no-comments enforced)
├── app/
│   ├── build.gradle.kts
│   ├── proguard-rules.pro
│   └── src/
│       ├── main/java/br/com/lucaslima/cryptogram/
│       │   ├── CryptogramApplication.java
│       │   ├── MainActivity.java
│       │   └── feature/
│       │       ├── auth/
│       │       │   ├── ui/         (LoginFragment, LoginViewModel)
│       │       │   ├── domain/     (LoginUseCase, LoginResult)
│       │       │   └── data/       (AuthRepository, AuthRepositoryImpl)
│       │       └── home/
│       │           ├── ui/         (HomeFragment, HomeViewModel)
│       │           ├── domain/     (GetPuzzleUseCase, Puzzle, PuzzleResult)
│       │           └── data/       (PuzzleRepository, PuzzleRepositoryImpl)
│       ├── test/java/
│       │   ├── br/com/lucaslima/cryptogram/feature/   # Unit tests per slice
│       │   └── integration/                           # Real-object integration tests
│       └── androidTest/                               # Espresso UI tests
├── build.gradle.kts                # Root build (SonarQube plugin)
├── settings.gradle.kts
└── gradle.properties
```

---

## Architecture

- **Vertical slices** — each feature (`auth`, `home`) owns its UI, domain, and data layers with no cross-slice imports.
- **Hexagonal layers within a slice**:
  - `domain/` — pure Java use cases and value objects; no Android imports.
  - `data/` — repository interface (domain) + implementation (data layer).
  - `ui/` — Fragment + ViewModel; depends only on domain types.
- **ViewModel + LiveData** — UI state flows from ViewModel; Fragments are passive observers.
- **`crypto-shared`** — shared domain types consumed via `mavenLocal`.

---

## Adding a New Feature Slice

1. Create `feature/<name>/` with `ui/`, `domain/`, and `data/` sub-packages.
2. Add use cases and records to `domain/`, repository interface + impl to `data/`, Fragment + ViewModel to `ui/`.
3. Register the Fragment in `res/navigation/nav_graph.xml`.
4. Add unit tests under `src/test/feature/<name>/` and integration tests under `src/test/integration/`.
5. `checkstyleMain` and coverage thresholds apply automatically.

---

## Quality Gates

| Dimension | Minimum |
|-----------|---------|
| Instruction coverage | 97% |
| Line coverage | 97% |
| Branch coverage | 90% |
| Method coverage | 97% |
| Class coverage | 97% |

Both Jacoco (`jacocoDebugTestCoverageVerification`, fails build) and SonarQube Quality Gate enforce these thresholds.

---

## Contributing

### Branching

```
main              ← protected
feature/<scope>   ← short-lived feature branches
fix/<scope>       ← bug fix branches
```

### Commit Conventions

```
<type>(<scope>): <imperative description>

Types:  feat | fix | test | refactor | infra | docs
Scopes: auth | home | puzzle | shared | infra | test
```

Examples:
```
feat(home): implement puzzle selection screen
test(auth): cover invalid credentials flow
fix(puzzle): correct out-of-bounds index in GameBoard
```

### PR Checklist

- [ ] `.\gradlew testDebugUnitTest --no-daemon --offline -g .gradle-user-home` passes
- [ ] `.\gradlew checkstyleMain --no-daemon --offline -g .gradle-user-home` passes (zero warnings)
- [ ] New public logic has unit tests AND integration tests under `integration/`
- [ ] No comments in Java files (`//`, `/* */`, Javadoc)
- [ ] `.\gradlew jacocoDebugTestCoverageVerification` passes
- [ ] SonarQube Quality Gate green

---

## Best Practices Enforced

- **Zero comments** — Checkstyle blocks `//`, `/* */`, and Javadoc; code must be self-documenting.
- **Constructor injection** — ViewModels receive use cases via constructor; fragments receive dependencies via factory or DI, never via field assignment; makes every dependency explicit and testable without a container.
- **Records for value objects** — Domain models (`Puzzle`, `LoginResult`, `PuzzleResult`) and any POJO/DTO are Java records; no Lombok, no boilerplate.
- **No Lombok** — Records, sealed classes, and well-named methods replace every Lombok annotation; no `@Data`, `@Builder`, `@Getter`; the build has no Lombok dependency.
- **Design patterns by intent** — Use cases follow the Command pattern; repositories follow the Repository pattern; `LoginResult`/`PuzzleResult` sealed hierarchies replace boolean flags and nullable returns; introduce a pattern only when it reduces coupling.
- **No Android in domain** — use case and model classes must have zero Android imports; run them in plain JVM tests.
- **Integration tests with real objects** — `integration/` package uses real use cases, real repositories, no mocks; this catches wiring bugs invisible to unit tests.
- **Offline-first Gradle** — use `-g .gradle-user-home` so the pre-seeded cache is used; avoids SSL PKIX failures on corporate networks.

---

## Trusted Committers

| Name | Role |
|------|------|
| Lucas Lima | Maintainer |

To request Trusted Committer status, open an issue describing your contributions.
