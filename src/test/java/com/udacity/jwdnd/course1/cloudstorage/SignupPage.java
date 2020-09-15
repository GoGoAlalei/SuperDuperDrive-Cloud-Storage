package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    @FindBy(id="inputFirstName")
    private WebElement firstnameinput;

    @FindBy(id="inputLastName")
    private WebElement lastnameinput;

    @FindBy(id="inputUsername")
    private WebElement usernameinput;

    @FindBy(id="inputPassword")
    private WebElement passwordinput;

    @FindBy(id="submit-button")
    private WebElement signupbutton;


    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void userSignup(String firstname, String lastname, String username, String password) {
        firstnameinput.sendKeys(firstname);
        lastnameinput.sendKeys(lastname);
        usernameinput.sendKeys(username);
        passwordinput.sendKeys(password);
        signupbutton.click();
    }

}
