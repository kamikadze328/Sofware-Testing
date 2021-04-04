package funs.log

import funs.Fun

abstract class Log(
    accuracy: Double,
    private val a: Int,
    private val ln: Ln = Ln(accuracy)
) : Fun(accuracy) {
    override fun invoke(x: Double): Double = ln(x) / ln(1.0 * a)
}
