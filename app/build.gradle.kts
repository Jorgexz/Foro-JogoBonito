plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)   // 👈 Obligatorio con Kotlin 2.0
    alias(libs.plugins.ksp)              // 👈 Usaremos KSP (no KAPT)
}

android {
    namespace = "com.example.forojogobonito"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.forojogobonito"
        minSdk = 24
        targetSdk = 34
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

    // ✅ Necesario para Compose
    buildFeatures {
        compose = true
    }
    // ❌ Con Kotlin 2.0 + plugin compose NO uses composeOptions{}

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // BOM Compose
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.00"))

    // Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Window size classes
    implementation("androidx.compose.material3:material3-window-size-class")

    // Lifecycle / Activity
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // Imágenes
    implementation("io.coil-kt:coil-compose:2.4.0")


    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.compose.foundation:foundation")

    // Dev tools
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}


ksp {
    // carpeta donde Room guardará los JSON de esquema
    arg("room.schemaLocation", "$projectDir/schemas")
    // opcional: para tener nombres de clases más “limpios”
    arg("room.incremental", "true")
    arg("room.expandProjection", "true")
}


