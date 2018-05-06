import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.fors.ods3.base.domain.FdcAsAddrobjEntity;
import ru.fors.ods3.msnow.domain.FdcWeatherEntity;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class RegistryWeatherTest extends WebDriverSettings {

    private static final String URL_WEATHER = "http://ods2.fors.ru:9090/dev/registry?2&code=registryForm&bk=ODH/MSNOW/Weather";

    @Test
    public void checkTitleRegistryWeather() {
        driver.get(URL_WEATHER);
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"), 20).isDisplayed();
        String titleWeather = driver.getTitle();

        Assert.assertTrue(titleWeather.equals("Система контроля и планирования работ в области дорожной инфраструктуры"));
    }

    @Test(dependsOnMethods = "checkTitleRegistryWeather")
    public void checkTableTitle() {
        getWhenVisible(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']"), 20).isDisplayed();
        WebElement tableTitle = driver.findElement(By.xpath("//div[@class='tab-hdr clearfix']/h1"));

        if (tableTitle.getText().equals("Погодные явления")) {
            System.out.println("Реестр Погоды открыт");
        } else {
            Assert.fail("Реестр Погоды открыт НЕ открыт");
        }
    }

    @Test(dependsOnMethods = "checkTitleRegistryWeather")
    public void checkConnectionDB() {
        FdcWeatherEntity entity = getSession().get(FdcWeatherEntity.class, 39114998L);

        if (entity.getId() != null) {
            System.out.println("Сущность подтянулась");
        } else {
            System.out.println("Сущность НЕ подтянулась");
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

        driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).click();

    }

    @Test (dependsOnMethods = "checkNotValidRainFall") // Проверка ввода больших цифр в поле Кол-во осадков
    public void checkLongNumberRainFall() {
        getWhenVisible(By.xpath("//button[text()='Добавить']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[text()='Добавить']")).click(); // открытие на создание карточки
        getWhenVisible(By.xpath("//input[@name='innerPanel:snowDateFrom']"),20).isDisplayed();

        WebElement popupListRegion = driver.findElement(By.xpath("//*[@class='select2 select2-container select2-container--default']")); // выбор менюшки
        popupListRegion.click();//клик на меню для ввода поиска
        driver.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("Щелковск");//ввод первых символов поиска
        getWhenVisible(By.xpath("//ul[@class='select2-results__options']/li[@class='select2-results__option select2-results__option--highlighted']"),20).isDisplayed();
        WebElement selectPopupListRegion = driver.findElement(By.xpath("//ul[@class='select2-results__options']/li[@class='select2-results__option select2-results__option--highlighted']"));
        selectPopupListRegion.click();

        driver.findElement(By.xpath("//input[@name='innerPanel:snowDateFrom']")).sendKeys("02.05.2018 13:49");
        driver.findElement(By.xpath("//*[@name='innerPanel:snowDateTo']")).sendKeys("02.05.2018 13:49");


        WebElement fieldRainFallValueWeb = driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']"));
        fieldRainFallValueWeb.sendKeys("9999999999");

        driver.findElement(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']")).click();
        getWhenVisible(By.xpath("//li[@class='feedbackPanelERROR']"),20).isDisplayed();
        WebElement feedbackError = driver.findElement(By.xpath("//li[@class='feedbackPanelERROR']/span"));
        System.out.println(feedbackError.getText());

        if (feedbackError.getText().equals("'Кол-во осадков, мм' выходит за границы допустимых значений.")) {
            System.out.println("Верно - корректное сообщение при большом числе вводимом в поле Кол-во осадков");
        } else {
            Assert.fail("Верно - корректное сообщение при большом числе вводимом в поле Кол-во осадков");
        }

       // driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).click();

    }

    @Test (dependsOnMethods = "checkLongNumberRainFall") //Проверка ввода валидных данных и сохранения карточки
    public void checkObjectCreation() {

    driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']")).clear();
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
            if (fieldRainFallValue.equals(weatherRainFall.getRainfall()) || fieldRainFallValueWeb.getAttribute("value").equals("")){
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
       getWhenVisible(By.xpath("//input[@name='innerPanel:rainfall']"),20).isDisplayed();
        Long idObject = idFieldKart();
        //Кол-во осадков, мм - Rainfal.делаем выборку одного значения из БД с использованием ID,выдраным раннее.
        FdcWeatherEntity weatherRainFall = session.get(FdcWeatherEntity.class, idObject);
        WebElement fieldRainFallValueWeb = driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']"));
        Integer fieldRainFallValue = new Integer(fieldRainFallValueWeb.getAttribute("value")); //из String в Integer
            if (fieldRainFallValue.equals(weatherRainFall.getRainfall()) || fieldRainFallValueWeb.getAttribute("value").equals("")){
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

       // session.clear(); //очистка кэша сессии
       // Long idObject = idFieldKart();
      //  FdcWeatherEntity weatherRainFall = session.get(FdcWeatherEntity.class, idObject);
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
        getWhenVisible(By.xpath("//button[@title='Просмотр']"),20).isEnabled();
        driver.findElement(By.xpath("//button[@title='Просмотр']")).click();
        getWhenVisible(By.xpath("//button[@name='innerPanel:buttonsPanel:saveBtn']"),20).isEnabled();

        session.clear(); // очистка кэша сессии
        Long idObject = idFieldKart();

        WebElement fieldRainFallValueWeb = driver.findElement(By.xpath("//input[@name='innerPanel:rainfall']"));
        getWhenVisible(By.xpath("//input[@class='inp-w100']"),20).isDisplayed();
        retryingFindClick(By.xpath("//input[@class='inp-w100']"));
        FdcWeatherEntity weatherRainFall = session.get(FdcWeatherEntity.class, idObject);

        Integer fieldRainFallValue = new Integer(fieldRainFallValueWeb.getAttribute("value")); //из String в Integer
            if (fieldRainFallValue.equals(weatherRainFall.getRainfall()) || fieldRainFallValueWeb.getAttribute("value").equals("")){
                System.out.println("Редактирование карточки. Верно - изменённое значение UI поля 'Кол-во осадков, мм' "+fieldRainFallValueWeb.getAttribute("value")+" равнo значению в БД "+weatherRainFall.getRainfall());
            } else {
                System.out.println("Редактирование карточки. НЕ верно - изменённое значение UI поля 'Кол-во осадков, мм' "+fieldRainFallValueWeb.getAttribute("value")+" НЕ равнo значению в БД "+weatherRainFall.getRainfall());
            }
        driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).click();
    }

    @Test (dependsOnMethods = "checkEditedValues") // Проверка модалки удаления наименования
    public void checkDeletionWindow() {
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),10).isDisplayed();
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

    @Test (dependsOnMethods = "checkCancellDeletionWindow") //подсчёт при удалении
    public void checkDeletionObject() {
            int countBdBefore = ((Long)getSession().createQuery("select count(*) from FdcWeatherEntity").uniqueResult()).intValue();
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();
        driver.findElement(By.xpath("//td[@class=' txt-right ']")).click();
        getWhenVisible(By.xpath("//button[@title='Удалить']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[@title='Удалить']")).click();

        getWhenVisible(By.xpath("//input[@name='buttons:1']"),20).isDisplayed(); //кнопка подтверждения удаления
        driver.findElement(By.xpath("//input[@name='buttons:1']")).click();
        driver.navigate().refresh();
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();

        int countBdAfter = ((Long)getSession().createQuery("select count(*) from FdcWeatherEntity").uniqueResult()).intValue();
            if (countBdAfter == countBdAfter-1){
                System.out.println("Модалка удаления. Проверка удаления объекта с БД. Удаление успешно. Число объектов +"+countBdAfter+" после удаления = число объектов до удаления "+countBdBefore+" -1");
            } else {
                System.out.println("Модалка удаления. Проверка удаления объекта с БД. Удаление НЕ успешно. Число объектов +"+countBdAfter+" после удаления != число объектов до удаления "+countBdBefore+" -1");
            }
        }

    @Test (dependsOnMethods = "checkDeletionObject") //Панель фильтров. Проверка Наименования Район фильтра
    public void checkTitleFilterMunicipalityId() {
        getWhenVisible(By.xpath("//label[@class='control-label col-lg-4']"),20).isDisplayed();
        //retryingFindClick(By.xpath("//label[@class='control-label col-lg-4']"));

        WebElement titleMunicipalityFilter = driver.findElement(By.xpath("//div[@class='row']/div[position()=1]/div[@class='form-group']/label"));

        if (titleMunicipalityFilter.getText().equals("РАЙОН:")) {
                System.out.println("Панель фильтров. Наименование Район Верно");
            }else  {
                Assert.fail("Панель фильтров. Наименование Район НЕ Верно");
            }
    }

    @Test (dependsOnMethods = "checkTitleFilterMunicipalityId") //Панель фильтров. Проверка Наименования Дата начала фильтра
    public void checkTitleDateFromFilter() {
        retryingFindClick(By.xpath("//div[@class='row']/div[position()=2]/div[@class='form-group']/label"));
        getWhenVisible(By.xpath("//div[@class='row']/div[position()=2]/div[@class='form-group']/label"),20).isEnabled();
        WebElement titleDateFromFilter = driver.findElement(By.xpath("//div[@class='row']/div[position()=2]/div[@class='form-group']/label"));

            if (titleDateFromFilter.getText().equals("ДАТА НАЧАЛА ПЕРИОДА:")) {
                System.out.println("Панель фильтров. Наименование Дата начала периода: Верно");
            }else  {
                Assert.fail("Панель фильтров. Наименование Дата начала периода: НЕ Верно");
            }
    }

    @Test (dependsOnMethods = "checkTitleDateFromFilter") //Панель фильтров. Проверка Наименования Дата окончания фильтра
    public void checkTitleDateToFilter() {
        getWhenVisible(By.xpath("//div[@class='row']/div[position()=3]/div[@class='form-group']/label"),20).isDisplayed();
        WebElement titleDateToFilter = driver.findElement(By.xpath("//div[@class='row']/div[position()=3]/div[@class='form-group']/label"));
            if (titleDateToFilter.getText().equals("ДАТА ОКОНЧАНИЯ ПЕРИОДА:")) {
                System.out.println("Панель фильтров. Наименование Дата окончания периода: Верно");
            }else  {
                Assert.fail("Панель фильтров. Наименование Дата окончания периода: НЕ Верно");
            }
    }

    @Test (dependsOnMethods = "checkTitleDateToFilter") ////Панель фильтров. ввод данных в ДАТА НАЧАЛА ПЕРИОДА: и обнуление
    public void checkValueDateFromFilter() {
        getWhenVisible(By.xpath("//input[@name='snowDateFrom']"),20).isEnabled();
        WebElement valueDateFromFilter = driver.findElement(By.xpath("//input[@name='snowDateFrom']"));
        valueDateFromFilter.sendKeys("22.02.2018 23:16");
        getWhenVisible(By.xpath("//div[@class='btn-group btn-group-flt-srch btn-group-justified']/div[position()=2]/button"),20).isEnabled();
        driver.findElement(By.xpath("//div[@class='btn-group btn-group-flt-srch btn-group-justified']/div[position()=2]/button")).click();

        if (valueDateFromFilter.getText().equals("")){
            System.out.println("Панель фильтров. Ввод данных и обнуление поля Дата Начала Успешно");
        }else {
            Assert.fail("Панель фильтров. Ввод данных и обнуление поля Дата Начала НЕ Успешно");
        }
    }

    @Test (dependsOnMethods = "checkValueDateFromFilter") //Панель фильтров. ввод данных в Район и обнуление
    public void checkValueMunicipalityFilter(){
       // getWhenVisible(By.xpath("//span[@class='select2-selection select2-selection--single']"),20).isEnabled();
        WebElement popupListRegion = driver.findElement(By.xpath("//span[@class='select2-selection__rendered']")); // выбор менюшки
        popupListRegion.click();//клик на меню для ввода поиска
        getWhenVisible(By.xpath("//input[@class='select2-search__field']"),20).isEnabled();
        driver.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("Щелковск");//ввод первых символов поиска
        getWhenVisible(By.xpath("//span[@class='select2-results']/ul/li"),20).isEnabled();

        WebElement selectPopupListRegion = driver.findElement(By.xpath("//span[@class='select2-results']/ul/li"));
        selectPopupListRegion.click();

        driver.findElement(By.xpath("//div[@class='btn-group btn-group-flt-srch btn-group-justified']/div[position()=2]/button")).click();
        getWhenVisible(By.xpath("//span[@class='select2-selection__placeholder']"),20).isDisplayed();
        WebElement titleDefaultMunicipalityFilter = driver.findElement(By.xpath("//span[@class='select2-selection__placeholder']"));
            if (titleDefaultMunicipalityFilter.getText().equals("Все")){
                System.out.println("Панель фильтров. Ввод данных и обнуление поля Район Успешно");
            }else {
                Assert.fail("Панель фильтров. Ввод данных и обнуление поля Район НЕ Успешно");
            }
    }

    @Test (dependsOnMethods = "checkValueMunicipalityFilter") //Панель фильтров. ввод данных в ДАТА окончание ПЕРИОДА: и обнуление
    public void checkValueDateToFilter() {
        driver.navigate().refresh();
        driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
        getWhenVisible(By.xpath("//input[@name='snowDateTo']"),20).isEnabled();
        retryingFindClick(By.xpath("//input[@name='snowDateTo']"));
        WebElement valueDateToFilter = driver.findElement(By.xpath("//input[@name='snowDateTo']"));
        getWhenVisible(By.xpath("//div[@class='btn-group btn-group-flt-srch btn-group-justified']/div[position()=2]/button"),20).isEnabled();

        valueDateToFilter.sendKeys("22.02.2018 25:16");

        driver.findElement(By.xpath("//td[@class=' txt-center ']")).click();

        driver.manage().timeouts().pageLoadTimeout(3, TimeUnit.SECONDS);
        valueDateToFilter.sendKeys(Keys.ESCAPE);

            if (valueDateToFilter.getText().equals("")){
                System.out.println("Панель фильтров. Ввод данных и обнуление поля Дата Окончания Успешно");
            }else {
                Assert.fail("Панель фильтров. Ввод данных и обнуление поля Дата Окончания НЕ Успешно");
            }
    }

    @Test (dependsOnMethods = "checkValueDateToFilter")  // Табличная часть. Сверка количества строк в таблице на 1й странице и наименования
    public void checkTableRecords10() {

         if (titleCountObjectTable() == numberListCountTable()) {
                System.out.println("Табличная часть. Количество записей"+numberListCountTable()+" таблицы соответствует номеру в тайтле таблицы " + titleCountObjectTable());
            } else {
                System.out.println("Табличная часть. Количество записей"+numberListCountTable()+" таблицы НЕ соответствует номеру в тайтле таблицы " + titleCountObjectTable());
            }
    }

    @Test (dependsOnMethods = "checkTableRecords10") //Табличная часть. Проверка выбора количества отображаемых записей  в наименовании таблицы 20 (10, 20,50)
    public void checkTableCountListSelection20() {
        driver.findElement(By.xpath("//select[@class='form-control']")).click();
        getWhenVisible(By.xpath("//option[@value='1']"), 20).isEnabled();
        WebElement countListTitle = driver.findElement(By.xpath("//option[@value='1']"));
        countListTitle.click();

        getWhenVisible(By.xpath("//option[@value='1']"), 20).isEnabled();

            if (countListTitle.getText().equals("20")) {
                System.out.println("Табличная часть. Значение выбранное в выпадающем списке кол-ва объектов на странице верно = " + countListTitle.getText());
            } else {
                Assert.fail("Табличная часть. Значение выбранное в выпадающем списке кол-ва объектов на странице НЕ верно != "+countListTitle.getText());
            }
    }

    @Test (dependsOnMethods = "checkTableCountListSelection20") //Табличная часть. Проверка количества записей в таблице при выборке в 20 элементов
    public void checkTableRecords20() {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            if (titleCountObjectTable()==numberListCountTable()) {

                System.out.println("Табличная часть. Количество записей"+numberListCountTable()+" таблицы соответствует номеру в тайтле таблицы " + titleCountObjectTable());
            } else {
                Assert.fail("Табличная часть. Количество записей"+numberListCountTable()+" таблицы НЕ соответствует номеру в тайтле таблицы " + titleCountObjectTable());
            }
    }


    @Test (dependsOnMethods = "checkTableRecords20") //Табличная часть. Проверка выбота количества отображаемых записей  50 (10, 20,50)
    public void checkTableCountListSelection50() {

        retryingFindClick(By.xpath("//select[@class='form-control']"));
        driver.findElement(By.xpath("//select[@class='form-control']")).click();

        getWhenVisible(By.xpath("//option[@value='2']"), 20).isDisplayed();
        WebElement countListTitle = driver.findElement(By.xpath("//option[@value='2']"));
        countListTitle.click();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        getWhenVisible(By.xpath("//option[@value='2']"), 20).isDisplayed();
        retryingFindClick(By.xpath("//option[@value='2']"));
            if (countListTitle.getText().equals("50")) {
                System.out.println("Табличная часть. Значение выбранное в выпадающем списке кол-ва объектов на странице  = " + countListTitle.getText());
            } else {
                Assert.fail("Табличная часть. Значение выбранное в выпадающем списке кол-ва объектов на странице НЕ  = "+ countListTitle.getText());
            }
    }
    @Test(dependsOnMethods = "checkTableCountListSelection50")  //Табличная часть. Проверка количества записей в таблице при выборке в 50 элементов
    public void checkTableRecords50() {

        getWhenVisible(By.xpath("//option[@value='2']"), 20).isEnabled();
            if (titleCountObjectTable() == numberListCountTable()) {
                System.out.println("Табличная часть. Количество записей таблицы соответствует номеру в тайтле таблицы =" + numberListCountTable());
            } else {
                Assert.fail("Табличная часть. Количество записей таблицы соответствует номеру в тайтле таблицы =" + numberListCountTable());
            }
    }

    @Test (dependsOnMethods = "checkTableRecords50")
    public void checkFilterSlideBtnUp() //панель фильтров. Сворачивание панели  checkTableRecords50
    {

        driver.findElement(By.xpath("//span[@class='fa fa-chevron-circle-up']")).click();
        WebElement filterPanelButtonsVisible = driver.findElement(By.xpath("//input[@name='snowDateFrom']"));
            if(filterPanelButtonsVisible.isDisplayed()) {
                System.out.println("Панель фильтров  сложилась. Кнопки не видны");
            } else {
                Assert.fail("Панель фильтров НЕ сложилась. Кнопки видны");
            }

    }

      @Test (dependsOnMethods = "checkFilterSlideBtnUp") //Панель фильтра. Проверка тайтла кнопки свёрнутого фильтра
    public void checkTitleClosedFilterBtn() {
          try {
              Thread.sleep(1000);                 //1000 milliseconds is one second.
          } catch(InterruptedException ex) {
              Thread.currentThread().interrupt();
          }
          WebElement filterBtnPanel2 = driver.findElement(By.xpath("//button[@class='btn btn-controls btn-filter-toggle']"));

          if (filterBtnPanel2.getAttribute("title").equals("Показать фильтр")) {
              System.out.println("Панель фильтров. Панель свёрнута и тайлт кнопки верен ="+filterBtnPanel2.getAttribute("title"));
          }else {
              System.out.println("Панель фильтров. Панель свёрнута и тайлт кнопки НЕ верен !="+filterBtnPanel2.getAttribute("title"));
          }
    }

    @Test (dependsOnMethods = "checkTitleClosedFilterBtn")
    public void checkFilterSlideBtnDown() //панель фильтров. Разворачивание панели
    {

        driver.findElement(By.xpath("//span[@class='fa fa-chevron-circle-down']")).click();
        WebElement filterPanelButtonsVisible = driver.findElement(By.xpath("//input[@name='snowDateFrom']"));
            if(filterPanelButtonsVisible.isEnabled()) {
                System.out.println("Панель фильтров развернулась. Кнопки видны");
            } else {
                Assert.fail("Панель фильтров  НЕ развернулась. Кнопки не видны");
            }
    }

    @Test (dependsOnMethods = "checkFilterSlideBtnDown") //Панель фильтра. Проверка тайтла кнопки развёрнутого фильтра
    public void checkTitleClosedFilterButton() {
        try {
            Thread.sleep(1000);                 //1000 milliseconds is one second.
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
      WebElement filterBtnPanel1 = driver.findElement(By.xpath("//button[@class='btn btn-controls btn-filter-toggle']"));

          if (filterBtnPanel1.getAttribute("title").equals("Скрыть фильтр")) {
              System.out.println("Панель фильтров. Панель развернута и тайлт кнопки верен ="+filterBtnPanel1.getAttribute("title"));
          }else {
              System.out.println("Панель фильтров. Панель развернута и тайлт кнопки НЕ верен !="+filterBtnPanel1.getAttribute("title"));
          }
    }

    @Test (dependsOnMethods = "checkTitleClosedFilterButton") //Поиск по Району
    public void searchMunicipalityResult(){
        //Поиск по фильтру Муниц.район
        getWhenVisible(By.xpath("//span[@class='select2 select2-container select2-container--default']"),20).isDisplayed();
        WebElement popupListRegion = driver.findElement(By.xpath("//*[@class='select2 select2-container select2-container--default']")); // выбор менюшки
        popupListRegion.click();//клик на меню для ввода поиска
        driver.findElement(By.xpath("//input[@class='select2-search__field']")).sendKeys("Щелковск");//ввод первых символов поиска
        getWhenVisible(By.xpath("//ul[@class='select2-results__options']/li[@class='select2-results__option select2-results__option--highlighted']"),20).isDisplayed();

        WebElement selectPopupListRegion = driver.findElement(By.xpath("//ul[@class='select2-results__options']/li[@class='select2-results__option select2-results__option--highlighted']"));
        selectPopupListRegion.click();
        driver.findElement(By.xpath("//button[@name='search']")).click();
        driver.navigate().refresh();
        // открытие карточки на просмотр
        retryingFindClick(By.xpath("//td[@class=' txt-right ']"));
        driver.findElement(By.xpath("//td[@class=' txt-right ']")).click();
        retryingFindClick(By.xpath("//td[@class=' txt-right ']"));
        getWhenVisible(By.xpath("//button[@title='Просмотр']"),20).isDisplayed();
        driver.findElement(By.xpath("//button[@title='Просмотр']")).click();

       //нахождение муниципального_ид
        Long idObject = idFieldKart();
        FdcWeatherEntity weatherRainFall = session.get(FdcWeatherEntity.class, idObject);
        Long municipalityId = weatherRainFall.getMunicipalityId();
        Long municipalityIdCount = ((Long)getSession().createQuery("select count(*) from FdcWeatherEntity where municipalityId="+municipalityId).uniqueResult());

        //возврат из просмотра в реестр с поиском
        driver.findElement(By.xpath("//button[@class='btn btn-default btn-no-brd btn-blue']/span[text()='Закрыть']")).click();
        getWhenVisible(By.xpath("//div[@class='tab-hdr clearfix']/h1"),20).isDisplayed();

            if (municipalityIdCount == numberCountTableObjectTotal()) {
                System.out.println("Число объектов по поиску Района верно числу объектов в БД с заданным районом "+municipalityIdCount+" = "+numberCountTableObjectTotal());
            } else {
                Assert.fail("Число объектов по поиску Района НЕ верно числу объектов в БД с заданным районом "+municipalityIdCount+" != "+numberCountTableObjectTotal());
            }
    }

    @Test (dependsOnMethods = "searchMunicipalityResult") // поиск по Дате Начала
    public void searchNotValidSnowDateFrom(){

        driver.findElement(By.xpath("//input[@name='snowDateFrom']")).sendKeys("0Д.ММ.2018");

        driver.findElement(By.xpath("//button[@name='search']")).click();
        retryingFindClick(By.xpath("//button[@name='search']"));
        WebElement snowDatePanelError = driver.findElement(By.xpath("//li[@class='feedbackPanelERROR']/span"));

            if (snowDatePanelError.getText().equals("'Дата начала периода' должно соответствовать формату ДД.ММ.ГГГГ.")) {
                System.out.println("Панель фильтров. Надпись о некорректном вводе Даты Начала верна - "+snowDatePanelError.getText());
            }else {
                Assert.fail("Панель фильтров. Надпись о некорректном вводе Даты Начала НЕ верна - 'Дата начала периода' должно соответствовать формату ДД.ММ.ГГГГ.");
            }
        driver.findElement(By.xpath("//div[@class='btn-group btn-group-flt-srch btn-group-justified']/div[position()=2]/button")).click();
    }

    @Test(dependsOnMethods = "searchNotValidSnowDateFrom") // поиск по Дате Начала
    public void searchNotValidSnowDateTo() {
        driver.navigate().refresh();
        getWhenVisible(By.xpath("//input[@name='snowDateTo']"), 20).isEnabled();

        WebElement valueDateToFilter = driver.findElement(By.xpath("//input[@name='snowDateTo']"));
        valueDateToFilter.isEnabled();
        valueDateToFilter.sendKeys("20.1М.201Г");
        driver.findElement(By.xpath("//td[@class=' txt-center ']")).click();

        WebElement searchBtn = driver.findElement(By.xpath("//button[@name='search']"));

                try {
                    Thread.sleep(1000);                 //1000 milliseconds is one second.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
        searchBtn.click();
                try {
                    Thread.sleep(1000);                 //1000 milliseconds is one second.
                } catch(InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
       WebElement snowDatePanelError = driver.findElement(By.xpath("//li[@class='feedbackPanelERROR']/span"));
        getWhenVisible(By.xpath("//li[@class='feedbackPanelERROR']/span"),20).isEnabled();
        if (snowDatePanelError.getText().equals("'Дата окончания периода' должно соответствовать формату ДД.ММ.ГГГГ.")) {
            System.out.println("Панель фильтров. Надпись о некорректном вводе Даты окончания верна -"+snowDatePanelError.getText());
        }else {
            Assert.fail("Панель фильтров. Надпись о некорректном вводе Даты окончания верна -'Дата окончания периода' должно соответствовать формату ДД.ММ.ГГГГ.");
        }
        driver.findElement(By.xpath("//div[@class='btn-group btn-group-flt-srch btn-group-justified']/div[position()=2]/button")).click();
    }

    @Test (dependsOnMethods = "searchNotValidSnowDateFrom") //Пагинация. Проверка залоченности кнопок перейти на последнюю и 1 пагинация
      public void checkPaginationMoveAll() {
        WebElement firstAll = driver.findElement(By.xpath("//a[@class='first']"));
        WebElement firstPagination = driver.findElement(By.xpath("//a[@title='Перейти на страницу 1']"));
        WebElement prevBtn = driver.findElement(By.xpath("//a[@class='prev']"));
            if (firstAll.isEnabled() && prevBtn.isEnabled() && firstPagination.isEnabled()) {
                System.out.println("Пагинация. Верно - Кнопки перейти на первую и предыдущую страницы пагинации залочены");
            } else {
                Assert.fail("Пагинация. Не верно - Кнопки перейти на первую и предыдущую страницы пагинации НЕ залочены");
            }
    }

    @Test (dependsOnMethods = "checkPaginationMoveAll") //Пагинация - проверка тайтла кнопки Перейти на первую
    public void checkTitleMoveFirst() {
        WebElement titleMoveFirstButn = driver.findElement(By.xpath("//a[@class='first']"));
            if (titleMoveFirstButn.getAttribute("title").equals("Перейти на первую страницу")) {
                System.out.println("Пагинация. Тайтл кнопки перехода на первую страницу верен ="+titleMoveFirstButn.getAttribute("title"));
            } else {
                Assert.fail("Пагинация. Тайтл кнопки перехода на первую страницу НЕ верен !="+titleMoveFirstButn.getAttribute("title"));
            }
    }

    @Test (dependsOnMethods = "checkTitleMoveFirst")  //Пагинация - проверка тайтла кнопки Перейти на предыдущую страницу
    public void checkTitleMovePrev(){
        WebElement titleMovePrevBtn = driver.findElement(By.xpath("//a[@class='prev']"));
            if (titleMovePrevBtn.getAttribute("title").equals("Перейти на предыдущую страницу")) {
                System.out.println("Пагинация. Тайтл кнопки перехода на предыдущую страницу верен ="+titleMovePrevBtn.getAttribute("title"));
            } else {
                Assert.fail("Пагинация. Тайтл кнопки перехода на предыдущую страницу НЕ верен !="+titleMovePrevBtn.getAttribute("title"));
            }
    }

    @Test (dependsOnMethods = "checkTitleMovePrev")  //Пагинация - проверка тайтла кнопки Перейти на предыдущую страницу
    public void checkTitleMoveLast(){
        WebElement titleMoveLastBtn = driver.findElement(By.xpath("//a[@class='last']"));
            if (titleMoveLastBtn.getAttribute("title").equals("Перейти на последнюю страницу")) {
                System.out.println("Пагинация. Тайтл кнопки перехода на последнюю страницу верен ="+titleMoveLastBtn.getAttribute("title"));
            } else {
                Assert.fail("Пагинация. Тайтл кнопки перехода на последнюю страницу НЕ верен !="+titleMoveLastBtn.getAttribute("title"));
            }
    }

    @Test (dependsOnMethods = "checkTitleMoveLast")  //Пагинация - проверка тайтла кнопки Перейти на предыдущую страницу
    public void checkTitleMoveNext(){
        WebElement titleMoveNextBtn = driver.findElement(By.xpath("//a[@class='next']"));
            if (titleMoveNextBtn.getAttribute("title").equals("Перейти на следующую страницу")) {
                System.out.println("Пагинация. Тайтл кнопки перехода на следующую страницу верен ="+titleMoveNextBtn.getAttribute("title"));
            } else {
                Assert.fail("Пагинация. Тайтл кнопки перехода на следующую страницу НЕ верен !="+titleMoveNextBtn.getAttribute("title"));
            }
    }

    @Test (dependsOnMethods = "checkTitleMoveNext") // Пагинация. Переход на посленюю и проверка залоченности кнопок
    public void checkLockLastBtn() {
        WebElement titleMoveLastBtn = driver.findElement(By.xpath("//a[@class='last']"));
        WebElement titleMoveNextBtn = driver.findElement(By.xpath("//a[@class='next']"));

        titleMoveLastBtn.click();

            if (titleMoveLastBtn.isEnabled() && titleMoveNextBtn.isEnabled()) {
                System.out.println("Пагинация. Верно - Кнопки перейти на последнюю и следующую страницы пагинации залочены");
            } else {
                Assert.fail("Пагинация. НЕ Верно - Кнопки перейти на последнюю и следующую страницы пагинации НЕ залочены");
            }
    }

    @Test (dependsOnMethods = "checkLockLastBtn") //checkLockLastBtn Пагинация. Проверка количества строк объектов на последней странице
    public void checkLastListObject10(){
        driver.findElement(By.xpath("//a[@class='last']")).click();
        getWhenVisible(By.xpath("//a[@class='first']"),20).isEnabled();
        WebElement listNumber = driver.findElement(By.xpath("//select[@class='form-control']/option[@selected='selected']"));
        Integer listNumberTitle = new Integer(listNumber.getText());
        Integer residueResult = numberCountTableObjectTotal() % listNumberTitle;

            if (numberListCountTable() == residueResult) {
                System.out.println("Пагинация. Число строк на последней пагинации верное при списке в 10 объектов на страницу");
            } else {
                Assert.fail("Пагинация. Число строк на последней пагинации НЕ верное при списке в 10 объектов на страницу");
            }
    }

    @Test (dependsOnMethods = "checkLastListObject10") // Пагинация. проверка количества страниц
    public void checkNumberList10(){

        WebElement countListObject = driver.findElement(By.xpath("//option[@selected='selected']"));
        Integer countListObj =new  Integer (countListObject.getText());

        WebElement paginationC = driver.findElement(By.xpath("//a[@disabled='disabled']/span"));
        Integer paginationCount = new Integer(paginationC.getText());

            if (numberCountTableObjectTotal()/countListObj+1 == paginationCount) {
                System.out.println("Пагинация. Количество страниц пагинации верно при значении списка объектов=10");
            } else {
                Assert.fail("Пагинация. Количество страниц пагинации НЕ верно при значении списка объектов=10");
            }
    }

    @Test (dependsOnMethods = "checkNumberList10") //
    public void checkLastListObject20(){
        driver.findElement(By.xpath("//select[@class='form-control']")).click();
        getWhenVisible(By.xpath("//option[@value='1']"), 20).isEnabled();
        WebElement countListTitle = driver.findElement(By.xpath("//option[@value='1']"));
        countListTitle.click();
        driver.navigate().refresh();
        getWhenVisible(By.xpath("//a[@class='last']"),20).click();

        WebElement listNumber = driver.findElement(By.xpath("//select[@class='form-control']/option[@selected='selected']"));
        Integer listNumberTitle = new Integer(listNumber.getText());
        Integer residueResult = numberCountTableObjectTotal() % listNumberTitle;

            if (numberListCountTable() == residueResult) {
                System.out.println("Пагинация. Число строк на последней пагинации верное при списке в 20 объектов на страницу");
            } else {
                Assert.fail("Пагинация. Число строк на последней пагинации НЕ верное при списке в 20 объектов на страницу");
            }
    }

    @Test (dependsOnMethods = "checkLastListObject20") // Пагинация. проверка количества страниц
    public void checkNumberList20(){

        WebElement countListObject = driver.findElement(By.xpath("//option[@selected='selected']"));
        Integer countListObj =new  Integer (countListObject.getText());

        WebElement paginationC = driver.findElement(By.xpath("//a[@disabled='disabled']/span"));
        Integer paginationCount = new Integer(paginationC.getText());

        if (numberCountTableObjectTotal()/countListObj+1 == paginationCount) {
            System.out.println("Пагинация. Количество страниц пагинации верно при значении списка объектов=20");
        } else {
            Assert.fail("Пагинация. Количество страниц пагинации НЕ верно при значении списка объектов=20");
        }
    }

    @Test (dependsOnMethods = "checkLastListObject20") //
    public void checkLastListObject50(){
        driver.findElement(By.xpath("//select[@class='form-control']")).click();
        getWhenVisible(By.xpath("//option[@value='2']"), 20).isEnabled();
        WebElement countListTitle = driver.findElement(By.xpath("//option[@value='2']"));
        countListTitle.click();
        driver.navigate().refresh();
        getWhenVisible(By.xpath("//a[@class='last']"),20).click();

        WebElement listNumber = driver.findElement(By.xpath("//select[@class='form-control']/option[@selected='selected']"));
        Integer listNumberTitle = new Integer(listNumber.getText());
        Integer residueResult = numberCountTableObjectTotal() % listNumberTitle;

            if (numberListCountTable() == residueResult) {
                System.out.println("Пагинация. Число строк на последней пагинации верное при списке в 50 объектов на страницу");
            } else {
                Assert.fail("Пагинация. Число строк на последней пагинации НЕ верное при списке в 50 объектов на страницу");
            }
    }

    @Test (dependsOnMethods = "checkLastListObject50") // Пагинация. проверка количества страниц
    public void checkNumberList50(){

        WebElement countListObject = driver.findElement(By.xpath("//option[@selected='selected']"));
        Integer countListObj =new  Integer (countListObject.getText());

        WebElement paginationC = driver.findElement(By.xpath("//a[@disabled='disabled']/span"));
        Integer paginationCount = new Integer(paginationC.getText());

        if (numberCountTableObjectTotal()/countListObj+1 == paginationCount) {
            System.out.println("Пагинация. Количество страниц пагинации верно при значении списка объектов=50");
        } else {
            Assert.fail("Пагинация. Количество страниц пагинации НЕ верно при значении списка объектов=50");
        }
    }

}
