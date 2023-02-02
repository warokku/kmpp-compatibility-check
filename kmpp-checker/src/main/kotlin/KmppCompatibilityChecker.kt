package com.amosolov.kmpp.compatibility.checker

import java.io.File

class KmppCompatibilityChecker(private val rules: Set<Rule>) {
    fun checkSources(files: Iterable<File>): List<Report> {
        val ruleCheckers = rules.map { it.getChecker() }
        var checkResults = mutableListOf<Report>()

        files.forEach { file: File ->
            file.readLines().forEachIndexed { index: Int, line: String ->
                val reports = ruleCheckers
                    .map { it.check(line) }
                    .filterNotNull()
                    .map {
                        Report(file, index + 1, it)
                    }
                checkResults.addAll(reports)
            }
        }

        return checkResults
    }
}

class Report(val file: File, val lineNumber: Int, val message: String)