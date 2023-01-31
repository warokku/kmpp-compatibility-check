package com.amosolov.kmpp.compatibility.check

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class KmppCompatibilityCheckPluginIntegrationTest {

    @Test
    fun `plugin registers task`() {
        val project = ProjectBuilder.builder().build()
        project.plugins.apply("com.amosolov.kmpp.compatibility.check")

        assertNotNull(project.tasks.findByName("kmppCompatibilityCheck"))
    }
}