# TwitTok — Mobile Social Application

TwitTok is a university mobile development project created for the **Mobile Computing** course at the **University of Milan**. Inspired by social media platforms, it lets users publish short posts (called *Twoks*), discover content, interact with other users, and explore location-based posts.

The project includes **two separate mobile implementations**: a native Android app developed in Java and an iOS-oriented implementation developed with React Native.

## Features

- **Content feed:** Browse Twoks published by other users.
- **Post creation:** Compose and publish Twoks.
- **User profiles:** View profile information and users' posts.
- **Social interactions:** Explore users and follow or unfollow them.
- **Location-based content:** View maps associated with Twoks.
- **Connectivity handling:** Display a dedicated state when the network is unavailable.

## Technologies

| Android | React Native implementation |
|---|---|
| Java, Android SDK | JavaScript, React Native |
| Android Studio, Gradle | React Navigation, Expo libraries |
| Retrofit, Volley | AsyncStorage |
| Room | React Native Maps, Expo Location |
| Google Maps SDK | Expo Image Picker |

Both implementations communicate with a remote university-provided backend, which is **not included** in this repository.

## Project Structure

- `android/` — Native Android application (Java)
- `ios/` — React Native application source code
- `docs/` — Project documentation and presentation
- `.gitignore` — Git exclusion rules

## Documentation

For a visual overview of the application, its main features, and development scope, see the [Project Presentation](docs/TwitTwok.pdf).

## Running the Project

**Android:** Open the `android/` directory in Android Studio, synchronize Gradle, and configure a valid Google Maps API key using `MAPS_API_KEY` in the local `android/local.properties` file.

**React Native:** The `ios/` directory contains the original React Native source files, but not a complete installable project configuration. A compatible Expo/React Native environment must be reconstructed to run this version.

**Backend:** Core features depend on the original university-hosted API, whose continued availability is not guaranteed.

## Academic Context

Developed as a Mobile Computing university project at the University of Milan.