
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverSettings {

    private static final String APPLICATION_LOGIN_URL = "http://ods2.fors.ru:9090/dev/auth";

    protected ChromeDriver driver;
    private SessionFactory sessionFactory;
    protected Session session;

    @BeforeTest
    public void setUp(){

        System.setProperty("webdriver.chrome.driver", "c:\\Program Files\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.get(APPLICATION_LOGIN_URL);

        System.out.println("Соединение с БД");
        sessionFactory = HibernateSessionFactory.getSessionFactory();

        //Воод юзернейма и логина
        WebElement loginField = driver.findElement(By.id("username"));
        loginField.sendKeys("admin");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("");
        WebElement loginButton = driver.findElement(By.xpath("//button[@class='btn btn-lg btn-login btn-block']"));

        loginButton.click();

        System.out.println("Приготовления к тесту");
    }

    /**
     * Функция для ожидания Элемента на странице
     */
    public WebElement getWhenVisible(By locator, int timeout) {

        WebDriverWait wait = new WebDriverWait(driver, timeout);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }

    /**
     * Функция выдирания ID из карточки объекта
     */
    public  Long idFieldKart() {

        getWhenVisible(By.xpath("//div[@class='tab-hdr']"),20).isDisplayed();
        WebElement idObjectTitle = driver.findElement(By.xpath("//div[@class='tab-hdr']/h1"));
        String digitsIdWeather = idObjectTitle.getText().replaceAll("[^0-9.]", "");
        Long idObject = new Long(digitsIdWeather); //из String в Long
        return idObject;
    }

    /**
     * Функция выдирания количества отображаемых записей таблицы из тайтла в левом нижнем углу "Показаны с 1 по 10 из #"
     */
    public int titleCountObjectTable(){
        int titleCountObjTable;
        driver.navigate().refresh();
        retryingFindClick(By.xpath("//div[@class='navigatorLabel']/div"));
        WebElement linkRecordCountFirstPage = driver.findElement(By.xpath("//div[@class='navigatorLabel']/div"));
        String checkFirstPartRecordCount= linkRecordCountFirstPage.getText().replace("Показаны с 1 по ", "");
        String checkSecondPartRecordCount =checkFirstPartRecordCount.replaceAll(" из \\d+","");
        titleCountObjTable = new Integer(checkSecondPartRecordCount);

        return titleCountObjTable;
    }

    /**
    * Функция подсчёта отображаемых строк таблицы
    */
    public int numberListCountTable() {
        driver.navigate().refresh();
        List<WebElement> list = driver.findElements(By.xpath("//tbody/tr[@keynavigator-watched='true']"));
        //WebElement listCountTableRecods = driver.findElement(By.xpath("//option[@selected='selected']"));
       // Integer listCounTableObjLines = new Integer(listCountTableRecods.getText());
        list.size();
        return list.size();
    }

    /**
     * Функция подсчёта результата поиска количества записей, отображаемой в таблице(внизу в тайтле слева)
     */
    public int numberCountTableObjectTotal() {
        getWhenVisible(By.xpath("//div[@class='navigatorLabel']/div"),20);
        WebElement linkRecordCountTotal = driver.findElement(By.xpath("//div[@class='navigatorLabel']/div"));
        String checkNumberObjectTCount= linkRecordCountTotal.getText().replaceAll("Показаны с \\d+ по \\d+ из ", "");
        Integer checkNumberObjectTableCount = new Integer(checkNumberObjectTCount);

        return checkNumberObjectTableCount;


    }

    public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch (StaleElementReferenceException ignored) {
            }
            attempts++;
        }
        return result;
    }

    public Session getSession() {
        if (session != null) {
            return session;
        }
        session = sessionFactory.openSession();

        return session;
    }

    @AfterTest public void close() {
        sessionFactory.close();
        session.close();
        driver.quit();
    }
}
