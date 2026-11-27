import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.konfig)
    alias(libs.plugins.koin.compiler)
}

kotlin {
    android {
        namespace = "com.chs.yoursplash.shared"
        compileSdk = libs.versions.compileSdkVersion.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        androidResources {
            enable = true
        }

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        androidMain.dependencies {
            implementation(libs.androidX.activity.compose)
            implementation(libs.ktor.client.android)

            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
        }
        commonMain.dependencies {
            implementation(libs.foundation)
            implementation(libs.components.resources)
            implementation(libs.ui.tooling.preview)
            implementation(libs.components.resources)

            implementation(libs.jetbrain.matreial3)
            implementation(libs.jetbrain.compose.material3.icon)
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.bundles.android)

            implementation(libs.bundles.ktor)

            implementation(libs.android.paging.compose)
            implementation(libs.android.paging.common)

            implementation(libs.koin.core)
            implementation(libs.bundles.koin)

            implementation(libs.koin.annotation)

            implementation(libs.bundles.coil)

            implementation(libs.bundles.room)

            implementation(libs.datastore.preferences)
            implementation(libs.datastore)
        }
    }
}

fun getApiKey(propertyKey: String): String {
    return gradleLocalProperties(rootDir, providers).getProperty(propertyKey)
}

buildkonfig {
    packageName = "com.chs.yoursplash"
    defaultConfigs {
        buildConfigField(STRING, "API_ACCESS_KEY", getApiKey("api.AccessKey"))
    }
}

compose.resources {
    publicResClass = false
    packageOfResClass = "com.chs.yoursplash.res"
    generateResClass = auto
}

ksp {
    arg("room.schemaLocation", "${projectDir}/schemas")
    arg("KOIN_CONFIG_CHECK", "false")
    arg("KOIN_USE_COMPOSE_VIEWMODEL","true")
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    listOf(
        "kspAndroid",
        "kspIosSimulatorArm64",
        "kspIosArm64"
    ).forEach {
        add(it, libs.room3.compiler)
    }
}

kotlin.sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}

tasks.matching { it.name.startsWith("ksp") && it.name != "kspCommonMainKotlinMetadata" }.configureEach {
    dependsOn("kspCommonMainKotlinMetadata")
}