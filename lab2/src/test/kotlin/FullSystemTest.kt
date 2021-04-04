package system

import funs.log.*
import funs.trig.*
import funs.system.*
import org.junit.jupiter.api.AfterAll

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import util.CSVWriter
import kotlin.math.PI

class FullSystemTest {

    private val testDelta = 10e-3

    companion object {
        private lateinit var system: System
        private const val accuracy = 1e-5

        private val inputs = arrayOf(1.0, 2.0, 3.0, 5.0, 10.0, 50.0, 0.0, -PI, -0.5 * PI, -1.0 / 3.0 * PI, -2.0 / 3.0 * PI, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)
        private val expectedSystem = arrayOf(Double.NaN, -0.39211718489, -1.284593797685, -4.4677742187788678, -14.1947459, -74.2770437209, Double.NaN, Double.NaN, Double.NaN, 2.0, -2.0,  Double.NaN, Double.NaN)

        private const val filename = "system.csv"

        @JvmStatic
        @BeforeAll
        fun setup() {
            val cos =  Cos(accuracy)
            val tan =  Tan(accuracy)
            val cot =  Cot(accuracy)
            val ln = Ln(accuracy)
            val log2 = Log2(ln, accuracy)
            val log3 = Log3(ln, accuracy)
            val log5 = Log5(ln, accuracy)
            val log10 = Log10(ln, accuracy)

            system = System(accuracy, Fun1(accuracy, cos, tan, cot), Fun2(accuracy, ln, log2, log3, log5, log10))
        }
        @JvmStatic
        fun arguments() = List(inputs.size) { Arguments.of(expectedSystem[it], inputs[it]) }

        @JvmStatic
        @AfterAll
        fun writeToFile(){
            CSVWriter(filename, system).writeFunc(-10.0, 10.0)
        }
    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun systemTest(expected: Double, input: Double) {
        Assertions.assertEquals(expected, system(input), testDelta)
    }


}
