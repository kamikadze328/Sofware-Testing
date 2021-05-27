package pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class CategoryPage (private val driver: WebDriver) : Page(driver, "//h1[contains(text(),'Science')]") {
    private val firstQuestion: By = By.xpath("//div/div[2]/div/div[2]/div[3]/div[2]//h1//a")
    private val unansweredQuestionsButton: By = By.xpath("//a[contains(text(), 'unanswered')]")


    fun toUnansweredCategory(){
        driver.findElement(unansweredQuestionsButton).click()
    }

    fun toFirstQuestion(){
        driver.findElement(firstQuestion).click()
    }
}