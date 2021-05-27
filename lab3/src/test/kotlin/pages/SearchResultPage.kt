package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class SearchResultPage(private var driver: WebDriver) : Page(driver, "//h3[contains(text(),'Search results:')]") {
    private val searchResults: By = By.xpath("//div/div[2]/div/div/div/div")
    private val firstSearchResults: By = By.xpath("//h3[contains(text(),'Search results:')]//..//div//div//div")

    var searchResultsElem: WebElement = driver.findElement(searchResults)
    var firstSearchResultsElem: WebElement = driver.findElement(firstSearchResults)

    fun openFirstResult() {
        WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(firstSearchResultsElem))?.click()
    }
}