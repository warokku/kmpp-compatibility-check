package com.amosolov.kmpp.compatibility.checker

class JdkClassRuleChecker: RuleChecker {

    private val illegalClasses = setOf<String>("AccessDeniedException")

    override fun check(line: String): String? {
        illegalClasses.forEach {
            if (line.contains(it)) {
                return "JdkClassRule check failed: $it not supported on iOS"
            }
        }

        return null
    }
}