package trig

import funs.trig.Cos
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.PI

class CosTest {
    private val accuracy = 1e-5
    private val cos = Cos(accuracy)

    companion object {
        @JvmStatic
        fun arguments() = listOf(
            Arguments.of(1.0, .0),
            Arguments.of(-1.0, PI),
            Arguments.of(-1.0, -PI),
            Arguments.of(0.0, 0.5 * PI),
            Arguments.of(0.0, -0.5 * PI),
            Arguments.of(0.5, 1.0 / 3.0 * PI),
            Arguments.of(0.5, -1.0 / 3.0 * PI),
            Arguments.of(-0.5, 2.0 / 3.0 * PI),
            Arguments.of(-0.5, -2.0 / 3.0 * PI),
            Arguments.of(Double.NaN, Double.NaN),
            Arguments.of(Double.NaN, Double.NEGATIVE_INFINITY),
            Arguments.of(Double.NaN, Double.POSITIVE_INFINITY),
        )
    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun cosTest(expected: Double, input: Double) {
        Assertions.assertEquals(expected, cos(input), accuracy)
    }
}