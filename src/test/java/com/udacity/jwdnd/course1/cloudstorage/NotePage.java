package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NotePage {
    @FindBy(id="logout-button")
    private WebElement logoutbutton;

    @FindBy(id="nav-notes-tab")
    private WebElement notestab;

    @FindBy(id="addnote-button")
    private WebElement addnotebutton;

    @FindBy(id="editnote-button")
    private WebElement editnotebutton;

    @FindBy(id="deletenote-button")
    private WebElement deletenotebutton;

    @FindBy(id="note-title")
    private WebElement notetitleInput;

    @FindBy(id="note-description")
    private WebElement notedescriptionInput;

    @FindBy(id="notesubmit-button")
    private WebElement notesubmitbutton;

    @FindBy(id="notetitle-display")
    private WebElement notetitledisplay;

    @FindBy(id="notedescription-display")
    private WebElement notedescriptiondisplay;


    public NotePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void userLogout() {
        logoutbutton.click();
    }

    public void clickNotesTab() {
        notestab.click();
    }

    public void clickAddNewNote() {
        addnotebutton.click();
    }

    public void clickEditNote() {
        editnotebutton.click();
    }

    public void clickDeleteNote() {
        deletenotebutton.click();
    }

    public void NoteInput(String notetitle, String notedescription) {
        notetitleInput.clear();
        notetitleInput.sendKeys(notetitle);
        notedescriptionInput.clear();
        notedescriptionInput.sendKeys(notedescription);
        notesubmitbutton.click();
    }

    public String getNoteTitle() {
        return notetitledisplay.getText();
    }

    public String getNoteDescription() {
        return notedescriptiondisplay.getText();
    }




}
