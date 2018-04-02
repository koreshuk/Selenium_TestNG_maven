import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.fors.ods3.msnow.domain.FdcWeatherEntity;

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

    /// TODO-- все что ниже не смотрел, но нужно переделать (в чатности наименование и константы) как сделано выще.


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
        WebElement chekRegionWaringMessage = driver.findElement(By.xpath("//ul[@class='feedbackPanel']/li[position()=1]/span"));
        if (chekRegionWaringMessage.getText().equals("Поле 'Район' обязательно для заполнения")){
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

}
