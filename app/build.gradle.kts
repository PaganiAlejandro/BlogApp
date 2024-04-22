plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    namespace = "com.alepagani.blogapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alepagani.blogapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Firebase
    implementation ("com.google.firebase:firebase-firestore:22.0.1")
    implementation ("com.google.firebase:firebase-storage:19.2.1")
    implementation ("com.google.firebase:firebase-auth:20.0.2")

    // Firebase Crashlytics
    implementation ("com.google.firebase:firebase-crashlytics-ktx:17.4.1")
    implementation ("com.google.firebase:firebase-analytics-ktx:18.0.2")
   // implementation(libs.firebase.crashlytics)

    // Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.3.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.3.3")

    // Utils
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    implementation ("androidx.legacy:legacy-support-v4:1.0.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.11.0")

    // Viewmodel and livedata KTX
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")

    // Play services coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1")

    //Used for StateFlow
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

    // Lifecycle
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0-alpha01")

    //Used for StateFlow
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}