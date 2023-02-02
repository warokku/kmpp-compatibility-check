package com.amosolov.kmpp.compatibility.checker

class ImportsRuleChecker: RuleChecker {

    private val illegalImports = setOf<String>("import java.io.File")

    override fun check(line: String): String? {
        illegalImports.forEach {
            if (line.contains(it)) {
                return "ImportRule check failed: $it not supported on iOS"
            }
        }

        return null
    }
}