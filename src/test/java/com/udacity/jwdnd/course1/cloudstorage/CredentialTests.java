package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.security.cert.CRLException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {
    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {

        this.driver = new ChromeDriver();

        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.userSignup("Harry", "Potter", "Hogwarts", "Magic");

        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.userLogin("Hogwarts", "Magic");
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void credentialCreateEditDelete() {

        driver.get("http://localhost:" + this.port + "/home");
        CredentialPage credentialPage = new CredentialPage(driver);
        credentialPage.clickCredentialTab();
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addcredential-button")));

        credentialPage.clickAddCredential();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialsubmit-button")));
        credentialPage.CredentialInput("www.udacity.com", "FirstUser", "f" +
                "" +
                "irstpswd");
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.showSuccess());

        //resultPage.Successtohome();
        driver.get("http://localhost:" + this.port + "/home");
        credentialPage.clickCredentialTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editcredential-button")));
        Assertions.assertEquals("www.udacity.com", credentialPage.getCredentialUrl());
        Assertions.assertEquals("FirstUser", credentialPage.getCredentialUsername());
        Assertions.assertNotEquals("firstpswd", credentialPage.getCredentialPassword());

        credentialPage.clickEditCredential();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credentialsubmit-button")));
        Assertions.assertEquals("firstpswd", credentialPage.getEditPassword());
        credentialPage.CredentialInput("www.clouds.com", "CloudUser", "cloudpswd");
        Assertions.assertTrue(resultPage.showSuccess());

        //resultPage.Successtohome();
        driver.get("http://localhost:" + this.port + "/home");
        credentialPage.clickCredentialTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("deletecredential-button")));
        Assertions.assertEquals("www.clouds.com", credentialPage.getCredentialUrl());
        Assertions.assertEquals("CloudUser", credentialPage.getCredentialUsername());
        Assertions.assertNotEquals("cloudpswd", credentialPage.getCredentialPassword());
        credentialPage.clickDeleteCredential();
        Assertions.assertTrue(resultPage.showSuccess());

        //resultPage.Successtohome();
        driver.get("http://localhost:" + this.port + "/home");
        credentialPage.clickCredentialTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addcredential-button")));
        Assertions.assertFalse(driver.getPageSource().contains("www.clouds.com"));

    }

}
