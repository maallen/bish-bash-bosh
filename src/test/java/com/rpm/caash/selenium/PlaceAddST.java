package com.rpm.caash.selenium;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class PlaceAddST {

	private final WebDriver driver = SeleniumSuite.driver;

	@Test
	public void testAddJobForm() {
		driver.get(SeleniumSuite.URL_UNDER_TEST);
		final WebElement placeAddLink = driver.findElement(By.cssSelector("a[href*='add']"));
		final Actions actions = new Actions(driver);
		actions.moveToElement(placeAddLink).click().perform();
		SeleniumSuite.waitForPageLoad(3);
		Assert.assertEquals(SeleniumSuite.URL_UNDER_TEST + "#/add", driver.getCurrentUrl());
		driver.findElement(By.id("jobTitle")).sendKeys("testJob");
		driver.findElement(By.id("jobDesc")).sendKeys("testDescription");
		driver.findElement(By.id("jobLocation")).sendKeys("Athlone");
		driver.findElement(By.id("jobPrice")).sendKeys("100");
		SeleniumSuite.waitForPageLoad(3);
	}

}
