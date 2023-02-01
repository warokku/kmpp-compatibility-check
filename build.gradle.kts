plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("com.android.library.gradle.plugin:7.4.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.jetbrains.kotlin.android.gradle.plugin:1.7.10")
}

gradlePlugin {
    val plugin by plugins.creating {
        id = "com.amosolov.kmpp-compatibility-check"
        implementationClass = "com.amosolov.kmpp.compatibility.check.KmppCompatibilityCheckPlugin"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
