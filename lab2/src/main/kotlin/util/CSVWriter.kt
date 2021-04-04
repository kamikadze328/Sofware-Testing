package util

import funs.Fun
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

class CSVWriter(private val filename: String, private val func: Fun) : AutoCloseable {
    private val file = File(filename)
    private lateinit var writer: BufferedWriter

    init {
        if (!file.exists())
            require(file.createNewFile()) { "Impossible to create the file with name $filename" }

        writer = BufferedWriter(FileWriter(file))
    }

    fun writeFunc(start: Double, end: Double, step: Double = 0.01) {
        writer.write("x;y(x)")
        writer.newLine()
        var current = start
        var y: Double
        do {
            y = func(current)
            writer.write("$current;$y")
            writer.newLine()
            current += step

        } while (end > current)
        writer.flush()
    }

    override fun close() {
        writer.close()
    }

}