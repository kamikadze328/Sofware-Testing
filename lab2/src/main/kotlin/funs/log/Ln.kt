package funs.log

import funs.Fun
import kotlin.math.abs
import kotlin.math.pow

open class Ln(accuracy: Double) : Fun(accuracy) {
    private val maxCycles = 100

    override fun invoke(x: Double): Double {
        require(accuracy > 0) { "Accuracy must be positive, was $accuracy" }
        if (x <= 0) return Double.NaN
        var current = 0.0
        var prev: Double
        var n = 0
        do {
            prev = current
            current += tailor(n++, x)
        } while (abs(prev - current) >= accuracy && n < maxCycles)

        return current
    }

    private fun tailor(n: Int, x: Double): Double {
        val z = (x - 1) / (x + 1)
        val pow = 2 * n + 1
        return 2 * z.pow(pow) / pow
    }
}