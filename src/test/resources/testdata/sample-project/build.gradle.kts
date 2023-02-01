plugins {
    id("com.android.library") version "7.4.0-beta02"
    kotlin("android") version "1.7.10"
    // pluginsPlaceholder
    id("com.amosolov.kmpp-compatibility-check") version "1.0"
}

repositories {
    mavenCentral()
    google()
}

android {
    compileSdkVersion(33)
}

kmppCompatibilityCheck {
    inputFiles.from(layout.projectDirectory.dir("src/main/kotlin").asFileTree)
}