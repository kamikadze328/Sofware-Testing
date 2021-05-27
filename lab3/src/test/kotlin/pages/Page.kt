package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

open class Page(private val driver: WebDriver, titleXPAth: String) {
    private val title: By = By.xpath(titleXPAth)

    fun isPageOpened(): Boolean = driver.findElement(title).isDisplayed

}