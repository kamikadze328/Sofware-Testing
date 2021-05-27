package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions


class SignUpPage(private val driver: WebDriver) : MainPage(driver, "//h2[contains(text(),'Sign up with email')]") {
    private val loginInput: By = By.xpath("//label[contains(text(), 'Email Address')]//..//input")
    private val passwordInput: By = By.xpath("//label[contains(text(), 'Create Password')]//..//input")
    private val submitButton: By = By.xpath("//button[contains(text(),'Submit')]")
    private val rulesAgreement: By = By.xpath("//p[contains(text(), 'I have read')]/ancestor::label[last()]//input")
    private val mailAgreement: By =
        By.xpath("//div[contains(text(), 'I agree to be contacted by email')]/ancestor::label[last()]//input")
    private val alreadyInUseText: By = By.xpath("//div[contains(text(), 'This email is already in use. Please ')]")


    var loginInputElem: WebElement = driver.findElement(loginInput)
    var passwordInputElem: WebElement = driver.findElement(passwordInput)
    var submitButtonElem: WebElement = driver.findElement(submitButton)
    var rulesAgreementElem: WebElement = driver.findElement(rulesAgreement)
    var mailAgreementElem: WebElement = driver.findElement(mailAgreement)

    fun signUp(login: String, password: String) {
        loginInputElem.click()
        loginInputElem.sendKeys(login)
        passwordInputElem.click()
        passwordInputElem.sendKeys(password)
        rulesAgreementElem.click()
        mailAgreementElem.click()

        val button = ReCaptcha(driver).pass(ExpectedConditions.elementToBeClickable(submitButtonElem))
        Thread.sleep(1000)
        button?.click()
    }

    fun isAlreadySignUp(): Boolean {
        try {
            driver.findElement(alreadyInUseText)
        } catch (kek: NoSuchElementException) {
            return false
        }
        return true
    }

}