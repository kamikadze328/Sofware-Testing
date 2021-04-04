package util

private val historyFactorial: HashMap<Int, Long> = hashMapOf()

fun factorial(number: Int): Long {
    require(number >= 0) { "Argument can't be less than zero" }
    var result = 1L
    if (number == 0) return 1
    for (i in 1..number) {
        result = historyFactorial.getOrPut(i, { StrictMath.multiplyExact(result, i.toLong()) })
    }
    return result
}
