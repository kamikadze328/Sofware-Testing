import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import kotlin.math.sqrt
import kotlin.math.PI

class MathTest {
    private val PRECISION = .000001

    companion object{
        @JvmStatic
        fun arguments() = listOf(
            Arguments.of(.0, .0),
            Arguments.of(.0, PI),
            Arguments.of(.0, -PI),
            Arguments.of(1.0, 0.5*PI),
            Arguments.of(-1.0, -0.5 * PI),
            Arguments.of(sqrt(3.0) / 2, 1.0 / 3.0 * PI),
            Arguments.of(-sqrt(3.0) / 2, - 1.0 / 3.0 * PI),
            Arguments.of(sqrt(3.0) / 2, 2.0 / 3.0 * PI),
            Arguments.of(-sqrt(3.0) / 2, -2.0 / 3.0 * PI),
            )
    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun `test parameterized values`(expected: Double, input: Double){
        assertEquals(expected, MyMath.sin(input), PRECISION)
    }

    @Test
    fun `test NaN and infinity values`(){
        assertEquals(Double.NaN, MyMath.sin(Double.NaN), PRECISION)
        assertEquals(Double.NaN, MyMath.sin(Double.NEGATIVE_INFINITY), PRECISION)
        assertEquals(Double.NaN, MyMath.sin(Double.POSITIVE_INFINITY), PRECISION)
    }
}