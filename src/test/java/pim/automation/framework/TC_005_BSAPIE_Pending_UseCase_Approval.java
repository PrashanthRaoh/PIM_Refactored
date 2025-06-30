package pim.automation.framework;

/************************************************
TC 02 - Updating BSA PIE record which is in  "BSA PIE Pending UseCase Approval" Workflow.
Descrption - This approves BSA PIE Pending UseCase Approval the record
************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import common_functions.BaseTest;
import common_functions.Utils;
import pages.DigitalAsset;
import pages.HomePage;
import pages.Login_Page;
import pages.SearchPage2;
import pages.SummaryPage;

public class TC_005_BSAPIE_Pending_UseCase_Approval extends BaseTest {
	public ExtentTest test;

	@Test(groups = {"BSAPIEowner" })
	public void BSAPIEOwner() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
		Login_Page loginPage = new Login_Page(driver);
		SearchPage2 searchPage = new SearchPage2(driver);
		SummaryPage summaryPage = new SummaryPage(driver);
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

		String Materialdata = Login_Page.getProperty("BSAPendingUsecaseApproval_Matid");
		searchPage.searchthingdomain_Input_Mat_Id().click();
		searchPage.searchthingdomain_Input_Mat_Id().clear();
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Materialdata);
		test.pass("Material id " + Materialdata + " is searched in Search thing domain");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
		Thread.sleep(5000);

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

		utils.waitForElement(() -> searchPage.getgrid(), "clickable");

		List<WebElement> arrrowsdefined = rowsredefined.getShadowRoot().findElements(By.cssSelector(
				"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div > div"));

		System.out.println("Total rows after clicking on Pending Usecase Approval - BSA PIE Inprogress status -- "
				+ arrrowsdefined.size());
		test.pass("Rows after after clicking on Pending Usecase Approval - BSA PIE Inprogress status appeared");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		assertTrue("There should be results after applying filters with Inprogress status", arrrowsdefined.size() > 0);

		WebElement RowByRow = arrrowsdefined.get(0);
		String SellableMaterialDescription = RowByRow
				.findElement(By.cssSelector("div[col-id='sellablematerialdescription']")).getText();
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
		 * --------- Verify workflow tab Hireacrhy. ------- *
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

		System.out.println("******Work flows listed in the Work flow tab are***** ");
		List<String> stepTitles = new ArrayList<>();
		int j = 0;
		for (WebElement step : pebbleSteps) {
			SearchContext stepShadow = step.getShadowRoot();
			WebElement spanTitle = stepShadow.findElement(By.cssSelector("#label > #connectedBadge > #step-heading > #textWrapper > #step-title > span"));

			String title = spanTitle.getAttribute("title");
			stepTitles.add(title);
			System.out.println("Step " + (++j) + ": " + title);
		}
		System.out.println("*************");
		
		test.pass("Workflows hirearchy listed for the record");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		String expectedStep = "Pending Usecase Approval - BSA PIE";
		Assert.assertTrue(stepTitles.contains(expectedStep),
				"Expected state '" + expectedStep + "' not found in: " + stepTitles);

		digitalssetPage.Pending_Use_Case_Approval_Commentinputbox().sendKeys("Approving the record");
		Thread.sleep(2000);
		
		test.pass("Approving the record");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		digitalssetPage.Pending_Use_Case_Approval_Approve_btn().click();
		Thread.sleep(8000);
		
		List<WebElement> pebbleSteps_after = driver.findElement(By.cssSelector("#app")).getShadowRoot()
				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
				.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
				.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector("#entityManageSidebar")).getShadowRoot()
				.findElement(By.cssSelector("#sidebarTabs")).getShadowRoot()
				.findElement(By.cssSelector("[id^='rock-workflow-panel-component-rs']")).getShadowRoot()
				.findElement(By.cssSelector(
						".base-grid-structure > .base-grid-structure-child-2 > #workflows-content > #accordion0 > [slot='accordion-content'] > .workflow-content > #workflowStepper_bsapieusecaseapproval_workflowDefinition"))
				.findElements(By.cssSelector("pebble-step"));
		
		Assert.assertTrue(pebbleSteps_after.size() <= 0);
		test.pass("Record moved to Approved state");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		

		
		
		
		
		
		
		

//		/******************************************** 
//		 * Get number of items under use case approvals 
//		 ***************************************/
//		List<WebElement> summaryElements = driver.findElement(By.cssSelector("#app")).getShadowRoot()
//				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
//				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
//				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
//				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
//				.findElements(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));
//
//		System.out.println("Total items: " + summaryElements.size());
//
//		List<String> expectedItems = Arrays.asList("Pending Usecase Approval - BSA PIE",
//				"On Hold - BSA PIE (User Selected)", "On Hold - BSA PIE (Rule Triggered)");
//
//		Assert.assertEquals(summaryElements.size(), expectedItems.size(), "Item count mismatch");
//		JavascriptExecutor js = (JavascriptExecutor) driver;
//
//		for (int i = 0; i < summaryElements.size(); i++) {
//			WebElement summary = summaryElements.get(i);
//			WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
//			String actualText = innerDiv.getText().trim();
//			System.out.println("Item " + (i + 1) + ":--" + actualText);
//			Assert.assertEquals(actualText, expectedItems.get(i), "Mismatch at item " + (i + 1));
//
//			if (actualText.contains("Pending Usecase Approval - BSA PIE")) {
//				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", innerDiv);
//				try {
//					innerDiv.click(); // Try clicking the real inner part
//					System.out.println("Clicked on " + actualText);
//				} catch (Exception e) {
//					js.executeScript("arguments[0].click();", innerDiv);
//				}
//				Thread.sleep(5000);
//				break;
//			}
//		}
//		test.pass("Pending Usecase Approval - BSA PIE entities listed ");
//		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//		
//		/*************************************** ***** 
//		 * Click on Pending Usecase Approval - BSA PIE ****
//		 ***************************************/
//		utils.waitForElement(() -> searchPage.getgrid(), "clickable");
//		test.pass("Search page grid displayed after clicking on Pending Usecase Approval - BSA PIE");
//		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

//		/*************************************** ***** 
//		 * Click on Pending Usecase Approval - BSA PIE ****
//		 ***************************************/
//		searchPage.getFilterButton().click();
//		System.out.println("Clicked on Filter button");
//		Thread.sleep(2000);
//		utils.waitForElement(() -> searchPage.Search_MaterialType(), "clickable");
//		
//		searchPage.Search_MaterialType().sendKeys("BSA PIE Sellable Product Status");
//		Thread.sleep(5000);
//		
//		digitalssetPage.SellableProductStatus().click();
//		Thread.sleep(3000);
//		digitalssetPage.Status_InProgress_dropdownvalue().click();
//		Thread.sleep(3000);
//		digitalssetPage.Status_Apply_btn().click();
//		Thread.sleep(5000);

//
//		/*************************************************
//		 * --------- Click on search icon and enter hold ------- *
//		 ************************************************/
//		Actions actions2 = new Actions(driver);
//		summaryPage.SearchIcon().click();
//		Thread.sleep(1000);
//		summaryPage.SearchInputfield().sendKeys("hold");
//		Thread.sleep(1500);
//		actions2.moveToElement(summaryPage.SearchInputfield()).sendKeys(Keys.ENTER).build().perform();
//		Thread.sleep(3000);
//		utils.waitForElement(() -> summaryPage.SystemAttributes(), "visible");
//		test.pass("System attributes elemets shown up");
//		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//
//		WebElement HoldMessage = summaryPage.OnholdMessage().findElement(By.cssSelector(".more-values-message"));
//		String onholdMessage = HoldMessage.getText();
//
//		System.out.println("There are " + onholdMessage + " onhold items");
//		test.pass("There are " + onholdMessage + " onhold items");
//		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//
//		HoldMessage.click();
//		Thread.sleep(5000);
//
//		String number = "";
//		Pattern pattern = Pattern.compile("\\d+");
//		Matcher matcher = pattern.matcher(onholdMessage);
//
//		if (matcher.find()) {
//			number = matcher.group();
//		}
//		int totalonholditems = Integer.parseInt(number);
//		System.out.println("Number is " + totalonholditems);
//
//		/*******************************************************
//		 * Get the name of each on hold item system attribute
//		 *******************************************************/
//		for (int i = 1; i <= totalonholditems; i++) {
//			WebElement tag = summaryPage.OnholdMessage().findElement(By.cssSelector("#tag" + i));
//			System.out.println("Tag " + i + ": " + tag.getText().trim());
//		}
//
//		/*******************************************************
//		 * Verify the same entity is not there in On Hold - BSA PIE (User Selected)
//		 *******************************************************/
//		homePage.HomePage_SearchButton().click();
//		Thread.sleep(2000);
//		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
//		homePage.BSAPIEUsecaseApprovalTab().click();
//		Thread.sleep(5000);
//		utils.waitForElement(() -> homePage.BSAPIEUsecaseApprovalTab(), "visible");
//		Thread.sleep(3000);
//		test.pass("I am on Approval tab");
//		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//		Thread.sleep(2000);
//
//		/********************************
//		 * click on On Hold - BSA PIE (User Selected) in approval tab
//		 ********************************/
//		List<WebElement> summaryElements1 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
//				.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='currentApp_home_rs']")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='app-dashboard-component-rs']")).getShadowRoot()
//				.findElement(By.cssSelector("rock-layout > rock-dashboard-widgets")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='rs']")).getShadowRoot().findElement(By.cssSelector("#rock-my-todos"))
//				.getShadowRoot().findElement(By.cssSelector("[id^='rock-my-todos-component-rs']")).getShadowRoot()
//				.findElement(By.cssSelector("#rock-my-todos-tabs")).getShadowRoot()
//				.findElement(By.cssSelector("[id^='my-todo-summary-list-component-rs']")).getShadowRoot()
//				.findElements(By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"));
//		
//		for (int i = 0; i < summaryElements1.size(); i++) {
//			WebElement summary = summaryElements1.get(i);
//			WebElement innerDiv = summary.getShadowRoot().findElement(By.cssSelector("#workflowMetadataContainer"));
//			String actualText = innerDiv.getText().trim();
//			System.out.println("Item " + (i + 1) + ":--" + actualText);
//			if (actualText.contains("On Hold - BSA PIE (User Selected)")) {
//				js.executeScript("arguments[0].scrollIntoView({block: 'center'});", innerDiv);
//				try {
//					innerDiv.click();
//					Thread.sleep(2000);
//				} catch (Exception e) {
//					js.executeScript("arguments[0].click();", innerDiv);
//				}
//				Thread.sleep(5000);
//				break;
//			}
//		}
//		/********************************
//		 * Waiting for On Hold - BSA PIE (User Selected) tab to check material availability
//		 ********************************/
//		utils.waitForElement(() -> searchPage.searchthingdomain_Input_Mat_Id(), "clickable");
//		test.pass("Search page grid displayed after clicking on On Hold - BSA PIE");
//		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

//		
//		/********************************
//		 * Enter the material ID On Hold - BSA PIE (User Selected) 
//		 ********************************/
//		searchPage.searchthingdomain_Input_Mat_Id().click();
//		searchPage.searchthingdomain_Input_Mat_Id().clear();
//		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(matid);
//		test.pass("Material id " + matid + " is searched in On Hold - BSA PIE (User Selected)");
//		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//		searchPage.searchthingdomain_Input_Mat_Id().sendKeys(Keys.ENTER);
//		Thread.sleep(5000);
//
//		/********************************
//		 * Verify no rows are listed
//		 ********************************/
//		try {
//			String txt = searchPage.rowsdisplayedtext().getText();
//			String result = txt.split(" / ")[1];
//			int zerorows = Integer.parseInt(result);
//			System.out.println(zerorows);
//			Assert.assertEquals(zerorows, 0);
//			test.pass(matid + " is not listed under On Hold - BSA PIE (User Selected)");
//			test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//
//		} catch (Exception e) {
//			WebElement rowsredefined2 = driver.findElement(By.cssSelector("#app")).getShadowRoot()
//					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
//					.findElement(By.cssSelector("[id^='currentApp_search-thing_']")).getShadowRoot()
//					.findElement(By.cssSelector("[id^='app-entity-discovery-component-']")).getShadowRoot()
//					.findElement(By.cssSelector("#entitySearchDiscoveryGrid")).getShadowRoot()
//					.findElement(By.cssSelector("#entitySearchGrid")).getShadowRoot()
//					.findElement(By.cssSelector("#entityGrid")).getShadowRoot()
//					.findElement(By.cssSelector("#pebbleGridContainer > pebble-grid")).getShadowRoot()
//					.findElement(By.cssSelector("#grid"));
//			List<WebElement> arrrowsdefined2 = rowsredefined2.getShadowRoot().findElements(By.cssSelector(
//					"#lit-grid > div > div.ag-root-wrapper-body.ag-layout-normal.ag-focus-managed > div.ag-root.ag-unselectable.ag-layout-normal > div.ag-body-viewport.ag-layout-normal.ag-row-no-animation > div.ag-center-cols-clipper > div > div> div.ag-row.ag-row-even.ag-row-level-0"));
//
//			if (arrrowsdefined2.size() > 0) {
//				System.out.println("Records found for the search criteria");
//				test.pass(matid + " completion is NOT 100%. Pleaes verify");
//				test.log(Status.FAIL,
//						MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
//			}
//		}
	}
}