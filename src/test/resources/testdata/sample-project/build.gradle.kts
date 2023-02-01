plugins {
    id("com.android.library") version "7.4.0-beta02"
    kotlin("android") version "1.7.10"
    // pluginsPlaceholder
}

repositories {
    mavenCentral()
    google()
}

android {
    compileSdkVersion(33)
}

kmppCompatibilityCheck {
    inputFiles.from(kotlin.sourceSets.getByName("main").kotlin.files)
    filter.exclude("*.java")
}