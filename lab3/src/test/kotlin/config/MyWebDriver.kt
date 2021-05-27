package config

import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

class MyWebDriver {
    val CHROME = "webdriver.chrome.driver"
    val FIREFOX = "webdriver.firefox.driver"
    var CURRENT = "webdriver.current.driver"
    lateinit var driver: WebDriver

    init {
        //options.addArguments("--user-data-dir=C:\\Users\\sk171\\AppData\\Local\\Google\\Chrome\\User Data")
        //options.addArguments("--profile-directory=Default");
        //options.addArguments("--disable-extensions");
        val currentDriver = Config.get(CURRENT)
        when (Config.get(CURRENT)) {
            FIREFOX -> {
                System.setProperty("webdriver.gecko.driver", Config.get(currentDriver));
                driver = FirefoxDriver()
            }
            CHROME -> {
                /*val options = ChromeOptions()
                options.addArguments("start-maximized")*/
                driver = ChromeDriver()
            }
        }
    }
}