package com.amosolov.kmpp.compatibility.check

import com.android.build.gradle.BaseExtension
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.*

class KmppCompatibilityCheckPluginUnitTest {

    @Test
    fun `plugin does not register task when dependencies are missing`() {
        val project = ProjectBuilder.builder().build()

        assertFails {
            project.plugins.apply(KmppCompatibilityCheckPlugin::class.java)
        }
    }

    @Test
    fun `plugin registers task when dependencies are present`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.android.library")
        project.plugins.apply("kotlin-android")
        project.plugins.apply("com.amosolov.kmpp-compatibility-check")

        assertNotNull(project.tasks.findByName("kmppCompatibilityCheck"))
    }

    @Test
    fun `task configures correctly`(@TempDir tempDir: File) {
        val project = ProjectBuilder.builder().build() as ProjectInternal
        project.plugins.apply("com.android.library")
        project.plugins.apply("kotlin-android")
        val androidExtension = project.extensions.getByName("android") as BaseExtension
        androidExtension.compileSdkVersion(33)
        project.plugins.apply("com.amosolov.kmpp-compatibility-check")

        val extension = project.extensions.getByType(KmppCompatibilityCheckExtension::class.java)
        val file = tempDir.resolve("testKotlin.kts")
        file.createNewFile()

        extension.inputFiles.from(file)

        val task = project.tasks.getByName("kmppCompatibilityCheck") as KmppCompatibilityCheckTask
        assertEquals(listOf(file.name), task.inputFiles.files.map { it.name })
    }

    @Test
    fun `task configures lazily`() {
        val project = ProjectBuilder.builder().build() as ProjectInternal
        project.plugins.apply("com.android.library")
        project.plugins.apply("kotlin-android")
        val androidExtension = project.extensions.getByName("android") as BaseExtension
        androidExtension.compileSdkVersion(33)
        project.plugins.apply("com.amosolov.kmpp-compatibility-check")

        val extension = project.extensions.getByType(KmppCompatibilityCheckExtension::class.java)
        val file = File("testKotlin.kts")
        extension.inputFiles.from(file)

        project.tasks.named("kmppCompatibilityCheck") {
            fail("Task ${it.name} was configured")
        }
        project.evaluate()
    }

    @Test
    fun `task filters input files based on pattern set`(@TempDir tempDir: File) {
        val project = ProjectBuilder.builder().build() as ProjectInternal
        project.plugins.apply("com.android.library")
        project.plugins.apply("kotlin-android")
        val androidExtension = project.extensions.getByName("android") as BaseExtension
        androidExtension.compileSdkVersion(33)
        project.plugins.apply("com.amosolov.kmpp-compatibility-check")

        val extension = project.extensions.getByType(KmppCompatibilityCheckExtension::class.java)
        val kotlinFile = tempDir.resolve("testKotlin.kts")
        kotlinFile.createNewFile()
        val javaFile = tempDir.resolve("testJava.java")
        javaFile.createNewFile()
        extension.inputFiles.from(tempDir)
        extension.filter.exclude("*.java")

        val task = project.tasks.getByName("kmppCompatibilityCheck") as KmppCompatibilityCheckTask
        assertFalse(task.inputFiles.files.contains(javaFile))
    }
}