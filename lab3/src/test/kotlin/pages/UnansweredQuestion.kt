package pages

import org.openqa.selenium.WebDriver

class UnansweredQuestion(private val driver: WebDriver) : Page(driver, "//p[contains(text(),'Be the first to answer!')]")