package com.amosolov.kmpp.compatibility.check

import com.amosolov.kmpp.compatibility.checker.Rule
import org.gradle.api.Plugin
import org.gradle.api.Project

open class KmppCompatibilityCheckPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        if (!isAndroidProject(project)) {
            error("Given Gradle Project is expected to be a Android-only Kotlin project")
        }

        val extension = project.extensions.create("kmppCompatibilityCheck", KmppCompatibilityCheckExtension::class.java)
        val filteredInput = extension.inputFiles.asFileTree.matching(extension.filter)

        project.tasks.register("kmppCompatibilityCheck", KmppCompatibilityCheckTask::class.java) {
            it.inputFiles.from(filteredInput)
            it.strict.set(extension.strict)

            if (extension.allRulesEnabled) {
                it.rules.addAll(Rule.values())
            } else {
                it.rules.addAll(extension.rules)
            }
        }
    }

    private fun isAndroidProject(project: Project): Boolean {
        if (!project.plugins.hasPlugin("org.jetbrains.kotlin.android")) {
            return false
        }

        return project.plugins.hasPlugin("com.android.application") ||  project.plugins.hasPlugin("com.android.library")
    }
}