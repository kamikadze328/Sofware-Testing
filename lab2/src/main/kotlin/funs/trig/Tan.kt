package funs.trig

import funs.Fun
import kotlin.math.abs

open class Tan(
    accuracy: Double,
    private val sin: Sin = Sin(accuracy),
    private val cos: Cos = Cos(accuracy)
) : Fun(accuracy) {
    override fun invoke(x: Double): Double {
        val cosVal = cos(x)
        if (abs(cosVal) < accuracy) return Double.NaN
        return sin(x) / cos(x)
    }
}