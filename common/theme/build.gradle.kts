import com.stopwatch.gradle.deps.Config
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.stopwatch.gradle.compose.dependency")
}

android {
    kotlinOptions {
        jvmTarget = Config.kotlin_jvm
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = com.stopwatch.gradle.deps.Versions.compose
    }
}

apply(plugin = "com.stopwatch.gradle.common.template")

dependencies {
    implementation(project(mapOf("path" to ":common:resources")))
}

