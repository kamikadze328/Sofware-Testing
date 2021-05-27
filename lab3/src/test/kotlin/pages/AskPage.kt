package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class AskPage(private val driver: WebDriver) : Page(driver, "//h1[contains(text(),'Ask Your Question:')]") {
    private val input: By = By.xpath("//textarea[contains(@placeholder, '\"How much should I feed my dog?\"')]")
    private val submitButton: By = By.xpath("//span[contains(text(),'Submit')]")
    private val confirmation: By = By.xpath("//div/div[5]//span[contains(text(),'You are not allowed to do that.')]")

    var inputElem: WebElement = driver.findElement(input)
    var submitButtonElem: WebElement = driver.findElement(submitButton)

    fun ask(question: String){
        inputElem.click()
        inputElem.sendKeys(question)
        submitButtonElem.click()
    }

    fun checkAsking(): Boolean {
        //return driver.findElements(confirmation).isNotEmpty()
        return driver.findElements(submitButton).isNotEmpty()
    }

}