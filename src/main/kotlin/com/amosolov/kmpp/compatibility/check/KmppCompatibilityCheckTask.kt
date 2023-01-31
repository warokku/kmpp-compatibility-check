package com.amosolov.kmpp.compatibility.check

import org.gradle.api.DefaultTask
import org.gradle.api.model.ObjectFactory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

open class KmppCompatibilityCheckTask @Inject constructor(
    objectFactory: ObjectFactory
): DefaultTask() {

    @TaskAction
    fun check() {
        println("Check task executed")
    }
}