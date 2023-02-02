package com.amosolov.kmpp.compatibility.check

import com.amosolov.kmpp.compatibility.checker.KmppCompatibilityChecker
import com.amosolov.kmpp.compatibility.checker.Rule
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.provider.Property
import org.gradle.api.tasks.*
import org.gradle.work.ChangeType
import org.gradle.work.InputChanges

abstract class KmppCompatibilityCheckTask: DefaultTask() {
    @get:InputFiles
    @get:SkipWhenEmpty
    @get:IgnoreEmptyDirectories
    abstract val inputFiles: ConfigurableFileCollection

    @get:Input
    val rules = mutableSetOf<Rule>()

    @get:Input
    abstract val strict: Property<Boolean>

    init {
        // Task
        // Required for @Incremental to work
        outputs.upToDateWhen { true }
    }

    @TaskAction
    fun check(inputChanges: InputChanges) {
        val changedFiles = inputChanges.getFileChanges(inputFiles)
            .filter { it.changeType in setOf(ChangeType.MODIFIED, ChangeType.ADDED) }
            .map { it.file }

        if (changedFiles.isEmpty()) {
            logger.info("Received empty input")
            return
        }

        logger.info("Input:\n")
        logger.info(changedFiles.joinToString(separator = "\n") { it.absolutePath })

        logger.info("Enabled rules:\n")
        logger.info(rules.joinToString(separator = ", ") { it.name })

        val checker = KmppCompatibilityChecker(rules)
        val errorReports = checker.checkSources(changedFiles)

        if (errorReports.isEmpty()) {
            return
        }

        errorReports.forEach {
            log("${it.file.absolutePath}: line ${it.lineNumber} - ${it.message}")
        }

        val finalMessage = "Got ${errorReports.size} KMPP compatibility error(s)"

        if (strict.get()) {
            throw GradleException(finalMessage)
        }

        logger.warn(finalMessage)
    }

    private fun log(message: String) {
        if (strict.get()) {
            logger.error(message)
        } else {
            logger.warn(message)
        }
    }
}