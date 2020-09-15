package com.udacity.jwdnd.course1.cloudstorage;

import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CredentialPage {
    @FindBy(id="nav-credentials-tab")
    private WebElement credentialstab;

    @FindBy(id="addcredential-button")
    private WebElement addcredentialbutton;

    @FindBy(id="editcredential-button")
    private WebElement editcredentialbutton;

    @FindBy(id="deletecredential-button")
    private WebElement deletecredentialbutton;

    @FindBy(id="credentialurl-display")
    private WebElement credentialurldisplay;

    @FindBy(id="credentialusername-display")
    private WebElement credentialusernamedisplay;

    @FindBy(id="credentialpassword-display")
    private WebElement credentialpassworddisplay;

    @FindBy(id="credential-url")
    private WebElement credentialurlInput;

    @FindBy(id="credential-username")
    private WebElement credentialusernameInput;

    @FindBy(id="credential-password")
    private WebElement credentialpasswordInput;

    @FindBy(id="credentialsubmit-button")
    private WebElement credentialsubmitbutton;

    public CredentialPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void clickCredentialTab() { credentialstab.click(); }

    public void clickAddCredential() { addcredentialbutton.click();}

    public void clickEditCredential() { editcredentialbutton.click();}

    public void clickDeleteCredential() { deletecredentialbutton.click();}

    public void CredentialInput(String curl, String cusername, String cpassword) {
        credentialurlInput.clear();
        credentialurlInput.sendKeys(curl);
        credentialusernameInput.clear();
        credentialusernameInput.sendKeys(cusername);
        credentialpasswordInput.clear();
        credentialpasswordInput.sendKeys(cpassword);
        credentialsubmitbutton.click();
    }

    public String getCredentialUrl() { return credentialurldisplay.getText(); }

    public String getCredentialUsername() { return credentialusernamedisplay.getText(); }

    public String getCredentialPassword() { return credentialpassworddisplay.getText(); }

    public String getEditPassword() { return credentialpasswordInput.getAttribute("value"); }

}
