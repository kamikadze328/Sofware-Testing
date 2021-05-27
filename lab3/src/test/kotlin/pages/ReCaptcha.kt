package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

class ReCaptcha(private val driver: WebDriver) {
    private val reCaptchaFrame: By = By.xpath("//iframe[contains(@title, 'reCAPTCHA')]")
    private val reCaptchaInput: By = By.xpath("//div[@class='recaptcha-checkbox-border']")

    fun pass(expectedCondition: ExpectedCondition<WebElement>): WebElement? {
        WebDriverWait(driver, 3).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(driver.findElement(reCaptchaFrame)))
        WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(reCaptchaInput)).click()
        driver.switchTo().defaultContent()

        return WebDriverWait(driver, 100).until(expectedCondition)

    }

}