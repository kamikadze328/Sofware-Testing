package funs.trig

import funs.Fun
import kotlin.math.abs

open class Cot(
    accuracy: Double,
    private val sin: Sin = Sin(accuracy),
    private val cos: Cos = Cos(accuracy)
) : Fun(accuracy) {
    override fun invoke(x: Double): Double {
        val sinVal = sin(x)
        if (abs(sinVal) < accuracy) return Double.NaN
        return cos(x) / sin(x)
    }
}