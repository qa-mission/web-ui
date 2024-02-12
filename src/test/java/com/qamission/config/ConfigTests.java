package com.qamission.config;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class ConfigTests {
    private String resourcePath;
    private Config config;

    @BeforeMethod
    public void setUpTestMethod() {

    }

    @Test
    public void testLoadLinuxProps(){
        System.setProperty("env","linux");
        config = Config.getConfig();
        resourcePath = config.getResourcesPath();
        String expected = resourcePath+"/drivers/chromedriver";
        Assert.assertEquals(expected,config.getChromeDriverPath(),"Check path to chrome driver for linux env");
    }

    @Test
    public void testLoadWindowsProps(){
        System.setProperty("env","windows");
        config = Config.getConfig();
        resourcePath = config.getResourcesPath();
        String expected = resourcePath+"/drivers/chromedriver.exe";
        Assert.assertEquals(expected,config.getChromeDriverPath(),"Check path to chrome driver for windows env");
    }
}
