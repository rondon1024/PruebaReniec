package com.bdd.FrontEnd.helpers;

import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;

public class ConfigCaps {

    public static String    url                     = "http://127.0.0.1:4723/wd/hub";
    public static String    PLATFORM_NAME           = "Android";
    public static String    DEVICE_NAME             = "emulator-5554";
    public static String    APPLICATION_NAME        = "tdp.app.col";
    public static String    APP_PACKAGE             = "tdp.app.col";
    public static String    APP_ACTIVITY            = "tdp.app.col.MainActivity";
    public static int       NEW_COMMAND_TIMEOUT     = 20;
    public static boolean   NO_RESET                = false;
    public static boolean   ACCEPT_INSECURE_CERTS   = true;



    public static final URL appiumServerUrl(){
        try{
            return new URL(url);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
        return null;
    }

    public static final DesiredCapabilities Android_Standard(){
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, PLATFORM_NAME);
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,DEVICE_NAME);
        capabilities.setCapability(MobileCapabilityType.APPLICATION_NAME, APPLICATION_NAME);
        capabilities.setCapability("appPackage", APP_PACKAGE);
        capabilities.setCapability("appActivity", APP_ACTIVITY);
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, NEW_COMMAND_TIMEOUT);
        capabilities.setCapability(MobileCapabilityType.NO_RESET, NO_RESET);
        capabilities.setCapability(MobileCapabilityType.ACCEPT_INSECURE_CERTS, ACCEPT_INSECURE_CERTS);//"noSign"

        return capabilities;
    }


    public static final DesiredCapabilities dcAndroid_PIXEL_4_API29_USSD(){

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "10.0");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,"PIXEL 4 API 29");
        capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
        capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 120);
        capabilities.setCapability("appPackage", "tdp.app.col"); //USSD
        capabilities.setCapability("appActivity", "tdp.app.col.MainActivity"); // USSD
        capabilities.setCapability("noReset","false");

        return capabilities;
    }

}
