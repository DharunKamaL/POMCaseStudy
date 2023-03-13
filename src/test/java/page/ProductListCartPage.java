package page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;

public class ProductListCartPage extends TestBase{
	
	WebDriverWait wait;
	
	@FindBy(xpath = "//a[text()='Cart']")
	public WebElement CartBtn;
	
	@FindBy(xpath = "//tr/td[2]")
	public List<WebElement> products;
	
	@FindBy(xpath = "(//tr/td//a)[1]")
	WebElement DelBtn;
	
	public ProductListCartPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void ProductListPage() {
		wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(CartBtn));
		CartBtn.click();
	}
	
	public void mouseAction() {
		actions.scrollByAmount(0, 200).perform();
	}
	
//	public void productQuantity() {
//		products.size();
//	}
	
	public void delItem() {
		
		wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(DelBtn));
		DelBtn.click();
		
	}
	
}
