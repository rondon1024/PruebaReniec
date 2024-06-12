package com.bdd.FrontEnd;

import com.bdd.FrontEnd.exceptions.FrontEndException;
import com.bdd.FrontEnd.utility.ExtentReportUtil;
import com.bdd.FrontEnd.utility.GenerateWord;
import com.bdd.FrontEnd.utility.ExcelReader;
import com.bdd.FrontEnd.utility.Sleeper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.time.Duration;
import java.util.*;


public class BaseClass {

    public static WebDriver driver;
    public BaseClass() {driver = Hook.getDriver();}

    private static final String EXCEL_WEB = "excel/Data-FrontEnd.xlsx";
    private static final String EXCEL_SHEET = "Data";

    private static final String LOADER = "//div[@class='splash']";
    private static final String MODAL_ERROR = "//mat-dialog-container[@id='mat-dialog-0']";

    public static List<HashMap<String, String>> getData() throws Throwable {
        return ExcelReader.data(EXCEL_WEB, EXCEL_SHEET);
    }

    static GenerateWord generateWord = new GenerateWord();
    private static final long MAX_WAIT_TIME_SECONDS = 90;
    private static final long NEXT_TRY_TIME_MILISECONDS = 200;

    public static void sendKeyValue(WebDriver driver, String key, String locator, String value) throws Throwable {
        try {

            switch (key.toLowerCase()) {

                case "id":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.id(locator))).sendKeys(value);
                    break;

                case "name":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.name(locator))).sendKeys(value);
                    break;

                case "css":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator))).sendKeys(value);
                    break;

                case "linktext":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.linkText(locator))).sendKeys(value);
                    break;

                case "xpath":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).sendKeys(value);
                    break;
            }

        } catch (Throwable t) {

            generateWord.sendText("No se encontró el elemento : " + locator);

            generateWord.addImageToWord(driver);

            handleError(driver, "", "No se encontro el elemento : " + locator);

            driver.close();

            throw t;
        }
    }

    public static void click(WebDriver driver, String key, String locator) throws Throwable {
        try {

            switch (key.toLowerCase()) {

                case "id":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.id(locator))).click();
                    break;

                case "name":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.name(locator))).click();
                    break;

                case "css":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(locator))).click();
                    break;

                case "linktext":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.linkText(locator))).click();
                    break;

                case "xpath":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).click();
                    break;

            }

        } catch (Throwable t) {

            generateWord.sendText("Error : No se encontró el elemento : " + locator);

            generateWord.addImageToWord(driver);

            handleError(driver, "", "No se encontro el elemento : " + locator);

            driver.close();

            throw t;
        }
    }

    public static void clear(WebDriver driver, String key, String locator) throws Throwable {
        try {

            switch (key.toLowerCase()) {

                case "id":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.id(locator))).clear();
                    break;

                case "name":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.name(locator))).clear();
                    break;

                case "css":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.elementToBeClickable(By.cssSelector(locator))).clear();
                    break;

                case "linktext":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.linkText(locator))).clear();
                    break;

                case "xpath":
                    new WebDriverWait(driver, 40, 80).until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator))).clear();
                    break;
            }
        } catch (Throwable t) {

            generateWord.sendText("Error : No se encontro el elemento : " + locator);

            generateWord.addImageToWord(driver);

            handleError(driver, "", "No se encontro el elemento : " + locator);

            driver.close();

            throw t;
        }
    }

    public static void selectByVisibleText(WebDriver driver, String locator, String value) throws Throwable {

        String found = value;

        Select select = new Select(driver.findElement(By.id(locator)));

        List<WebElement> options = select.getOptions();

        if (CollectionUtils.isNotEmpty(options)) {

            for (WebElement option : options) {

                if (StringUtils.equalsIgnoreCase(option.getText(), locator)) {

                    found = option.getText();

                    break;
                }
            }
        }

        if (found == null) {

            handleError(driver, "", "No se encontro el elemento : " + locator);

            generateWord.sendText("No se encontró el elemento : " + locator);

            generateWord.addImageToWord(driver);
        }

        select.selectByVisibleText(found);
    }

    public static String getNumberOfCaracter(String caracter) {

        char[] caracterArray = caracter.toCharArray();

        String number = StringUtils.EMPTY;

        for (int i = 0; i < caracterArray.length; i++) {

            if (Character.isDigit(caracterArray[i])) {

                number += caracterArray[i];

            }
        }
        return number;
    }

    public static Exception handleError(WebDriver driver, String codigo, String msg) throws Throwable {
        stepWarning(driver, msg);
        System.out.println(msg);
        return new FrontEndException(StringUtils.trimToEmpty(codigo), msg);
    }

    public static String waitForElementNotVisible(WebDriver driver, String key, String locator) throws Throwable {
        if ((driver == null) || (locator == null) || locator.isEmpty()) { return "[Error : Mal uso del metodo waitForElementNotVisible()]"; }
        try {
            switch (key.toLowerCase()) {

                case "id":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.invisibilityOfElementLocated(By.id(locator)));
                    break;

                case "name":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.invisibilityOfElementLocated(By.name(locator)));
                    break;

                case "css":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(locator)));
                    break;

                case "linktext":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.invisibilityOfElementLocated(By.linkText(locator)));
                    break;

                case "xpath":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(locator)));
                    break;
            }
            return null;
        } catch (TimeoutException e) {
            generateWord.sendText("No se encontró el elemento : " + locator);
            generateWord.addImageToWord(driver);
            handleError(driver, "", "No se encontro el elemento : " + locator);
            driver.close();
            throw e;
        }
    }

    public static void writeHowHuman(WebDriver driver, String locator, String text) {

        Random r = new Random();

        WebElement webElement = driver.findElement(By.id(locator));

        for (int i = 0; i < text.length(); i++) {

            try {

                Thread.sleep((int) (r.nextGaussian() * 12 + 110));

            } catch (InterruptedException ignored) {
            }

            String s = new StringBuilder().append(text.charAt(i)).toString();

            webElement.sendKeys(s);
        }
    }

    public static void selectByValue(WebDriver driver, String key, String locator, String value) throws Throwable {
        String found = value;
        Select select ;
        try {
            switch (key.toLowerCase()) {
                case "id":
                    select = new Select(driver.findElement(By.id(locator)));
                    break;
                case "name":
                    select = new Select(driver.findElement(By.name(locator)));
                    break;
                case "css":
                    select = new Select(driver.findElement(By.cssSelector(locator)));
                    break;
                case "linktext":
                    select = new Select(driver.findElement(By.linkText(locator)));
                    break;
                case "xpath":
                    select = new Select(driver.findElement(By.xpath(locator)));
                    break;
                default:
                    select = null;
            }
        } catch (Throwable t) {
            stepWarning(driver, "No encontró el elemento " + locator);
            throw new FrontEndException("BC-001", "No encontró el elemento " + locator);
        }


        select.selectByValue(found);
    }

    public static String getText(WebDriver driver, String key, String locator) throws Throwable {
        try{
            WebElement webElement = null;
            switch (key.toLowerCase()) {
                case "id":
                    webElement = driver.findElement(By.id(locator));
                    break;
                case "name":
                    webElement = driver.findElement(By.name(locator));
                    break;
                case "css":
                    webElement = driver.findElement(By.cssSelector(locator));
                    break;
                case "linktext":
                    webElement = driver.findElement(By.linkText(locator));
                    break;
                case "xpath":
                    webElement = driver.findElement(By.xpath(locator));
                    break;
                default:
                    new FrontEndException("BC-005", "No se encontro :" + webElement);
            }
            return StringUtils.trimToEmpty(webElement.getText());

        }catch (Throwable t){
            generateWord.sendText("No se encontró el elemento : " + locator);
            generateWord.addImageToWord(driver);
            handleError(driver, "", "No se encontro el elemento : " + locator);
            throw t;
        }
    }

    public static String getAttribute(WebDriver driver, String key, String locator, String attributeName) throws Throwable {
        try{
            WebElement webElement = null;
            switch (key.toLowerCase()) {
                case "id":
                    webElement = driver.findElement(By.id(locator));
                    break;
                case "name":
                    webElement = driver.findElement(By.name(locator));
                    break;
                case "css":
                    webElement = driver.findElement(By.cssSelector(locator));
                    break;
                case "linktext":
                    webElement = driver.findElement(By.linkText(locator));
                    break;
                case "xpath":
                    webElement = driver.findElement(By.xpath(locator));
                    break;
                default:
                    new FrontEndException("BC-005", "No se encontro :" + webElement);
            }
            return StringUtils.trimToEmpty(webElement.getAttribute(attributeName));

        }catch (Throwable t){
            generateWord.sendText("No se encontró el elemento : " + locator);
            generateWord.addImageToWord(driver);
            handleError(driver, "", "No se encontro el elemento : " + locator);
            throw t;
        }
    }

    public Boolean isEnabled(WebDriver driver, String key, String locator) throws Throwable {
        try{
            WebElement webElement;
            switch (key.toLowerCase()) {
                case "id":
                    webElement = driver.findElement(By.id(locator));
                    break;
                case "name":
                    webElement = driver.findElement(By.name(locator));
                    break;
                case "css":
                    webElement = driver.findElement(By.cssSelector(locator));
                    break;
                case "linktext":
                    webElement = driver.findElement(By.linkText(locator));
                    break;
                case "xpath":
                    webElement = driver.findElement(By.xpath(locator));
                    break;
                default:
                    webElement = null;
            }

            return webElement.isEnabled();
        }catch (Throwable t){
            generateWord.sendText("No se encontró el elemento : " + locator);
            generateWord.addImageToWord(driver);
            handleError(driver, "", "No se encontro el elemento : " + locator);
            driver.close();
            throw t;
        }
    }

    public static Boolean isDisplayed(WebDriver driver, String key, String locator) throws Throwable {
        try{
            WebElement webElement;
            switch (key.toLowerCase()) {
                case "id":
                    webElement = driver.findElement(By.id(locator));
                    break;
                case "name":
                    webElement = driver.findElement(By.name(locator));
                    break;
                case "css":
                    webElement = driver.findElement(By.cssSelector(locator));
                    break;
                case "linktext":
                    webElement = driver.findElement(By.linkText(locator));
                    break;
                case "xpath":
                    webElement = driver.findElement(By.xpath(locator));
                    break;
                default:
                    webElement = null;
            }

            return webElement.isDisplayed();

        }catch (Throwable t){
            generateWord.sendText("No se encontró el elemento : " + locator);
            generateWord.addImageToWord(driver);
            handleError(driver, "", "No se encontro el elemento : " + locator);
            driver.close();
            throw t;
        }
    }

    public static Boolean isDisplayed(WebDriver driver, String key, String locator, Boolean continueTest) throws Throwable {
        try{
            WebElement webElement;
            switch (key.toLowerCase()) {
                case "id":
                    webElement = driver.findElement(By.id(locator));
                    break;
                case "name":
                    webElement = driver.findElement(By.name(locator));
                    break;
                case "css":
                    webElement = driver.findElement(By.cssSelector(locator));
                    break;
                case "linktext":
                    webElement = driver.findElement(By.linkText(locator));
                    break;
                case "xpath":
                    webElement = driver.findElement(By.xpath(locator));
                    break;
                default:
                    webElement = null;
            }

            return webElement.isDisplayed();

        }catch (Throwable t){
            String msgContinuesTest = "";
            if(continueTest){msgContinuesTest = " .....Se continua con el Test";}
            generateWord.sendText("No se encontró el elemento : " + locator + msgContinuesTest);
            generateWord.addImageToWord(driver);
            handleError(driver, "", "No se encontro el elemento : " + locator + msgContinuesTest);
            if(!continueTest){driver.close();}
            //throw t;
            return false;
        }
    }


    public Boolean isSelected(WebDriver driver, String key, String locator) throws Throwable {
        try{
            WebElement webElement;
            switch (key.toLowerCase()) {
                case "id":
                    webElement = driver.findElement(By.id(locator));
                    break;
                case "name":
                    webElement = driver.findElement(By.name(locator));
                    break;
                case "css":
                    webElement = driver.findElement(By.cssSelector(locator));
                    break;
                case "linktext":
                    webElement = driver.findElement(By.linkText(locator));
                    break;
                case "xpath":
                    webElement = driver.findElement(By.xpath(locator));
                    break;
                default:
                    webElement = null;
            }

            return webElement.isSelected();

        }catch (Throwable t){
            generateWord.sendText("No se encontró el elemento : " + locator);
            generateWord.addImageToWord(driver);
            handleError(driver, "", "No se encontro el elemento : " + locator);
            driver.close();
            throw t;
        }
    }

    public static void scrollBar(WebDriver driver, int size) {
        JavascriptExecutor ev = (JavascriptExecutor) driver;
        ev.executeScript("window.scrollBy(0, " + size + ")");
    }

    public static void scrollBarElement(WebDriver driver, String idElement, int size) {
        JavascriptExecutor ev = (JavascriptExecutor) driver;
        System.out.println("document.getElementByName('" + idElement + "').scrollBy(0, " + size + ")");
        ev.executeScript("document.getElementByName('" + idElement + "').scrollBy(0, " + size + ")");
    }

    public static void sendTextJS(WebDriver driver, String elementId, String msg) {
        JavascriptExecutor ev = (JavascriptExecutor) driver;
        ev.executeScript("document.getElementById('" + elementId + "').setAttribute('value', '" + msg + "');");
    }

    public static void sleep(int milisegundos) {
        Sleeper.Sleep(milisegundos);
    }

    public static void stepPass(WebDriver driver, String descripcion) throws Throwable {
        try {
            ExtentReportUtil.INSTANCE.stepPass(driver, descripcion);
        } catch (Throwable t) {
            System.out.println(t.getStackTrace());
            throw t;
        }
    }

    public static void stepWarning(WebDriver driver, String descripcion) throws Throwable {
        try {
            ExtentReportUtil.INSTANCE.stepWarning(driver, descripcion);
        } catch (Throwable t) {
            System.out.println(t.getStackTrace());
            throw t;
        }
    }

    public static void stepFailNoShoot(String descripcion) throws Throwable {
        try {
            ExtentReportUtil.INSTANCE.stepFailNoShoot(descripcion);
        }catch (Throwable t){
            System.out.println(Arrays.toString(t.getStackTrace()));
            throw t;
        }
    }

    public static void stepFail(WebDriver driver, String descripcion) throws Throwable {
        try {
            ExtentReportUtil.INSTANCE.stepFail(driver, descripcion);
        } catch (Throwable t) {
            System.out.println(t.getStackTrace());
            throw t;
        }
    }

    public static void stepError(WebDriver driver, String descripcion) throws Throwable {
        try {
            ExtentReportUtil.INSTANCE.stepError(driver, descripcion);
        } catch (Throwable t) {
            System.out.println(t.getStackTrace());
            throw t;
        }
    }

    public static void stepInfo(WebDriver driver, String descripcion) throws Throwable {
        try {
            ExtentReportUtil.INSTANCE.stepInfo(driver, descripcion);
        } catch (Throwable t) {
            System.out.println(t.getStackTrace());
            throw t;
        }
    }
    public static void wait(int milliSeconds){
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    //NELSON METODOS
    public static void cerrarAppError(WebDriver driver){
        driver.close();
    }

    public static void validaExistenciaElementoClick(WebDriver driver, String locator){
        if(!driver.findElements(By.xpath(locator)).isEmpty()){
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            driver.findElement(By.xpath(locator)).click();
        }
    }

    public static void mensajeImprimirWord(WebDriver driver,String mensajePersonalisado){
        try {
            generateWord.sendText(mensajePersonalisado);
            generateWord.addImageToWord(driver);
        }catch (Exception t){
            generateWord.sendText("Error al imprimir en el word");
            generateWord.addImageToWord(driver);
            driver.close();
            throw t;
        }
    }

    public static void validaExistenciaElemento(WebDriver driver, String locator){
        if(!driver.findElements(By.xpath(locator)).isEmpty()){
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            System.out.println("Elemento si es reconocido en la pantalla");
        }
        else{
            System.out.println("Elemento no existe");
            mensajeImprimirWord(driver, "Elemento no existe");
            driver.close();
        }
    }

    public static void exceptionErrorNotExpected(WebDriver driver, String locator, String mensaje) {
        if(!driver.findElements(By.xpath(locator)).isEmpty()){
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            wait(2000);
            System.out.println("Errores que se repiten en varios CP - Close");
            mensajeImprimirWord(driver,mensaje);
            driver.close();
        }
    }

    public static void exceptionErrorJustOnce(WebDriver driver, String locator, String equals) throws Throwable{
        if(!driver.findElements(By.xpath(locator)).isEmpty()){
            System.out.println("objeto si existe en la pantalla");
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            if(getCadElement(driver,"xpath", locator).contains(equals)){
                System.out.println("Errores que solo suceden en este CP - Close");
                mensajeImprimirWord(driver,"Los servicios no estan cargando correctamente");
                driver.close();
            }
        }
    }

    //dar click hasta que no aparezca elemento
    public static void clickUntilNotExists(WebDriver driver, String locator) throws  Throwable{
        while (!driver.findElements(By.xpath(locator)).isEmpty()){
            WebDriverWait wait = new WebDriverWait(driver, 60);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            driver.findElement(By.xpath(locator)).click();
        }
    }



    public static void tiempoDeEsperaLocatorVisible(WebDriver driver, String locator){
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
    }

    public static String getCadElement(WebDriver driver, String key, String locator) throws Throwable {
        try{
            String cad = "";
            switch (key.toLowerCase()) {
                case "id":
                    cad = driver.findElement(By.id(locator)).getText();
                    break;
                case "name":
                    cad = driver.findElement(By.name(locator)).getText();
                    break;
                case "css":
                    cad = driver.findElement(By.cssSelector(locator)).getText();
                    break;
                case "linktext":
                    cad = driver.findElement(By.linkText(locator)).getText();
                    break;
                case "xpath":
                    cad = driver.findElement(By.xpath(locator)).getText();
                    break;
                default:
                    new FrontEndException("BC-005", "No se encontro :" + cad);
            }
            return cad;

        }catch (Throwable t){
            generateWord.sendText("No se encontró el elemento : " + locator);
            generateWord.addImageToWord(driver);
            handleError(driver, "", "No se encontro el elemento : " + locator);
            throw t;
        }
    }

    public static void scrollDown(AppiumDriver<MobileElement> driver){
        Dimension dimension = driver.manage().window().getSize();

        Double scrollHeightStart = dimension.getHeight()*0.5;
        int scrollStart = scrollHeightStart.intValue();

        Double scrollHeightEnd = dimension.getHeight()*0.2;
        int scrollEnd = scrollHeightEnd.intValue();

        new TouchAction((PerformsTouchActions) driver)
                .press(PointOption.point(0, scrollStart))
                .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(2)))
                .moveTo(PointOption.point(0,scrollEnd))
                .release().perform();
    }

    public static  void validaPantallaNoDeseadas(AppiumDriver<MobileElement> driver, String xpathElement, String msjPersonalizado) throws Exception{

        switch (driver.findElement(By.xpath(xpathElement)).getText()){
            case "¡Oh no, algo sucedió!":
                ExtentReportUtil.INSTANCE.stepPass(driver,msjPersonalizado);
                generateWord.sendText(msjPersonalizado);
                generateWord.addImageToWord(driver);
                driver.quit();
            default:
                break;
            case "":
        }
    }

    public static void errorDeCargaPantalla(WebDriver driver,  String msjPersonalizado) throws Exception{
        generateWord.sendText(msjPersonalizado);
        generateWord.addImageToWord(driver);
        driver.close();
    }

    public static String waitForElementVisible(WebDriver driver, String key, String locator) throws Throwable {
        if ((driver == null) || (locator == null) || locator.isEmpty()) { return "[Error : Mal uso del metodo waitForElementNotVisible()]"; }
        try {
            switch (key.toLowerCase()) {

                case "id":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.visibilityOfElementLocated(By.id(locator)));
                    break;

                case "name":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.visibilityOfElementLocated(By.name(locator)));
                    break;

                case "css":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(locator)));
                    break;

                case "linktext":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.visibilityOfElementLocated(By.linkText(locator)));
                    break;

                case "xpath":
                    (new WebDriverWait(driver, MAX_WAIT_TIME_SECONDS, NEXT_TRY_TIME_MILISECONDS)).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
                    break;
            }
            return null;
        } catch (TimeoutException e) {
            generateWord.sendText("No se encontró el elemento : " + locator);
            generateWord.addImageToWord(driver);
            handleError(driver, "", "No se encontro el elemento : " + locator);
            driver.close();
            throw e;
        }
    }

    public static boolean compararTexto(WebDriver driver, String locator, String mensajeTitulo, String mensajePersonalisado,Boolean continueTest) throws Throwable{
        try {
            Boolean isPresent = driver.findElements(By.xpath(locator)).size() > 0;
            if (isPresent.equals(true)){
                String TituloPantalla = driver.findElement(By.xpath(locator)).getText();
                if (TituloPantalla.contains(mensajeTitulo)){
                    generateWord.sendText(mensajePersonalisado);
                    generateWord.addImageToWord(driver);
                }//else {
                // generateWord.sendText("No se encontro: "+mensajePersonalisado);
                //generateWord.addImageToWord(driver);
                //driver.close();
                //}
            }else   {
                String msgContinuesTest = "";
                if(continueTest){msgContinuesTest = " .....Se continua con el Test";}
            }
        }catch (Exception t){
            generateWord.sendText("No se encontró el elemento");
            generateWord.addImageToWord(driver);
            driver.close();
            throw t;
        }
        return false;
    }

    //Bloque de canales
    public static void elegirAlAzarBloqueDeCanales(WebDriver driver, String locator, String locator1, String locator2)throws Throwable{
        Boolean isPresent = driver.findElements(By.xpath(locator)).size() > 0;
        if (isPresent.equals(true)){
            Random r = new Random();
            int valor = r.nextInt(3);
            switch (valor) {
                case 0:
                    Boolean isPresent1 = driver.findElements(By.xpath(locator)).size() > 0;
                    if (isPresent1.equals(true)){
                        BaseClass.click(driver,"xpath",locator);
                        BaseClass.mensajeImprimirWord(driver,"Se selecciona bloque HBO");
                    }else {
                        BaseClass.mensajeImprimirWord(driver,"La opción bloque HBO no esta disponible");
                        driver.close();
                    }
                    break;
                case 1:
                    Boolean isPresent2 = driver.findElements(By.xpath(locator1)).size() > 0;
                    if (isPresent2.equals(true)){
                        BaseClass.click(driver,"xpath",locator1);
                        BaseClass.mensajeImprimirWord(driver,"Se selecciona bloque hot pack");
                    }else {
                        BaseClass.mensajeImprimirWord(driver,"La opción bloque HOT no esta disponible");
                        driver.close();
                    }
                    break;
                case 2:
                    Boolean isPresent3 = driver.findElements(By.xpath(locator2)).size() > 0;
                    if (isPresent3.equals(true)){
                        BaseClass.click(driver,"xpath",locator2);
                        BaseClass.mensajeImprimirWord(driver,"Se selecciona bloque star");
                    }else {
                        BaseClass.mensajeImprimirWord(driver,"La opción bloque STAR no esta disponible");
                        driver.close();
                    }
                default:
                    BaseClass.mensajeImprimirWord(driver,"Valor fuera del rango");
            }

        }else {
            Boolean isPresent3 = driver.findElements(By.xpath(locator)).size() > 0;
            if (isPresent3.equals(false)) {
                BaseClass.mensajeImprimirWord(driver,"Tienes un pedido de bloque de canales en proceso, al finalizar podrás solicitar uno nuevo.");
                driver.close();
            }else {
                BaseClass.mensajeImprimirWord(driver,"No se mostraron productos a elegir, inténtalo nuevamente.");
                driver.close();
            }
        }
    }

    public static void elegirAlAzarDecoYRepetidor(WebDriver driver, String locator, String locator1)throws Throwable{
        Boolean isPresent = driver.findElements(By.xpath(locator)).size() > 0;
        if (isPresent.equals(true)){
            Random r = new Random();
            int valor = r.nextInt(2);
            switch (valor) {
                case 0:
                    Boolean isPresent1 = driver.findElements(By.xpath(locator)).size() > 0;
                    if (isPresent1.equals(true)){
                        BaseClass.click(driver,"xpath",locator);
                        BaseClass.mensajeImprimirWord(driver,"Se selecciona opción REPETIDOR");
                    }else {
                        BaseClass.mensajeImprimirWord(driver,"La opción PUNTO ADICIONAL SMART HD no esta disponible");
                        driver.close();
                    }
                    break;
                case 1:
                    Boolean isPresent2 = driver.findElements(By.xpath(locator1)).size() > 0;
                    if (isPresent2.equals(true)){
                        BaseClass.click(driver,"xpath",locator1);
                        BaseClass.mensajeImprimirWord(driver,"Se selecciona opción PUNTO ADICIONAL");

                    }else {
                        BaseClass.mensajeImprimirWord(driver,"La opción REPETIDOR SMART WIFI no esta disponible");
                        driver.close();
                    }
                    break;
                default:
                    BaseClass.mensajeImprimirWord(driver,"Valor fuera del rango");
            }

        }else {
            Boolean isPresent3 = driver.findElements(By.xpath(locator)).size() > 0;
            if (isPresent3.equals(false)) {
                BaseClass.mensajeImprimirWord(driver,"Tienes un pedido de Decos/Repetidores en proceso, al finalizar podrás solicitar uno nuevo.");
                driver.close();
            }else {
                BaseClass.mensajeImprimirWord(driver,"No se mostraron productos a elegir, inténtalo nuevamente.");
                driver.close();
            }
        }
    }

    //verifica el tipo de linea
    public static void validarTipoDeLinea(WebDriver driver, String locator) throws Exception{
        try {
            //variables
            Boolean isPresent_Postpago = driver.findElements(By.xpath(locator)).size() > 0;
            Boolean isPresent2 = driver.findElements(By.xpath(locator)).size() > 0;

            //linea postpago
            if (isPresent_Postpago.equals(true)){
                String POSTPAGO = driver.findElement(By.xpath(locator)).getText();
                if (POSTPAGO.contains("Postpago")){
                    generateWord.sendText("Esta en el Home de la línea: "+POSTPAGO);
                    generateWord.addImageToWord(driver);
                }
            }
        }catch (Exception t){
            generateWord.sendText("No se encontró el elemento");
            generateWord.addImageToWord(driver);
            //handleError(driver, "", "No se encontro el elemento : " + xpathElement);
            driver.close();
            throw t;
        }
    }
    //verifica el tipo de linea prepago
    public static void validarTipoDeLineaPrepago(WebDriver driver, String locator, String subLocator)throws Throwable{
        try {
            Boolean isPresent = driver.findElements(By.xpath(locator)).size() > 0;
            if (isPresent.equals(true)){
                String PREPAGO = driver.findElement(By.xpath(locator)).getText();
                if (PREPAGO.contains("Prepago")){
                    generateWord.sendText("Esta en el Home de la línea: "+PREPAGO);
                    generateWord.addImageToWord(driver);
                }
                else{
                    String POSTPAGO = driver.findElement(By.xpath(locator)).getText();
                    if (POSTPAGO.contains("Postpago")){
                        mensajeImprimirWord(driver,"se encuentra en el Home");
                        wait(2000);
                        driver.findElement(By.xpath(locator)).click();
                        wait(2000);
                        driver.findElement(By.xpath(subLocator)).click();
                        wait(2000);
                        mensajeImprimirWord(driver,"Esta en el Home de la línea: Prepago");
                    }
                }
            }

        }catch (Exception t){
            generateWord.sendText("No se encontró el elemento");
            generateWord.addImageToWord(driver);
            driver.close();
            throw t;
        }
    }

    //validar linea HOGAR
    public static void validarLineaHogar(WebDriver driver,String locator)throws Throwable{
        try {
            Boolean isPresent_Hogar = driver.findElements(By.xpath(locator)).size() > 0;
            //LINEA HOGAR
            //linea trio
            if (isPresent_Hogar.equals(true)){
                String TRIO = driver.findElement(By.xpath(locator)).getText();
                if (TRIO.contains("Trio")){
                    generateWord.sendText("Esta en el Home de la línea: "+TRIO);
                    generateWord.addImageToWord(driver);
                    //linea duo
                }else{
                    String DUO = driver.findElement(By.xpath(locator)).getText();
                    if (DUO.contains("Duo")){
                        generateWord.sendText("Esta en el Home de la línea: "+DUO);
                        generateWord.addImageToWord(driver);
                    }
                }
            }
        }catch (Exception t){
            generateWord.sendText("No se encontró el elemento");
            generateWord.addImageToWord(driver);
            //handleError(driver, "", "No se encontro el elemento : " + xpathElement);
            driver.close();
            throw t;
        }
    }


    //validar linea MT
    public static void validarLineaMT (WebDriver driver, String locator,String subLocator) throws Throwable{
        try {
            Boolean isPresent_MT = driver.findElements(By.xpath(locator)).size() > 0;

            String MT = driver.findElement(By.xpath(locator)).getText();
            String TRIO = driver.findElement(By.xpath(locator)).getText();
            String PSPG = driver.findElement(By.xpath(locator)).getText();

            if (isPresent_MT.equals(true)){
                if (MT.contains("Movistar Total")){
                    generateWord.sendText("Esta en el Home de la línea: "+MT);
                    generateWord.addImageToWord(driver);
                }
                else if (TRIO.contains("Trio")){
                    click(driver,"xpath",locator);
                    String subLoct = driver.findElement(By.xpath(subLocator)).getText();
                    if (subLoct.contains("Movistar Total")){
                        click(driver,"xpath",subLocator);
                        Thread.sleep(3000);
                    }else {
                        generateWord.sendText("No se encontró la opción MT");
                        generateWord.addImageToWord(driver);
                        driver.close();
                    }
                }else if (PSPG.contains("Postpago")){
                    click(driver,"xpath",locator);
                    String subLoct = driver.findElement(By.xpath(subLocator)).getText();
                    if (subLoct.contains("Movistar Total")){
                        click(driver,"xpath",subLocator);
                        Thread.sleep(3000);
                    }else{
                        generateWord.sendText("No se encontró la opción MT");
                        generateWord.addImageToWord(driver);
                        driver.close();
                    }
                }
            }

        }catch (Exception t){
            generateWord.sendText("No se encontró el elemento");
            generateWord.addImageToWord(driver);
            //handleError(driver, "", "No se encontro el elemento : " + xpathElement);
            driver.close();
            throw t;
        }
    }

    //verifica el tipos de servicios
    public static void validarTipoDeServicios(WebDriver driver, String locator) throws Exception{
        try {
            //variables
            Boolean isPresent_BM = driver.findElements(By.xpath(locator)).size() > 0;
            Boolean isPresent_PG = driver.findElements(By.xpath(locator)).size() > 0;
            Boolean isPresent_CP = driver.findElements(By.xpath(locator)).size() > 0;

            //boton servicio beneficio movistar
            if (isPresent_BM.equals(true)){
                String BENEFICIO_MOVISTAR = driver.findElement(By.xpath(locator)).getText();
                if (BENEFICIO_MOVISTAR.contains("Beneficios Movistar")){
                    generateWord.sendText("Se da clic al servicio Beneficio Movistar");
                    generateWord.addImageToWord(driver);
                }
            }
            //boton servicio PASA GIGAS
            else if (isPresent_PG.equals(true)){
                String PASA_GIGAS = driver.findElement(By.xpath(locator)).getText();
                if (PASA_GIGAS.contains("Pasa gigas")) {
                    generateWord.sendText("Se da clic al servicio Pasa Gigas");
                    generateWord.addImageToWord(driver);
                }
                //boton servicio CAMBIO DE PLAN
            }else if (isPresent_CP.equals(true)){
                String CAMBIO_PLAN = driver.findElement(By.xpath(locator)).getText();
                if (CAMBIO_PLAN.contains("Cambiar mi plan")) {
                    generateWord.sendText("Se da clic al servicio Cambiar mi plan ");
                    generateWord.addImageToWord(driver);
                }
            }
        }catch (Exception t){
            generateWord.sendText("No se encontró ningún servicio");
            generateWord.addImageToWord(driver);
            driver.close();
            throw t;
        }
    }

   //FOOTER
    public static void validarFooter(WebDriver driver, String locator)throws Exception{
        try {
            //variables
            Boolean isPresent_RESUMEN = driver.findElements(By.xpath(locator)).size() > 0;
            Boolean isPresent_DESCUBRE = driver.findElements(By.xpath(locator)).size() > 0;
            Boolean isPresent_JUEGA = driver.findElements(By.xpath(locator)).size() > 0;

            //boton RESUMEN
            if (isPresent_RESUMEN.equals(true)){
                String RESUMEN = driver.findElement(By.xpath(locator)).getText();
                if (RESUMEN.contains("Resumen")) {
                    generateWord.sendText("Se da clic a la opción RESUMEN");
                    generateWord.addImageToWord(driver);
                }
                //boton DESCUBRE
            }else if (isPresent_DESCUBRE.equals(true)){
                String DESCUBRE = driver.findElement(By.xpath(locator)).getText();
                if (DESCUBRE.contains("Descubre")) {
                    generateWord.sendText("Se da clic a la opción DESCUBRE");
                    generateWord.addImageToWord(driver);
                }
                //boton JUEGA
            }else if (isPresent_JUEGA.equals(true)){
                String JUEGA = driver.findElement(By.xpath(locator)).getText();
                if (JUEGA.contains("Juega")){
                    generateWord.sendText("Se da clic a la opción JUEGA");
                    generateWord.addImageToWord(driver);
                }

            }
        }catch (Exception t){
            generateWord.sendText("No se encontró ningún footer");
            generateWord.addImageToWord(driver);
            driver.close();
            throw t;
        }
    }

    public static void swipePanelRightTowardLeft(WebDriver driver,WebElement panel){
            Dimension dimension = panel.getSize();

            int anchor = panel.getSize().getHeight()/2;

            Double ScreenWidthStart = dimension.getWidth()* 0.8;
            int scrollStart = ScreenWidthStart.intValue();

            Double ScreenWidthEnd = dimension.getWidth()* 0.2;
            int scrollEnd = ScreenWidthEnd.intValue();

            new TouchAction((PerformsTouchActions) driver)
                    .press(PointOption.point(scrollStart, anchor))
                    .waitAction(WaitOptions.waitOptions(Duration.ofSeconds(1)))
                    .moveTo(PointOption.point(scrollEnd, anchor))
                    .release().perform();
            sleep(2000);

    }

    public static void elegirAlAzarTipoDePaquetes(WebDriver driver, String locator, String locator1,String locator2,String locator3)throws Throwable{
        Boolean isPresent = driver.findElements(By.xpath(locator)).size() > 0;
        if (isPresent.equals(true)){
            Random r = new Random();
            int valor = r.nextInt(4);
            //aqui (valor) deberia estar asi
            switch (2) {
                case 0:
                    Boolean isPresent1 = driver.findElements(By.xpath(locator)).size() > 0;
                    if (isPresent1.equals(true)){
                        BaseClass.click(driver,"xpath",locator);
                        BaseClass.mensajeImprimirWord(driver,"Se selecciona tipo de paquete: MIXTOS");
                    }else {
                        BaseClass.mensajeImprimirWord(driver,"La opción no esta disponible");
                        driver.close();
                    }
                    break;
                case 1:
                    Boolean isPresent2 = driver.findElements(By.xpath(locator1)).size() > 0;
                    if (isPresent2.equals(true)){
                        BaseClass.click(driver,"xpath",locator1);
                        BaseClass.mensajeImprimirWord(driver,"Se selecciona tipo de paquete: DATOS");
                    }else {
                        BaseClass.mensajeImprimirWord(driver,"La opción no esta disponible");
                        driver.close();
                    }
                    break;
                case 2:
                    Boolean isPresent3 = driver.findElements(By.xpath(locator2)).size() > 0;
                    if (isPresent3.equals(true)){
                        BaseClass.click(driver,"xpath",locator2);
                        BaseClass.mensajeImprimirWord(driver,"Se selecciona tipo de paquete: LLAMADAS");
                    }else {
                        BaseClass.mensajeImprimirWord(driver,"La opción no esta disponible");
                        driver.close();
                    }
                    break;
                case 3:
                    Boolean isPresent4 = driver.findElements(By.xpath(locator3)).size() > 0;
                    if (isPresent4.equals(true)){
                        BaseClass.click(driver,"xpath",locator3);
                        BaseClass.mensajeImprimirWord(driver,"Se selecciona tipo de paquete: SMS");
                    }else {
                        BaseClass.mensajeImprimirWord(driver,"La opción no esta disponible");
                        driver.close();
                    }
                    break;
                default:
                    BaseClass.mensajeImprimirWord(driver,"Valor fuera del rango");
            }

        }else {
            Boolean isPresent3 = driver.findElements(By.xpath(locator)).size() > 0;
            if (isPresent3.equals(false)) {
                BaseClass.mensajeImprimirWord(driver,"Algo salio mal");
                driver.close();
            }else {
                BaseClass.mensajeImprimirWord(driver,"No se mostraron productos a elegir, inténtalo nuevamente.");
                driver.close();
            }
        }
    }

       public static void ScrollAction(String[] args,WebDriver driver, String locator) {
            // identify element
            WebElement n =driver.findElement(By.xpath(locator));
            // Javascript executor
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", n);
        }

    private static String shadowRootQuerySelector(String elementBase, String pathCss) {
             return "return document.querySelector(" + "\"" + elementBase + "\").shadowRoot.querySelector(\"" + pathCss + "\")";
    }
    public static WebElement getDirectionOptionFromList(WebDriver driver, String appBase, String locationName) {
        JavascriptExecutor ex = (JavascriptExecutor)driver;
        String query = "return Array.from(document.querySelector('"+appBase+"').shadowRoot.querySelectorAll('li')).find(x => x.innerText == '"+locationName+"')";
        WebElement element = (WebElement)ex.executeScript(query, new Object[0]);
        if (element == null) {
            System.out.println("No se ubicó elemento.");

        } else {
            System.out.println("Se ubicó elemento.");
        }
        return element;
    }
    public static WebElement getButtonFromTdpstbutton(WebDriver driver, String textInButton) {
        JavascriptExecutor ex = (JavascriptExecutor)driver;
        String query = "return Array.from(document.querySelector('tdp-st-button').shadowRoot.querySelectorAll('button')).find(x => x.innerText == '"+textInButton+"')";
        WebElement element = (WebElement)ex.executeScript(query);
        if (element == null) {
            System.out.println("No se ubicó elemento.");

        } else {
            System.out.println("Se ubicó elemento.");
        }
        return element;
    }
    public static WebElement getInputFromTdpstinputtextControl(WebDriver driver, String formControlName) {
        JavascriptExecutor ex = (JavascriptExecutor)driver;


       String query = "return document.querySelector('tdp-st-input-text[formcontrolname=\"" + formControlName + "\"]').shadowRoot.querySelector('input')";

        //SE DEBE CORREGIR CON EL SIGUIENTE EN UAT4:
       // String query = "return document.querySelector('body > app-root > app-agenda-mt > div.tdp-container.contentMfShedule > app-lista-contactos > div > div > div.tdp-mt-5 > form > div:nth-child(1) > div:nth-child(1) > tdp-st-input-text').shadowRoot.querySelector('div > div > div.tdp-st-input-text.mdc-text-field.text-field.mdc-text-field--outlined.flex_100.has-errors > input')";


        WebElement element = (WebElement)ex.executeScript(query, new Object[0]);
        if (element == null) {
            System.out.println("No se ubicó elemento.");

        } else {
            System.out.println("Se ubicó elemento.");
        }
        return element;
    }
    public static WebElement getSelectorFromOptionsSpan(WebDriver driver, String dato) {
        JavascriptExecutor ex = (JavascriptExecutor)driver;
        String query = "return Array.from(document.querySelector('div[class=\"_options tdp-row\"]').querySelectorAll('span')).find(x => x.innerText == '"+dato+"')";
        WebElement element = (WebElement)ex.executeScript(query);
        if (element == null) {
            System.out.println("No se ubicó elemento.");

        } else {
            System.out.println("Se ubicó elemento.");
        }
        return element;
    }


    public static WebElement getElementoFromJavaScript(WebDriver driver, String appBase, String pathCss) {
        JavascriptExecutor ex = (JavascriptExecutor)driver;
        String query = shadowRootQuerySelector(appBase, pathCss);
        WebElement element = (WebElement)ex.executeScript(query, new Object[0]);
        if (element == null) {
            System.out.println("No se ubico elemento.");

        } else {
            System.out.println("Se ubico elemento.");
        }
        return element;
    }
    public static WebElement getElementFromJS(WebDriver driver, String appBase) {
        JavascriptExecutor ex = (JavascriptExecutor)driver;
        String query = QuerySelector(appBase);
        WebElement element = (WebElement)ex.executeScript(query, new Object[0]);
        if (element == null) {
            System.out.println("No se ubico elemento.");

        } else {
            System.out.println("Se ubico elemento.");
        }
        return element;
    }
    private static String QuerySelector(String elementBase) {
        return "return document.querySelector(" + "\"" + elementBase + "\")";
    }

    public static void closeWindowsChild( WebDriver driver) {
        try {
            Set<String> windows = driver.getWindowHandles();
            Iterator<String> iter = windows.iterator();
            String[] winNames=new String[windows.size()];
            int i=0;
            while (iter.hasNext()) {
                winNames[i]=iter.next();
                i++;
            }

            if(winNames.length > 1) {
                for(i = winNames.length; i > 1; i--) {
                    driver.switchTo().window(winNames[i - 1]);
                    driver.close();
                }
            }
            driver.switchTo().window(winNames[0]);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void ScrollDownPage( WebDriver driver) {
            /////Scroll Down
            JavascriptExecutor js = (JavascriptExecutor) driver;
            //This will scroll the web page till end.
            WebElement element = (WebElement) js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        }


    // Haga clic en el día indicado
    public static void clickGivenDay(List<WebElement> elementList, String day) {
        /*Functional JAVA version of this method.*/
        elementList.stream()
                .filter(element -> element.getText().contains(day))
                .findFirst()
                .ifPresent(WebElement::click);
    }


    private static void showExelData(List sheetData) {
        // Iterates the data and print it out to the console.
        for (int i = 0; i < sheetData.size(); i++) {
            List list = (List) sheetData.get(i);
            for (int j = 0; j < list.size(); j++) {
                Cell cell = (Cell) list.get(j);
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    System.out.print(" a ");
                    System.out.print(cell.getNumericCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
                    System.out.print(" b ");
                    System.out.print(cell.getRichStringCellValue());
                } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                    System.out.print(" c ");
                    System.out.print(cell.getBooleanCellValue());
                }
                if (j < list.size() - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
    }
    public static void colocarStringEnCajadeTexto(String Documento) throws AWTException {
        int length = Documento.length();
        for(int i = 0; i < length; i++){

            char c = Documento.charAt(i);
            Robot robotNum = new Robot();
            switch (c) {
                case '0':
                    robotNum.keyPress(KeyEvent.VK_0);
                    robotNum.keyRelease(KeyEvent.VK_0);
                    break;
                case '1':
                    robotNum.keyPress(KeyEvent.VK_1);
                    robotNum.keyRelease(KeyEvent.VK_1);
                    break;
                case '2':
                    robotNum.keyPress(KeyEvent.VK_2);
                    robotNum.keyRelease(KeyEvent.VK_2);
                    break;
                case '3':
                    robotNum.keyPress(KeyEvent.VK_3);
                    robotNum.keyRelease(KeyEvent.VK_3);
                    break;
                case '4':
                    robotNum.keyPress(KeyEvent.VK_4);
                    robotNum.keyRelease(KeyEvent.VK_4);
                    break;
                case '5':
                    robotNum.keyPress(KeyEvent.VK_5);
                    robotNum.keyRelease(KeyEvent.VK_5);
                    break;
                case '6':
                    robotNum.keyPress(KeyEvent.VK_6);
                    robotNum.keyRelease(KeyEvent.VK_6);
                    break;
                case '7':
                    robotNum.keyPress(KeyEvent.VK_7);
                    robotNum.keyRelease(KeyEvent.VK_7);
                    break;
                case '8':
                    robotNum.keyPress(KeyEvent.VK_8);
                    robotNum.keyRelease(KeyEvent.VK_8);
                    break;
                case '9':
                    robotNum.keyPress(KeyEvent.VK_9);
                    robotNum.keyRelease(KeyEvent.VK_9);
                    break;
                case 'a':
                    robotNum.keyPress(KeyEvent.VK_A);
                    robotNum.keyRelease(KeyEvent.VK_A);
                    break;
                case 'b':
                    robotNum.keyPress(KeyEvent.VK_B);
                    robotNum.keyRelease(KeyEvent.VK_B);
                    break;
                case 'c':
                    robotNum.keyPress(KeyEvent.VK_C);
                    robotNum.keyRelease(KeyEvent.VK_C);
                    break;
                case 'd':
                    robotNum.keyPress(KeyEvent.VK_D);
                    robotNum.keyRelease(KeyEvent.VK_D);
                    break;
                case 'e':
                    robotNum.keyPress(KeyEvent.VK_E);
                    robotNum.keyRelease(KeyEvent.VK_E);
                    break;
                case 'f':
                    robotNum.keyPress(KeyEvent.VK_F);
                    robotNum.keyRelease(KeyEvent.VK_F);
                    break;
                case 'g':
                    robotNum.keyPress(KeyEvent.VK_G);
                    robotNum.keyRelease(KeyEvent.VK_G);
                    break;
                case 'h':
                    robotNum.keyPress(KeyEvent.VK_H);
                    robotNum.keyRelease(KeyEvent.VK_H);
                    break;
                case 'i':
                    robotNum.keyPress(KeyEvent.VK_I);
                    robotNum.keyRelease(KeyEvent.VK_I);
                    break;
                case 'j':
                    robotNum.keyPress(KeyEvent.VK_J);
                    robotNum.keyRelease(KeyEvent.VK_J);
                    break;
                case 'k':
                    robotNum.keyPress(KeyEvent.VK_K);
                    robotNum.keyRelease(KeyEvent.VK_K);
                    break;
                case 'l':
                    robotNum.keyPress(KeyEvent.VK_L);
                    robotNum.keyRelease(KeyEvent.VK_L);
                    break;
                case 'm':
                    robotNum.keyPress(KeyEvent.VK_M);
                    robotNum.keyRelease(KeyEvent.VK_M);
                    break;
                case 'n':
                    robotNum.keyPress(KeyEvent.VK_N);
                    robotNum.keyRelease(KeyEvent.VK_N);
                    break;
                case 'o':
                    robotNum.keyPress(KeyEvent.VK_O);
                    robotNum.keyRelease(KeyEvent.VK_O);
                    break;
                case 'p':
                    robotNum.keyPress(KeyEvent.VK_P);
                    robotNum.keyRelease(KeyEvent.VK_P);
                    break;
                case 'q':
                    robotNum.keyPress(KeyEvent.VK_Q);
                    robotNum.keyRelease(KeyEvent.VK_Q);
                    break;
                case 'r':
                    robotNum.keyPress(KeyEvent.VK_R);
                    robotNum.keyRelease(KeyEvent.VK_R);
                    break;
                case 's':
                    robotNum.keyPress(KeyEvent.VK_S);
                    robotNum.keyRelease(KeyEvent.VK_S);
                    break;
                case 't':
                    robotNum.keyPress(KeyEvent.VK_T);
                    robotNum.keyRelease(KeyEvent.VK_T);
                    break;
                case 'u':
                    robotNum.keyPress(KeyEvent.VK_U);
                    robotNum.keyRelease(KeyEvent.VK_U);
                    break;
                case 'v':
                    robotNum.keyPress(KeyEvent.VK_V);
                    robotNum.keyRelease(KeyEvent.VK_V);
                    break;
                case 'w':
                    robotNum.keyPress(KeyEvent.VK_W);
                    robotNum.keyRelease(KeyEvent.VK_W);
                    break;
                case 'x':
                    robotNum.keyPress(KeyEvent.VK_X);
                    robotNum.keyRelease(KeyEvent.VK_X);
                    break;
                case 'y':
                    robotNum.keyPress(KeyEvent.VK_Y);
                    robotNum.keyRelease(KeyEvent.VK_Y);
                    break;
                case 'z':
                    robotNum.keyPress(KeyEvent.VK_Z);
                    robotNum.keyRelease(KeyEvent.VK_Z);
                    break;
                case '$':
                    robotNum.keyPress(KeyEvent.VK_DOLLAR);
                    robotNum.keyRelease(KeyEvent.VK_DOLLAR);
                    break;
                case '&':
                    robotNum.keyPress(KeyEvent.VK_AMPERSAND);
                    robotNum.keyRelease(KeyEvent.VK_AMPERSAND);
                    break;
            }
        }
    }
    public static void robotTab(int veces) throws AWTException {
        Robot robot = new Robot();
        for(int i = 0; i < veces; i++){
            robot.keyPress (KeyEvent.VK_TAB);
            robot.keyRelease (KeyEvent.VK_TAB);
        }

    }
    public static void robotDown(int veces) throws AWTException {
        Robot robot = new Robot();
        for(int i = 0; i < veces; i++){
            robot.keyPress (KeyEvent.VK_DOWN);
            robot.keyRelease (KeyEvent.VK_DOWN);
        }

    }
    public static void robotEnter(int veces) throws AWTException {
        Robot robot = new Robot();
        for(int i = 0; i < veces; i++){
            robot.keyPress (KeyEvent.VK_ENTER);
            robot.keyRelease (KeyEvent.VK_ENTER);
        }

    }
    protected static void zoom(WebDriver driver, int size) {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("document.body.style.zoom = '" + size + "%'");
        } catch (Exception t) {
            System.out.println(Arrays.toString(t.getStackTrace()));
            throw t;
        }
    }

    public static void validarError(){

    }

    public static void ByVisibleElement(WebElement Element) {

            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript("arguments[0].scrollIntoView();", Element);
    }

    /*************************** new ****************************/
    public static boolean validarExistenciaElemento(WebDriver driver, String locator) {
        boolean validator;
        WebDriverWait wait = new WebDriverWait(driver, 10);
        if (!driver.findElements(By.xpath(locator)).isEmpty()) {
            System.out.println("Elemento si es reconocido en la pantalla");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            validator = true;
        } else {
            System.out.println("Elemento no existe");
            validator = false;
        }
        return validator;
    }

    public static boolean validarExistenciaElementoConLoader(WebDriver driver, String locator) {
        boolean validator;
        WebDriverWait wait = new WebDriverWait(driver, 120);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(LOADER)));
        sleep(500);
        if(!driver.findElements(By.xpath(locator)).isEmpty()){
            System.out.println("Se valida existencia de elemento");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator)));
            validator = true;
        }
        else{
            System.out.println("Se valida que el elemento no existe");
            mensajeImprimirWord(driver, "Elemento no existe");
            validator = false;
        }
        System.out.println("b7");
        return validator;
    }

    public static boolean validarModalError() {
        boolean validator;
        validator = validarExistenciaElementoConLoader(driver, MODAL_ERROR);
        return validator;
    }

    public static WebElement obtenerELementoDeLista(WebDriver driver, String appBase, String textoOpcion) {
        JavascriptExecutor ex = (JavascriptExecutor)driver;
        sleep(2000);
        String query = "return Array.from(document.querySelector(\"" + appBase + "\").shadowRoot.querySelectorAll('li')).find(x => x.textContent === '" + textoOpcion + "')";
        WebElement element = (WebElement)ex.executeScript(query, new Object[0]);
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        if (element == null) {
            System.out.println("No se encontro la opción de la lista");
        } else {
            System.out.println("Opción de lista encontrada");
            ByVisibleElement(element);
        }
        return element;
    }
}
