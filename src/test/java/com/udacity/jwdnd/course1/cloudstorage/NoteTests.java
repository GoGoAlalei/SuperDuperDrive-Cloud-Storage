package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NoteTests {

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
    public void noteCreateEditDelete() {

        driver.get("http://localhost:" + this.port + "/home");
        NotePage notePage = new NotePage(driver);
        notePage.clickNotesTab();
        WebDriverWait wait = new WebDriverWait(driver, 1000);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addnote-button")));

        notePage.clickAddNewNote();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("notesubmit-button")));
        notePage.NoteInput("New Note", "This is the first note description.");
        ResultPage resultPage = new ResultPage(driver);
        Assertions.assertTrue(resultPage.showSuccess());

        //resultPage.Successtohome();
        //Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
        driver.get("http://localhost:" + this.port + "/home");
        notePage.clickNotesTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("editnote-button")));
        Assertions.assertEquals("New Note", notePage.getNoteTitle());
        Assertions.assertEquals("This is the first note description.", notePage.getNoteDescription());

        notePage.clickEditNote();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("notesubmit-button")));
        notePage.NoteInput("Edit Note", "This is a modified note description.");
        Assertions.assertTrue(resultPage.showSuccess());

        //resultPage.Successtohome();
        driver.get("http://localhost:" + this.port + "/home");
        notePage.clickNotesTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("deletenote-button")));
        Assertions.assertEquals("Edit Note", notePage.getNoteTitle());
        Assertions.assertEquals("This is a modified note description.", notePage.getNoteDescription());
        notePage.clickDeleteNote();
        Assertions.assertTrue(resultPage.showSuccess());

        //resultPage.Successtohome();
        driver.get("http://localhost:" + this.port + "/home");
        notePage.clickNotesTab();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("addnote-button")));
        //Assertions.assertEquals(0, driver.findElements(By.id("notetitle-display")).size());
        //Assertions.assertEquals(0, driver.findElements(By.id("notedescription-display")).size());
        Assertions.assertFalse(driver.getPageSource().contains("Edit Note"));

    }

}
