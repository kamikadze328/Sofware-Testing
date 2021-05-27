import config.MyWebDriver
import org.junit.jupiter.api.*
import org.openqa.selenium.WebDriver
import pages.*
import java.util.concurrent.TimeUnit


open class SeleniumTest {

    private val email = "bananbanan@gmail.com"
    private val login = "bananbanan"
    private val password = "TestPassword"
    //private val password = "TestPassword"

    companion object {
        lateinit var driver: WebDriver

        @JvmStatic
        @BeforeAll
        fun setup() {
            driver = MyWebDriver().driver
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        }

        @JvmStatic
        @AfterAll
        fun close() {
            driver.quit()
        }
    }

    @BeforeEach
    fun openPage() {
        driver.get("https://www.answers.com/")
        driver.manage().window().maximize()
        //val mainPage = MainPage(driver)
        //if (mainPage.isAlreadyLodIn()) mainPage.logOut()
    }
    @AfterEach
    fun logOut() {
        openPage()
        val mainPage = MainPage(driver)
        if(mainPage.isAlreadyLodIn()) mainPage.logOut()
    }

    @Test
    fun `open question`() {
        val searchStr = "kek"

        val mainPage = MainPage(driver)
        Assertions.assertTrue(mainPage.isPageOpened())
        mainPage.search(searchStr)

        val searchResultPage = SearchResultPage(driver)
        Assertions.assertTrue(searchResultPage.isPageOpened())
        searchResultPage.openFirstResult()

        QuestionPage(driver).isPageOpened()
    }

    @Test
    fun `answer and delete the answer`() {
        val answerStr = "kekkeke"
        `log in`()
        `open question`()
        val questionPage = QuestionPage(driver)
        questionPage.answer(answerStr)
        var counter = 0
        while(!questionPage.checkDeleting(login) && (counter++ < 5)) questionPage.delete(login)
        Assertions.assertTrue(questionPage.checkDeleting(login))
    }

    @Test
    fun answer() {
        val answerStr = "kek"
        `log in`()
        `open question`()
        val questionPage = QuestionPage(driver)
        Assertions.assertTrue(questionPage.isPageOpened())
        questionPage.answer(answerStr)
        Assertions.assertTrue(questionPage.checkAnswering())

    }

    @Test
    fun `answer and rate the answer`() {
        answer()
        val questionPage = QuestionPage(driver)
        Assertions.assertTrue(questionPage.isPageOpened())
        val prevRate = questionPage.getRateOfMyAnswerBefore(login)
        questionPage.rateUpMyAnswer(login)
        /*val currRate = questionPage.getRateOfMyAnswerMiddleUp(login)
        println(currRate)*/
        val currRate1 = questionPage.getRateOfMyAnswerAfterUp(login)
        println(currRate1)
        //println(questionPage.getRateOfMyAnswerAfterUp(login)?.toInt())
        Assertions.assertEquals(prevRate?.toInt()?.minus(1), questionPage.getRateOfMyAnswerAfterUp(login)?.toInt())
    }

    @Test
    fun `ask a question`() {
        val questionStr = "А как какать?"

        val mainPage = MainPage(driver)
        Assertions.assertTrue(mainPage.isPageOpened())
        `log in`()
        mainPage.toAskPage()

        val askPage = AskPage(driver)
        Assertions.assertTrue(askPage.isPageOpened())
        askPage.ask(questionStr)
        Assertions.assertTrue(askPage.checkAsking())
    }

    @Test
    fun `sign up`() {
        val mainPage = MainPage(driver)
        Assertions.assertTrue(mainPage.isPageOpened())
        if(mainPage.isAlreadyLodIn()) mainPage.logOut()
        mainPage.toSignUp()

        val signUpPage = SignUpPage(driver)
        Assertions.assertTrue(signUpPage.isPageOpened())
        signUpPage.signUp(email, password)

        Assertions.assertTrue(signUpPage.isAlreadySignUp())
    }

    @Test
    fun `log in`() {
        val mainPage = MainPage(driver)
        Assertions.assertTrue(mainPage.isPageOpened())
        mainPage.toLogIn()

        val logInPage = LogInPage(driver)
        Assertions.assertTrue(logInPage.isPageOpened())
        logInPage.logIn(email, password)

        Assertions.assertTrue(mainPage.isPageOpened())
        Assertions.assertTrue(mainPage.isAlreadyLodIn())
    }

    @Test
    fun `log out`() {
        val mainPage = MainPage(driver)
        Assertions.assertTrue(mainPage.isPageOpened())
        `log in`()
        mainPage.logOut()
        Assertions.assertFalse(mainPage.isAlreadyLodIn())
    }

    @Test
    fun `open category`(){
        val mainPage = MainPage(driver)
        Assertions.assertTrue(mainPage.isPageOpened())
        mainPage.toFirstCategory()

        val categoryPage = CategoryPage(driver)
        Assertions.assertTrue(categoryPage.isPageOpened())
    }

    @Test
    fun `open a question from category`(){
        `open category`()
        val categoryPage = CategoryPage(driver)
        Assertions.assertTrue(categoryPage.isPageOpened())
        categoryPage.toFirstQuestion()
        QuestionPage(driver).isPageOpened()
    }

    @Test
    fun `open a question from unanswered category`(){
        `open category`()
        val categoryPage = CategoryPage(driver)
        Assertions.assertTrue(categoryPage.isPageOpened())
        categoryPage.toUnansweredCategory()
        categoryPage.toFirstQuestion()
        UnansweredQuestion(driver).isPageOpened()
    }
}