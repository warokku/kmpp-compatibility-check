package com.amosolov.kmpp.compatibility.check

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.gradle.testkit.runner.TaskOutcome
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.*


class KmppCompatibilityCheckPluginFunctionalTest {

    @field:TempDir
    lateinit var testProjectDir: File
    @BeforeEach
    fun setup() {
        val testDataDir = javaClass.classLoader.getResource("testdata/sample-project").toURI()
            .let { File(it) }
        testDataDir.copyRecursively(testProjectDir)
        val buildFile = testProjectDir
            .resolve("build.gradle.kts")
        val contentWithPlugin = buildFile
            .readText()
            .replace("// pluginsPlaceholder", "id(\"com.amosolov.kmpp-compatibility-check\")")
        buildFile.writeText(contentWithPlugin)
    }

    @Test
    fun testTaskOutcome() {
        testProjectDir
            .resolve("build.gradle.kts")
            .appendText("""
                
                kmppCompatibilityCheck {
                    inputFiles.from(File("testSource.kt"))
                }
            """.trimIndent())

        val result: BuildResult = GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments("kmppCompatibilityCheck")
            .withPluginClasspath()
            .build()
        assertEquals(TaskOutcome.SUCCESS, result.task(":kmppCompatibilityCheck")!!.outcome)
    }
}