import com.stopwatch.gradle.deps.Config
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("com.stopwatch.gradle.hilt.dependency")
    id("com.stopwatch.gradle.common.dependency")
}

android {
    kotlinOptions {
        jvmTarget = Config.kotlin_jvm
    }
}

apply(plugin = "com.stopwatch.gradle.common.template")

dependencies {
    implementation(com.stopwatch.gradle.deps.Dependencies.androidx_compose_ui_tooling)
}

