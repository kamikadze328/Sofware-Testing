package trig

import funs.trig.Cos
import funs.trig.Sin
import funs.trig.Cot
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.math.PI

class CotTest {

    companion object {
        private lateinit var mockSin: Sin
        private lateinit var mockCos: Cos
        private lateinit var cot: Cot
        private const val accuracy = 1e-5

        private val inputs = arrayOf(0.0, PI, -PI, 0.5 * PI, -0.5 * PI, 1.0 / 3.0 * PI, -1.0 / 3.0 * PI, 2.0 / 3.0 * PI, -2.0 / 3.0 * PI, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
        private val mockSinRet = arrayOf(0.0, 0.0, 0.0, 1.0, -1.0, 0.86602540378, -0.86602540378, 0.86602540378, -0.86602540378, Double.NaN, Double.NaN, Double.NaN)
        private val mockCosRet = arrayOf(1.0, -1.0, -1.0, 0.0, 0.0, 0.5, 0.5, -0.5, -0.5, Double.NaN, Double.NaN, Double.NaN)
        private val expectedCot = arrayOf(Double.NaN, Double.NaN, Double.NaN, 0.0, 0.0, 0.57735026919, -0.57735026919, -0.57735026919, 0.57735026919, Double.NaN, Double.NaN, Double.NaN)

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

            cot = Cot(accuracy, mockSin, mockCos)
        }
        @JvmStatic
        fun arguments() = List(inputs.size) { Arguments.of(expectedCot[it], inputs[it]) }

    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun cotTest(expected: Double, input: Double) {
        Assertions.assertEquals(expected, cot(input), accuracy)
    }
}