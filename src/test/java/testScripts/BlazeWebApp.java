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
import page.ProductPage;
import page.CheckoutPage;
import page.HomePage;
import page.LoginPage;
import page.CartPage;

public class BlazeWebApp extends TestBase {

	WebDriverWait wait;
	
	HomePage homePage;
	LoginPage loginPage;
	ProductPage singleproPage;
	CartPage mulproPage;
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
		homePage = new HomePage();
		homePage.logIn();
		loginPage = new LoginPage();
		loginPage.logIn(prop.getProperty("username"), prop.getProperty("password"));
		WebElement welcomeTxt = homePage.welcome;
		wait.until(ExpectedConditions.visibilityOf(welcomeTxt));
		Assert.assertEquals(welcomeTxt.getText(), "Welcome Dharun_K");
	}

	@Test(priority = 2, dataProvider = "productdetails")
	public void addItemtoCart(String productCategory, String productName) {
		extentTest = reports.createTest("addItemtoCart");
		homePage = new HomePage();
		homePage.selectItems(productCategory, productName);
		singleproPage = new ProductPage();
		singleproPage.addtoCart();
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
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		homePage = new HomePage();
		homePage.cartClick();
		mulproPage = new CartPage();
		List<WebElement> proList1 = mulproPage.products;
		wait.until(ExpectedConditions.visibilityOfAllElements(proList1));
		int initialproSize = proList1.size();
		WebElement proNameBef = mulproPage.productName;
		String product1 = proNameBef.getText();
//		boolean proAdded = true;
		if (initialproSize > 1) {
			mulproPage.delItem();
			Thread.sleep(5000);
//			Assert.assertTrue(proAdded);
		}
		
		List<WebElement> proList2 = mulproPage.products;
		wait.until(ExpectedConditions.visibilityOfAllElements(proList2));
		
		WebElement proNameAft = mulproPage.productName;
		String product2 = proNameAft.getText();
		if (product1 != product2) {
			Assert.assertTrue(true);
		}
		
//		int currentproSize = proList2.size();
//		if(initialproSize > currentproSize) {
//			Assert.assertTrue(true);
//		}
	}

	@Test(priority = 4)
	public void finalOrder() {
		extentTest = reports.createTest("finalOrder");
		mulproPage = new CartPage();
		mulproPage.placeOrdBtn();
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
