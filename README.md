
MovieApp
A movie browsing application for Android that allows users to search, filter, and save favorite movies. This app utilizes Jetpack Compose for a modern UI, RoomDB for local data persistence, and Retrofit for network requests to fetch movie data.

Features

Movie Search and Filter: Search movies by title and filter results by popularity, release date, or rating.
Detailed Movie Info: View movie details, including genres, ratings, and release date.
Save to Favorites: Save favorite movies locally using RoomDB.
Async Data Fetching: Fetch and display movie data using Retrofit, with efficient data handling through ViewModel.


Technology Stack : 

Kotlin: Main programming language.

Jetpack Compose: UI toolkit for building native Android interfaces.

RoomDB: Local database for persisting favorite movies.

Retrofit: Network library for making API requests.

KSP: Kotlin Symbol Processing for Room annotations.

Coroutines : For working with asynchronic programming


Project Structure
ViewModel: Handles business logic, including fetching movie data, applying filters, and managing UI state.
RoomDB: Stores favorite movies in a local database.
UI Components: Built with Jetpack Compose, including LazyRow and ModalNavigationDrawer.
Navigation: Jetpack Compose navigation with a composable-based structure.


Prerequisites
API Key: You need an API key for movie data. Replace "YOUR_API_KEY" in your code with your actual key.
Android Studio: Recommended for development and debugging.
Gradle 7.0+: For project builds and dependencies.
