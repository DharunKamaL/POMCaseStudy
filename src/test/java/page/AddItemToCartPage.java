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

public class AddItemToCartPage extends TestBase {
	
	
	
	@FindBy(xpath = "(//ul/li//a)[1]")
	WebElement Home;
	
	public AddItemToCartPage() {
		PageFactory.initElements(driver, this);
	}

	public ProductListCartPage additems(String productCategory, String productName) {
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		actions.scrollByAmount(0, 200).perform();
		driver.findElement(By.linkText(productCategory)).click();
		actions.scrollByAmount(0, 200).perform();
		driver.findElement(By.linkText(productName)).click();
		driver.findElement(By.xpath("//a[text()='Add to cart']")).click();
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		alert.accept();
		Home.click();
		return new ProductListCartPage();
	}

}
