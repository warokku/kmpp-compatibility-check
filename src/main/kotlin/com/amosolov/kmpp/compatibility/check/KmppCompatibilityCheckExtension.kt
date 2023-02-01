package com.amosolov.kmpp.compatibility.check

import org.gradle.api.file.ConfigurableFileCollection

abstract class KmppCompatibilityCheckExtension {
    abstract val inputFiles: ConfigurableFileCollection
}