package com.rpm.caash.selenium;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class JobsFeedTest {

	private static WebDriver driver;
	private static String os;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = System.getProperty("os.name").toLowerCase();
		if(os.contains("linux")){
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/linux_x64/chromedriver");
		}else{
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/windows/chromedriver");
		}
		driver = new ChromeDriver();
	}


	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
		driver = null;
	}

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
		final WebElement mapCanvas = (new WebDriverWait(driver, 5))
				.until(ExpectedConditions.presenceOfElementLocated(By.id("mapCanvas")));
		jobsMapIsPresent = driver.findElements(By.cssSelector("ui-gmap-google-map")).size() > 0;
		Assert.assertEquals(true, jobsMapIsPresent);
		jobsFeedList = driver.findElements(By.id("jobsFeedList")).size() > 0;
		Assert.assertEquals(true, jobsFeedList);
	}

}
