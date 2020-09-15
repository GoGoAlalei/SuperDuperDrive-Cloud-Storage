package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupLoginTests {
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
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void unauthoizedAccess() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("http://localhost:" + this.port + "/signup", driver.getCurrentUrl());

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
    }

    @Test
    public void SignupLoginLogout() {
        driver.get("http://localhost:" + this.port + "/signup");
        SignupPage signupPage = new SignupPage(driver);
        signupPage.userSignup("Harry", "Potter", "Hogwarts", "Magic");

        driver.get("http://localhost:" + this.port + "/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.userLogin("Hogwarts", "Magic");

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
        NotePage notePage = new NotePage(driver);
        notePage.userLogout();

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

    }

}
