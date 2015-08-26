package com.rpm.caash.selenium;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


/**
 * Tests are executed alphabetically using @FixMethodOrder annotation below.
 * 1. Login + Logout
 * 2. Login Failure
 * 3. Login Success ==> THis allows the other tests to be executed as the test user is logged in
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HomepageST{

	private static WebDriver driver = SeleniumSuite.driver;
	private static String FAILURE_EMAIL = "fakeaccount@gmail.com";
	private static String FAILURE_PASSWORD = "badpassword";
	
	@Test
	public void testLoginAndLogout(){
		driver.get(SeleniumSuite.URL_UNDER_TEST);
		SeleniumSuite.waitForPageLoad(5);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/landingpage", driver.getCurrentUrl());
		driver.findElement(By.id("loginEmail")).sendKeys(SeleniumSuite.SUCCESSFUL_EMAIL);
		driver.findElement(By.id("loginPassword")).sendKeys(SeleniumSuite.SUCCESSFUL_PASSWORD);
		driver.findElement(By.id("loginButton")).click();
		SeleniumSuite.waitForPageLoad(5);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/add", driver.getCurrentUrl());
		driver.findElement(By.id("logOut")).click();
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/landingpage", driver.getCurrentUrl());
	}
	
	@Test
	public void testLoginFailure(){
		driver.get(SeleniumSuite.URL_UNDER_TEST);
		SeleniumSuite.waitForPageLoad(5);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/landingpage", driver.getCurrentUrl());
		driver.findElement(By.id("loginEmail")).sendKeys(FAILURE_EMAIL);
		driver.findElement(By.id("loginPassword")).sendKeys(FAILURE_PASSWORD);
		driver.findElement(By.id("loginButton")).click();
		SeleniumSuite.waitForPageLoad(5);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/landingpage", driver.getCurrentUrl());
	}
	
	@Test
	public void testLoginSuccess(){
		driver.get(SeleniumSuite.URL_UNDER_TEST);
		SeleniumSuite.waitForPageLoad(5);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/landingpage", driver.getCurrentUrl());
		driver.findElement(By.id("loginEmail")).sendKeys(SeleniumSuite.SUCCESSFUL_EMAIL);
		driver.findElement(By.id("loginPassword")).sendKeys(SeleniumSuite.SUCCESSFUL_PASSWORD);
		driver.findElement(By.id("loginButton")).click();
		SeleniumSuite.waitForPageLoad(5);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/add", driver.getCurrentUrl());
	}

}
