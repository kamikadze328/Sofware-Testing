package log

import funs.log.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever


class LogTest {

    companion object {
        private lateinit var mockLn: Ln
        private lateinit var log2: Log2
        private lateinit var log3: Log3
        private lateinit var log5: Log5
        private lateinit var log10: Log10

        private val inputs = arrayOf(1.0, 2.0, 3.0, 5.0, 10.0, 100.0, 0.001)
        private val mockLnRet = arrayOf(0.0, 0.69314718056, 1.09861228867, 1.60943791243, 2.30258509299, 4.60517018599, -6.90775527898)
        private val mockLog2Ret = arrayOf(0.0, 1.0, 1.58496250072, 2.32192809489, 3.32192809489, 6.64385618977, -9.96578428466)
        private val mockLog3Ret = arrayOf(0.0, 0.63092975357, 1.0, 1.46497352072, 2.09590327429, 4.19180654858, -6.28770982287)
        private val mockLog5Ret = arrayOf(0.0, 0.43067655807, 0.68260619448, 1.0, 1.43067655807, 2.86135311615, -4.29202967422)
        private val mockLog10Ret = arrayOf(0.0, 0.30102999566, 0.47712125472, 0.69897000433, 1.0, 2.0, -3.0)

        private const val accuracy = 10e-4


        @JvmStatic
        @BeforeAll
        fun setup() {
            mockLn = mock(Ln::class.java)
            for(i in inputs.indices){
                whenever(mockLn(inputs[i])).thenReturn(mockLnRet[i])
            }
            log2 = Log2(mockLn, accuracy)
            log3 = Log3(mockLn, accuracy)
            log5 = Log5(mockLn, accuracy)
            log10 = Log10(mockLn, accuracy)
        }

        @JvmStatic
        fun argumentsLog2() = List(inputs.size) { Arguments.of(mockLog2Ret[it], inputs[it]) }
        @JvmStatic
        fun argumentsLog3() = List(inputs.size) { Arguments.of(mockLog3Ret[it], inputs[it]) }
        @JvmStatic
        fun argumentsLog5() = List(inputs.size) { Arguments.of(mockLog5Ret[it], inputs[it]) }
        @JvmStatic
        fun argumentsLog10() = List(inputs.size) { Arguments.of(mockLog10Ret[it], inputs[it]) }

    }

    @ParameterizedTest
    @MethodSource("argumentsLog2")
    fun log2Test(expected: Double, input: Double) {
        assertEquals(expected, log2(input), accuracy)
    }

    @ParameterizedTest
    @MethodSource("argumentsLog3")
    fun log3Test(expected: Double, input: Double) {
        assertEquals(expected, log3(input), accuracy)
    }

    @ParameterizedTest
    @MethodSource("argumentsLog5")
    fun log5Test(expected: Double, input: Double) {
        assertEquals(expected, log5(input), accuracy)
    }

    @ParameterizedTest
    @MethodSource("argumentsLog10")
    fun log10Test(expected: Double, input: Double) {
        assertEquals(expected, log10(input), accuracy)
    }
}