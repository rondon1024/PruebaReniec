package com.bdd.web.page;

import com.bdd.FrontEnd.BaseClass;
import com.bdd.FrontEnd.Hook;
import com.bdd.FrontEnd.utility.ExtentReportUtil;
import com.bdd.FrontEnd.utility.GenerateWord;
import com.bdd.web.model.ExcelModel;
import com.bdd.web.path.ReniecPath;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;

public class ReniecPage extends BaseClass {

    private WebDriver driver;
    static GenerateWord generateWord = new GenerateWord();

    public ReniecPage() {
        driver = Hook.getDriver();
    }


//LINIO
    public void accederWeb(String IDTest) throws Throwable {
        try {
            int valor = Integer.parseInt(IDTest) - 1;
            String url = getData().get(valor).get(ExcelModel.URL);
            driver.get(url);
            sleep(2000);
            ExtentReportUtil.INSTANCE.stepPass(driver, "Se inició correctamente la página reniec");
            generateWord.sendText("Se inició correctamente la página reniec");
            generateWord.addImageToWord(driver);
        } catch (Exception e) {
            ExtentReportUtil.INSTANCE.stepFail(driver, "Fallo el caso de prueba : " + e.getMessage());
            generateWord.sendText("Tiempo de espera ");
            generateWord.addImageToWord(driver);
            driver.close();
            throw e;
        }
    }





    public void Ingreso_oficina_consultar(String IDTest) throws Throwable {

        sleep(4000);
        try {
            String originalWindow = driver.getWindowHandle();



            for (String windowHandle : driver.getWindowHandles()) {
                if(!originalWindow.contentEquals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }



            int valor = Integer.parseInt(IDTest) - 1;
            String Ofcina =getData().get(valor).get(ExcelModel.OFICINA);
            sendKeyValue(driver, "xpath", ReniecPath.txtOficina, Ofcina);
            click(driver, "xpath", ReniecPath.lupita);
            sleep(4000);



            ArrayList tabs = new ArrayList (driver.getWindowHandles());
            System.out.println(tabs.size());

            driver.switchTo().window(tabs.get(0).toString());


            driver.switchTo().window(originalWindow);

            ExtentReportUtil.INSTANCE.stepPass(driver, "Se Ingresa el dato : " + Ofcina );
            generateWord.sendText("Se Ingresa el ultimo dato: " + Ofcina );
            generateWord.addImageToWord(driver);
        } catch (Exception e) {
            ExtentReportUtil.INSTANCE.stepFail(driver, "Fallo el caso de prueba : " + e.getMessage());
            generateWord.sendText("Tiempo de espera ha excedido");
            generateWord.addImageToWord(driver);
            driver.close();
        }
    }



    public void Clic_Boton_extranjeros() throws Throwable {
        try {
            sleep(1000);
          click(driver, "xpath", ReniecPath.txtPeruanoExtranjeros);

        } catch (Exception e) {
            driver.close();
        }
    }

    public void Despliega_opcion_Consulados() throws Throwable {
        try {
            sleep(1000);
            click(driver, "xpath", ReniecPath.txtConsulado);

        } catch (Exception e) {
            driver.close();
        }
    }

    public void Ingreso_opcion_lista() throws Throwable {
        try {
            sleep(1000);
            click(driver, "xpath", ReniecPath.txtListaConsulado);
            sleep(2000);

        } catch (Exception e) {
            driver.close();
        }
    }























}
