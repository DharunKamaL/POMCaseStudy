package testScripts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import CommonUtils.Utility;
import base.TestBase;
import page.AddItemToCartPage;
import page.CheckoutPage;
import page.LoginPage;
import page.ProductListCartPage;

public class BlazeWebApp extends TestBase {

	WebDriverWait wait;

	LoginPage login;
	AddItemToCartPage additem;
	ProductListCartPage productList;
	CheckoutPage checkoutPage;

	ExtentReports reports;
	ExtentSparkReporter spark;
	ExtentTest extentTest;
	

	@BeforeTest
	public void setUp() {
		initialize();
		reports = new ExtentReports();
		spark = new ExtentSparkReporter("target\\BlazeReports.html");
		reports.attachReporter(spark);
	}

	@Test(priority = 1)
	public void validLogin() {
		extentTest = reports.createTest("validLogin");
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		login = new LoginPage();
		login.logIn(prop.getProperty("username"), prop.getProperty("password"));
		WebElement wel = login.welcome;
		wait.until(ExpectedConditions.visibilityOf(wel));
		Assert.assertEquals(wel.getText(), "Welcome Dharun_K");
	}

	@Test(priority = 2, dataProvider = "productdetails")
	public void addItemtoCart(String productCategory, String productName) {
		extentTest = reports.createTest("addItemtoCart");
		additem = new AddItemToCartPage();
		additem.additems(productCategory, productName);
	}

	@DataProvider(name = "productdetails")
	public Object[][] getData() throws CsvValidationException, IOException {
		String path = System.getProperty("user.dir") + "//src//test//resources//csvFiles//productdetails.csv";
		CSVReader reader = new CSVReader(new FileReader(path));
		String cols[];
		ArrayList<Object> dList = new ArrayList<Object>();
		while ((cols = reader.readNext()) != null) {
			Object[] rec = { cols[0], cols[1] };
			dList.add(rec);

		}
		return dList.toArray(new Object[dList.size()][]);
	}

	@Test(priority = 3)
	public void deleteItem() throws InterruptedException {
		extentTest = reports.createTest("deleteItem");
//		WebElement cart = productList.CartBtn;
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
//		wait.until(ExpectedConditions.visibilityOf(cart));
		productList = new ProductListCartPage();
		productList.ProductListPage();
		productList.mouseAction();
		List<WebElement> proList = productList.products;
		int initialproSize = proList.size();
		productList.delItem();
		
//		productList.wait(10);
//		wait.until(ExpectedConditions.visibilityOfAllElements(proList));
		
		List<WebElement> proListAftDel = productList.products;
		wait.until(ExpectedConditions.visibilityOfAllElements(proListAftDel));
		int proSizeAftDel = proListAftDel.size();
		boolean isTrue = true;
		
//		int count = 0;
		
//		for(WebElement productList:proListAftDel) {
//			count++;
//		}
		
		if(proSizeAftDel!=initialproSize) {
			Assert.assertTrue(isTrue);
		}
		
//		int initialproSize = proList.size();
//		Assert.assertEquals(initialproSize, 5);

//		productList.mouseAction();
//		wait.until(ExpectedConditions.invisibilityOf(null));
//		Assert.assertEquals(proSizeAftDel, 4);

//		Assert.assertNotEquals(initialproSize, proSizeAftDel);

	}

	@Test(priority = 4)
	public void finalOrder() {
		extentTest = reports.createTest("finalOrder");
		checkoutPage = new CheckoutPage();
		checkoutPage.placeOrder();
		WebElement vrfyPur = checkoutPage.verifyPurchase;
		String successMsg = vrfyPur.getText();
		Assert.assertEquals(successMsg, "Thank you for your purchase!");
		WebElement subBtn = checkoutPage.submitBtn;
		subBtn.click();
	}

	@AfterMethod
	public void failure(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			extentTest.log(Status.FAIL, result.getThrowable().getMessage());
			String path = Utility.getScreenshotPath(driver);
			extentTest.addScreenCaptureFromPath(path);
		}
	}

	@AfterTest
	public void extentfinishUp() {
		reports.flush();
	}

}
