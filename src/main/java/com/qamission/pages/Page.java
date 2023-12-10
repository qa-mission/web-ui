package com.qamission.pages;


import com.qamission.config.Config;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Point;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Page {
    protected WebDriver driver;
    protected Wait wait;

    public Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(Config.getConfig().getPauseInTest()));
    }

    public List<String> getAllRegExpFrom(String input, String regExp) {
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(input);
        ArrayList<String> list = new ArrayList<String>();
        while (m.find()) {
            for (int i = 0; i <= m.groupCount(); i++) {
                list.add(m.group(i));
            }
        }
        return list;
    }

    public void scrollToTheTop() {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollTo(0, 0)");
    }

    public void scrollToTheBottom() {
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void moveToElement(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement el = driver.findElement(locator);
        moveToElement(el);
    }

    public void moveToElement(WebElement el) {
        Actions actions = new Actions(driver);
        actions.moveToElement(el);
        actions.perform();
    }

    public void scrollToElement(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        WebElement element = driver.findElement(By.id("id_of_element"));
        scrollToElement(element);
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        wait.until(ExpectedConditions.visibilityOfAllElements(element));
    }

    public void scrollToPoint(Point p) {
        int x = p.getX();
        int y = p.getY();
        String script = "window.scrollTo("+x+","+y+");";
        ((JavascriptExecutor) driver).executeScript(script);
    }

    public void scrollByPixels(int length) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String command = "window.scrollBy(0,"+length+")";
        js.executeScript(command, "");

    }

    public boolean containsText(String text) {
        return driver.getPageSource().contains(text);
    }

    public void pause(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



}
