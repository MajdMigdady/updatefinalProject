package FinalProject.FinalProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestData {


	WebDriver driver = new ChromeDriver();
	String URL = "https://automationteststore.com/";
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	Random rand = new Random();
	JavascriptExecutor js = (JavascriptExecutor) driver;
	Connection con;
	Statement stmt;
	ResultSet rs;
	String password = "Test@123";

	boolean ExpectedHomeLoad = true;
	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	String fileName = "HomepageAccessibility_" + timeStamp + ".png";
	

	String[] firstNames = { "mohammad", "ola", "khalid", "yasmine", "ayat", "alaa", "waleed", "Rama" };
	String[] lastNames = { "yaser", "mustafa", "Mohammad", "abdullah", "sami", "omar" };

	int RandomIndexForFirstName = rand.nextInt(firstNames.length);
	int RandomIndexForLastName = rand.nextInt(lastNames.length);

	String userFirstName = firstNames[RandomIndexForFirstName];
	String userLastName = lastNames[RandomIndexForLastName];
	
	int randomNumberForEmail = rand.nextInt(500);
	String RANDOM_EMAIL = userFirstName + userLastName + randomNumberForEmail + "@example.com";
	
	String username = userFirstName + "_" + UUID.randomUUID().toString().substring(0, 4);
	
	String ExpectedRegister = "YOUR ACCOUNT HAS BEEN CREATED!";

	Actions action = new Actions(driver);
	String ExpectedLogin = "MY ACCOUNT";

	int ExpextedProductCount = 0;

	String[] listOfItems = {
			"img[src='//automationteststore.com/image/thumbnails/18/6a/demo_product18_jpg-100013-250x250.jpg']\r\n",
			"body > div:nth-child(1) > div:nth-child(3) > div:nth-child(1) > div:nth-child(1) > section:nth-child(5) > div:nth-child(2) > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > div:nth-child(2) > a:nth-child(2) > img:nth-child(1)",
			"img[src='//automationteststore.com/image/thumbnails/18/6f/demo_product16_1_jpg-100091-250x250.jpg']",
			"img[src='//automationteststore.com/image/thumbnails/18/6d/demo_product17_jpg-100052-250x250.jpg']" };

	int randomItems = rand.nextInt(listOfItems.length);
	String randomLatestProducts = listOfItems[randomItems];

	String ExpectedQuantity = String.valueOf(3);

	String Expectedcheckout = "YOUR ORDER HAS BEEN PROCESSED!";
	String expectedMessage_ContactUs = "Your enquiry has been successfully sent to the store owner!";

	
	public void setup() throws SQLException {

		driver.get(URL);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));

		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/finalproject", "root", "1234");

		
		
	}
	
}
