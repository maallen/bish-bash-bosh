package com.rpm.caash.selenium;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Suite.class)
@SuiteClasses({ HomepageST.class, JobsFeedST.class })
public class SeleniumSuite {

	protected static WebDriver driver;
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
}
