package page;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.TestBase;

public class ProductPage extends TestBase {
	
	WebDriverWait wait;
	
	@FindBy(xpath = "//a[text()='Add to cart']")
	WebElement addtocartBtn;
	
	@FindBy(xpath = "(//ul/li//a)[1]")
	WebElement homeBtn;
	
	public ProductPage() {
		PageFactory.initElements(driver, this);
	}

	public void addtoCart() {
		
		wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		wait.until(ExpectedConditions.visibilityOf(addtocartBtn));
		addtocartBtn.click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
		homeBtn.click();
	
	}
	

}
