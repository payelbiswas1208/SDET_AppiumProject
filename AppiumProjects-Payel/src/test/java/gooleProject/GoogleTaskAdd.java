package gooleProject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class GoogleTaskAdd {
	WebDriverWait wait;
	AndroidDriver<MobileElement> driver = null;
	String[] tasks = { "Complete Activity with Google Tasks", "Complete Activity with Google Keep",
			"Complete the second Activity Google Keep" };

	@Test
	public void addTask() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		MobileElement add_task_btn = driver
				.findElement(MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.apps.tasks:id/tasks_fab\")"));
		
		//Adding tasks
		for (String s : tasks) {
			// wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(add_task_btn)).click();
			add_task_btn.click();
			driver.findElement(
					MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.apps.tasks:id/add_task_title\")"))
					.sendKeys(s);
			driver.findElement(
					MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.apps.tasks:id/add_task_done\")"))
					.click();
		}

		//MobileElement task_title = driver.findElement(
				//MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.apps.tasks:id/task_list_title\")"));
		//Assert.assertEquals("My Tasks", task_title.getText(), "Assert title is My Tasks");

		//Print tasks
		List<MobileElement> textItems = driver.findElementsByClassName("android.widget.TextView");
		for (MobileElement textItem : textItems) {
			System.out.println(textItem.getText());
		}

		//Assert tasks are added
		Assert.assertTrue(driver.findElementByXPath(
				"//android.widget.FrameLayout[@content-desc=\"Complete the second Activity Google Keep\"]/android.widget.LinearLayout/android.widget.TextView")
				.isDisplayed());
		Assert.assertTrue(driver.findElementByXPath(
				"//android.widget.FrameLayout[@content-desc=\"Complete Activity with Google Keep\"]/android.widget.LinearLayout/android.widget.TextView")
				.isDisplayed());
		Assert.assertTrue(driver.findElementByXPath(
				"//android.widget.FrameLayout[@content-desc=\"Complete Activity with Google Tasks\"]/android.widget.LinearLayout/android.widget.TextView")
				.isDisplayed());

		List<MobileElement> task_num = driver.findElements(
				MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.apps.tasks:id/task_name\")"));
		int count = (task_num.size());

		Assert.assertEquals(count, 3);
	}

	@BeforeMethod
	public void beforeMethod() throws MalformedURLException {

		DesiredCapabilities caps = new DesiredCapabilities();
		// caps.setCapability("deviceName", "Galaxy A10");
		caps.setCapability("deviceId", "RZ8M62HQCFR");
		caps.setCapability("platformName", "Android");
		caps.setCapability("appPackage", "com.google.android.apps.tasks");
		caps.setCapability("appActivity", ".ui.TaskListsActivity");
		caps.setCapability("noReset", true);

		URL appServer = new URL("http://0.0.0.0:4723/wd/hub");
		driver = new AndroidDriver<MobileElement>(appServer, caps);
		wait = new WebDriverWait(driver, 10);

	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
