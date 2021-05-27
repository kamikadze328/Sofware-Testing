package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class LogInPage(private val driver: WebDriver) : Page(driver, "//h2[contains(text(),'Login in with email')]") {
    private val byEmailButton: By = By.xpath("//span[contains(text(), 'Log in with email')]/ancestor::button[last()]")
    private val loginInput: By = By.xpath("//label[contains(text(), 'Email Address or Username')]//..//input")
    private val passwordInput: By = By.xpath("//label[contains(text(), 'Password')]//..//input")
    private val submitButton: By = By.xpath("//button[contains(text(),'Submit')]")

    var byEmailButtonElem: WebElement = driver.findElement(byEmailButton)
    var loginInputElem: WebElement
    var passwordInputElem: WebElement
    var submitButtonElem: WebElement

    init {
        WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(byEmailButtonElem))?.click()
        loginInputElem = driver.findElement(loginInput)
        passwordInputElem = driver.findElement(passwordInput)
        submitButtonElem = driver.findElement(submitButton)
    }

    fun logIn(login: String, password: String) {
        loginInputElem.click()
        loginInputElem.sendKeys(login)
        passwordInputElem.click()
        passwordInputElem.sendKeys(password)
        WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(submitButtonElem))?.click()
        WebDriverWait(driver, 3).until(ExpectedConditions.numberOfElementsToBe(submitButton, 0))
    }
}