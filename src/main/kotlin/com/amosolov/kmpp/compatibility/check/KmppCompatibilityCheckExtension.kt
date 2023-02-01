package com.amosolov.kmpp.compatibility.check

import org.gradle.api.provider.Property
import java.io.File

abstract class KmppCompatibilityCheckExtension {
    abstract val inputFile: Property<File>
}