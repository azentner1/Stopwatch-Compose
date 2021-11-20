
import com.stopwatch.gradle.deps.Config
import com.stopwatch.gradle.deps.Versions
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("com.stopwatch.gradle.compose.dependency")
    id("com.stopwatch.gradle.hilt.dependency")
    id("com.stopwatch.gradle.testing.dependency")
    id("com.stopwatch.gradle.common.dependency")
}
android {

    defaultConfig {
        applicationId = "com.stopwatch.app"
        versionCode = 2
        versionName = "2.0"
    }

    kotlinOptions {
        jvmTarget = Config.kotlin_jvm
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose
    }

}

apply(plugin = "com.stopwatch.gradle.common.template")

dependencies {
    implementation(project(mapOf("path" to ":common:theme")))
    implementation(project(mapOf("path" to ":services:authentication")))
    implementation(project(mapOf("path" to ":common:resources")))
    implementation(project(mapOf("path" to ":services:timer")))
    implementation(project(mapOf("path" to ":storage:shared_prefs")))

}

