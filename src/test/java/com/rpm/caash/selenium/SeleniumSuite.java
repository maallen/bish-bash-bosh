package com.rpm.caash.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Suite.class)
@SuiteClasses({ HomepageST.class, JobsFeedST.class, PlaceAddST.class })
public class SeleniumSuite {

	protected static WebDriver driver;
	protected static String address = "http://caash-caash.rhcloud.com/";
	private static String os;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		os = System.getProperty("os.name").toLowerCase();
		if(os.contains("linux")){
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/linux_x64/chromedriver");
		}else{
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver/windows/chromedriver.exe");
		}

		driver = new ChromeDriver();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		driver.close();
		driver = null;
	}

	protected static void waitForPageLoad(int timeoutInSeconds){
		timeoutInSeconds = timeoutInSeconds * 1000;
		try {
			Thread.sleep(timeoutInSeconds);
		} catch (final InterruptedException e) {}
	}
}
