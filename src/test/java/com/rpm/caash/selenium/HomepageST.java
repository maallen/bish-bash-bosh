package com.rpm.caash.selenium;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class HomepageST {

	private static WebDriver driver;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		driver = new ChromeDriver();
	}

	@Before
	public void setUp() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
		driver = null;
	}


	@Test
	public void testDirectedToLandingPage() {
		driver.get("http://caash-caash.rhcloud.com/");
		Assert.assertEquals("http://caash-caash.rhcloud.com/#/landingpage", driver.getCurrentUrl());
	}

	@Test
	public void testLinkToJobsFeed(){
		driver.get("http://caash-caash.rhcloud.com/");
		final WebElement jobsFeedLink = driver.findElement(By.cssSelector("a[href*='jobsFeed']"));
		final Actions actions = new Actions(driver);
		actions.moveToElement(jobsFeedLink).click().perform();
		Assert.assertEquals("http://caash-caash.rhcloud.com/#/jobsFeed", driver.getCurrentUrl());
	}

	@Test
	public void testLinkToPlaceAdd(){
		driver.get("http://caash-caash.rhcloud.com/");
		final WebElement jobsFeedLink = driver.findElement(By.cssSelector("a[href*='add']"));
		final Actions actions = new Actions(driver);
		actions.moveToElement(jobsFeedLink).click().perform();
		Assert.assertEquals("http://caash-caash.rhcloud.com/#/add", driver.getCurrentUrl());
	}

	@Test
	public void testLinkToRegister(){
		driver.get("http://caash-caash.rhcloud.com/");
		final WebElement jobsFeedLink = driver.findElement(By.cssSelector("a[href*='register']"));
		final Actions actions = new Actions(driver);
		actions.moveToElement(jobsFeedLink).click().perform();
		Assert.assertEquals("http://caash-caash.rhcloud.com/#/register", driver.getCurrentUrl());
	}

}
