package system

import funs.system.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import kotlin.math.PI

class SystemTest {

    companion object {
        private lateinit var mockFun1: Fun1
        private lateinit var mockFun2: Fun2

        private lateinit var system: System
        private const val accuracy = 1e-4

        private val inputs = arrayOf(1.0, 2.0, 3.0, 5.0, 10.0, 50.0, 0.001, 0.0, -PI, -0.5 * PI, -1.0 / 3.0 * PI, -2.0 / 3.0 * PI, Double.NaN, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
        private val mockFun1Ret = arrayOf(1.85081571768, -2.40299796, -1.0101086659, 1.003819837543, 1.01542661188, 1.55572382686, 1.0000005, Double.NaN, Double.NaN, Double.NaN, 2.0, -2.0,  Double.NaN, Double.NaN, Double.NaN)
        private val mockFun2Ret = arrayOf(Double.NaN, -0.39211718489, -1.284593797685, -4.4677742187788678, -14.1947459, -74.2770437209, 418.968, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN, Double.NaN)

        private val expectedSystem = arrayOf(Double.NaN, -0.39211718489, -1.284593797685, -4.4677742187788678, -14.1947459, -74.2770437209, 418.968, Double.NaN, Double.NaN, Double.NaN, 2.0, -2.0,  Double.NaN, Double.NaN, Double.NaN)

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockFun1 = mock(Fun1::class.java)
            for(i in inputs.indices){
                whenever(mockFun1(inputs[i])).thenReturn(mockFun1Ret[i])
            }
            mockFun2 = mock(Fun2::class.java)
            for(i in inputs.indices){
                whenever(mockFun2(inputs[i])).thenReturn(mockFun2Ret[i])
            }

            system = System(accuracy, mockFun1, mockFun2)
        }
        @JvmStatic
        fun arguments() = List(inputs.size) { Arguments.of(expectedSystem[it], inputs[it]) }

    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun systemTest(expected: Double, input: Double) {
        Assertions.assertEquals(expected, system(input), accuracy)
    }
}
