package com.rpm.caash.selenium;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Suite.class)
@SuiteClasses({ HomepageST.class, JobsFeedST.class, PlaceAddST.class, ProfileST.class })
public class SeleniumSuite {

    protected static String SUCCESSFUL_EMAIL = "testaccount@gmail.com";
    protected static String SUCCESSFUL_PASSWORD = "11111111";

	protected static WebDriver driver;
	protected static String URL_UNDER_TEST;
	private static String os;

	static {
		final Properties props = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File("src/test/resources/selenium.properties"));
			props.load(inputStream);
			URL_UNDER_TEST = props.getProperty("URL_UNDER_TEST");
		}catch (final IOException e) {
			e.printStackTrace();
		}
	}

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
