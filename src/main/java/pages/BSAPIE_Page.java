package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class BSAPIE_Page {

	private WebDriver driver;
	private By searchInputField = By.cssSelector("#app");

	public BSAPIE_Page(WebDriver driver) {
		this.driver = driver;
	}
		public WebElement common_element() {
			return driver.findElement(searchInputField).getShadowRoot()
					.findElement(By.cssSelector("#contentViewManager")).getShadowRoot()
					.findElement(By.cssSelector("[id^='currentApp_entity-manage_rs']")).getShadowRoot()
					.findElement(By.cssSelector("[id^='app-entity-manage-component-rs']")).getShadowRoot()
					.findElement(By.cssSelector("#rockDetailTabs"));
	}
		
		public WebElement BSAPIESelectAttributesforHold() {
			return common_element().getShadowRoot()
				    .findElement(By.cssSelector("#rockTabs")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rock-wizard-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rock-attribute-manage-component-rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#rock-attribute-list-container > rock-attribute-list")).getShadowRoot()
				    .findElement(By.cssSelector("[id^='rs']")).getShadowRoot()
				    .findElement(By.cssSelector("#input")).getShadowRoot()
				    .findElement(By.cssSelector("bedrock-lov")).getShadowRoot()
				    .findElement(By.cssSelector("#collectionContainer")).getShadowRoot()
				    .findElement(By.cssSelector("#collection_container_wrapper > div.d-flex > div.tags-container"));
		}
		
		public WebElement BSAPIEUsecaseSalesOrgRegions_Auto () {
			return driver.findElement(By.cssSelector("#app")).getShadowRoot()
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
		}
}
