import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlinx-serialization")
    id("com.google.devtools.ksp") version "1.7.10-1.0.6"
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir).getProperty(propertyKey)
}

android {
    namespace = "com.chs.yoursplash"
    compileSdk =  33

    defaultConfig {
        applicationId = "com.chs.yoursplash"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        buildConfigField("String", "API_ACCESS_KEY", getApiKey("api.AccessKey"))
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.1"
    }
    packagingOptions {
        resources {
            exclude("/META-INF/{AL2.0,LGPL2.1}")
        }
    }
}

dependencies {

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.3.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.3.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.3.1")

    // Compose dependencies
    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.compose.material:material-icons-extended:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //Ktor
    implementation("io.ktor:ktor-client-core:2.1.3")
    implementation("io.ktor:ktor-client-android:2.1.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.1.3")
    implementation("io.ktor:ktor-client-content-negotiation:2.1.3")
    implementation("io.ktor:ktor-client-logging:2.1.3")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    // Coil
    implementation("io.coil-kt:coil-compose:2.2.2")

    // Pager
    implementation("com.google.accompanist:accompanist-pager:0.27.1")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.27.1")

    // Paging
    implementation("androidx.paging:paging-compose:1.0.0-alpha17")
}