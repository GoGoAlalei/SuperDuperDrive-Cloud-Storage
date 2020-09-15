package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

    @FindBy(id="inputUsername")
    private WebElement usernameinput;

    @FindBy(id="inputPassword")
    private WebElement passwordinput;

    @FindBy(id="submit-button")
    private WebElement loginbutton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void userLogin(String username, String password) {
        usernameinput.sendKeys(username);
        passwordinput.sendKeys(password);
        loginbutton.click();
    }


}
