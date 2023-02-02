package com.amosolov.kmpp.compatibility.checker

enum class Rule {
    IMPORTS, JDK_CLASSES
}

interface RuleChecker {
    fun check(line: String): String?
}

internal fun Rule.getChecker(): RuleChecker = when (this) {
    Rule.IMPORTS -> ImportsRuleChecker()
    Rule.JDK_CLASSES -> JdkClassRuleChecker()
}