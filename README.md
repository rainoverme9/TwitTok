# TwitTok — Android & iOS Mobile Applications

University mobile development project featuring two client implementations of a social networking application, built for **Android (Java)** and **iOS (React Native / JavaScript)**.

## Overview

TwitTok is a social application centered on short user posts ("twoks"), profiles, following relationships and location-related features. This repository preserves the two implementations developed for coursework.

## Technology stack

| Android | iOS |
| --- | --- |
| Java, Android SDK, Gradle | React Native, JavaScript / JSX |
| Android Jetpack, Room | React Context, AsyncStorage-related modules |
| Google Maps SDK, Volley / Retrofit | React Native UI components and remote HTTP requests |

## Repository structure

```text
android/         Android Studio Gradle project
  app/src/       Java source code and Android resources
  gradle/        Gradle wrapper
  local.properties.example   Example Google Maps configuration
ios/             React Native application source code
  App.js         Application entry component
  components/    Pages and UI components
  MODEL/         Communication and data-access modules
  CONTEXT/       React application context
  CLASS/         Domain models and utilities
SECURITY_NOTES.md
```

## Features represented in the source code

- User profiles and settings
- Creating and displaying short posts
- Following and viewing other users
- Map / location-oriented UI
- Integration with the original university-hosted backend

## Android setup

1. Open the `android/` directory in Android Studio.
2. Let Gradle sync and install the SDK versions requested by the project (original configuration: compile SDK 33, Java 11).
3. Create `android/local.properties` (Android Studio may generate it automatically) and set `MAPS_API_KEY` to a **new, restricted** Google Maps key. See `android/local.properties.example`.
4. Run on a compatible emulator or Android device. The remote university service may no longer be available.

## iOS source-code status

`ios/` contains the React Native application source, **not** a complete standalone Xcode / React Native workspace: the original `package.json`, lockfile and native iOS project are absent from the provided archive. A compatible React Native environment and the dependencies imported by the components would have to be restored before attempting to run it. No build/run guarantee is made for this part.

## Backend availability

Both apps refer to an external university-hosted API. That backend is **not included**, and its present availability is unknown. This repository documents the mobile clients rather than a fully self-hosted platform.

## Scope and limitations

Academic project, preserved largely in its original implementation. No production readiness, full security audit, or current-platform compatibility is claimed. Do not use real personal information or privileged API keys in testing. See [SECURITY_NOTES.md](SECURITY_NOTES.md).
