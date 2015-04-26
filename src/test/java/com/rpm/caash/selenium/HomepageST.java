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
	private static String SUCCESSFUL_EMAIL = "testaccount@gmail.com";
	private static String SUCCESSFUL_PASSWORD = "11111111";
	private static String FAILURE_EMAIL = "fakeaccount@gmail.com";
	private static String FAILURE_PASSWORD = "badpassword";
	
	@Test
	public void testLoginAndLOgout(){
		driver.get(SeleniumSuite.URL_UNDER_TEST);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/landingpage", driver.getCurrentUrl());
		driver.findElement(By.id("loginEmail")).sendKeys(SUCCESSFUL_EMAIL);
		driver.findElement(By.id("loginPassword")).sendKeys(SUCCESSFUL_PASSWORD);
		driver.findElement(By.id("loginButton")).click();
		SeleniumSuite.waitForPageLoad(5);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/add", driver.getCurrentUrl());
		driver.findElement(By.id("logOut")).click();
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/landingpage", driver.getCurrentUrl());
	}
	
	@Test
	public void testLoginFailure(){
		driver.get(SeleniumSuite.URL_UNDER_TEST);
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
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/landingpage", driver.getCurrentUrl());
		driver.findElement(By.id("loginEmail")).sendKeys(SUCCESSFUL_EMAIL);
		driver.findElement(By.id("loginPassword")).sendKeys(SUCCESSFUL_PASSWORD);
		driver.findElement(By.id("loginButton")).click();
		SeleniumSuite.waitForPageLoad(5);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/add", driver.getCurrentUrl());
	}

}
