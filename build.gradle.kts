plugins {
    `java-gradle-plugin`
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
}

gradlePlugin {
    val plugin by plugins.creating {
        id = "com.amosolov.kmpp-compatibility-check.properties"
        implementationClass = "com.amosolov.kmpp.compatibility.check.KmppCompatibilityCheckPlugin"
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
