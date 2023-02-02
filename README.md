### Usage

```
plugins {
    id("com.amosolov.kmpp-compatibility-check") version "1.0"
}

kmppCompatibilityCheck {
    inputFiles                      // File collection for inputs
                                    // Example: inputFiles.from(File("src/main/kotlin/testSourceBadImport.kt"))  
    filter                          // Set of patterns to filter inputs
                                    // Example: filter.exclude("*.java")
    rules                           // Set of rules to check against
                                    // Example: rules.add(com.amosolov.kmpp.compatibility.checker.Rule.IMPORTS)
    errorInsteadOfWarnings = false  // Throw exception if any issues are found and log reports as errors (false by default)
    allRulesEnabled = true          // Enable all rules (true by default)
}
```

### Things to improve

- Rule checkers are very basic, right now there're only two:
-- IMPORTS check for bad import based on hardcoded list
-- JDK_CLASSES checks for class names that are part of [stdlib](https://kotlinlang.org/api/latest/jvm/stdlib/alltypes/) but not part of Common.
- Not happy with Rule enum class defined in a way that makes using it in build.gradle.kts kinda ugly
- Reporting is very basic, utilizing logger to write either warnings or errors. Could've been made more configurable. I was looking at Detekt plugin but their implementation is too complex to be reproduced even in simplified form. 
- I was trying to pass main/kotlin sourcets to inputFiles by default (commented code removed in commit `8f123fc`) but encountered problem that I couldn't solve.
```
// Added `implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")` to build.gradle.kts dependencies

FAILURE: Build failed with an exception.

* What went wrong:
com/android/build/gradle/BaseExtension
> com.android.build.gradle.BaseExtension
```

