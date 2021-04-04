package funs.system

import funs.Fun
import funs.log.*
import kotlin.math.pow

open class Fun2(
    accuracy: Double,
    private val ln: Ln = Ln(accuracy),
    private val log2: Log2 = Log2(ln, accuracy),
    private val log3: Log3 = Log3(ln, accuracy),
    private val log5: Log5 = Log5(ln, accuracy),
    private val log10: Log10 = Log10(ln, accuracy),
) : Fun(accuracy) {
    override fun invoke(x: Double): Double = if(x!= 1.0) ((log3(x) + log10(x)) * log2(x) - log3(x)) / log2(x) - (log2(x).pow(2) * log5(x) - (log5(x) - ln(x))) else Double.NaN
}