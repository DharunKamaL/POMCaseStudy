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

public class CartPage extends TestBase{
	
	WebDriverWait wait;
	
	
	
	@FindBy(xpath = "//tr/td[2]")
	public List<WebElement> products;
	
	@FindBy(xpath = "(//tr/td[2])[1]")
	public WebElement productName;
	
	@FindBy(xpath = "(//tr/td//a)[1]")
	WebElement delBtn;
	
	@FindBy(xpath = "//button[text()='Place Order']")
	WebElement placeOrderBtn;
	
	public CartPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void delItem() {
		wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(delBtn));
		delBtn.click();
	}
	
	public CheckoutPage placeOrdBtn() {
		wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(placeOrderBtn));
		placeOrderBtn.click();
		return new CheckoutPage();
	}
	
	
	
	
}
