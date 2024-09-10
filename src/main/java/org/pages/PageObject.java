package org.pages;

import core.driver.DriverManager;
import org.openqa.selenium.support.PageFactory;

public class PageObject {

    public PageObject(){
        PageFactory.initElements(DriverManager.getDriver(), this);
        trait();
    }

    public void trait() {
    }
}