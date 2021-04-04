package system

import funs.system.Fun1
import funs.trig.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.math.PI

class Fun1Test {

    companion object {
        private lateinit var mockCos: Cos
        private lateinit var mockTan: Tan
        private lateinit var mockCot: Cot
        private lateinit var fun1: Fun1
        private const val accuracy = 1e-5

        private val inputs = arrayOf(0.0, PI, -PI, 0.5 * PI, -0.5 * PI, 1.0 / 3.0 * PI, -1.0 / 3.0 * PI, 2.0 / 3.0 * PI, -2.0 / 3.0 * PI, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
        private val mockCosRet = arrayOf(1.0, -1.0, -1.0, 0.0, 0.0, 0.5, 0.5, -0.5, -0.5, Double.NaN, Double.NaN, Double.NaN)
        private val mockTanRet = arrayOf(0.0, 0.0, 0.0, Double.NaN, Double.NaN, 1.73205080757, -1.73205080757, -1.73205080757, 1.73205080757, Double.NaN, Double.NaN, Double.NaN)
        private val mockCotRet = arrayOf(Double.NaN, Double.NaN, Double.NaN, 0.0, 0.0, 0.57735026919, -0.57735026919, -0.57735026919, 0.57735026919, Double.NaN, Double.NaN, Double.NaN)

        private val expectedFun1 = arrayOf(Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, 2.0, 2.0, -2.0, -2.0, Double.NaN, Double.NaN, Double.NaN)

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockCos = mock(Cos::class.java)
            for(i in inputs.indices){
                whenever(mockCos(inputs[i])).thenReturn(mockCosRet[i])
            }

            mockTan = mock(Tan::class.java)
            for(i in inputs.indices){
                whenever(mockTan(inputs[i])).thenReturn(mockTanRet[i])
            }

            mockCot = mock(Cot::class.java)
            for(i in inputs.indices){
                whenever(mockCot(inputs[i])).thenReturn(mockCotRet[i])
            }

            fun1= Fun1(accuracy, mockCos, mockTan, mockCot)
        }
        @JvmStatic
        fun arguments() = List(inputs.size) { Arguments.of(expectedFun1[it], inputs[it]) }

    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun fun1Test(expected: Double, input: Double) {
        Assertions.assertEquals(expected, fun1(input), accuracy)
    }
}