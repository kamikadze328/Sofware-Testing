package funs.system

import funs.Fun

class System(
    accuracy: Double,
    private val fun1: Fun1,
    private val fun2: Fun2
) : Fun(accuracy) {
    override fun invoke(x: Double): Double = if (x <= 0) fun1(x) else fun2(x)

}