package AmazonPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class readData extends SettingsFile {

	public static void main(String[] args) throws InterruptedException, IOException {
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

	}
}
