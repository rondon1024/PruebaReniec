package com.bdd.FrontEnd.helpers;
import com.bdd.FrontEnd.utility.ExcelReader;
import com.bdd.FrontEnd.utility.GenerateWord;
import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.screenrecording.CanRecordScreen;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import org.apache.commons.codec.binary.Base64;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;


public class PageBase {
    public static final String AndroidVersion = "10";
    public static final Boolean isFederado = true;
    public static GenerateWord generateWord = new GenerateWord();
    public static AppiumDriver<MobileElement> driver;

    private static final String EXCEL_APK = "excel/DocExel.xlsx";
    private static final String LOGIN_APK = "LoginExitosoMM";

    private static final String FILE_TEST_CASES_DATA = "excel/DocExel.xlsx";
    private static final String SHEET_TEST_CASES_DATA = "LoginExitosoMM";

    private WebDriverWait wait;
    protected static ThreadLocal <String> dateTime = new ThreadLocal<String>();

    public PageBase() {
     //   this.driver = Hook.getDriver();
        WebDriverWait wait = new WebDriverWait(this.driver, 60);
    }

    protected static List<HashMap<String, String>> getData1() throws Throwable {
        return ExcelReader.data(EXCEL_APK, LOGIN_APK);
    }
    protected static List<HashMap<String, String>> getTestData() throws Throwable {
        return ExcelReader.data(EXCEL_APK, LOGIN_APK);
    }

    protected static HashMap<String, String> findRow(String columnNameKey, String columKeyValue) throws Throwable {
        Boolean found = false;
        List<HashMap<String, String>> rows          = ExcelReader.data(FILE_TEST_CASES_DATA, SHEET_TEST_CASES_DATA);
        HashMap<String, String> rowFound            = new HashMap<String, String>();

        for(int i=0; i<rows.size() && !found ;i++) {
            if (rows.get(i).get(columnNameKey).toString().equals(columKeyValue)) {
                rowFound = rows.get(i);
                found = true;
            }
        }
        return rowFound;
    }

    public static void wait(int milliSeconds){
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }


    public static String dateTime()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String dateTimeShort()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDateTime() {
        return dateTime.get();
    }

    public static void startRecording() {
        System.out.println("..start recording");
       // ((CanRecordScreen) Hook.driver).startRecordingScreen();
    }

    public static synchronized void stopRecording(String platformName, String deviceName, Scenario scenario) throws Exception {
        System.out.println("..stop recording");
     //   String media = ((CanRecordScreen) Hook.driver).stopRecordingScreen();
        String featureName  = scenario.getId().split(";")[0];
        String scenarioName = scenario.getId().split(";")[1];
        String dirPath =    "target" + File.separator +  "resultado" + File.separator +  "videos" + File.separator + platformName +
                "_" + deviceName + File.separator + dateTimeShort() + File.separator + featureName;

        File videoDir = new File(dirPath);

        synchronized(videoDir){
            if(!videoDir.exists()) {
                videoDir.mkdirs();
            }
        }
        FileOutputStream stream = null;
        try {
            System.out.println("video path: " + videoDir + File.separator + scenarioName + ".mp4");
            stream = new FileOutputStream(videoDir + File.separator + scenarioName + ".mp4");
         //   stream.write(Base64.decodeBase64(media));
            stream.close();
        } catch (Exception e) {
            System.out.println("error during video capture" + e.toString());
        } finally {
            if(stream != null) {
                stream.close();
            }
        }
    }


}