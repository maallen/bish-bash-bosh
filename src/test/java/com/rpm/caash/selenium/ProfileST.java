package com.rpm.caash.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author maallen87 (Mark Allen)
 */
public class ProfileST {

    private static WebDriver driver = SeleniumSuite.driver;

    @Test
    public void testProfileInformationIsCorrect(){
        driver.get(SeleniumSuite.URL_UNDER_TEST);
        SeleniumSuite.waitForPageLoad(5);
        driver.findElement(By.id("profile")).click();
        SeleniumSuite.waitForPageLoad(5);
        Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/profile", driver.getCurrentUrl());
        String name = driver.findElement(By.id("name")).getText();
        String email = driver.findElement(By.id("email")).getText();
        Assert.assertEquals("Name: Test Account", name);
        Assert.assertEquals("Email: testaccount@gmail.com", email);
    }
}
