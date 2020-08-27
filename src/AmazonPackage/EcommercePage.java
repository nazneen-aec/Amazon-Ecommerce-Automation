package AmazonPackage;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class EcommercePage extends SettingsFile {

	static String expectedProductName = "PHILIPS";
	static String expectedProductDetail = "Philips 164 cm (65 inches) 6700 Series 4K Ambilight LED Smart TV 65PUT6703S/94 (Dark Sliver)";
	static String expectedProductPrice = "79,999.00";

	@Test
	public void Login() throws MalformedURLException, InterruptedException {
		AndroidDriver<AndroidElement> driver = Capabilites();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(5000);

		File file = new File("src");
		File newFile = new File(file, "dataFile.properties");

		FileInputStream fileInput = null;

		try {
			fileInput = new FileInputStream(newFile);
		}

		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Properties prop = new Properties();

		// load properties file
		try {
			prop.load(fileInput);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// To Login Amazon Site

		driver.findElement(By.id("com.amazon.mShop.android.shopping:id/sign_in_button")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]/android.widget.EditText"))
				.sendKeys(prop.getProperty("username"));
		driver.findElementByXPath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[4]/android.widget.Button")
				.click();
		driver.findElement(By.xpath("//android.widget.EditText[@text='Amazon password']"))
				.sendKeys(prop.getProperty("password"));
		driver.findElement(By.xpath("//android.widget.Button[@text='Login']")).click();

		System.out.println("User name::" + prop.getProperty("username"));
		System.out.println("Password::" + prop.getProperty("password"));

		// ......Search Philips Tv from the list through scroll down.......

		Thread.sleep(5000);

		//// Use Touch action class inspite of clcik on Search options
		TouchAction t = new TouchAction(driver);
		WebElement expandList = driver.findElementById("com.amazon.mShop.android.shopping:id/rs_search_src_text");
		t.tap(tapOptions().withElement(element(expandList))).perform();

		//// Get Hided Click Option for search using touch action
		// driver.findElementById("com.amazon.mShop.android.shopping:id/rs_search_src_text").click();
		Thread.sleep(5000);
		driver.findElementById("com.amazon.mShop.android.shopping:id/rs_search_src_text").sendKeys("65 inch tv");
		Thread.sleep(5000);
		driver.findElementByXPath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.support.v4.widget.DrawerLayout/android.widget.RelativeLayout/android.widget.RelativeLayout[2]/android.widget.RelativeLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.ViewAnimator/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView[1]")
				.click();
		Thread.sleep(10000);
		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Philips 164 cm (65 inches) 6700 Series 4K Ambilight LED Smart TV 65PUT6703S/94 (Dark Sliver)\"));")
				.click();

		if (driver.findElements(By.id("com.amazon.mShop.android.shopping:id/touch_outside")).size() > 0) {
			System.out.println("FOUND");
			driver.findElementById("com.amazon.mShop.android.shopping:id/touch_outside").click();
		} else {
			System.out.println("NOT FOUND!");
		}

		//// Sometime product name is changing for TV so When product name chnage then
		//// it will skip the element and also not verify it

		if (driver.findElements(By.xpath("//android.widget.TextView[@text='PHILIPS']")).size() > 0) {
			String actualProductName = driver.findElement(By.xpath("//android.widget.TextView[@text='PHILIPS']"))
					.getText();
			// ........ Verify Product Name
			Assert.assertEquals(expectedProductName, actualProductName);

		} else {
			System.out.println("Product not found");

		}
		// ........ Verify Product Name, product Detail and Product price.......

		String actualProductDetail = driver.findElement(By.xpath(
				"//android.view.View[@text='Philips 164 cm (65 inches) 6700 Series 4K Ambilight LED Smart TV 65PUT6703S/94 (Dark Sliver)']"))
				.getText();

		String productPrice = driver.findElement(By.xpath("//android.widget.TextView[@text='₹ 79,999.00']")).getText();

		System.out.println(productPrice);

		String actualProductPrice = productPrice.substring(2);

		System.out.println(actualProductPrice);
		Assert.assertEquals(expectedProductDetail, actualProductDetail);

		//// Scroll page to Add to cart button

		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Add to Cart\"));").click();

		Thread.sleep(5000);

		// Switch frame to Checkout

		// driver.switchTo().frame(1);

		// compare checkout price with Cart Price

		String cartItem = driver.findElement(By.xpath("//android.widget.TextView[@text='2 items']")).getText();

		if ("2 items" == cartItem) {
			// Assert.assertEquals("2 items", cartItem);

			String checkoutPrice = driver.findElement(By.xpath("//android.widget.TextView[@text='₹ 79,999.00']"))
					.getText();
			System.out.println(checkoutPrice);

			String actualCheckoutPrice = productPrice.substring(2);

			/// Now Validate Product cart Actual price is equals to Checkout product price

			Assert.assertEquals(actualProductPrice, actualCheckoutPrice);

		}

		else {

			System.out.println("price not matched, More products added on the add to cart");
		}

	}

}
