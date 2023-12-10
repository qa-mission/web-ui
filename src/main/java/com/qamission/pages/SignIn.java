package com.qamission.pages;

import com.qamission.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class SignIn extends Page{
    private By credentials = By.cssSelector("div.text-group__input-container>input");
    private By submitButton = By.cssSelector("button.button.button--block.button--submit.button--theme-base.button--theme-dark.submit-button");


    String userName;
    String userPassword;

    public SignIn(WebDriver driver) {
        super(driver);
        userName = Config.getConfig().getUserName();
        userPassword = Config.getConfig().getUserPassword();
    }

    public void login() {
        wait.until(ExpectedConditions.presenceOfElementLocated(credentials));
        List<WebElement> credFields = driver.findElements(credentials);
        if (credFields.size()!= 2) throw new RuntimeException("No credential fields found on Sign In page");
        credFields.get(0).sendKeys(userName);
        credFields.get(1).sendKeys(userName);
        driver.findElement(submitButton).submit();

    }

}
