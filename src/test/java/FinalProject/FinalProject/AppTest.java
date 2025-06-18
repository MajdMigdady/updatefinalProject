package FinalProject.FinalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class AppTest {

	WebDriver driver = new ChromeDriver();
	String URL = "https://automationteststore.com/";
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	Random rand = new Random();
	JavascriptExecutor js = (JavascriptExecutor) driver;
	Connection con;
	Statement stmt;
	ResultSet rs;
	String userFirstName;
	String username;
	String password = "Test@123";

	@BeforeTest
	public void mySetup() throws SQLException {

		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalproject", "root", "1234");

	}

	@Test(priority = 1, enabled = true)
	public void HomepageAccessibility() {
		WebElement featured = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".maintext")));
		boolean ActualHomeLoad = featured.isDisplayed();
		boolean ExpectedHomeLoad = true;

		Assert.assertEquals(ActualHomeLoad, ExpectedHomeLoad);

	}

	@Test(priority = 2, enabled = true)
	public void UserRegistrationProcess() throws InterruptedException, SQLException {

		WebElement LoginButton = driver.findElement(By.linkText("Login or register"));
		LoginButton.click();

		WebElement ContinuoButton = driver.findElement(By.xpath("//button[@title='Continue']"));
		ContinuoButton.click();

		// Fill registration form

		String[] firstNames = { "mohammad", "ola", "khalid", "yasmine", "ayat", "alaa", "waleed", "Rama" };
		String[] lastNames = { "yaser", "mustafa", "Mohammad", "abdullah", "sami", "omar" };

		int RandomIndexForFirstName = rand.nextInt(firstNames.length);
		int RandomIndexForLastName = rand.nextInt(lastNames.length);

		userFirstName = firstNames[RandomIndexForFirstName];
		String userLastName = lastNames[RandomIndexForLastName];

		WebElement firstNamefield = driver.findElement(By.id("AccountFrm_firstname"));
		firstNamefield.sendKeys(userFirstName);

		WebElement lastNamefield = driver.findElement(By.id("AccountFrm_lastname"));
		lastNamefield.sendKeys(userLastName);

		int randomNumberForEmail = rand.nextInt(500);
		String RANDOM_EMAIL = userFirstName + userLastName + randomNumberForEmail + "@example.com";
		WebElement emailfeild = driver.findElement(By.id("AccountFrm_email"));
		emailfeild.sendKeys(RANDOM_EMAIL);

		WebElement telephonefeild = driver.findElement(By.id("AccountFrm_telephone"));
		telephonefeild.sendKeys("1234567890");

		WebElement faxfeild = driver.findElement(By.id("AccountFrm_fax"));
		faxfeild.sendKeys("1234567890");

		Thread.sleep(500);
		js.executeScript("window.scrollBy(0, 660)"); // Scrolls down 500 pixels

		WebElement companyfeild = driver.findElement(By.id("AccountFrm_company"));
		companyfeild.sendKeys("Test Company");

		WebElement addressFeild = driver.findElement(By.id("AccountFrm_address_1"));
		addressFeild.sendKeys("123 Test St");

		WebElement address2Feild = driver.findElement(By.id("AccountFrm_address_2"));
		address2Feild.sendKeys("123 Test 2 St");

		WebElement cityFeild = driver.findElement(By.id("AccountFrm_city"));
		cityFeild.sendKeys("Test City");

		WebElement countryFeild = driver.findElement(By.id("AccountFrm_country_id"));
		Select countrySelect = new Select(countryFeild);
		countrySelect.selectByVisibleText("Jordan");

		Thread.sleep(1500); // Wait for states to load

		WebElement zoneFeild = driver.findElement(By.id("AccountFrm_zone_id"));
		Select zoneSelect = new Select(zoneFeild);
		zoneSelect.selectByValue("1704");

		WebElement postcodeFeild = driver.findElement(By.id("AccountFrm_postcode"));
		postcodeFeild.sendKeys("11181");

		username = userFirstName + "_" + UUID.randomUUID().toString().substring(0, 4);
		WebElement usernameFeild = driver.findElement(By.id("AccountFrm_loginname"));
		usernameFeild.sendKeys(username);

		WebElement passwordFeild = driver.findElement(By.id("AccountFrm_password"));
		passwordFeild.sendKeys(password);

		WebElement confirmPasswordFeild = driver.findElement(By.id("AccountFrm_confirm"));
		confirmPasswordFeild.sendKeys(password);

		WebElement agreeCheck = driver.findElement(By.id("AccountFrm_agree"));
		agreeCheck.click();

		WebElement submitButton = driver.findElement(By.cssSelector(".btn.btn-orange.pull-right.lock-on-click"));
		submitButton.click();

		String query = "INSERT INTO users (first_name, last_name, email, telephone, fax, company, address1, address2, city, region_state, zip_code, country, login_name, password) VALUES ('"
				+ userFirstName + "', '" + userLastName + "', '" + RANDOM_EMAIL
				+ "', '1234567890', '1234567890', 'Test Company', '123 Test St', '123 Test 2 St', 'Test City', 'Amman', '11181', 'Jordan', '"
				+ username + "', '" + password + "');";

		stmt = con.createStatement();
		int rowsInserted = stmt.executeUpdate(query);
		System.out.println("Rows inserted: " + rowsInserted);
		

		String ExpectedRegister = "YOUR ACCOUNT HAS BEEN CREATED!";
		String successText = driver.findElement(By.cssSelector(".maintext")).getText();
		Assert.assertTrue(successText.contains(ExpectedRegister), " User Registration Failed");

	}
 
	@Test(priority = 3, enabled = true)
	public void UserLoginFunctionality() throws InterruptedException {

		
		Actions action = new Actions(driver);
		WebElement MouseHover = driver.findElement(By.id("customer_menu_top"));
		action.moveToElement(MouseHover).build().perform();
		
		WebElement dropdownlogoff = driver.findElement(By.linkText("Not "+userFirstName+"? Logoff"));
		dropdownlogoff.click();

		WebElement LoginButton = driver.findElement(By.linkText("Login or register"));
		LoginButton.click();

		WebElement usernameFeild = driver.findElement(By.id("loginFrm_loginname"));
		usernameFeild.sendKeys(username);

		WebElement passwordFeild = driver.findElement(By.id("loginFrm_password"));
		passwordFeild.sendKeys(password);

		Thread.sleep(500);
		WebElement ContinuoButton = driver.findElement(By.xpath("//button[@title='Login']"));
		ContinuoButton.click();

		String ExpectedLogin = "MY ACCOUNT";
		WebElement featured = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".maintext")));

		Assert.assertTrue(featured.getText().contains(ExpectedLogin), " User Login Failed");

	}

	@Test(priority = 4, enabled = true)
	public void ProductSearchFunctionality() {

		WebElement searchbar = driver.findElement(By.id("filter_keyword"));
		searchbar.sendKeys("Shampoo" + Keys.ENTER);

		js.executeScript("window.scrollBy(0, 300)"); // Scrolls down 320 pixels

		List<WebElement> searchResult = driver.findElements(By.className("fixed_wrapper"));

		int ExpextedProductCount = 0;

		for (int i = 0; i < searchResult.size(); i++) {
			String text = searchResult.get(i).getText().toLowerCase();

			if (text.contains("shampoo")) {

				ExpextedProductCount++;
			}
		}
		Assert.assertEquals(searchResult.size(), ExpextedProductCount);
	}

	@Test(priority = 5, enabled = true)
	public void FilteringSearchResults() throws InterruptedException {

		WebElement filterResult = driver.findElement(By.id("sort"));
		Select sortResult = new Select(filterResult);
		sortResult.selectByVisibleText("Price Low > High");

		js.executeScript("window.scrollBy(0, 300)");
		Thread.sleep(500);

		List<WebElement> sortLowToHigh = driver.findElements(By.cssSelector(".pricetag.jumbotron"));
		List<Double> prices = new ArrayList<>();

		for (int i = 0; i < sortLowToHigh.size(); i++) {

			String priceText = sortLowToHigh.get(i).getText().replace("$", "").trim();
			double price = Double.parseDouble(priceText);
			prices.add(price);

		}

		boolean isSorted = true;
		for (int i = 0; i < prices.size() - 1; i++) {
			if (prices.get(i) > prices.get(i + 1)) {
				isSorted = false;
				break;
			}
		}

		Assert.assertTrue(isSorted, "Prices are not sorted from Low to High");
	}

	@Test(priority = 6, enabled = true)
	public void ViewingProductDetails() throws InterruptedException {

		driver.get(URL);

		String[] listOfItems = {
				"img[src='//automationteststore.com/image/thumbnails/18/6a/demo_product18_jpg-100013-250x250.jpg']\r\n",
				"body > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > section:nth-child(5) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(2) > a:nth-child(2) > img:nth-child(1)",
				"img[src='//automationteststore.com/image/thumbnails/18/6f/demo_product16_1_jpg-100091-250x250.jpg']",
				"img[src='//automationteststore.com/image/thumbnails/18/6d/demo_product17_jpg-100052-250x250.jpg']" };

		int randomItems = rand.nextInt(listOfItems.length);
		String randomLatestProducts = listOfItems[randomItems];

		js.executeScript("window.scrollBy(0,1300)");
		Thread.sleep(1000);

		WebElement product = driver.findElement(By.cssSelector(randomLatestProducts));
		product.click();

		WebElement descriptionsection = wait
				.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Description")));

		// Step 2: Verify product details are displayed
		WebElement productTitle = driver.findElement(By.cssSelector("div.col-md-12 h1"));
		WebElement productImage = driver.findElement(By.xpath("//a[@class='local_image']//img"));
		WebElement price = driver.findElement(By.cssSelector(".productfilneprice"));
		WebElement availability = driver.findElement(By.className("cart"));

		// Assertions
		Assert.assertTrue(productTitle.isDisplayed(), "Product title is not displayed.");
		Assert.assertTrue(productImage.isDisplayed(), "Product image is not displayed.");
		Assert.assertTrue(price.isDisplayed(), "Product price is not displayed.");
		Assert.assertTrue(availability.isDisplayed(), "Availability info is not displayed.");
		Assert.assertTrue(descriptionsection.isDisplayed(), "description is not displayed. ");

	}

	@Test(priority = 7, enabled = true)
	public void AddingProductsToCart() throws InterruptedException {

		Thread.sleep(1000);
		WebElement AddToCartButton = driver.findElement(By.cssSelector(".cart"));
		AddToCartButton.click();

		WebElement TheCartsection = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cart")));

		Assert.assertTrue(TheCartsection.isDisplayed(), " The product did not add to the cart ");

	}

	@Test(priority = 8, enabled = true)
	public void ViewingAndModifyingShoppingCart() throws InterruptedException {

		String ExpectedQuantity = String.valueOf(3);

		Thread.sleep(500);
		WebElement quantityDiv = wait
				.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".input-group.input-group-sm")));

		WebElement quantityInput = quantityDiv.findElement(By.cssSelector("input"));

		quantityInput.clear();
		quantityInput.sendKeys(ExpectedQuantity);

		WebElement updateButton = driver.findElement(By.id("cart_update"));
		updateButton.click();

		WebElement UnitPrice = driver.findElement(By.cssSelector(
				"body > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > form:nth-child(2) > div:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(4)"));

		WebElement ActualtotalPrice = driver.findElement(By.cssSelector(
				"body > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1) > form:nth-child(2) > div:nth-child(1) > div:nth-child(1) > table:nth-child(1) > tbody:nth-child(1) > tr:nth-child(2) > td:nth-child(6)"));

		String priceForunit = UnitPrice.getText().replace("$", "").trim();
		;
		double intPrice = Double.parseDouble(priceForunit);

		String priceFortotal = ActualtotalPrice.getText().replace("$", "").trim();
		;
		double ActualTotalPrice = Double.parseDouble(priceFortotal);

		double ExpectedtotalPrice = intPrice * 3.0;

		Assert.assertEquals(ActualTotalPrice, ExpectedtotalPrice);

	}

	@Test(priority = 9, enabled = true)
	public void ProceedingToCheckout() throws InterruptedException {

		js.executeScript("window.scrollBy(0,400)");
		Thread.sleep(1000);

		WebElement mySelectForCountry = driver.findElement(By.id("estimate_country"));

		Select myCountry = new Select(mySelectForCountry);

		myCountry.selectByValue("108");

		Thread.sleep(2000);

		WebElement mySelectForState = driver.findElement(By.id("estimate_country_zones"));

		Select myState = new Select(mySelectForState);

		myState.selectByValue("1704");

		WebElement postcode = driver.findElement(By.id("estimate_postcode"));
		postcode.clear();
		postcode.sendKeys("1234");

		// checkout
		WebElement checkout2 = driver.findElement(By.id("cart_checkout2"));
		checkout2.click();

		WebElement confcheckout = driver.findElement(By.id("checkout_btn"));
		confcheckout.click();

		WebElement messageElement = wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//span[contains(text(),'Your Order Has Been Processed!')]")));

		String Expectedcheckout = "YOUR ORDER HAS BEEN PROCESSED!";
		String Actualcheckout = messageElement.getText();

		Assert.assertEquals(Actualcheckout, Expectedcheckout);

	}

	@Test(priority = 10, enabled = true)
	public void ContactFormSubmission() throws InterruptedException {

		js.executeScript("window.scrollBy(0,3300)");
		Thread.sleep(1000);

		WebElement contectUsButton = driver.findElement(By.linkText("Contact Us"));
		contectUsButton.click();

		Thread.sleep(1000);
		js.executeScript("window.scrollBy(0,300)");
		Thread.sleep(1000);
		WebElement firstNameforContact = driver.findElement(By.id("ContactUsFrm_first_name"));
		firstNameforContact.sendKeys("Tester");

		WebElement EmailforContact = driver.findElement(By.id("ContactUsFrm_email"));
		EmailforContact.sendKeys("TesterUser2025@example.com");

		WebElement Enquiry = driver.findElement(By.id("ContactUsFrm_enquiry"));
		Enquiry.sendKeys("This is a test enquiry message.");

		WebElement Submitcontactform = driver.findElement(By.cssSelector("button[title='Submit']"));
		Submitcontactform.click();

		// Your enquiry has been successfully sent to the store owner!

		WebElement confirmation = driver.findElement(By.cssSelector(".mb40"));
		String ActualMessage = confirmation.getText().trim();

		String expectedMessage = "Your enquiry has been successfully sent to the store owner!";
		Assert.assertTrue(ActualMessage.contains(expectedMessage),
				"Confirmation message does not contain expected text.");
	}

	
	@AfterTest
	public void tearDown() {
	    if (driver != null) {
	        driver.quit(); 
	    }
	}

}
