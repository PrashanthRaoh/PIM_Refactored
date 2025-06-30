package common_functions;

import org.testng.annotations.BeforeSuite;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.beust.jcommander.Parameter;

import pages.HomePage;
import pages.Login_Page;

public class BaseTest {
	public static WebDriver driver;
	public static Utils utils;
	public static Login_Page loginPage;
	public HomePage homePage;
	public static ExtentReports extentreport;
	public static ExtentTest extentTest;
	public static final String filepathname = "target/PIM_Report.html";

	public void BaseTest() {
		this.driver = driver;
	}
	
	@BeforeSuite
	public void intitializeReport() {
		extentreport = new ExtentReports();
		ExtentSparkReporter sparkReporter = new ExtentSparkReporter(filepathname);
		sparkReporter.config().setTheme(Theme.DARK);
		sparkReporter.config().setDocumentTitle("PIM Automation Report");
		extentreport.attachReporter(sparkReporter);
	}
	
	@AfterSuite
	public void publishReport() throws IOException {
		extentreport.flush();
		Desktop.getDesktop().browse(new File(filepathname).toURI());
	}

	@Parameters("UseCaseOwner")
	@BeforeClass
	public void setUp(String UseCaseOwner) throws IOException {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
		driver.manage().window().maximize();
		utils = new Utils(driver,extentTest);
		loginPage = new Login_Page(driver);
		loginPage.LogintoPIM(UseCaseOwner);
		homePage = new HomePage(driver);
	}
	
//	@AfterClass
	public void Logout() throws InterruptedException {
		homePage.AppHeader_Administrator().click();
		Thread.sleep(1000);
		homePage.Logout_btn().click();
		WebDriverWait wait3 = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait3.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector("[id='username']"))));
		Thread.sleep(2000);
		driver.quit();
	}
}