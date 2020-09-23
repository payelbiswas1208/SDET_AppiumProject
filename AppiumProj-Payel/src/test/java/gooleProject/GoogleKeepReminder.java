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

public class GoogleKeepReminder {
	WebDriverWait wait;
	AndroidDriver<MobileElement> driver = null;

	@Test
	public void addReminder() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElementByAccessibilityId("Open navigation drawer").click();
		driver.findElement(
				MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/drawer_navigation_reminders\")"))
				.click();
		List<MobileElement> notes_count = driver.findElements(
				MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/browse_text_note\")"));
		int initial_count = (notes_count.size());

		driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/new_note_button\")"))
				.click();
		driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/editable_title\")"))
				.sendKeys("My Notes");
		driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/edit_note_text\")"))
				.sendKeys("Notes add");
		driver.findElementByAccessibilityId("Reminder").click();
		driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/date_spinner\")"))
				.click();
		driver.findElement(
				MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/reminder_date_tomorrow\")"))
				.click();

		driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/time_spinner\")"))
				.click();
		driver.findElement(
				MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/reminder_time_afternoon\")"))
				.click();
		driver.findElement(MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/save\")")).click();

		driver.findElementByAccessibilityId("Open navigation drawer").click();
		driver.findElementByAccessibilityId("Open navigation drawer").click();
		driver.findElement(
				MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/drawer_navigation_reminders\")"))
				.click();
		List<MobileElement> created_notes = driver.findElements(
				MobileBy.AndroidUIAutomator("resourceId(\"com.google.android.keep:id/browse_text_note\")"));
		int new_count = (created_notes.size());

		Assert.assertEquals((new_count - initial_count), 1, "Assert new note is created");
	}

	@BeforeMethod
	public void beforeMethod() throws MalformedURLException {
		DesiredCapabilities caps = new DesiredCapabilities();
		// caps.setCapability("deviceName", "Galaxy A10");
		caps.setCapability("deviceId", "RZ8M62HQCFR");
		caps.setCapability("platformName", "Android");
		caps.setCapability("appPackage", "com.google.android.keep");
		caps.setCapability("appActivity", ".activities.BrowseActivity");
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
