package trig

import funs.trig.Sin
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.PI
import kotlin.math.sqrt

class SinTest {
    private val accuracy = 1e-5
    private val sin = Sin(accuracy)

    companion object {
        @JvmStatic
        fun arguments() = listOf(
            Arguments.of(.0, .0),
            Arguments.of(.0, PI),
            Arguments.of(.0, -PI),
            Arguments.of(1.0, 0.5 * PI),
            Arguments.of(-1.0, -0.5 * PI),
            Arguments.of(sqrt(3.0) / 2, 1.0 / 3.0 * PI),
            Arguments.of(-sqrt(3.0) / 2, -1.0 / 3.0 * PI),
            Arguments.of(sqrt(3.0) / 2, 2.0 / 3.0 * PI),
            Arguments.of(-sqrt(3.0) / 2, -2.0 / 3.0 * PI),
            Arguments.of(Double.NaN, Double.NaN),
            Arguments.of(Double.NaN, Double.NEGATIVE_INFINITY),
            Arguments.of(Double.NaN, Double.POSITIVE_INFINITY),
        )
    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun sinTest(expected: Double, input: Double) {
        assertEquals(expected, sin(input), accuracy)
    }
}