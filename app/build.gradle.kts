import org.gradle.kotlin.dsl.implementation

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
    id("kotlin-kapt") // If you use libraries that require annotation processing (not strictly needed for just Firestore basic ops)
}

android {
    namespace = "com.example.tobedefined"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.tobedefined"
        minSdk = 27
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)

    ////
    // In your app/build.gradle.kts
        // Firebase
        // Import the Firebase BoM (Bill of Materials) - Recommended way
        implementation(platform("com.google.firebase:firebase-bom:33.1.0")) // Use the latest stable BoM version

        // Cloud Firestore with Kotlin extensions
        implementation("com.google.firebase:firebase-firestore-ktx")

        // Firebase Authentication with Kotlin extensions
        implementation("com.google.firebase:firebase-auth-ktx")

        // REMOVE these lines if they exist, as they might conflict with the BoM:
        // implementation(libs.firebase.auth)  // REMOVE THIS if it refers to a non-KTX version or a specific version
        // implementation(libs.firebase.firestore) // REMOVE THIS if it refers to a non-KTX version or a specific version

        // Kotlin Coroutines (these look fine)
        // Kotlin Coroutines
        // For using await() with Play Services Tasks (like Firestore tasks)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1") // Check for the latest version
        // Core coroutines library (often included transitively, but good to have explicitly if needed)
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1") // Check for the latest version

        // Lifecycle components (these look fine, assuming versions are valid)
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.2") // Consider aligning to a common version or using Lifecycle BoM
        implementation(libs.androidx.lifecycle.runtime.ktx) // This is "2.8.2"
        implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.2")

        // Navigation Compose (ensure nav_version is valid)
        val nav_version = "2.9.0-alpha01" // Make sure this exact version string is correct and available
        implementation("androidx.navigation:navigation-compose:$nav_version")


        // ... your test dependencies



    ///

/*
    val nav_version = "2.9.0"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // Firebase
    // Import the Firebase BoM (Bill of Materials) - Recommended way
    // This ensures that all Firebase libraries use compatible versions.
    implementation("com.google.firebase:firebase-bom:33.1.0") // Use the latest BoM version

    // Cloud Firestore
    implementation("com.google.firebase:firebase-firestore-ktx") // For Kotlin extensions (includes core Firestore)

    // Firebase Authentication (if you're still using it for login, etc.)
    implementation("com.google.firebase:firebase-auth-ktx")

    // Kotlin Coroutines
    // For using await() with Play Services Tasks (like Firestore tasks)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.8.1") // Check for the latest version
    // Core coroutines library (often included transitively, but good to have explicitly if needed)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1") // Check for the latest version
*/
    // Lifecycle components for ViewModelScope and LiveData/StateFlow integration with Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.2") // For viewModelScope - Check latest
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")   // For lifecycleScope - Check latest
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.2") // For collectAsStateWithLifecycle - Check latest




    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}