package funs

abstract class Fun(val accuracy: Double = 10e-5) {
    abstract operator fun invoke(x: Double): Double
}