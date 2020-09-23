package chromeProject;

import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class LoginPopup {

	MobileDriver<MobileElement> driver;
	WebDriverWait wait;

	@Test
	public void loginUser() {

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.view.View")));
		driver.findElement(MobileBy.AndroidUIAutomator(
				"UiScrollable(UiSelector().scrollable(true).instance(0)).scrollIntoView(textStartsWith(\"Popups\"))"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(MobileBy.xpath("//android.widget.TextView[@text='Popups']")).click();

		String userName = "admin";
		String passWord = "password";

		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Sign In\")")).click();

		WebElement user = driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"username\")"));
		WebElement pass = driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"password\")"));

		user.sendKeys(userName);
		pass.sendKeys(passWord);
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Log in\")")).click();

		String loginMessage = driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"action-confirmation\")"))
				.getText();

		Assert.assertEquals(loginMessage, "Welcome Back, admin");

		String userName1 = "payel123";
		String passWord1 = "password123";

		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Sign In\")")).click();

		user.clear();
		pass.clear();
		user.sendKeys(userName1);
		pass.sendKeys(passWord1);
		driver.findElement(MobileBy.AndroidUIAutomator("text(\"Log in\")")).click();

		String loginMessageInv = driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"action-confirmation\")"))
				.getText();

		Assert.assertEquals(loginMessageInv, "Invalid Credentials");

	}

	@BeforeMethod
	public void beforeMethod() throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		// caps.setCapability("deviceName", "Galaxy A10");
		caps.setCapability("deviceId", "RZ8M62HQCFR");
		caps.setCapability("platformName", "android");
		caps.setCapability("automationName", "UiAutomator2");
		caps.setCapability("appPackage", "com.android.chrome");
		caps.setCapability("appActivity", "com.google.android.apps.chrome.Main");
		caps.setCapability("noReset", true);

		URL appServer = new URL("http://0.0.0.0:4723/wd/hub");

		driver = new AndroidDriver<MobileElement>(appServer, caps);

		wait = new WebDriverWait(driver, 30);
		driver.get("https://www.training-support.net/selenium");
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();

	}
}
