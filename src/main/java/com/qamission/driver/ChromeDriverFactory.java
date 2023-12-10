package com.qamission.driver;

import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.qamission.config.Config;
import com.qamission.utils.ResourceUtils;


import java.net.URL;

public class ChromeDriverFactory {
    private static final String WEBDRIVE_CHROME_DRIVER = "webdriver.chrome.driver";
    private ChromeDriverFactory(){}
    public static ChromeDriver createChromeDriver(String pathToExec) {
        System.setProperty(WEBDRIVE_CHROME_DRIVER,pathToExec);
        ChromeOptions chromeOptions = getChromeOptions();
        return new ChromeDriver(chromeOptions);
    }

    public static ChromeDriver createChromeDriver(String pathToExec, ChromeOptions chromeOptions) {
        System.setProperty(WEBDRIVE_CHROME_DRIVER,pathToExec);
        return new ChromeDriver(chromeOptions);
    }

    public static ChromeDriver createChromeDriver() {
        String pathToExec = getChromeDriverPath();
        return createChromeDriver(pathToExec);
    }

    public static WebDriver createRemoteChromeDriver(URL hub) {
        ChromeOptions chromeOptions = getChromeOptions();
        return createRemoteWebdriver(hub, new DesiredCapabilities(chromeOptions));
    }

    public static String getChromeDriverPath() {
        String defValue = Config.getConfig().getChromeDriverPath();
        return ResourceUtils.getSystemProperty(WEBDRIVE_CHROME_DRIVER,defValue);
    }

    public static ChromeOptions getChromeOptions() {
        MutableCapabilities mc = getDefaultMutualCapabilities();
        return getChromeOptionsFrom(mc);
    }


    public static RemoteWebDriver createRemoteWebdriver(URL hub, DesiredCapabilities capabilities) {
        addDefaultCapabilitiesTo(capabilities);
        return new RemoteWebDriver(hub, capabilities);
    }

    public static MutableCapabilities getDefaultMutualCapabilities() {
        MutableCapabilities mc = new MutableCapabilities();
        mc.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        mc.setCapability(CapabilityType.PAGE_LOAD_STRATEGY, PageLoadStrategy.NORMAL);
        return mc;
    }

    private static ChromeOptions getChromeOptionsFrom(MutableCapabilities mc) {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.merge(mc);
        chromeOptions = chromeOptions.addArguments("--disable-extensions");
        chromeOptions.setExperimentalOption("useAutomationExtension", false);
        return chromeOptions;
    }

    private static void addDefaultCapabilitiesTo(DesiredCapabilities dc) {
        dc.merge(getDefaultMutualCapabilities());
    }


}

