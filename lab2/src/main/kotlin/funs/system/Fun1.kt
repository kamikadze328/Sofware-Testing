package funs.system

import funs.Fun
import funs.trig.Cos
import funs.trig.Sin
import funs.trig.Cot
import funs.trig.Tan
import kotlin.math.abs

open class Fun1(
    accuracy: Double,
    private val cos: Cos = Cos(accuracy),
    private val tan: Tan = Tan(accuracy, Sin(accuracy), cos),
    private val cot: Cot = Cot(accuracy, Sin(accuracy), cos)
) : Fun(accuracy) {
    override fun invoke(x: Double): Double {
        val cosVal = cos(x)
        val sinVal = Sin(accuracy)(x)
        return if(cosVal.isNaN() || sinVal.isNaN() || abs(cosVal) < accuracy || abs(sinVal) < accuracy) Double.NaN
        else tan(x) * cot(x) / cosVal
    }
}