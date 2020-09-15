package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ResultPage {

    @FindBy(id="success")
    private WebElement successheader;

    @FindBy(id="error")
    private WebElement errorheader;

    @FindBy(id="banner")
    private WebElement bannerhome;

    @FindBy(id="success2home")
    private WebElement successHome;

    @FindBy(id="error2home")
    private WebElement errorHome;

    @FindBy(id="banner2home")
    private WebElement bannerHome;

    public ResultPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean showSuccess() {
        return successheader.isDisplayed();
    }

    public boolean showError() {
        return errorheader.isDisplayed();
    }

    public boolean showBanner() {
        return bannerhome.isDisplayed();
    }

    public void Successtohome() { successHome.click(); }

    public void Errortohome() {
        errorHome.click();
    }

    public void Bannertohome() {
        bannerHome.click();
    }

}
