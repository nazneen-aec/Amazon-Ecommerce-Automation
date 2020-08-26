package AmazonPackage;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class Orientation extends SettingsFile {

	public static void main(String[] args) throws MalformedURLException, InterruptedException {

		File appDir = new File("src");
		File app = new File(appDir, "Amazon_shopping.apk");

		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel2");
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		capabilities.setCapability("autoAcceptAlerts", "true");
		capabilities.setCapability("appPackage", "com.amazon.mShop.android.shopping");
		capabilities.setCapability("appActivity", "com.amazon.mShop.home.HomeActivity");
		capabilities.setCapability("unicodeKeyboard", "true");
		capabilities.setCapability("resetKeyboard", "true");
		// capabilities.setCapability("applicationName", "MeveroApplication");

		capabilities.setCapability("newCommandTimeout", 100);
		AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(new URL("http://0.0.0.0:4723/wd/hub"),
				capabilities);

		// Get and print current screen orientation.
		System.out.println("*--*--*-- Current screen orientation Is : " + driver.getOrientation());
		System.out.println("*--*--*-- Changing screen Orientation to LANDSCAPE.");
		// Changing screen Orientation to LANDSCAPE.
		driver.rotate(org.openqa.selenium.ScreenOrientation.LANDSCAPE);
		// Get and print screen orientation after changing It.
		System.out.println("*--*--*-- Now screen orientation Is : " + driver.getOrientation());
		Thread.sleep(5000);
		driver.findElement(By.id("com.amazon.mShop.android.shopping:id/sign_in_button")).click();
		Thread.sleep(5000);
		
		// Scroll till element which contains "Views" text If It Is not visible on
		// screen.
		// driver.scrollTo("Views");
		// Click on Views.
		// driver.findElement(By.name("Views")).click();
		System.out.println("*--*--*-- Changing screen Orientation to PORTRAIT.");
		// Changing screen Orientation to PORTRAIT.
		driver.findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]/android.widget.EditText"))
				.sendKeys("9330024800");
		;
		driver.findElementByXPath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[4]/android.widget.Button")
				.click();
		driver.findElement(By.id("ap_password")).sendKeys("admin#123");
		driver.findElement(By.xpath("//android.widget.Button[@text='Login']")).click();


	}
}
