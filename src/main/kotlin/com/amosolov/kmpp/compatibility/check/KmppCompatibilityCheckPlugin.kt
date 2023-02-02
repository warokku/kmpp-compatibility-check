package com.amosolov.kmpp.compatibility.check

import org.gradle.api.Plugin
import org.gradle.api.Project
//import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
//import java.io.File

open class KmppCompatibilityCheckPlugin: Plugin<Project> {

    override fun apply(project: Project) {
        if (!isAndroidProject(project)) {
            error("Given Gradle Project is expected to be a Android-only Kotlin project")
        }

        val extension = project.extensions.create("kmppCompatibilityCheck", KmppCompatibilityCheckExtension::class.java)
//        extension.inputFiles.from(project.getDefaultInputFiles())
        val filteredInput = extension.inputFiles.asFileTree.matching(extension.filter)

        project.tasks.register("kmppCompatibilityCheck", KmppCompatibilityCheckTask::class.java) {
            it.inputFiles.from(filteredInput)
            it.rules.addAll(extension.rules)
        }

        println("Success")
    }

    private fun isAndroidProject(project: Project): Boolean {
        if (!project.plugins.hasPlugin("org.jetbrains.kotlin.android")) {
            return false
        }

        return project.plugins.hasPlugin("com.android.application") ||  project.plugins.hasPlugin("com.android.library")
    }
//
//    private fun Project.getDefaultInputFiles(): Iterable<File> {
//        return extensions.getByType(KotlinProjectExtension::class.java)
//            .sourceSets.getByName("main")
//            .kotlin
//    }
}