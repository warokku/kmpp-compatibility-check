plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    `maven-publish`
}

repositories {
    mavenCentral()
    google()
}

dependencies {
    api(project("kmpp-checker"))
    testImplementation("com.android.library:com.android.library.gradle.plugin:7.4.0")
    testImplementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.0")
    testImplementation(kotlin("test"))
    testImplementation(gradleTestKit())
}

group = "com.amosolov"
version = "1.0"

gradlePlugin {
    plugins.create("kmpp-compatibility-check") {
        id = "com.amosolov.kmpp-compatibility-check"
        implementationClass = "com.amosolov.kmpp.compatibility.check.KmppCompatibilityCheckPlugin"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
