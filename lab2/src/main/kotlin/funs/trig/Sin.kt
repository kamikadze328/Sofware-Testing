package funs.trig

import funs.Fun
import util.factorial
import kotlin.math.abs
import kotlin.math.pow

open class Sin(accuracy: Double) : Fun(accuracy) {
    private val maxCycles = 10

    override fun invoke(x: Double): Double {
        if (x.isNaN() || x.isInfinite()) return Double.NaN

        var result = 0.0
        var prev: Double
        var n = 0
        do {
            prev = result
            result += (-1.0).pow(n) * x.pow(2 * n + 1) / (1.0 * factorial(2 * n + 1))
            n++
        }while (abs(prev - result) >= accuracy && n < maxCycles)

        return result
    }
}