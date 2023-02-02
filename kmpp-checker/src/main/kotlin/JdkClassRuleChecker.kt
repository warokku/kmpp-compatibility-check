package com.amosolov.kmpp.compatibility.checker

class JdkClassRuleChecker: RuleChecker {
    override fun check(line: String): String? {
        if (line.contains("kotlin.io.AccessDeniedException")) {
            return "JdkClassRule check failed"
        }

        return null
    }
}