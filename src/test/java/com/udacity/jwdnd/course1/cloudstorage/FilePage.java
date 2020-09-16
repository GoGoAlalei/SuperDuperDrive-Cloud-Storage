package com.udacity.jwdnd.course1.cloudstorage;

public class FilePage {
    /* https://github.com/kcm3394/SuperDuperDrive---Udacity-JDND/blob/master/starter/cloudstorage/src/test/java/com/udacity/jwdnd/course1/cloudstorage/pageobjects/FilesHomePage.java

    @FindBy(id = "upload-file-button")
    private WebElement uploadFileButton;

    @FindBy(id = "fileUpload")
    private WebElement chooseFile;

    @FindBy(id = "delete-file-button")
    private WebElement deleteFileButton;

    @FindBy(xpath = "//*[@id=\"fileTable\"]/tbody/tr")
    private WebElement firstFile;

    public FilesHomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void uploadFile(String filePath) {
        this.chooseFile.sendKeys(filePath);
        this.uploadFileButton.click();
    }

    public void deleteFile() {
        this.deleteFileButton.click();
    }

    public String getFileName() {
        return this.firstFile.findElement(By.id("displayed-file-name")).getText();
    }



     */

}
