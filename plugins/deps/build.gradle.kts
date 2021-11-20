plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    mavenCentral()
    google()
}

dependencies{
    implementation("com.android.tools.build:gradle:7.0.0-beta04")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.10")

}


gradlePlugin {
    plugins.register(("com.stopwatch.gradle.deps")) {
            id = "com.stopwatch.gradle.deps"
            implementationClass = "com.stopwatch.gradle.deps.ExperimentDependenciesPlugin"
    }
}

gradlePlugin {
    plugins.register(("com.stopwatch.gradle.compose.dependency")) {
        id = "com.stopwatch.gradle.compose.dependency"
        implementationClass = "com.stopwatch.gradle.deps.ComposeDependencyPlugin"
    }
}

gradlePlugin {
    plugins.register(("com.stopwatch.gradle.hilt.dependency")) {
        id = "com.stopwatch.gradle.hilt.dependency"
        implementationClass = "com.stopwatch.gradle.deps.HiltDependencyPlugin"
    }
}

gradlePlugin {
    plugins.register(("com.stopwatch.gradle.testing.dependency")) {
        id = "com.stopwatch.gradle.testing.dependency"
        implementationClass = "com.stopwatch.gradle.deps.TestingDependencyPlugin"
    }
}
gradlePlugin {
    plugins.register(("com.stopwatch.gradle.common.dependency")) {
        id = "com.stopwatch.gradle.common.dependency"
        implementationClass = "com.stopwatch.gradle.deps.CommonDependencyPlugin"
    }
}

gradlePlugin {
    plugins.register(("com.stopwatch.gradle.common.template")) {
        id = "com.stopwatch.gradle.common.template"
        implementationClass = "com.stopwatch.gradle.deps.TemplateAndroidPlugin"
    }
}

