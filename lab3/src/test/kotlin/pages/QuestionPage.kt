package pages

import org.openqa.selenium.*
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait


class QuestionPage(val driver: WebDriver) : Page(driver, "//div[1]/div[1]/span[contains(text(),'Top Answer')]") {
    private val answerInput: By = By.xpath("//h2[contains(text(), 'Your Answer')]//..//div[contains(@role, 'textbox')]")
    private val submitAnswer: By = By.xpath("//span[contains(text(), 'Post Your Answer')]/ancestor::button[last()]")
    private val confirmDeleteAnswer: By = By.xpath("//button[contains(text(), 'Delete')]")
    private val confirmation: By = By.xpath("//div/div[5]//span[contains(text(), '+20 points have been added')]")

    private fun myAnswerToggleDeleteButtonElem(login: String): WebElement? {
        return driver.findElement(myAnswerToggleDeleteButton(login))
    }
    private fun myAnswerToggleDeleteButton(login: String): By? {
        return By.xpath("//h6//a[contains(@href, '$login')]//..//..//..//..//..//div//button//..//..//div")
    }

    private fun myAnswerDeleteButton(login: String): WebElement? {
        return driver.findElement(By.xpath("//h6//a[contains(@href, '$login')]//..//..//..//..//..//a[contains(text(), 'Delete')]"))
    }

    private fun threeLevelOfRatings(login: String): MutableList<WebElement>? {
        return driver.findElements(By.xpath("//h6//a[contains(@href, '$login')]//..//..//..//..//..//../div/div/div/div/div/div/span"))
    }
    private fun threeLevelOfRatingsBefore(login: String): WebElement? {
        return threeLevelOfRatings(login)?.get(2)
    }
    /*private fun threeLevelOfRatingsMiddleUp(login: String): WebElement? {
        return threeLevelOfRatings(login)?.get(1)
    }*/
    private fun threeLevelOfRatingsAfterUp(login: String): WebElement? {
        return threeLevelOfRatings(login)?.get(1)
    }

    private fun myRatingChangeButtons(login: String): MutableList<WebElement>? {
        return driver.findElements(By.xpath("//h6//a[contains(@href, '$login')]//..//..//..//..//..//../div/div/div/div/button"))
    }
    private fun myRatingUpButtons(login: String): WebElement? {
        return myRatingChangeButtons(login)?.get(1)
    }

    fun answer(answer: String) {
        driver.findElement(By.xpath("//body")).sendKeys(Keys.END)
        (driver as JavascriptExecutor).executeScript(
            "arguments[0].scrollIntoView(true);",
            driver.findElement(answerInput)
        )
        //TODO wait while element is on the screen
        Thread.sleep(1000)
        driver.findElement(By.xpath("//body")).sendKeys(Keys.UP, Keys.UP, Keys.UP, Keys.UP, Keys.UP)
        Thread.sleep(1000)
        WebDriverWait(driver, 3).until(ExpectedConditions.elementToBeClickable(answerInput)).click()
        driver.findElement(answerInput).sendKeys(answer)
        driver.findElement(submitAnswer).click()
    }

    fun delete(login: String) {
        val wait = WebDriverWait(driver, 1)
        (driver as JavascriptExecutor).executeScript(
            "arguments[0].scrollIntoView(true);",
            myAnswerToggleDeleteButtonElem(login)
        )

        Thread.sleep(1000)
        driver.findElement(By.xpath("//body")).sendKeys(Keys.UP, Keys.UP)
        //wait.until(ExpectedConditions.visibilityOfElementLocated(myAnswerToggleDeleteButton(login))).click()

        Thread.sleep(1000)
        wait.until(ExpectedConditions.elementToBeClickable(myAnswerToggleDeleteButtonElem(login))).click()
        wait.until(ExpectedConditions.elementToBeClickable(myAnswerDeleteButton(login))).click()
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(confirmDeleteAnswer))).click()

    }

    fun checkAnswering(): Boolean {
        return driver.findElements(confirmation).isNotEmpty()
    }

    fun checkDeleting(login: String): Boolean {
        try {
            myAnswerToggleDeleteButtonElem(login)
        } catch (e: NoSuchElementException) {
            return true
        }
        return false
    }

    fun rateUpMyAnswer(login: String){
        Thread.sleep(1000)
        myRatingUpButtons(login)?.click()
        Thread.sleep(1000)
    }

    fun getRateOfMyAnswerBefore(login: String): String? {
        driver.findElement(By.xpath("//body")).sendKeys(Keys.UP, Keys.UP)
        Thread.sleep(1000)
        /*val kek = threeLevelOfRatingsBefore(login)
        val kekText = kek?.text
        println(kekText)*/
        return threeLevelOfRatingsBefore(login)?.text
    }
    fun getRateOfMyAnswerAfterUp(login: String): String? {
        println(threeLevelOfRatingsAfterUp(login)?.text)
        return threeLevelOfRatingsAfterUp(login)?.text
    }
    /*fun getRateOfMyAnswerMiddleUp(login: String): String? {
        println(threeLevelOfRatingsMiddleUp(login)?.text)
        return threeLevelOfRatingsMiddleUp(login)?.text
    }*/

}
