
import com.stopwatch.gradle.deps.Config
import com.stopwatch.gradle.deps.Versions
plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.stopwatch.gradle.common.dependency")
}

android {
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

}

