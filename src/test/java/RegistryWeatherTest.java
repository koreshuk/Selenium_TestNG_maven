import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.fors.ods3.msnow.domain.FdcWeatherEntity;
import sun.awt.windows.WBufferStrategy;

public class RegistryWeatherTest extends WebDriverSettings {

    private static final String URL_WEATHER = "http://ods2.fors.ru:9090/dev/registry?2&code=registryForm&bk=ODH/MSNOW/Weather";

    @Test
    public void checkTitleRegistryWeather() {
        driver.get(URL_WEATHER);
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        String titleWeather = driver.getTitle();

        Assert.assertTrue(titleWeather.equals("Система контроля и планирования работ в области дорожной инфраструктуры"));
    }

    @Test(dependsOnMethods = "checkTitleRegistryWeather")
    public void checkTableTitle() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement tableTitle = driver.findElement(By.xpath("//div[@class='tab-hdr clearfix']/h1"));

        if (tableTitle.getText().equals("Погодные явления")) {
            System.out.println("Реестр Погоды открыт");
        } else {
            Assert.fail("Реестр Погоды открыт НЕ открыт");
        }
    }

    @Test(dependsOnMethods = "checkTitleRegistryWeather")
    public void checkConnectionDB() {
        FdcWeatherEntity entity = getSession().get(FdcWeatherEntity.class, 36714118L);

        if (entity.getId() != null) {
            System.out.println("Сущность подтянулась");
        }
    }

    @Test(dependsOnMethods = "checkTableTitle")// кнопка тайтла Создать
    public void checkButtonCreate() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();

        WebElement checkButtonCreate = driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"));

        if (checkButtonCreate.getText().equals("ДОБАВИТЬ")) {
            System.out.println("Кнопка Добавить присутствует. Надпись соответствует");
        } else {
            Assert.fail("Кнопка Добавить присутствует. Надпись НЕ соответствует");
        }
    }

    @Test (dependsOnMethods = "checkButtonCreate")// нажатие на кнопку и открытие карточки Погоды
    public void checkButtonCreateClick() {

        WebElement checkButtonCreate = driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"));
        checkButtonCreate.click();
        getWhenVisible(By.xpath("//div[@class='tab-hdr']"),20).isDisplayed();
        WebElement titleObjectWeather = driver.findElement(By.xpath("//div[@class='tab-hdr']/h1"));
        if (titleObjectWeather.getText().equals("Данные погодных явлений")) {
            System.out.println("Карточка на создание открыта");
        } else {
            Assert.fail("Карточка не открылась");
        }
    }

    @Test (dependsOnMethods = "checkButtonCreateClick")//Проверка наличия кнопки Создать и её тайтла на карточке
    public void checkButtonObjectCreate() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement buttonObjectCreate = driver.findElement(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"));
        if (buttonObjectCreate.getText().equals("СОЗДАТЬ")) {
            System.out.println("Кнопка Создать присутствует. Надпись соответствует");
        } else {
            Assert.fail("Кнопка Создать присутствует. Надпись НЕ соответствует");
        }
    }

    @Test (dependsOnMethods = "checkButtonObjectCreate")//Проверка доступности кнопки Создать на карточке
    public void enableObjectCreate() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement enableBtnObjectCreate = driver.findElement(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"));
        if (enableBtnObjectCreate.isEnabled()) {
            System.out.println("Кнопка Создать активна");
        } else {
            Assert.fail("Кнопка Создать залочена");
        }
    }

    @Test (dependsOnMethods = "enableObjectCreate") //Проверка наличия кнопки Закрыть и её тайтла
    public void buttonObjectClose() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement buttonObjectClose = driver.findElement(By.xpath("//div[@class='btn-group']/button[2]"));
        if (buttonObjectClose.getText().equals("ЗАКРЫТЬ")) {
            System.out.println("Кнопка Закрыть присутствует. Надпись соответствует");
        } else {
            Assert.fail("Кнопка Закрыть присутствует. Надпись НЕ соответствует");
        }
    }

    @Test (dependsOnMethods = "buttonObjectClose")//Проверка доступности кнопки Закрыть
    public void enableButtonObjectClose() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"),20).isDisplayed();
        WebElement enableButtonObjectClose = driver.findElement(By.xpath("//div[@class='btn-group']/button[2]"));
        if (enableButtonObjectClose.isEnabled()) {
            System.out.println("Кнопка Закрыть активна");
        } else {
            Assert.fail("Кнопка Закрыть залочена");
        }
    }

    @Test (dependsOnMethods = "enableButtonObjectClose") //Проверка наличия обязательных сообщений Район и Дата начала
    public void checkMandatoryWarnings(){

        WebElement buttonObjectCreate = driver.findElement(By.xpath("//*[@name='innerPanel:buttonsPanel:saveBtn']"));
        buttonObjectCreate.click();
        getWhenVisible(By.xpath("//div[@class='alert alert-danger alert-custom alert-relative']"),20).isDisplayed();
        WebElement checkRegionWaringMessage = driver.findElement(By.xpath("//ul[@class='feedbackPanel']/li[position()=1]/span"));
        if (checkRegionWaringMessage.getText().equals("Поле 'Район' обязательно для заполнения")){
            System.out.println("Верно - Поле 'Район' обязательно для заполнения");
        } else {
            Assert.fail("НЕ верно - Поле 'Район' обязательно для заполнения");
        }

        WebElement checkDateWarningMessage = driver.findElement(By.xpath("//ul[@class='feedbackPanel']/li[position()=2]/span"));
        if (checkDateWarningMessage.getText().equals("Поле 'Дата начала снегопада' обязательно для заполнения")) {
            System.out.println("Верно - Поле 'Дата начала снегопада' обязательно для заполнения");
        } else {
            Assert.fail("НЕ верно - Поле 'Дата начала снегопада' обязательно для заполнения");
        }
    }

    @Test (dependsOnMethods = "checkMandatoryWarnings") //Проверка закрытия блока обязательных сообщений
    public void checkCloseAlertPanel() {
        getWhenVisible(By.xpath("//div[@class='alert alert-danger alert-custom alert-relative']"),20).isDisplayed();
        WebElement alertPanel =  driver.findElement(By.xpath("//div[@class='alert alert-danger alert-custom alert-relative']/a[@title='Закрыть']"));
        alertPanel.click();
        System.out.println("Блок обязательных сообщений закрыт");
    }

    @Test (dependsOnMethods = "checkCloseAlertPanel") //проверка когда дата начала > даты окончания
    public void checkFieldSnowDate(){

        driver.findElement(By.xpath("//*[@name='innerPanel:snowDateFrom']")).sendKeys("22.02.2018 23:16");
        driver.findElement(By.xpath("//*[@name='innerPanel:snowDateTo']")).sendKeys("22.02.2018 23:10");

        WebElement clickCreateBtn = driver.findElement(By.xpath("//*[@name='innerPanel:buttonsPanel:saveBtn']")); //нажатие кропки Создать
        clickCreateBtn.click();
        getWhenVisible(By.xpath("//ul[@class='feedbackPanel']/li[position()=2]/span"),20).isDisplayed();
        WebElement alertSnowDatePanel=driver.findElement(By.xpath("//ul[@class='feedbackPanel']/li[position()=2]/span"));

            if (alertSnowDatePanel.getText().equals("'Дата окончания снегопада' не может быть раньше 'Дата начала снегопада'")) {
                System.out.println("Верно - 'Дата окончания снегопада' не может быть раньше 'Дата начала снегопада'");
            } else {
                Assert.fail("Не верно - 'Дата окончания снегопада' не может быть раньше 'Дата начала снегопада'");
            }
        driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).click(); // закрытие карточки
    }

   @Test (dependsOnMethods = "checkFieldSnowDate") // проверка вводе невалидных символов в дату
    public void checkFieldSnowDateNotValid() {
        getWhenVisible(By.xpath("//button[text()='Добавить']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[text()='Добавить']")).click(); // открытие на создание карточки
        getWhenVisible(By.xpath("//input[@name='innerPanel:snowDateFrom']"),20).isDisplayed();
        driver.findElement(By.xpath("//input[@name='innerPanel:snowDateFrom']")).sendKeys("ДД.ММ.20ГГ 23:16");
        driver.findElement(By.xpath("//*[@name='innerPanel:snowDateTo']")).sendKeys("ДД.ММ.8ГГГ 23:16");
        driver.findElement(By.xpath("//*[@name='innerPanel:buttonsPanel:saveBtn']")).click();
        getWhenVisible(By.xpath("//ul[@class='feedbackPanel']"),20).isDisplayed();


         WebElement alertSnowDateFrom = driver.findElement(By.xpath("//ul[@class='feedbackPanel']/li[position()=1]/span"));
            if (alertSnowDateFrom.getText().equals("'Дата начала снегопада' должно соответствовать формату ДД.MM.ГГГГ ЧЧ:ММ.")) {
                System.out.println("Верно - 'Дата начала снегопада' должно соответствовать формату ДД.MM.ГГГГ ЧЧ:ММ.");
            } else {
                Assert.fail("НЕ верно - 'Дата начала снегопада' должно соответствовать формату ДД.MM.ГГГГ ЧЧ:ММ.");
            }

        WebElement alertSnowDateTo = driver.findElement(By.xpath("//ul[@class='feedbackPanel']/li[position()=2]/span"));
            if (alertSnowDateTo.getText().equals("'Дата окончания снегопада' должно соответствовать формату ДД.MM.ГГГГ ЧЧ:ММ.")) {
                System.out.println("Верно - 'Дата окончания снегопада' должно соответствовать формату ДД.MM.ГГГГ ЧЧ:ММ.");
            } else {
                Assert.fail("НЕ верно- 'Дата окончания снегопада' должно соответствовать формату ДД.MM.ГГГГ ЧЧ:ММ.");
            }
    }
}
