package system

import funs.system.Fun2
import funs.log.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class Fun2Test {

    companion object {
        private lateinit var mockLn: Ln
        private lateinit var mockLog2: Log2
        private lateinit var mockLog3: Log3
        private lateinit var mockLog5: Log5
        private lateinit var mockLog10: Log10

        private lateinit var fun2: Fun2
        private const val accuracy = 1e-4

        private val inputs = arrayOf(1.0, 2.0, 3.0, 5.0, 10.0, 50.0, 0.001)
        private val mockLnRet = arrayOf(0.0, 0.69314718056, 1.09861228867, 1.60943791243, 2.30258509299, 3.91202300543, -6.90775527898)
        private val mockLog2Ret = arrayOf(0.0, 1.0, 1.58496250072, 2.32192809489, 3.32192809489, 5.64385618977, -9.96578428466)
        private val mockLog3Ret = arrayOf(0.0, 0.63092975357, 1.0, 1.46497352072, 2.09590327429, 3.56087679501, -6.28770982287)
        private val mockLog5Ret = arrayOf(0.0, 0.43067655807, 0.68260619448, 1.0, 1.43067655807, 2.43067655807, -4.29202967422)
        private val mockLog10Ret = arrayOf(0.0, 0.30102999566, 0.47712125472, 0.69897000433, 1.0, 1.69897000434, -3.0)

        private val expectedFun2 = arrayOf(Double.NaN, -0.39211718489, -1.284593797685, -4.4677742187788678, -14.1947459, -74.2770437209, 418.968)

        @JvmStatic
        @BeforeAll
        fun setup() {
            mockLn = mock(Ln::class.java)
            for(i in inputs.indices){
                whenever(mockLn(inputs[i])).thenReturn(mockLnRet[i])
            }
            mockLog2 = mock(Log2::class.java)
            for(i in inputs.indices){
                whenever(mockLog2(inputs[i])).thenReturn(mockLog2Ret[i])
            }
            mockLog3 = mock(Log3::class.java)
            for(i in inputs.indices){
                whenever(mockLog3(inputs[i])).thenReturn(mockLog3Ret[i])
            }
            mockLog5 = mock(Log5::class.java)
            for(i in inputs.indices){
                whenever(mockLog5(inputs[i])).thenReturn(mockLog5Ret[i])
            }
            mockLog10 = mock(Log10::class.java)
            for(i in inputs.indices){
                whenever(mockLog10(inputs[i])).thenReturn(mockLog10Ret[i])
            }

            fun2= Fun2(accuracy, mockLn, mockLog2, mockLog3, mockLog5, mockLog10)
        }
        @JvmStatic
        fun arguments() = List(inputs.size) { Arguments.of(expectedFun2[it], inputs[it]) }

    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun fun2Test(expected: Double, input: Double) {
        Assertions.assertEquals(expected, fun2(input), accuracy)
    }
}