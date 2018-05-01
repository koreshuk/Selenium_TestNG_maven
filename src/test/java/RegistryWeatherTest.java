import com.sun.xml.internal.bind.v2.TODO;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.NoInjection;
import org.testng.annotations.Test;
import ru.fors.ods3.base.domain.FdcAsAddrobjEntity;
import ru.fors.ods3.msnow.domain.FdcWeatherEntity;
import sun.awt.windows.WBufferStrategy;

import javax.annotation.concurrent.NotThreadSafe;
import javax.swing.text.html.CSS;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

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

   @Test (dependsOnMethods = "checkFieldSnowDate") // проверка вводa невалидных символов в дату
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
       driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).click(); // закрытие карточки
    }

    @Test (dependsOnMethods = "checkFieldSnowDate") //Проверка ввода невалидных данных в поле Кол-во осадков, мм
    public void checkNotValidRainFall() {
        getWhenVisible(By.xpath("//button[text()='Добавить']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[text()='Добавить']")).click(); // открытие на создание карточки
        getWhenVisible(By.xpath("//input[@name='innerPanel:rainfall']"),20).isDisplayed();

        WebElement fieldRainFall = driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']"));
        fieldRainFall.sendKeys("qwe%$@");

            if (fieldRainFall.getText().equals("")){
                System.out.println("Верно -  невозможен ввод букв и спецсимволов в поле Кол-во осадков");
            } else {
                Assert.fail("Не верно -  возможен ввод букв и спецсимволов в поле Кол-во осадков");
            }

    }

    @Test (dependsOnMethods = "checkNotValidRainFall") //Проверка ввода валидных данных и сохранения карточки
    public void checkObjectCreation() {

    WebElement popupListRegion = driver.findElement(By.xpath("//*[@class='select2 select2-container select2-container--default']")); // выбор менюшки
    popupListRegion.click();//клик на меню для ввода поиска
    driver.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("Щелковск");//ввод первых символов поиска
    getWhenVisible(By.xpath("//ul[@class='select2-results__options']/li[@class='select2-results__option select2-results__option--highlighted']"),20).isDisplayed();
    WebElement selectPopupListRegion = driver.findElement(By.xpath("//ul[@class='select2-results__options']/li[@class='select2-results__option select2-results__option--highlighted']"));
    selectPopupListRegion.click();

    driver.findElement(By.xpath("//*[@name='innerPanel:snowDateFrom']")).sendKeys("07.02.2020 23:38");
    driver.findElement(By.xpath("//*[@name='innerPanel:snowDateTo']")).sendKeys("08.02.2020 23:39");
    driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']")).sendKeys("150");
    driver.findElement(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']")).click();

    getWhenVisible(By.xpath("//*[@class='tab-hdr clearfix']/h1"),20).isDisplayed();
    WebElement titleWeatherTable = driver.findElement(By.xpath("//*[@class='tab-hdr clearfix']/h1"));
        if (titleWeatherTable.getText().equals("Погодные явления")){
            System.out.println("Верно - Сохранение карточки успешно");
        } else {
            Assert.fail("Не верно - Сохранение карточки не успешно");
        }


    }

    @Test (dependsOnMethods = "checkObjectCreation") //вызов подсвеченной панели и проверка наличия кнопки Просмотр
    public void settingsObjectPanelView(){
        driver.findElement(By.xpath("//td[@class=' txt-right ']")).click();
        getWhenVisible(By.xpath("//button[@title='Просмотр']"),20).isDisplayed();
        WebElement viewBtnPanel = driver.findElement(By.xpath("//button[@title='Просмотр']"));

            if (viewBtnPanel.isDisplayed()){
                System.out.println("Верно - кнопка просмотр присутствует");
            } else {
                Assert.fail("Не верно - отсутствует кнопка просмотр");
            }
    }

    @Test (dependsOnMethods = "settingsObjectPanelView")  //Проверка наличия кнопки Редактировать
    public void settingsObjectPanelEdit() {
        WebElement editBtnPanel = driver.findElement(By.xpath("//button[@title='Изменить']"));

            if (editBtnPanel.isDisplayed()){
                System.out.println("Верно - кнопка Редактирование присутствует");
            } else {
                Assert.fail("Не верно - отсутствует Редактирование просмотр");
            }
    }

    @Test (dependsOnMethods = "settingsObjectPanelView")  //Проверка наличия кнопки Удалить
    public void settingsObjectPanelDel() {
        WebElement editBtnPanel = driver.findElement(By.xpath("//button[@title='Удалить']"));

            if (editBtnPanel.isDisplayed()){
                System.out.println("Верно - кнопка Удаления присутствует");
            } else {
                Assert.fail("Не верно - отсутствует Удаления просмотр");
            }
    }

    @Test (dependsOnMethods = "settingsObjectPanelDel") //открытие карточки на Просмотр. Проверка залоченности Кнопки Сохранить
    public void viewObject() {
        driver.findElement(By.xpath("//button[@title='Просмотр']")).click();
        getWhenVisible(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"),20).isDisplayed();

            if (driver.findElement(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']")).isEnabled()) {
                Assert.fail("Просмотр карточки. Не верно - Карточка открыта на просмотр. Кнопка Создать НЕ залочена");
            } else {
                System.out.println("Просмотр карточки. Верно - Карточка открыта на просмотр. Кнопка Создать залочена");
            }

    }

    @Test (dependsOnMethods = "viewObject") //открытие карточки на Просмотр. Проверка НЕ залоченности Кнопки Закрыть
    public void viewObjectCloseBtn() {
        getWhenVisible(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"),20).isDisplayed();

            if (driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).isEnabled()) {
                System.out.println("Просмотр карточки. Верно - Карточка открыта на просмотр. Кнопка Закрыть НЕ залочена");
            } else {
                Assert.fail("Просмотр карточки. Не верно - Карточка открыта на просмотр. Кнопка Закрыть залочена");
            }
    }

    @Test (dependsOnMethods = "viewObject")//открытие карточки на Просмотр. Проверка залоченности поля Район
    public void viewObjectRegion() {

            if (driver.findElement(By.xpath("//select[@class='selectpicker  select2-hidden-accessible']")).isEnabled()) {
                Assert.fail("Просмотр карточки. Не верно - Карточка открыта на просмотр. Поле Район НЕ залочено");
            } else {
                System.out.println("Просмотр карточки. Верно - Карточка открыта на просмотр. Поле Район залочено");
            }
    }

    @Test (dependsOnMethods = "viewObject") //открытие карточки на Просмотр. Проверка залоченности поля Дата начала снегопада
    public void viewObjectSnowDateFrom() {
            if (driver.findElement(By.xpath("//input[@name='innerPanel:snowDateFrom']")).isEnabled()) {
                Assert.fail("Просмотр карточки. Не верно - Карточка открыта на просмотр. Поле 'Дата начала снегопада' НЕ залочено");
            } else {
                System.out.println("Просмотр карточки. Верно - Карточка открыта на просмотр. Поле 'Дата начала снегопада' залочено");
            }
    }

    @Test (dependsOnMethods = "viewObject")//открытие карточки на Просмотр. Проверка залоченности поля Дата окончания  снегопада
    public void viewObjectSnowDateTo() {
            if (driver.findElement(By.xpath("//input[@name='innerPanel:snowDateTo']")).isEnabled()) {
                Assert.fail("Просмотр карточки. Не верно - Карточка открыта на просмотр. Поле 'Дата окончания снегопада' НЕ залочено");
            } else {
                System.out.println("Просмотр карточки. Верно - Карточка открыта на просмотр. Поле 'Дата окончания снегопада' залочено");
            }
    }

    @Test (dependsOnMethods = "viewObject") //открытие карточки на Просмотр. Проверка залоченности поля Кол-во осадков, мм
    public void viewObjectRainFall() {
            if (driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']")).isEnabled()) {
                Assert.fail("Просмотр карточки. Не верно - Карточка открыта на просмотр. Поле 'Кол-во осадков, мм' НЕ залочено");
            } else {
                System.out.println("Просмотр карточки. Верно - Карточка открыта на просмотр. Поле 'Кол-во осадков, мм' залочено");
            }
    }

    @Test (dependsOnMethods = "viewObject") //открытие карточки на Просмотр. Сверка наименования карточки без учёта ID
    public void titleObject() {
        WebElement titleObject = driver.findElement(By.xpath("//div[@class='tab-hdr']/h1"));
            if (titleObject.getText().matches("Данные погодных явлений.\\(ID:.\\d*\\)")) {
                System.out.println("Просмотр карточки. Верно - Наименование карточки верно");
            } else {
                Assert.fail("Просмотр карточки. НЕ верно - Наименование карточки НЕ верно");
            }
    }

    @Test (dependsOnMethods = "viewObject") //Просмотр карточки. проверка поля Кол-во осадков, мм: с БД  (exeption когда приходит null-т.е. поле не заполнено)
    public void checkRainFallValue() {
        Long idObject = idFieldKart();

        //Кол-во осадков, мм - Rainfal.делаем выборку одного значения из БД с использованием ID,выдраным раннее.
        FdcWeatherEntity weatherRainFall = session.get(FdcWeatherEntity.class, idObject);

        WebElement fieldRainFallValueWeb = driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']"));
        Integer fieldRainFallValue = new Integer(fieldRainFallValueWeb.getAttribute("value")); //из String в Integer
            if (fieldRainFallValue.equals(weatherRainFall.getRainfall()) || fieldRainFallValue.equals("")){
                System.out.println("Верно - Значение UI поля 'Кол-во осадков, мм' равнo значению в БД");
            } else {
                Assert.fail("НЕ верно - Значение UI поля 'Кол-во осадков, мм' НЕ равнo значению в БД!!!");
            }
    }

    @Test (dependsOnMethods = "viewObject") //Просмотр карточки. Сверка поля Район: municipality_id с БД
    public void checkFieldMunicipalityId() {
        Long idObject = idFieldKart();

        FdcWeatherEntity weatherRainFall = session.get(FdcWeatherEntity.class, idObject);
        FdcAsAddrobjEntity asAddrobj = session.get(FdcAsAddrobjEntity.class, weatherRainFall.getMunicipalityId());

        WebElement fieldMunicipalityValue = driver.findElement(By.xpath("//span[@class='select2-selection__rendered']"));
            if (fieldMunicipalityValue.getText().equals(asAddrobj.getFormalName()+" "+asAddrobj.getShortName())) {
                System.out.println("Просмотр карточки. Верно - Значение поля Район равны в БД и UI");
            } else {
                Assert.fail("Просмотр карточки. НЕ верно - Значение поля Район НЕ равны в БД и UI");
            }
    }

    @Test (dependsOnMethods = "viewObject") //Просмотр карточки.Проверка даты-времени SnowDateFrom-Дата начала снегопада
    public void checkSnowDateFromValue(){
        Long idObject = idFieldKart();

        FdcWeatherEntity weatherSnowDateFrom = session.get(FdcWeatherEntity.class, idObject);

        WebElement snowDateFromField = driver.findElement(By.xpath("//input[@name='innerPanel:snowDateFrom']"));
            DateFormat df= new SimpleDateFormat("dd.MM.yyyy HH:mm");
            df.setLenient(true);
            String formattedDateSnowFrom = df.format(weatherSnowDateFrom.getSnowDateFrom());

            if ((snowDateFromField.getAttribute("value")).equals(formattedDateSnowFrom)){
                System.out.println("Просмотр карточки.Верно - Значение поля 'Дата начала снегопада' равны в БД и UI");
            } else {
                Assert.fail("Просмотр карточки.НЕ верно - Значение поля 'Дата начала снегопада' НЕ равны в БД и UI");
            }
    }

    @Test (dependsOnMethods = "viewObject") //Просмотр карточки.Проверка даты-времени SnowDateFrom-Дата начала снегопада
    public void checkSnowDateToValue(){
        Long idObject = idFieldKart();

        FdcWeatherEntity weatherSnowDateTo = session.get(FdcWeatherEntity.class, idObject);

        WebElement snowDateToField = driver.findElement(By.xpath("//input[@name='innerPanel:snowDateTo']"));
            DateFormat df= new SimpleDateFormat("dd.MM.yyyy HH:mm");
            df.setLenient(true);
            String formattedDateSnowTo = df.format(weatherSnowDateTo.getSnowDateTo());

            if ((snowDateToField.getAttribute("value")).equals(formattedDateSnowTo)){
                System.out.println("Просмотр карточки. Верно - Значение поля 'Дата окончания снегопада' равны в БД и UI");
            } else {
                Assert.fail("Просмотр карточки. НЕ верно - Значение поля 'Дата окончания снегопада' НЕ равны в БД и UI");
            }
    }

    @Test (dependsOnMethods = "checkSnowDateToValue") // возврат из режима просмотра в реестр погоды
    public void checkFromViewToReestr() {
        driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).click();
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();
        WebElement tableTitle = driver.findElement(By.xpath("//div[@class='tab-hdr clearfix']/h1"));

            if (tableTitle.getText().equals("Погодные явления")) {
                System.out.println("Возврат из режима просмотра в Реестр Погоды успешен");
            } else {
                Assert.fail("Возврат из режима просмотра в Реестр Погоды НЕ успешен");
            }
    }

    @Test (dependsOnMethods = "checkFromViewToReestr") //открытие карточки на Редактирование. Проверка НЕ залоченности Кнопки Сохранить
    public void editObject() {
        driver.findElement(By.xpath("//td[@class=' txt-right ']")).click();
        getWhenVisible(By.xpath("//button[@title='Изменить']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[@title='Изменить']")).click();
        getWhenVisible(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"),20).isDisplayed();

        if (driver.findElement(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']")).isEnabled()) {
            System.out.println("Редактирование. Верно - Карточка открыта на редактирование. Кнопка Создать НЕ залочена");
        } else {
            Assert.fail("Редактирование. Не верно - Карточка открыта на редактирование. Кнопка Создать залочена");
        }
    }

    @Test (dependsOnMethods = "editObject") //открытие карточки на Редактирование. Проверка НЕ залоченности Кнопки Закрыть
    public void editObjectCloseBtn() {
        getWhenVisible(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"),20).isDisplayed();

        if (driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).isEnabled()) {
            System.out.println("Редактирование. Верно - Карточка открыта на Редактирование. Кнопка Закрыть НЕ залочена");
        } else {
            Assert.fail("Редактирование. Не верно - Карточка открыта на Редактирование. Кнопка Закрыть залочена");
        }
    }

    @Test (dependsOnMethods = "editObjectCloseBtn")//открытие карточки на Редактирование. Проверка НЕ залоченности поля Район
    public void editObjectRegion() {

        if (driver.findElement(By.xpath("//select[@class='selectpicker required select2-hidden-accessible']")).isEnabled()) {
            System.out.println("Редактирование. Верно - Карточка открыта на Редактирование. Поле Район НЕ залочено");
        } else {
            Assert.fail("Редактирование. Не верно - Карточка открыта на Редактирование. Поле Район залочено");
        }
    }

    @Test (dependsOnMethods = "editObjectRegion") //открытие карточки на Редактирование. Проверка НЕ залоченности поля Дата начала снегопада
    public void editObjectSnowDateFrom() {
        if (driver.findElement(By.xpath("//input[@name='innerPanel:snowDateFrom']")).isEnabled()) {
            System.out.println("Редактирование. Верно - Карточка открыта на Редактирование. Поле 'Дата начала снегопада' НЕ залочено");
        } else {
            Assert.fail("Редактирование. Не верно - Карточка открыта на Редактирование. Поле 'Дата начала снегопада'  залочено");
        }
    }

    @Test (dependsOnMethods = "editObjectSnowDateFrom")//открытие карточки на Редактирование. Проверка НЕ залоченности поля Дата окончания  снегопада
    public void editObjectSnowDateTo() {
        if (driver.findElement(By.xpath("//input[@name='innerPanel:snowDateTo']")).isEnabled()) {
            System.out.println("Редактирование. Верно - Карточка открыта на Редактирование. Поле 'Дата окончания снегопада' НЕ залочено");
        } else {
            Assert.fail("Редактирование. Не верно - Карточка открыта на Редактирование. Поле 'Дата окончания снегопада' залочено");
        }
    }

    @Test (dependsOnMethods = "editObjectSnowDateTo") //открытие карточки на Редактирование. Проверка НЕ залоченности поля Кол-во осадков, мм
    public void editObjectRainFall() {
        if (driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']")).isEnabled()) {
            System.out.println("Редактирование. Верно - Карточка открыта на просмотр. Поле 'Кол-во осадков, мм' НЕ залочено");
        } else {
            Assert.fail("Редактирование. Не верно - Карточка открыта на просмотр. Поле 'Кол-во осадков, мм' залочено");
        }
    }

    @Test (dependsOnMethods = "editObjectRainFall") //открытие карточки на Редактирование. Сверка наименования карточки без учёта ID
    public void editTitleObject() {
        WebElement titleObject = driver.findElement(By.xpath("//div[@class='tab-hdr']/h1"));
        if (titleObject.getText().matches("Данные погодных явлений.\\(ID:.\\d*\\)")) {
            System.out.println("Редактирование. Верно - Наименование карточки верно");
        } else {
            Assert.fail("Редактирование. НЕ верно - Наименование карточки НЕ верно");
        }
    }

    @Test (dependsOnMethods = "editTitleObject") //Редактирование карточки. Сверка поля Район: municipality_id с БД
    public void editCheckFieldMunicipalityId() {
        Long idObject = idFieldKart();

        FdcWeatherEntity editWeatherRainFall = session.get(FdcWeatherEntity.class, idObject);
        FdcAsAddrobjEntity asAddrobj = session.get(FdcAsAddrobjEntity.class, editWeatherRainFall.getMunicipalityId());

        WebElement editFieldMunicipalityValue = driver.findElement(By.xpath("//span[@class='select2-selection__rendered']"));
            if (editFieldMunicipalityValue.getAttribute("title").equals(asAddrobj.getFormalName()+" "+asAddrobj.getShortName())) {
                System.out.println("Редактирование карточки. Верно - Значение поля Район равны в БД и UI");

            } else {
                Assert.fail("Редактирование карточки.НЕ верно - Значение поля Район НЕ равны в БД и UI");
            }
    }

    @Test (dependsOnMethods = "editCheckFieldMunicipalityId") //Редактирование карточки. Проверка даты-времени SnowDateFrom-Дата начала снегопада
    public void checkEditSnowDateFromValue(){
        Long idObject = idFieldKart();

        FdcWeatherEntity weatherSnowDateFrom = session.get(FdcWeatherEntity.class, idObject);

        WebElement snowDateFromField = driver.findElement(By.xpath("//input[@name='innerPanel:snowDateFrom']"));
        DateFormat df= new SimpleDateFormat("dd.MM.yyyy HH:mm");
        df.setLenient(true);
        String formattedDateSnowFrom = df.format(weatherSnowDateFrom.getSnowDateFrom());

            if ((snowDateFromField.getAttribute("value")).equals(formattedDateSnowFrom)){
                System.out.println("Редактирование карточки. Верно - Значение поля 'Дата начала снегопада' равны в БД и UI");
            } else {
                Assert.fail("Редактирование карточки. НЕ верно - Значение поля 'Дата начала снегопада' НЕ равны в БД и UI");
            }
    }

    @Test (dependsOnMethods = "checkEditSnowDateFromValue") //Редактирование карточки.Проверка даты-времени SnowDateFrom-Дата начала снегопада
    public void checkEditSnowDateToValue(){
        Long idObject = idFieldKart();

        FdcWeatherEntity weatherSnowDateTo = session.get(FdcWeatherEntity.class, idObject);

        WebElement snowDateToField = driver.findElement(By.xpath("//input[@name='innerPanel:snowDateTo']"));
        DateFormat df= new SimpleDateFormat("dd.MM.yyyy HH:mm");
        df.setLenient(true);
        String formattedDateSnowTo = df.format(weatherSnowDateTo.getSnowDateTo());

            if ((snowDateToField.getAttribute("value")).equals(formattedDateSnowTo)){
                System.out.println("Редактирование карточки. Верно - Значение поля 'Дата окончания снегопада' равны в БД и UI");
            } else {
                Assert.fail("Редактирование карточки.НЕ верно - Значение поля 'Дата окончания снегопада' НЕ равны в БД и UI");
            }
    }

    @Test (dependsOnMethods = "checkEditSnowDateToValue") //Редактирование карточки. проверка поля Кол-во осадков, мм: с БД
    public void checkEditRainFallValue() {
        Long idObject = idFieldKart();

        //Кол-во осадков, мм - Rainfal.делаем выборку одного значения из БД с использованием ID,выдраным раннее.
        FdcWeatherEntity weatherRainFall = session.get(FdcWeatherEntity.class, idObject);

        WebElement fieldRainFallValueWeb = driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']"));
        Integer fieldRainFallValue = new Integer(fieldRainFallValueWeb.getAttribute("value")); //из String в Integer
            if (fieldRainFallValue.equals(weatherRainFall.getRainfall()) || fieldRainFallValue.equals("")){
                System.out.println("Редактирование карточки. Верно - Значение UI поля 'Кол-во осадков, мм' равнo значению в БД");
            } else {
                Assert.fail("Редактирование карточки. НЕ верно - Значение UI поля 'Кол-во осадков, мм' НЕ равнo значению в БД!!!");
            }
    }

    @Test (dependsOnMethods = "checkEditRainFallValue")
    public void checkEditChangeObject() //Редактирование карточки. Внесение изменений в карточку
    {
            //очистка значения поля RainFall и ввод нового значения
        getWhenVisible(By.xpath("//input[@name='innerPanel:rainfall']"),20).isDisplayed();
        WebElement fieldRainFallValueWeb = driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']"));
        fieldRainFallValueWeb.clear();
        fieldRainFallValueWeb.sendKeys("550");

            //Сохранение карточки
        driver.findElement(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']")).click();
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();

        WebElement tableTitle = driver.findElement(By.xpath("//div[@class='tab-hdr clearfix']/h1"));

            if (tableTitle.getText().equals("Погодные явления")) {
                System.out.println("Редактирование карточки. Сохранение карточки из режима редактирования Успешно. Реестр Погоды открыт");
            } else {
                Assert.fail("Редактирование карточки. Сохранение карточки из режима редактирования НЕ Успешно. Реестр Погоды НЕ открыт");
            }
    }

    @Test (dependsOnMethods = "checkEditChangeObject") // проверка внесённых изменений
    public void checkEditedValues() {

        //Вход в режим просмотра
        driver.findElement(By.xpath("//td[@class=' txt-right ']")).click();
        getWhenVisible(By.xpath("//button[@title='Просмотр']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[@title='Просмотр']")).click();
        getWhenVisible(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"),20).isDisplayed();

        Long idObject = idFieldKart();
        FdcWeatherEntity weatherRainFall = session.get(FdcWeatherEntity.class, idObject);

        WebElement fieldRainFallValueWeb = driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']"));
        Integer fieldRainFallValue = new Integer(fieldRainFallValueWeb.getAttribute("value")); //из String в Integer
            if (fieldRainFallValue.equals(weatherRainFall.getRainfall()) || fieldRainFallValue.equals("")){
                System.out.println("Редактирование карточки. Верно - изменённое значение UI поля 'Кол-во осадков, мм' равнo значению в БД");
            } else {
                Assert.fail("Редактирование карточки. НЕ верно - изменённое значение UI поля 'Кол-во осадков, мм' НЕ равнo значению в БД!!!");
            }
        driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).click();
    }

    @Test (dependsOnMethods = "checkEditedValues") // Проверка модалки удаления наименования
    public void checkDeletionWindow() {
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();
        driver.findElement(By.xpath("//td[@class=' txt-right ']")).click();
        getWhenVisible(By.xpath("//button[@title='Удалить']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[@title='Удалить']")).click();

        getWhenVisible(By.xpath("//h3[@class='w_captionText']"),20).isDisplayed();
        WebElement deletionWindowTitle = driver.findElement(By.xpath("//h3[@class='w_captionText']"));
           if (deletionWindowTitle.getText().equals("Подтвердите")){
               System.out.println("Модалка удаления. Наименование Верно");
           } else {
               Assert.fail("Модалка удаления. Наименование НЕ Верно");
           }
    }

    @Test (dependsOnMethods = "checkDeletionWindow") //Модалка удаления. Проверка наименования кнопки удаления Подтвердить
    public void checkLableDeletionConfirmBtn() {
        WebElement deletionLabelConfirmBtn = driver.findElement(By.xpath("//input[@name='buttons:1']"));
            if (deletionLabelConfirmBtn.getAttribute("value").equals("Подтвердить")){
                System.out.println("Модалка удаления. Наименование кнопки подтверждения удаления Верно");
            } else {
                Assert.fail("Модалка удаления. Наименование кнопки подтверждения удаления НЕ Верно");
            }
    }

    @Test (dependsOnMethods = "checkLableDeletionConfirmBtn") //Модалка удаления. Проверка наименования кнопки удаления Отмена
    public void checkLableDeletionCancellBtn() {
        WebElement deletionLabelCancellmBtn = driver.findElement(By.xpath("//div[@class='fdc-wicket-dialog-buttons']/input[position()=2]"));
            if (deletionLabelCancellmBtn.getAttribute("value").equals("Отменить")){
                System.out.println("Модалка удаления. Наименование кнопки Отмены удаления Верно");
            } else {
                Assert.fail("Модалка удаления. Наименование кнопки Отмены удаления НЕ Верно");
            }
    }

    @Test (dependsOnMethods = "checkLableDeletionCancellBtn") //Модалка удаления. Проверка текста на модалке
    public void checkTextDeletionWindow() {
        WebElement textDeletionWindow = driver.findElement(By.xpath("//span[@class='message-text']"));
        if (textDeletionWindow.getText().equals("Запись будет удалена. Продолжить?")){
            System.out.println("Модалка удаления. Текст на модалке удаления Верен");
        } else {
            Assert.fail("Модалка удаления. Текст на модалке удаления Верен");
        }
    }

    @Test (dependsOnMethods = "checkTextDeletionWindow") //Модалка удаления. Проверка закрытия модалки на кнопку в правом верхнем углу
    public void checkCloseRightTopBtn() {
        driver.findElement(By.xpath("//a[@class='w_close']")).click();
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();
        WebElement tableTitle = driver.findElement(By.xpath("//div[@class='tab-hdr clearfix']/h1"));

            if (tableTitle.getText().equals("Погодные явления")) {
                System.out.println("Модалка удаления. Закрытие кнопкой в правом верхнем углу Успешно");
            } else {
                Assert.fail("Модалка удаления. Закрытие кнопкой в правом верхнем углу Успешно");
            }
    }

    @Test (dependsOnMethods = "checkCloseRightTopBtn") //Модалка удаления. Проверка закрытия модалки на кнопку Отмена
    public void checkCancellDeletionWindow() {
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();
        driver.findElement(By.xpath("//td[@class=' txt-right ']")).click();
        getWhenVisible(By.xpath("//button[@title='Удалить']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[@title='Удалить']")).click();

        getWhenVisible(By.xpath("//div[@class='fdc-wicket-dialog-buttons']/input[position()=2]"),20).isDisplayed();
        driver.findElement(By.xpath("//div[@class='fdc-wicket-dialog-buttons']/input[position()=2]")).click();
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();
        WebElement tableTitle = driver.findElement(By.xpath("//div[@class='tab-hdr clearfix']/h1"));

        if (tableTitle.getText().equals("Погодные явления")) {
            System.out.println("Модалка удаления. Закрытие кнопкой Отмена Успешно");
        } else {
            Assert.fail("Модалка удаления. Закрытие кнопкой Отмена Успешно");
        }
    }
//TODO  eeedfdsfsdf
    @Test (dependsOnMethods = "checkCancellDeletionWindow") //подсчёт
    public void checkDeletionObject() {
        int countBdBefore = ((Long)getSession().createQuery("select count(*) from FdcWeatherEntity").uniqueResult()).intValue();
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();
        driver.findElement(By.xpath("//td[@class=' txt-right ']")).click();
        getWhenVisible(By.xpath("//button[@title='Удалить']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[@title='Удалить']")).click();

        getWhenVisible(By.xpath("//div[@class='fdc-wicket-dialog-buttons']/input[position()=1]"),20).isDisplayed();
        driver.findElement(By.xpath("//div[@class='fdc-wicket-dialog-buttons']/input[position()=1]")).click();
        int countBdAfter = ((Long)getSession().createQuery("select count(*) from FdcWeatherEntity").uniqueResult()).intValue();

        System.out.println(countBdAfter+" "+countBdBefore);
    }
//


}
