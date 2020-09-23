package chromeProject;

import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import org.testng.annotations.BeforeMethod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class ToDoListTest {

	MobileDriver<MobileElement> driver;
	String[] toDos = { "Add tasks to list", "Get number of tasks", "Clear the list" };

	@Test
	public void toDoList() {
		driver.findElement(MobileBy.AndroidUIAutomator(
				"UiScrollable(UiSelector().scrollable(true).instance(0)).scrollIntoView(textStartsWith(\"To-Do List\"))"));
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(MobileBy.xpath("//android.widget.TextView[@text='To-Do List']")).click();

		// Clear toDos initially

		driver.findElement(
				By.xpath("//android.webkit.WebView/android.view.View/android.view.View[3]/android.view.View[3]"))
				.click();

		//Adding tasks
		
		for (String s : toDos) {
			driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"taskInput\")")).sendKeys(s);
			driver.findElement(MobileBy.AndroidUIAutomator("text(\"Add Task\")")).click();
		}
		
		//Assert tasks are added
		
		Assert.assertTrue(driver.findElement(MobileBy.AndroidUIAutomator("text(\"Add tasks to list\")")).isDisplayed());
		Assert.assertTrue(driver.findElement(MobileBy.AndroidUIAutomator("text(\"Clear the list\")")).isDisplayed());
		Assert.assertTrue(
				driver.findElement(MobileBy.AndroidUIAutomator("text(\"Get number of tasks\")")).isDisplayed());

		//Strikeout tasks
		List<MobileElement> created_List = driver
				.findElements(MobileBy.AndroidUIAutomator("resourceId(\"tasksList\")"));
		for (MobileElement me : created_List) {
			me.click();
		}

		driver.findElement(
				By.xpath("//android.webkit.WebView/android.view.View/android.view.View[3]/android.view.View[3]"))
				.click();

		List<MobileElement> cleared_Tasks = driver
				.findElements(MobileBy.AndroidUIAutomator("resourceId(\"tasksList\")"));
		Assert.assertEquals(cleared_Tasks.size(), 0);
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

		driver.get("https://www.training-support.net/selenium");
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
