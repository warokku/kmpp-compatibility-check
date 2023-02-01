package com.amosolov.kmpp.compatibility.check

import org.gradle.api.Plugin
import org.gradle.api.Project

open class KmppCompatibilityCheckPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        if (!isAndroidProject(project)) {
            error("Given Gradle Project is expected to be a Android-only Kotlin project")
        }

        val extension = project.extensions.create("kmppCompatibilityCheck", KmppCompatibilityCheckExtension::class.java)
        project.tasks.register("kmppCompatibilityCheck", KmppCompatibilityCheckTask::class.java) {
            it.inputFiles.from(extension.inputFile)
        }

        println("Success")
    }

    private fun isAndroidProject(project: Project): Boolean {
        if (!project.plugins.hasPlugin("org.jetbrains.kotlin.android")) {
            return false
        }

        return project.plugins.hasPlugin("com.android.application") ||  project.plugins.hasPlugin("com.android.library")
    }
}