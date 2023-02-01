package com.amosolov.kmpp.compatibility.check

import org.gradle.api.DefaultTask
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class KmppCompatibilityCheckTask @Inject constructor(
    objectFactory: ObjectFactory
): DefaultTask() {
    @get:InputFiles
    abstract val inputFiles: ConfigurableFileCollection

    @TaskAction
    fun check() {
        println("Checking files: ${inputFiles.files.map { it.name }}")
    }
}