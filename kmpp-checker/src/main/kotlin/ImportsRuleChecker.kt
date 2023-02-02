package com.amosolov.kmpp.compatibility.checker

class ImportsRuleChecker: RuleChecker {
    override fun check(line: String): String? {
        if (line.contains("import java.io.File")) {
            return "ImportRule check failed"
        }

        return null
    }
}