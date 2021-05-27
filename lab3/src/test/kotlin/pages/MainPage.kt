package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

open class MainPage(
    private val driver: WebDriver,
    titleXPAth: String = "//h1[contains(text(),'What is your question?')]"
) :
    Page(driver, titleXPAth) {
    private val questionInput: By = By.xpath("//input[contains(@placeholder,'Type your question...')]")
    private val questionInputSubmit: By = By.xpath("//section[1]/div[2]/button")
    private val logInButton: By = By.xpath("//a[contains(text(),'Log in')]")
    private val signUpButton: By = By.xpath("//a[contains(text(),'Sign up')]")
    private val logOutButton: By = By.xpath("//span[contains(text(),'Logout')]/ancestor::button[last()]")
    private val profileButton: By = By.xpath("//span[contains(text(),'Profile')]/ancestor::button[last()]")
    private val categoryButton: By = By.xpath("//span[contains(text(),'Science')]/ancestor::a[last()]")

    private val AskQuestionButton: By = By.xpath("//button[contains(text(),'Ask Question')]")
    private val toggleProfileDropdownButton: By =
        By.xpath("//button[contains(text(),'Ask Question')]//..//..//..//span//button")
    private val LogInThtoughtProfileButton: By =
        By.xpath("//button[contains(text(),'Ask Question')]//..//..//..//span//button[contains(text(),'Log in')]")
    var questionInputElem: WebElement = driver.findElement(questionInput)
    var questionInputSubmitElem: WebElement = driver.findElement(questionInputSubmit)

    fun search(text: String) {
        questionInputElem.click()
        questionInputElem.sendKeys(text)
        questionInputSubmitElem.click()
    }

    fun toSignUp() {
        driver.findElement(signUpButton).click()
    }

    fun toLogIn() {
        driver.findElement(logInButton).click()
    }

    fun toAskPage() {
        driver.findElement(AskQuestionButton).click()
    }

    fun toFirstCategory(){
        driver.findElement(categoryButton).click()
    }

    fun toProfile() {
        driver.findElement(toggleProfileDropdownButton).click()
        WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(profileButton))?.click()
    }

    fun isAlreadyLodIn(): Boolean {
        return driver.findElements(logInButton).isEmpty()
    }
    /*fun isNotAlreadyLodIn(): Boolean {
        return driver.findElements(LogInThtoughtProfileButton).isNotEmpty()
    }*/

    fun logOut() {
        driver.findElement(toggleProfileDropdownButton).click()
        WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(logOutButton))?.click()

    }

}