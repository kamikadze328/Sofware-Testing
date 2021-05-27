package config

import java.io.FileInputStream
import java.util.*


class Config {
    companion object {
        var fileInputStream: FileInputStream = FileInputStream("src/test/resources/config.properties")
        var PROPERTIES: Properties = Properties()

        init {
            fileInputStream.use {
                PROPERTIES.load(it)
            }
        }

        fun get(key: String): String = PROPERTIES.getProperty(key)
    }
}
