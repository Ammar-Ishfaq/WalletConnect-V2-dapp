plugins {
    id("com.android.library")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.walletconnect.sample.dapp"
    compileSdk = 33


    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
        freeCompilerArgs = listOf("-Xcontext-receivers")
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
}

dependencies {
    implementation(project(":WC2Lib:common"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.palette:palette:1.0.0")

    implementation("io.insert-koin:koin-androidx-compose:3.4.3")
    implementation("io.coil-kt:coil-compose:2.3.0")

    // Compose
    implementation(platform("androidx.compose:compose-bom:2023.05.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material:material")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.compose.material:material:1.5.0-alpha04")


    implementation(platform("com.walletconnect:android-bom:1.15.0"))
    implementation("com.walletconnect:android-core")
    implementation("com.walletconnect:push")
    implementation("com.walletconnect:walletconnect-modal")


    //
    api("com.github.WalletConnect.Scarlet:scarlet:1.0.0")
    api("com.github.WalletConnect.Scarlet:websocket-okhttp:1.0.0")
    api("com.github.WalletConnect.Scarlet:stream-adapter-coroutines:1.0.0")
    api("com.github.WalletConnect.Scarlet:message-adapter-moshi:1.0.0")

    implementation("com.github.alexzhirkevich:custom-qr-generator:1.6.1")

}
