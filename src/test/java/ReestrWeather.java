import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class ReestrWeather extends WebDriverSettings {

    @Test //проверка тайтла таблицы страницы Реестра Погоды
    public void proverkaTitleWeather() {
        driver.get("http://ods2.fors.ru:9090/dev/registry?2&code=registryForm&bk=ODH/MSNOW/Weather");
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        String titleWeather = driver.getTitle();

        Assert.assertTrue(titleWeather.equals("Система контроля и планирования работ в области дорожной инфраструктуры"));
    }

    @Test    //проверка наименования таблицы страницы Реестра Погоды
            (dependsOnMethods = "proverkaTitleWeather")
    public void proverkaNaimTableWeather(){
        WebElement naimKarto4kaPogodi1 = driver.findElement(By.xpath("//div[@class='tab-hdr clearfix']/h1"));

        if (naimKarto4kaPogodi1.getText().equals("Погодные явления")){
            System.out.println("Реестр Погоды открыт");
        } else {
            Assert.fail("Реестр Погоды открыт НЕ открыт");
        }
    }

    @Test // кнопка Создать
    public void buttonCreate() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();

        WebElement buttonNewCreate = driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"));

        if (buttonNewCreate.getText().equals("ДОБАВИТЬ")) {
            System.out.println("Кнопка присутствует. Надпись соответствует");
        } else {
            System.out.println("Кнопка присутствует. Надпись НЕ соответствует");
        }
    }
}
