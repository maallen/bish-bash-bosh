package com.rpm.caash.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class HomepageST{

	private static WebDriver driver = SeleniumSuite.driver;

	@Test
	public void testDirectedToLandingPage() {
		driver.get(SeleniumSuite.URL_UNDER_TEST);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/landingpage", driver.getCurrentUrl());
	}

	@Test
	public void testLinkToJobsFeed(){
		driver.get(SeleniumSuite.URL_UNDER_TEST);
		final WebElement jobsFeedLink = driver.findElement(By.cssSelector("a[href*='jobsFeed']"));
		final Actions actions = new Actions(driver);
		actions.moveToElement(jobsFeedLink).click().perform();
		SeleniumSuite.waitForPageLoad(3);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/jobsFeed", driver.getCurrentUrl());
	}

	@Test
	public void testLinkToPlaceAdd(){
		driver.get(SeleniumSuite.URL_UNDER_TEST);
		final WebElement placeAddLink = driver.findElement(By.cssSelector("a[href*='add']"));
		final Actions actions = new Actions(driver);
		actions.moveToElement(placeAddLink).click().perform();
		SeleniumSuite.waitForPageLoad(3);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/add", driver.getCurrentUrl());
	}

}
