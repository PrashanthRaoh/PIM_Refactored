package common_functions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

public class Utils {
	private WebDriver driver;
	private WebDriverWait wait;
	private ExtentTest test;

	public Utils(WebDriver driver, ExtentTest test) {
		this.driver = driver;
		this.test = test;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
	}

	public WebElement waitForElement(Supplier<WebElement> elementSupplier, String conditionType) {
		try {
			switch (conditionType.toLowerCase()) {
				case "clickable":
					return wait.until(driver -> {
						try {
							WebElement el = elementSupplier.get();
							return (el != null && el.isDisplayed() && el.isEnabled()) ? el : null;
						} catch (Exception ignored) {
							return null;
						}
					});

				case "visible":
					return wait.until(driver -> {
						try {
							WebElement el = elementSupplier.get();
							return (el != null && el.isDisplayed()) ? el : null;
						} catch (Exception ignored) {
							return null;
						}
					});

				case "invisibility":
					wait.until(driver -> {
						try {
							WebElement el = elementSupplier.get();
							return el == null || !el.isDisplayed();
						} catch (Exception ignored) {
							return true;
						}
					});
					return null;

				default:
					throw new IllegalArgumentException("Invalid wait condition type: " + conditionType);
			}
		} catch (Exception e) {
			try {
				String screenshotPath = Takescreenshot(driver);
				if (test != null) {
					test.fail("Wait failed: " + e.getMessage(),
						MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
				}
			} catch (IOException io) {
				io.printStackTrace();
			}
			throw new RuntimeException("waitForElement failed: " + e.getMessage(), e);
		}
	}

	public static WebElement safeShadowDomChain(WebDriver driver, List<By> selectors) {
		try {
			SearchContext context = driver;
			for (int i = 0; i < selectors.size() - 1; i++) {
				WebElement el = context.findElement(selectors.get(i));
				context = el.getShadowRoot();
			}
			return context.findElement(selectors.get(selectors.size() - 1));
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return null;
		}
	}

	public static String Takescreenshot(WebDriver driver) throws IOException {
		String date = new SimpleDateFormat("ddMMYY_HHmmss").format(new Date());
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dest = new File("./Screenshots/" + date + ".png");
		FileUtils.copyFile(src, dest);
		return dest.getAbsolutePath();
	}
	
	public static List<By> extend(List<By> baseChain, By... additional) {
        List<By> extended = new ArrayList<>(baseChain);
        extended.addAll(Arrays.asList(additional));
        return extended;
    }

    public static List<By> extend(List<By> baseChain, List<By> additional) {
        List<By> extended = new ArrayList<>(baseChain);
        extended.addAll(additional);
        return extended;
    }
}
