package com.rpm.caash.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JobsFeedST {

	private static WebDriver driver = SeleniumSuite.driver;

	@Test
	public void testJobsFeedCreation() {
		driver.get("http://caash-caash.rhcloud.com/");
		boolean jobsMapIsPresent = driver.findElements(By.cssSelector("ui-gmap-google-map")).size() > 0;
		Assert.assertEquals(false, jobsMapIsPresent);
		boolean jobsFeedList = driver.findElements(By.id("jobsFeedList")).size() > 0;
		Assert.assertEquals(false, jobsFeedList);
		final WebElement jobsFeedLink = driver.findElement(By.cssSelector("a[href*='jobsFeed']"));
		final Actions actions = new Actions(driver);
		actions.moveToElement(jobsFeedLink).click().perform();
		Assert.assertEquals("http://caash-caash.rhcloud.com/#/jobsFeed", driver.getCurrentUrl());
		@SuppressWarnings("unused")
		final WebElement mapCanvas = (new WebDriverWait(driver, 5))
		.until(ExpectedConditions.presenceOfElementLocated(By.id("mapCanvas")));
		jobsMapIsPresent = driver.findElements(By.cssSelector("ui-gmap-google-map")).size() > 0;
		Assert.assertEquals(true, jobsMapIsPresent);
		jobsFeedList = driver.findElements(By.id("jobsFeedList")).size() > 0;
		Assert.assertEquals(true, jobsFeedList);
	}

}
