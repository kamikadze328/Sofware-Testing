
import java.lang.StrictMath.multiplyExact
import kotlin.math.pow

object MyMath {
    fun sin(a: Double): Double {
        if (a.isNaN() || a.isInfinite()) return Double.NaN
        else if (a == 0.0) return a
        var result = 0.0
        for (n in 0..7) {
            result += (-1.0).pow(n) * a.pow(2 * n + 1) / (1.0 * factorial(2 * n + 1))
        }
        return result
    }
    private fun factorial(number: Int): Long {
        require(number >= 0) { "Argument can't be less than zero" }
        var result = 1L
        if (number==0) return 1
        for (i in 1..number) {
            result = multiplyExact(result, i.toLong())
        }
        return result
    }

}