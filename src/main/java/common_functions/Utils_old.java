package common_functions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.NoSuchElementException;
import java.util.function.Supplier;
import com.aventstack.extentreports.MediaEntityBuilder;

public class Utils extends BaseTest {
	private WebDriver driver;
	public WebDriverWait wait;
	final Exception[] lastException = { null };

	public Utils(WebDriver driver) {
		this.driver = driver;
	}

	/*****************************************************
	 ************* Updated code  ************************
	 ******************************************************/

	public WebElement waitForElement(Supplier<WebElement> elementSupplier, String conditionType) {
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(50));
		final Exception[] lastException = { null }; // Track last exception for reporting

		try {
			switch (conditionType.toLowerCase()) {
			case "clickable": {
				return wait.until(driver -> {
					try {
						WebElement el = elementSupplier.get();
						if (el.isDisplayed() && el.isEnabled()) {
							return el;
						}
					} catch (StaleElementReferenceException | NoSuchElementException e) {
						lastException[0] = e;
					}
					return null;
				});
			}

			case "visible": {
				return wait.until(driver -> {
					try {
						WebElement el = elementSupplier.get();
						if (el.isDisplayed()) {
							return el;
						}
					} catch (StaleElementReferenceException | NoSuchElementException e) {
						lastException[0] = e;
					}
					return null;
				});
			}

			case "invisibility": {
				wait.until(driver -> {
					try {
						return !elementSupplier.get().isDisplayed();
					} catch (StaleElementReferenceException | NoSuchElementException e) {
						return true;
					}
				});
				return null;
			}

			default:
				throw new IllegalArgumentException("Invalid wait condition type: " + conditionType);
			}

		} catch (Exception e) {
			try {
				String screenshotPath = Takescreenshot(driver);
				BaseTest.extentTest.fail("Unexpected error while waiting for element: " + e.getMessage(),
						MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
			} catch (IOException io) {
				io.printStackTrace();
			}
			throw new RuntimeException("Unexpected error in waitForElement: " + e.getMessage(), e);
		}
	}

	public static String Takescreenshot(WebDriver driver) throws IOException {
		Date today = new Date();
		SimpleDateFormat SIMPDFORMAT = new SimpleDateFormat("ddMMYY_HHmmss");
		String date = SIMPDFORMAT.format(today);
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File Destinationfilepath = new File("./Screenshots/ " + date + ".png");
		FileUtils.copyFile(src, Destinationfilepath);
		String destinationpath = Destinationfilepath.getAbsolutePath();
		return destinationpath;
	}

	public String getClassName(Object obj) {
		return obj.getClass().getSimpleName();
	}
}