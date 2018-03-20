import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;


public class ReestrWeather extends WebDriverSettings {

    @Test //проверка тайтла таблицы страницы Реестра Погоды
    public void proverkaTitleWeather() {
        driver.get("http://ods2.fors.ru:9090/dev/registry?2&code=registryForm&bk=ODH/MSNOW/Weather");
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        String titleWeather = driver.getTitle();

        Assert.assertTrue(titleWeather.equals("Система контроля и планирования работ в области дорожной инфраструктуры"));
    }

    @Test   (dependsOnMethods = "proverkaTitleWeather") //проверка наименования таблицы страницы Реестра Погоды
    public void proverkaNaimTableWeather(){
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement naimKarto4kaPogodi1 = driver.findElement(By.xpath("//div[@class='tab-hdr clearfix']/h1"));

        if (naimKarto4kaPogodi1.getText().equals("Погодные явления")){
            System.out.println("Реестр Погоды открыт");
        } else {
            Assert.fail("Реестр Погоды открыт НЕ открыт");
        }
    }

    @Test (dependsOnMethods = "proverkaNaimTableWeather")// кнопка тайтла Создать
    public void buttonCreate() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();

        WebElement buttonNewCreate = driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"));

            if (buttonNewCreate.getText().equals("ДОБАВИТЬ")) {
                System.out.println("Кнопка Добавить присутствует. Надпись соответствует");
            } else {
                Assert.fail("Кнопка Добавить присутствует. Надпись НЕ соответствует");
            }
    }

    @Test (dependsOnMethods = "buttonCreate")// нажатие на кнопку и открытие карточки Погоды
    public void buttonCreateClick() {

        WebElement buttonCreate = driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"));
        buttonCreate.click();
        getWhenVisible(By.xpath("//div[@class='tab-hdr']"),20).isDisplayed();
        WebElement titleObjectweather = driver.findElement(By.xpath("//div[@class='tab-hdr']/h1"));
            if (titleObjectweather.getText().equals("Данные погодных явлений")){
                System.out.println("Карточка на создание открыта");
            } else {
                Assert.fail("Карточка не открылась");
            }
    }

    @Test (dependsOnMethods = "buttonCreateClick")//Проверка наличия кнопки Создать и её тайтла
    public void buttonObjectCreate() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement buttonObjectCreate = driver.findElement(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"));
            if (buttonObjectCreate.getText().equals("СОЗДАТЬ")) {
                System.out.println("Кнопка Создать присутствует. Надпись соответствует");
            }else {
                Assert.fail("Кнопка Создать присутствует. Надпись НЕ соответствует");
            }
    }

    @Test (dependsOnMethods = "buttonCreateClick")//Проверка доступности кнопки Создать
    public void enableObjectCreate() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement enableBtnObjectCreate = driver.findElement(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"));
            if (enableBtnObjectCreate.isEnabled()) {
                System.out.println("Кнопка Создать активна");
            } else {
                Assert.fail("Кнопка Создать залочена");
            }
    }

    @Test (dependsOnMethods = "buttonCreateClick") //Проверка наличия кнопки Закрыть и её тайтла
    public void buttonObjectClose() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement buttonObjectClose = driver.findElement(By.xpath("//div[@class='btn-group']/button[2]"));
            if (buttonObjectClose.getText().equals("ЗАКРЫТЬ")) {
                System.out.println("Кнопка Закрыть присутствует. Надпись соответствует");
            }else {
                Assert.fail("Кнопка Закрыть присутствует. Надпись НЕ соответствует");
            }
    }

    @Test (dependsOnMethods = "buttonCreateClick")//Проверка доступности кнопки Закрыть
    public void enableObjectClose() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement enableBtnObjectClose = driver.findElement(By.xpath("//div[@class='btn-group']/button[2]"));
        if (enableBtnObjectClose.isEnabled()) {
            System.out.println("Кнопка Закрыть активна");
        } else {
            Assert.fail("Кнопка Закрыть залочена");
        }
    }
}
