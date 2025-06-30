package pim.automation.framework;

/************************************************
TC 03 BSA PIE - Updating  BSA PIE record which is "On Hold - BSA PIE (User Selected)".
Descrption - Updates the approve record to hold by user
************************************************/
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.Utils;
import pages.BSAPIE_Page;
import pages.DigitalAsset;
import pages.HomePage;
import pages.Login_Page;
import pages.SearchPage2;
import pages.SummaryPage;

public class TC_007_OnHold_BSA_OnHold_UserSelected extends BaseTest {
	public ExtentTest test;

	@Test(groups = { "BSAPIEowner" })
	public void BSAPIEOwner() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
		BSAPIE_Page BSAPIE_Page = new BSAPIE_Page(driver);
		DigitalAsset digitalssetPage = new DigitalAsset(driver);

		utils.waitForElement(() -> homePage.sellablematerialtabelement(), "clickable");

		test.pass("Home Page is displayed");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");

		/**********************************
		 * Enter the Material ID which has all the attributes completed
		 **********************************/
		homePage.clickSearch_Products_Button().click();
		Thread.sleep(5000);

		utils.waitForElement(() -> searchPage.getgrid(), "clickable");

		String Materialdata = Login_Page.getProperty("BSAPIEOnHoldUserselect");
		searchPage.searchthingdomain_Input_Mat_Id().click();
		searchPage.searchthingdomain_Input_Mat_Id().clear();
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Materialdata);
		test.pass("Material id " + Materialdata + " is searched in Search thing domain");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
		Thread.sleep(5000);

		/**************************************************
		 * ***** Click Use case approval tab
		 **************************************************/
		Thread.sleep(2000);
		homePage.BSAPIEUsecaseApprovalTab().click();
		Thread.sleep(5000);
		test.pass("Clicked on Approval tab");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		Thread.sleep(2000);

		/********************************************
		 * Get number of items under use case approvals
		 ***************************************/
		List<WebElement> summaryElements = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
				.findElements(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));

		System.out.println("Total items: " + summaryElements.size());

		List<String> expectedItems = Arrays.asList("Pending Usecase Approval - BSA PIE",
				"On Hold - BSA PIE (User Selected)", "On Hold - BSA PIE (Rule Triggered)");

		Assert.assertEquals(summaryElements.size(), expectedItems.size(), "Item count mismatch");
		JavascriptExecutor js = (JavascriptExecutor) driver;

		for (int i = 0; i < summaryElements.size(); i++) {
			WebElement summary = summaryElements.get(i);
			WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
			String actualText = innerDiv.getText().trim();
			System.out.println("Item " + (i + 1) + ":--" + actualText);
			Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));

			if (actualText.contains("Pending Usecase Approval - BSA PIE")) {
				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", innerDiv);
				try {
					innerDiv.click();
				} catch (Exception e) {
					js.executeScript("arguments[0].click();", innerDiv);
				}
				Thread.sleep(3000);
				break;
			}
		}
		test.pass("BSA PIE Use case Approval entities listed ");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/***************************************
		 * ***** Click on On Hold - BSA PIE(Rule Triggered) ****
		 ***************************************/
		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
		test.pass("Search page grid displayed after clicking on Pending Usecase Approval - BSA PIE");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/**************************************************
		 * --------- Apply filter for BSA PIE with staus Pending ------- *
		 ************************************************/
		searchPage.getFilterButton().click();
		System.out.println("Clicked on Filter button");
		Thread.sleep(2000);
		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");

		searchPage.Search_MaterialType().sendKeys("BSA PIE Sellable Product Status");
		Thread.sleep(5000);
		utils.waitForElement(() -> digitalssetPage.SellableProductStatus(), "clickable");
		digitalssetPage.SellableProductStatus().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> digitalssetPage.Status_InProgress_dropdownvalue(), "clickable");
		digitalssetPage.Status_InProgress_dropdownvalue().click();

		Thread.sleep(2000);
		digitalssetPage.Status_Apply_btn().click();
		Thread.sleep(3000);

		/**************************************************
		 * --------- Get Row count------- *
		 ********************************************************/
		Actions actions = new Actions(driver);
		WebElement rowsredefined = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
				.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
				.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
				.findElement(By.cssSelector("#grid"));

		List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		utils.waitForElement(() -> searchPage.getgrid(), "clickable");

		System.out.println("Total rows after clicking on In Progress sellable product " + arrrowsdefined.size());
		test.pass("Rows after after clicking on In Progress sellable product");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after selecting In Progress sellable product", arrrowsdefined.size() > 0);

		WebElement RowByRow = arrrowsdefined.get(0);
		String SellableMaterialDescription = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
		String matid = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']")).getText();
		System.out.println("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription);

		/*************************************************
		 * --------- Click on the materialid from the result------- *
		 ************************************************/
		WebElement matidElement = RowByRow.findElement(By.cssSelector("div[col-id='sellablematerialid']"));
		actions.moveToElement(RowByRow).build().perform();
		Thread.sleep(2000);
		matidElement.click();
		Thread.sleep(3000);
		utils.waitForElement(() -> summaryPage.Things_INeedToFix(), "visible");
		test.pass("Material ID -- " + matid + " Material Description --" + SellableMaterialDescription
				+ " is selected for completion");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/*************************************************
		 * --------- Verify Pending Usecase Approval - BSA PIE is In Progress. ------- *
		 ************************************************/
		List<WebElement> pebbleSteps = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
				.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector(
						".base-grid-structure > .base-grid-structure-child-2 > #workflows-content > #accordion0 > [slot='accordion-content'] > .workflow-content > #workflowStepper_bsapieusecaseapproval_workflowDefinition"))
				.findElements(By.cssSelector("pebble-step"));

		System.out.println("****** Workflows listed in the Workflow tab are *****");
//
		boolean isInProgress = false;
		String targetTitle = "Pending Usecase Approval - BSA PIE";

		for (int i = 0; i < pebbleSteps.size(); i++) {
			WebElement step = pebbleSteps.get(i);
			SearchContext stepShadow = step.getShadowRoot();

			String title = stepShadow.findElement(By.cssSelector("#label > #connectedBadge > #step-heading > #textWrapper > #step-title > span"))
					.getAttribute("title");

			boolean inProgress = step.getAttribute("class") != null && step.getAttribute("class").contains("iron-selected");
			System.out.println((i + 1) + ": " + title + (inProgress ? " (In Progress)" : ""));

			if (title.equals(targetTitle)) {
				if (inProgress) {
					System.out.println("As Expected : '" + targetTitle + "' is In Progress.");
					isInProgress = true;
					break; 
				} else {
					throw new AssertionError("As Expected : '" + targetTitle + "' is NOT in progress.");
				}
			}
		}

		if (!isInProgress) {
			throw new AssertionError(targetTitle + "' not found in the workflow.");
		}

		/*************************************************
		 * --------- Click on drop down next to Attributes tab
		 ************************************************/
		WebElement dropdownWrapper = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot().findElement(By.cssSelector("#rockTabs"))
				.getShadowRoot().findElement(By.cssSelector("#tab-attributes")).getShadowRoot()
				.findElement(By.cssSelector("#dropdown-wrapper"));

		dropdownWrapper.click();
		Thread.sleep(2000);
		digitalssetPage.Use_Case_Attributes_selection().click();
		Thread.sleep(2000);

		utils.waitForElement(() -> BSAPIE_Page.BSAPIEUsecaseSalesOrgRegions_Auto(), "visible");
		/*************************************************
		 * --------- Click on search icon and enter hold ------- *
		 ************************************************/
		Thread.sleep(2000);
		Actions actions2 = new Actions(driver);
		summaryPage.SearchIcon().click();
		Thread.sleep(1000);
		summaryPage.SearchInputfield().sendKeys("hold");
		Thread.sleep(1000);
		actions2.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(3000);
		utils.waitForElement(() -> summaryPage.SystemAttributes(), "visible");
		test.pass("System attributes elemets shown up");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		/*************************************************
		 * --------- Wait for BSAPIESelectAttributesforHold object ------- *
		 ************************************************/
		utils.waitForElement(() -> BSAPIE_Page.BSAPIESelectAttributesforHold(), "clickable");

		/*************************************************
		 * --------- Wait for BSAPIESelectAttributesforHold object ------- *
		 ************************************************/
		BSAPIE_Page.BSAPIESelectAttributesforHold().click();
		Thread.sleep(3000);
		utils.waitForElement(() -> digitalssetPage.Total_Checkboxes(), "clickable");
		List<WebElement> totalcbs = digitalssetPage.Total_Checkboxes()
				.findElements(By.cssSelector("[ref='eBodyViewport'] > [name='left'] > [role='row']"));
		System.out.println("There are " + totalcbs.size() + " On hold items ");
		assertTrue("Manual Hold items  available are ---  ", totalcbs.size() > 0);
		test.pass("Manual Hold  items available");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		totalcbs.get(0).click();
		Thread.sleep(5000);
		test.pass("Selected the first hold item");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		digitalssetPage.Save_btn_BSA_PIE_Transaction().click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		Function<WebDriver, WebElement> getBannerElement = drv -> {
			try {
				return drv.findElement(By.cssSelector("#app")).getShadowRoot()
					.findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
					.findElement(By.cssSelector("#pebbleAppToast > pebble-echo-html")).getShadowRoot()
					.findElement(By.cssSelector("#bind-html"));
			} catch (Exception e) {
				return null;
			}
		};

		WebElement banner = wait.until(driver -> {
			WebElement el = getBannerElement.apply(driver);
			return (el != null && el.isDisplayed()) ? el : null;
		});

		String bannerText = banner.getText();
		System.out.println("✅ Banner appeared: " + bannerText);

		// Wait for banner to disappear
		new WebDriverWait(driver, Duration.ofSeconds(5)).until(driver -> {
			WebElement el = getBannerElement.apply(driver);
			return el == null || !el.isDisplayed();
		});

		System.out.println("✅ Banner disappeared.");
	}
}