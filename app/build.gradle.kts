plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")

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

    // 🔹 Activa Compose
    buildFeatures {
        compose = true
    }

    // 🔹 Configura la versión del compilador Compose
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.4"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // 🔹 BOM de Compose (maneja versiones automáticamente)
    implementation(platform("androidx.compose:compose-bom:2024.05.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.05.00"))

    // 🔹 Librerías principales de Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // 🔹 Navegación entre pantallas (Jetpack Navigation Compose)
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // 🔹 Tamaños de ventana (Material3 Window Size Classes)
    implementation("androidx.compose.material3:material3-window-size-class")

    // 🔹 Para ViewModel y estado en Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")

    // 🔹 Herramientas de desarrollo
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // 🔹 Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
}
