plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.google.services)
    alias(libs.plugins.plugin.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.toquemedia.ekklesia"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.toquemedia.ekklesia"
        minSdk = 24
        targetSdk = 34
        versionCode = 4
        versionName = "0.0.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.7.21"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.material)
    implementation(libs.androidx.room)
    implementation(libs.androidx.room.ktx)

    implementation(libs.hilt.android)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.storage)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.play.services.auth)
    implementation(libs.firebase.analytics)
    implementation(libs.credentials.play.service.auth)
    implementation(libs.credentials)
    implementation(libs.google.id)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)
    implementation(libs.retrofit.http)
    implementation(libs.converter.gson)
    implementation(libs.core.splashscreen)
    implementation(libs.camera.camera)
    implementation(libs.camera.core)
    implementation(libs.lifecycle.camera)
    implementation(libs.camera.video)
    implementation(libs.camera.view)
    implementation(libs.accompanist.permissions)
    implementation(libs.compose.colorpicker)

    ksp(libs.hilt.compiler)
    ksp(libs.androidx.compiler)

    implementation(libs.gson)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}
