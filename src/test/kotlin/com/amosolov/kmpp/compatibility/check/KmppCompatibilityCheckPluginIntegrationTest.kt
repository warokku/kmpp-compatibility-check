package com.amosolov.kmpp.compatibility.check

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test
import kotlin.test.assertFails
import kotlin.test.assertNotNull

class KmppCompatibilityCheckPluginIntegrationTest {

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
}