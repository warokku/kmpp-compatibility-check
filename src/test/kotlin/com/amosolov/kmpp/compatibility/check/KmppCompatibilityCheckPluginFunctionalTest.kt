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
        val testDataDir = javaClass.classLoader.getResource("testdata/sample-project")?.toURI()
            .let { File(it) }
        testDataDir.copyRecursively(testProjectDir)

        val contentWithPlugin = buildFile()
            .readText()
            .replace("// pluginPlaceholder", "id(\"com.amosolov.kmpp-compatibility-check\")")
        buildFile().writeText(contentWithPlugin)
    }

    @Test
    fun `task outcome is SUCCESS if provided with correct inputs`() {
        buildFile().appendBadImportSourceToInputs()

        val result = createKmppCompatibilityCheckRunner().build()

        assertEquals(TaskOutcome.SUCCESS, taskOutcome(result))
    }

    @Test
    fun `task outcome is NO_SOURCE if provided with empty inputs`() {
        val result = createKmppCompatibilityCheckRunner()
            .build()

        assertEquals(TaskOutcome.NO_SOURCE, taskOutcome(result))
    }

    @Test
    fun `task outcome is NO_SOURCE if provided with inputs that match filters`() {
        buildFile().appendJavaImportSourceToInputs()
        buildFile().appendJavaExtensionToFilters()

        val result = createKmppCompatibilityCheckRunner()
            .build()

        assertEquals(TaskOutcome.NO_SOURCE, taskOutcome(result))
    }

    @Test
    fun `task outcome is UP_TO_DATE if provided with same inputs unchaged`() {
        buildFile().appendBadImportSourceToInputs()

        val runner = createKmppCompatibilityCheckRunner()
        runner.build()

        val result = runner.build()
        assertEquals(TaskOutcome.UP_TO_DATE, taskOutcome(result))
    }

    @Test
    fun `task outcome is SUCCESS if inputs change between runs`() {
        buildFile().appendBadImportSourceToInputs()

        val runner = createKmppCompatibilityCheckRunner()
        runner.build()

        testProjectDir
            .resolve("src/main/kotlin/testSourceBadImport.kt")
            .appendText("""
                
                fun List<String>.foo() { }
                """.trimIndent())

        val result = runner.build()
        assertEquals(TaskOutcome.SUCCESS, taskOutcome(result))
    }

    private fun taskOutcome(result: BuildResult): TaskOutcome? =
        result.task(":kmppCompatibilityCheck")!!.outcome

    private fun buildFile() = testProjectDir.resolve("build.gradle.kts")

    private fun settingsFile() = testProjectDir.resolve("settings.gradle.kts")

    private fun File.appendBadImportSourceToInputs() {
        this
            .appendText("""
                
                kmppCompatibilityCheck {
                    inputFiles.from(File("src/main/kotlin/testSourceBadImport.kt"))
                    rules.add(com.amosolov.kmpp.compatibility.checker.Rule.IMPORTS)
                }
            """.trimIndent())
    }

    private fun File.appendJavaExtensionToFilters() {
        this
            .appendText("""
                
                kmppCompatibilityCheck {
                    filter.exclude("*.java")
                }
            """.trimIndent())
    }

    private fun File.appendJavaImportSourceToInputs() {
        this
            .appendText("""
                
                kmppCompatibilityCheck {
                    inputFiles.from(File("src/main/java/testSource.java"))
                    rules.add(com.amosolov.kmpp.compatibility.checker.Rule.IMPORTS)
                }
            """.trimIndent())
    }

    private fun createKmppCompatibilityCheckRunner(): GradleRunner {
        return GradleRunner.create()
            .withProjectDir(testProjectDir)
            .withArguments("kmppCompatibilityCheck", "--info")
            .withPluginClasspath()
    }
}