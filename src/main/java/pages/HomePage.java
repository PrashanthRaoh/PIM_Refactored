package pages;

import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import common_functions.Utils;

public class HomePage {
	private WebDriver driver;

	private static final List<By> BASE_TASK_LIST_CHAIN = List.of(
		By.cssSelector("#app"),
		By.cssSelector("#contentViewManager"),
		By.cssSelector("[id^='currentApp_home_']"),
		By.cssSelector("[id^='app-dashboard-component-']"),
		By.cssSelector("rock-layout > rock-dashboard-widgets"),
		By.cssSelector("[id^='rs']"),
		By.cssSelector("#rock-task-list"),
		By.cssSelector("[id^='rock-task-list-component-']")
	);

	private static final List<By> BASE_TODOS_TAB_CHAIN = List.of(
		By.cssSelector("#app"),
		By.cssSelector("#contentViewManager"),
		By.cssSelector("[id^='currentApp_home_rs']"),
		By.cssSelector("[id^='app-dashboard-component-rs']"),
		By.cssSelector("rock-layout > rock-dashboard-widgets"),
		By.cssSelector("[id^='rs']"),
		By.cssSelector("#rock-my-todos"),
		By.cssSelector("[id^='rock-my-todos-component-rs']"),
		By.cssSelector("#rock-my-todos-tabs")
	);

	public HomePage(WebDriver driver) {
		this.driver = driver;
	}

	public WebElement HomePage_SearchButton() {
		return Utils.safeShadowDomChain(driver, List.of(
			By.cssSelector("#app"),
			By.cssSelector("[id^='rs']"),
			By.cssSelector("#navMenu"),
			By.cssSelector("#pageMenuIcon_1")
		));
	}

	public WebElement clickSearch_Products_Button() {
		return Utils.safeShadowDomChain(driver, List.of(
			By.cssSelector("#app"),
			By.cssSelector("[id^='rs']"),
			By.cssSelector("#navMenu"),
			By.cssSelector("#pageMenuIcon_2 > a.menu-icon.page-title-icon")
		));
	}

	public WebElement EntityDataImports_DropDown_Object() {
		return Utils.safeShadowDomChain(driver, Utils.extend(BASE_TASK_LIST_CHAIN,
			By.cssSelector("div > div.base-grid-structure-child-1 > div > div.row-1.flex-container-row-1 > div.item.task-type > pebble-dropdown")
		));
	}

	public WebElement Status_DropDown_Object() {
		return Utils.safeShadowDomChain(driver, Utils.extend(BASE_TASK_LIST_CHAIN,
			By.cssSelector("div > div.base-grid-structure-child-1 > div > div.row-1.flex-container-row-1 > div.item.task-status > pebble-dropdown")
		));
	}

	public WebElement From_Last_DropDown_Object() {
		return Utils.safeShadowDomChain(driver, Utils.extend(BASE_TASK_LIST_CHAIN,
			By.cssSelector("div > div.base-grid-structure-child-1 > div > div.row-1.flex-container-row-1 > div.item.task-from-last > pebble-dropdown")
		));
	}

	public WebElement EntityDataimports_Dropdown() {
		return EntityDataImports_DropDown_Object();
	}

	public WebElement AppHeader_Administrator() {
		return Utils.safeShadowDomChain(driver, List.of(
			By.cssSelector("#app"),
			By.cssSelector("[id^='rs']"),
			By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions"),
			By.cssSelector("div > div:nth-child(1) > div.header-action-wrapper")
		));
	}

	public WebElement Version_manager() {
		return Utils.safeShadowDomChain(driver, List.of(
			By.cssSelector("#app"),
			By.cssSelector("[id^='rs']"),
			By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions"),
			By.cssSelector("#userProfile"),
			By.cssSelector("#rsVersionManager > span.option-value")
		));
	}

	public WebElement Version_manager_info_dialog() {
		return Utils.safeShadowDomChain(driver, List.of(
			By.cssSelector("#app"),
			By.cssSelector("[id^='rs']"),
			By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions"),
			By.cssSelector("#userProfile"),
			By.cssSelector("rock-version-manage"),
			By.cssSelector("#attribute")
		));
	}

	public WebElement Version_manager_version_number() {
		return Utils.safeShadowDomChain(driver, List.of(
			By.cssSelector("#app"),
			By.cssSelector("[id^='rs']"),
			By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions"),
			By.cssSelector("#userProfile"),
			By.cssSelector("rock-version-manage"),
			By.cssSelector("#attrVal")
		));
	}

	public WebElement Version_manager_Close_btn() {
		return Utils.safeShadowDomChain(driver, List.of(
			By.cssSelector("#app"),
			By.cssSelector("[id^='rs']"),
			By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions"),
			By.cssSelector("#userProfile"),
			By.cssSelector("rock-version-manage"),
			By.cssSelector("#versionManagerDialog"),
			By.cssSelector("#closeIcon")
		));
	}

	public WebElement loggedin_User() {
		return Utils.safeShadowDomChain(driver, List.of(
			By.cssSelector("#app"),
			By.cssSelector("[id^='rs']"),
			By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions"),
			By.cssSelector("div > div:nth-child(1) > div.profile-text.text-ellipsis > div > span.profile-name-wrap > span")
		));
	}

	public WebElement enrichMarketingAttributelink() {
		return Utils.safeShadowDomChain(driver, Utils.extend(BASE_TODOS_TAB_CHAIN, List.of(
			By.cssSelector("[id^='my-todo-summary-list-component-rs']"),
			By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"),
			By.cssSelector("#workflowMetadataContainer")
		)));
	}

	public WebElement HomepageTabRootElement() {
		return Utils.safeShadowDomChain(driver, BASE_TODOS_TAB_CHAIN);
	}

	public WebElement Moredetails_MarketingEnrich() {
		return Utils.safeShadowDomChain(driver, Utils.extend(BASE_TODOS_TAB_CHAIN, List.of(
			By.cssSelector("[id^='my-todo-summary-list-component-rs']"),
			By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary")
		)));
	}

	public WebElement ReadyForTransistion_Market_Enrich() {
		return Utils.safeShadowDomChain(driver, Utils.extend(BASE_TODOS_TAB_CHAIN, List.of(
			By.cssSelector("[id^='my-todo-summary-list-component-rs']"),
			By.cssSelector("pebble-list-view > pebble-list-item > my-todo-summary"),
			By.cssSelector("#passedBCButton"),
			By.cssSelector("#button-text-box")
		)));
	}

	public WebElement sellablematerialtabelement() {
		return Utils.safeShadowDomChain(driver, Utils.extend(BASE_TODOS_TAB_CHAIN, List.of(
			By.cssSelector("[id^='my-todo-summary-list-component-rs']"),
			By.cssSelector("pebble-list-view > pebble-list-item:nth-child(2) > my-todo-summary"),
			By.cssSelector("div")
		)));
	}

	public WebElement BSAPIEUsecaseApprovalTab() {
		return Utils.safeShadowDomChain(driver, Utils.extend(BASE_TODOS_TAB_CHAIN, List.of(
			By.cssSelector("#tab-bsapieusecaseapproval"),
			By.cssSelector("div")
		)));
	}

	public WebElement Logout_btn() {
		return Utils.safeShadowDomChain(driver, List.of(
			By.cssSelector("#app"),
			By.cssSelector("[id^='rs']"),
			By.cssSelector("app-header > app-toolbar > div > div.right-content > div.right-content-bar > rock-header-actions"),
			By.cssSelector("#userProfile"),
			By.cssSelector("#rsLogOut > span.option-value.text-ellipsis")
		));
	}
} 
