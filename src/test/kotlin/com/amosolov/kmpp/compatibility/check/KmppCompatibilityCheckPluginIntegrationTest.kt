package com.amosolov.kmpp.compatibility.check

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class KmppCompatibilityCheckPluginIntegrationTest {

    @Test
    fun `plugin does not register task when dependencies are missing`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("org.jetbrains.kotlin.android")

        assertNull(project.tasks.findByName("kmppCompatibilityCheck"))
    }

    @Test
    fun `plugin registers task when dependencies are present`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("org.jetbrains.kotlin.android")
        project.plugins.apply("com.android.application")
        project.plugins.apply("com.amosolov.kmpp.compatibility.check")

        assertNotNull(project.tasks.findByName("kmppCompatibilityCheck"))
    }
}