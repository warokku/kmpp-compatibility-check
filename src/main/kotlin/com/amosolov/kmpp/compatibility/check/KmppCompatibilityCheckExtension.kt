package com.amosolov.kmpp.compatibility.check

import org.gradle.api.file.ConfigurableFileCollection
import org.gradle.api.tasks.util.PatternSet

abstract class KmppCompatibilityCheckExtension {
    abstract val inputFiles: ConfigurableFileCollection
    val filter = PatternSet() 
}