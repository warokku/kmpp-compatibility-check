import com.amosolov.kmpp.compatibility.checker.KmppCompatibilityChecker
import com.amosolov.kmpp.compatibility.checker.Rule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class KmppCompatibilityCheckerTest {

    @field:TempDir
    lateinit var testSourceDir: File

    @Test
    fun `checker returns no reports for empty inputs`() {
        val checker = KmppCompatibilityChecker(setOf(Rule.IMPORTS))

        assertTrue(checker.checkSources(emptyList()).isEmpty())
    }

    @Test
    fun `checker returns no reports for inputs with issues if no rules are enabled`() {
        val testSource = buildTestSource().appendingBadImport()
        val checker = KmppCompatibilityChecker(emptySet())

        assertTrue(checker.checkSources(setOf(testSource)).isEmpty())
    }

    @Test
    fun `checker returns no reports for inputs with no issues`() {
        val testSource = buildTestSource().appendingGoodImport()
        val checker = KmppCompatibilityChecker(setOf(Rule.IMPORTS))

        assertTrue(checker.checkSources(setOf(testSource)).isEmpty())
    }

    @Test
    fun `checker returns no reports for inputs with issues if they don't match enabled rules`() {
        val testSource = buildTestSource().appendingBadImport()
        val checker = KmppCompatibilityChecker(setOf(Rule.JDK_CLASSES))

        assertTrue(checker.checkSources(setOf(testSource)).isEmpty())
    }

    @Test
    fun `checker returns reports for inputs with issues if corresponding rules are enabled`() {
        val testSource = buildTestSource().appendingBadImport()
        val checker = KmppCompatibilityChecker(setOf(Rule.IMPORTS))

        assertFalse(checker.checkSources(setOf(testSource)).isEmpty())
    }

    private fun buildTestSource() = testSourceDir.resolve("testSource.kt")

    private fun File.appendingGoodImport(): File {
        this.appendText("""
            import kotlinx.coroutines.delay
        """.trimIndent())

        return this
    }

    private fun File.appendingBadImport(): File {
        this.appendText("""
            import java.io.File
        """.trimIndent())

        return this
    }
}