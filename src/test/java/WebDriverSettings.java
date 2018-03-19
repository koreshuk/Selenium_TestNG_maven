
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import java.util.concurrent.TimeUnit;

public class WebDriverSettings {

    public ChromeDriver driver;
    public Select select;

    @BeforeTest
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "c:\\Program Files\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.get("http://ods2.fors.ru:9090/dev/auth?0");

        //Воод юзернейма и логина
        WebElement loginField = driver.findElement(By.id("username"));
        loginField.sendKeys("admin");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("admin");
        WebElement loginButton = driver.findElement(By.xpath("//button[@class='btn btn-lg btn-login btn-block']"));

        loginButton.click();

        System.out.println("Приготовления к тесту");
    }


//Функция для ожидания Элемента на странице
    public WebElement getWhenVisible(By locator, int timeout) {
        WebElement element = null;
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return element;
    }



    public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

    /*   public Select getSelect(WebElement element) {
           select = new Select(element);
           return select;
       }*/
    @AfterTest public void close() {

        driver.quit();
    }




}