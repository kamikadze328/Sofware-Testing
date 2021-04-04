package trig

import funs.trig.Cos
import funs.trig.Sin
import funs.trig.Tan
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.math.PI

class TanTest {

    companion object {
        private lateinit var mockSin: Sin
        private lateinit var mockCos: Cos
        private lateinit var tan: Tan
        private const val accuracy = 1e-5

        private val inputs = arrayOf(0.0, PI, -PI, 0.5 * PI, -0.5 * PI, 1.0 / 3.0 * PI, -1.0 / 3.0 * PI, 2.0 / 3.0 * PI, -2.0 / 3.0 * PI, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
        private val mockSinRet = arrayOf(0.0, 0.0, 0.0, 1.0, -1.0, 0.86602540378, -0.86602540378, 0.86602540378, -0.86602540378, Double.NaN, Double.NaN, Double.NaN)
        private val mockCosRet = arrayOf(1.0, -1.0, -1.0, 0.0, 0.0, 0.5, 0.5, -0.5, -0.5, Double.NaN, Double.NaN, Double.NaN)
        private val expectedTan = arrayOf(0.0, 0.0, 0.0, Double.NaN, Double.NaN, 1.73205080757, -1.73205080757, -1.73205080757, 1.73205080757, Double.NaN, Double.NaN, Double.NaN)

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockSin = mock(Sin::class.java)
            for(i in inputs.indices){
                whenever(mockSin(inputs[i])).thenReturn(mockSinRet[i])
            }

            mockCos = mock(Cos::class.java)
            for(i in inputs.indices){
                whenever(mockCos(inputs[i])).thenReturn(mockCosRet[i])
            }

            tan = Tan(accuracy, mockSin, mockCos)
        }
        @JvmStatic
        fun arguments() = List(inputs.size) { Arguments.of(expectedTan[it], inputs[it]) }

    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun tanTest(expected: Double, input: Double) {
        Assertions.assertEquals(expected, tan(input), accuracy)
    }
}