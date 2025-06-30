package pim.automation.framework;

/************************************************
TC 06 - User updating "BSA PIE Regions (Override)"
Descrption - This updates the value in BSA PIE Regions (Override)
and approves the record
************************************************/
import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class TC_006_BSAPIE_User_updating_BSA_PIE_Regions_Override extends BaseTest {
	public ExtentTest test;

	@Test(groups = {"BSAPIEowner"})
	public void BSAPIEOwner() throws InterruptedException, IOException {
		String className = this.getClass().getSimpleName();
		System.out.println(className);
		test = BaseTest.extentreport.createTest(className);
		test.assignAuthor(System.getProperty("user.name")).assignCategory("Regression").assignDevice(System.getenv("COMPUTERNAME"));

		homePage = new HomePage(driver);
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

		String Materialdata = Login_Page.getProperty("BSAPIEUsecaseSalesOrgRegionsOverride");
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
		test.pass("Workflows hirearchy listed for the record" + stepTitles);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());

		String expectedStep = "Pending Usecase Approval - BSA PIE";
		Assert.assertTrue(stepTitles.contains(expectedStep),"Expected state '" + expectedStep + "' not found in: " + stepTitles);
		
		/*************************************************
		 * --------- Click on drop down next to Attributes tab
		 ************************************************/
		WebElement dropdownWrapper = driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
			    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			    .findElement(By.cssSelector("#tab-attributes")).getShadowRoot()
			    .findElement(By.cssSelector("#dropdown-wrapper"));

				dropdownWrapper.click();
				Thread.sleep(2000);
				digitalssetPage.Use_Case_Attributes_selection().click();
				Thread.sleep(2000);
		
		/*************************************************
		 * --------- Get the value which is in BSA PIE Usecase Sales Org Regions (Auto) ------- *
		 ************************************************/
		WebElement BSAPIEUsecaseSalesOrgRegions_Auto = driver.findElement(By.cssSelector("#app")).getShadowRoot()
			    .findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#rockDetailTabs")).getShadowRoot()
			    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
			    .findElement(By.cssSelector("[id^='rock-attribute-split-screen-component-rs']")).getShadowRoot()
			    .findElement(By.cssSelector("#undefined-attribute-container > rock-attribute-manage")).getShadowRoot()
			    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
			    .findElements(By.cssSelector("[id^='rs']")).get(8).getShadowRoot()
			    .findElement(By.cssSelector("#input")).getShadowRoot()
			    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
			    .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
			    .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container > pebble-tags")).getShadowRoot()
			    .findElement(By.cssSelector("#tag0")).getShadowRoot()
			    .findElement(By.cssSelector("#pebble-tag"))
			    .findElement(By.cssSelector(".tag-item > .tag-name-value > .attribute-name"));
		
		utils.waitForElement(() -> BSAPIEUsecaseSalesOrgRegions_Auto, "visible");

		String UsecaseSORegionsvalue = BSAPIEUsecaseSalesOrgRegions_Auto.getText();
		System.out.println("BSAPIEUsecaseSalesOrgRegions_Auto value is : -  " + UsecaseSORegionsvalue);
		test.pass("BSA PIE Usecase Sales Org Regions (Auto) value is " + UsecaseSORegionsvalue);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		/*************************************************
		 * --------- Update the same data to BSA PIE Usecase Sales Org Regions (Override) which is in BSA PIE Usecase Sales Org Regions (Auto ------- *
		 ************************************************/
		summaryPage.SearchIcon().click();
		Thread.sleep(1000);
		summaryPage.SearchInputfield().sendKeys("BSA PIE Usecase Sales Org Regions (Override)",Keys.ENTER);
		Thread.sleep(5000);
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(digitalssetPage.BSAPIEUsecaseSalesOrgRegions_Override_dropdown()));

		Assert.assertTrue(dropdown.isDisplayed(),"Dropdown element 'BSAPIEUsecaseSalesOrgRegions_Override' should be visible");
		dropdown.click();
		Thread.sleep(2000);
		digitalssetPage.BSAPIEUsecaseSalesOrgRegions_Override_Search_Input().sendKeys(UsecaseSORegionsvalue);
		Thread.sleep(3000);
		utils.waitForElement(()-> digitalssetPage.Total_Checkboxes(), "clickable");
		List<WebElement> totalcbs = digitalssetPage.Total_Checkboxes().findElements(By.cssSelector("[ref='eBodyViewport'] > [name='left'] > [role='row']"));
		System.out.println("There are " + totalcbs.size() + " Regions for the search " + UsecaseSORegionsvalue);
		assertTrue("There should be results after searching for " + UsecaseSORegionsvalue, totalcbs.size() > 0);
		test.pass("Regions fetched after searching " + UsecaseSORegionsvalue);
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		totalcbs.get(0).click();
		Thread.sleep(5000);
		test.pass("Selected " + UsecaseSORegionsvalue + " as search value");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		
		digitalssetPage.Save_btn_BSA_PIE_Transaction().click();
		test.pass("Transaction saved ");
		test.log(Status.INFO, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
		/*************************************************
		 * --------- Enter the comments to approve the record
		 ************************************************/
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
		
		Assert.assertEquals(pebbleSteps_after.size(), 0, "Expected no pebble-step elements, but found some.");

		test.pass("Record moved to Approved state");
		test.log(Status.PASS, MediaEntityBuilder.createScreenCaptureFromPath(Utils.Takescreenshot(driver)).build());
	}
}