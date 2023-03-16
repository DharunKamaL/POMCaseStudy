package page;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;

public class HomePage extends TestBase{
	
	WebDriverWait wait;

	@FindBy(id = "login2")
	WebElement loginBtn;

	@FindBy(id = "cartur")
	WebElement cartBtn;
	
	@FindBy(xpath = "//a[contains(text(),'Welcome')]")
	public WebElement welcome;
	
	public HomePage(){
		PageFactory.initElements(driver, this);
	}

	public LoginPage logIn() {
		loginBtn.click();
		return new LoginPage();
	}
	
	public ProductPage selectItems(String productCategory, String productName) {
		driver.findElement(By.linkText(productCategory)).click();
		driver.findElement(By.linkText(productName)).click();
		return new ProductPage();
	}
	
	public CartPage cartClick() {
		wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(cartBtn));
		cartBtn.click();
		return new CartPage();
	}
	
	
	
	
//	WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(40));

	
	
//	driver.findElement(By.xpath("//a[text()='Add to cart']")).click();
//	wait.until(ExpectedConditions.alertIsPresent());
//	Alert alert = driver.switchTo().alert();
//	alert.accept();
//	Home.click();

}
