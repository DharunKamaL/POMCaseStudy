package page;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import base.TestBase;

public class LoginPage extends TestBase {

	@FindBy(xpath = "//a[text()='Log in']")
	WebElement loginBtn;

	@FindBy(css = "input[id = loginusername]")
	WebElement userInput;

	@FindBy(id = "loginpassword")
	WebElement passInput;

	@FindBy(xpath = "//button[text() = 'Log in']")
	WebElement submitBtn;
	
	@FindBy(xpath = "//a[contains(text(),'Welcome')]")
	public WebElement welcome;
	
	public LoginPage(){
		PageFactory.initElements(driver, this);
	}

	public AddItemToCartPage logIn(String username, String password) {
		loginBtn.click();
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(40));
		wait.until(ExpectedConditions.elementToBeClickable(userInput));
		userInput.sendKeys(username);
		passInput.sendKeys(password);
		submitBtn.click();
		return new AddItemToCartPage();
		
	}
}
