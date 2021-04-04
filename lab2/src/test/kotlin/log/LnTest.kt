package log

import funs.log.Ln
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource


class LnTest {
    private val accuracy = 1e-3
    private val ln = Ln(accuracy)

    companion object {
        @JvmStatic
        fun arguments() = listOf(
            Arguments.of(0.0, 1.0),
            Arguments.of(0.693, 2.0),
            Arguments.of(1.099, 3.0),
            Arguments.of(1.386, 4.0),
            Arguments.of(1.609, 5.0),
            Arguments.of(Double.NaN, 0.0),
            Arguments.of(Double.NaN, -0.001)
            )
    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun lnTest(expected: Double, input: Double) {
        assertEquals(expected, ln(input), accuracy)
    }
}