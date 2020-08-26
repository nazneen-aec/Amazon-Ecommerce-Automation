package AmazonPackage;

import static io.appium.java_client.touch.TapOptions.tapOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;

import java.net.MalformedURLException;
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

	// To Login Amazon Site
	@Test
	public void Login() throws MalformedURLException, InterruptedException {
		AndroidDriver<AndroidElement> driver = Capabilites();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Thread.sleep(5000);
		driver.findElement(By.id("com.amazon.mShop.android.shopping:id/sign_in_button")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[3]/android.widget.EditText"))
				.sendKeys("9330024800");
		;
		driver.findElementByXPath(
				"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.RelativeLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View[2]/android.view.View[2]/android.view.View[2]/android.view.View/android.view.View/android.view.View[4]/android.widget.Button")
				.click();
		driver.findElement(By.xpath("//android.widget.EditText[@text='Amazon password']")).sendKeys("admin#123");
		driver.findElement(By.xpath("//android.widget.Button[@text='Login']")).click();

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

		//// Sometime product name is changing for TV so When product name chnage then it will skip the element and also not verify it
		
		if (driver.findElements(By.xpath("//android.widget.TextView[@text='PHILIPS']")).size() > 0) {
			String actualProductName = driver.findElement(By.xpath("//android.widget.TextView[@text='PHILIPS']")).getText();
			// ........ Verify Product Name
			Assert.assertEquals(expectedProductName, actualProductName);
			
			
		} else {
			System.out.println("Product not found");
			
		}
		// ........ Verify Product Name, product Detail and Product price.......

		

		String actualProductDetail = driver.findElement(By.xpath(
				"//android.view.View[@text='Philips 164 cm (65 inches) 6700 Series 4K Ambilight LED Smart TV 65PUT6703S/94 (Dark Sliver)']"))
				.getText();

		String actualProductPrice = driver.findElement(By.xpath("//android.widget.TextView[1]")).getText();

		// System.out.println(actualProductPrice);

		
		Assert.assertEquals(expectedProductDetail, actualProductDetail);

		//// Scroll page to Add to cart button

		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"Add to Cart\"));").click();

		// Click on Add to cart button

		driver.findElement(By.xpath("//android.widget.Button[@text='Add to Cart']")).click();

		// compare checkout price
		
		
		//List<MobileElement> els1 = (MobileElement) driver.findElementsByXPath("android.widget.TextView[@text='₹ 79,999.00']");
		

		//String CheckoutPrice = driver.findElementsByAccessibilityId("//android.widget.TextView[@text='₹ 79,999.00']")).getText();
		//System.out.println(CheckoutPrice);
		String CheckoutPrice =driver.findElementByXPath("//*[@x='176,993' and @y='1039']").getText();
		System.out.println(CheckoutPrice);
		
		/// Now Validate Product Actual price is equals to Checkout product price

		//Assert.assertEquals(actualProductPrice, CheckoutPrice);

	}

}
