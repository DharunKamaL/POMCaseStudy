package page;

import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;

public class CheckoutPage extends TestBase{
	
	WebDriverWait wait;
	
	@FindBy(xpath = "//button[text()='Place Order']")
	WebElement placeOrderBtn;
	
	@FindBy(css = "input[id=name]")
	WebElement inputName;
	
	@FindBy(css = "input[id=country]")
	WebElement inputCountry;
	
	@FindBy(css = "input[id=city]")
	WebElement inputCity;
	
	@FindBy(css = "input[id=card]")
	WebElement inputCard;
	
	@FindBy(css = "input[id=month]")
	WebElement inputMonth;
	
	@FindBy(css = "input[id=year]")
	WebElement inputYear;
	
	@FindBy(xpath = "//button[text()='Purchase']")
	WebElement purchaseBtn;
	
	@FindBy(xpath = "//h2[contains(text(),'Thank you')]")
	public WebElement verifyPurchase;
	
	@FindBy(xpath = "//button[text()='OK']")
	public WebElement submitBtn;
	
	public CheckoutPage() {
		PageFactory.initElements(driver, this);
	}
	
	public void placeOrder() {
		wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		wait.until(ExpectedConditions.elementToBeClickable(placeOrderBtn));
		placeOrderBtn.click();
		wait.until(ExpectedConditions.elementToBeClickable(inputName));
		inputName.sendKeys(prop.getProperty("Name"));
		inputCountry.sendKeys(prop.getProperty("Country"));
		inputCity.sendKeys(prop.getProperty("City"));
		inputCard.sendKeys(prop.getProperty("CreditCard"));
		inputMonth.sendKeys(prop.getProperty("Month"));
		inputCity.sendKeys(prop.getProperty("Year"));
		purchaseBtn.click();
	}
	

}
