package DemoBlazeTests.QPros;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class AppTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver",
        		System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
    }
    
    @Test(priority = 1)
    public void registerAndLogin() {
        driver.get("https://www.demoblaze.com/");
        
        WebElement signUpButton = driver.findElement(By.id("signin2"));
        signUpButton.click();
      
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signInModal")));
        WebElement usernameField = driver.findElement(By.id("sign-username"));
        WebElement passwordField = driver.findElement(By.id("sign-password"));
        ///WebElement loginButton = driver.findElement(By.id("login2"));
       WebElement SignupPop = driver.findElement(By.xpath("//*[@id=\"signInModal\"]/div/div/div[3]/button[2]"));
        // Register
       String username = "testuser"+  System.currentTimeMillis();
       String password = "testpassword";
       usernameField.sendKeys(username);
       passwordField.sendKeys(password);
       SignupPop.click();
        
       // Wait for the success message to appear
       //WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='toast-message'][contains(text(),'Sign up successful')]")));

       // Verify that the success message is displayed
     //  Assert.assertTrue(successMessage.isDisplayed());
       wait.until(ExpectedConditions.alertIsPresent());
           Alert alert = driver.switchTo().alert();
          String alertText = alert.getText();
          Assert.assertEquals(alertText, "Sign up successful.");
           alert.accept();
       


    
     
  //Click on the Log in button
       //wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));
        WebElement loginButton = driver.findElement(By.id("login2"));
        loginButton.click();

        // Wait for the login modal to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logInModal")));

     // Find the username and password fields in the login modal
        WebElement loginUsernameField = driver.findElement(By.id("loginusername"));
        WebElement loginPasswordField = driver.findElement(By.id("loginpassword"));

        // Enter the username and password
        loginUsernameField.sendKeys(username);
        loginPasswordField.sendKeys(password);
        
        
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"logInModal\"]/div/div/div[3]/button[2]")));
        WebElement userLabel = driver.findElement(By.xpath("//*[@id=\"logInModal\"]/div/div/div[3]/button[2]"));
        userLabel.click();
        
        // Wait for the user to be logged in
        WebElement welcomeMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));

        // Verify that the user is logged in
        Assert.assertEquals(welcomeMessage.getText(), "Welcome " + username);


    }
    
    
 
    
    @Test(priority = 2)
    public void testCheckCategoryItems() {
        // Find the Categories section
        WebElement categoriesSection = driver.findElement(By.id("cat"));

        // Find all the category links java.util.List to avoid any potential naming conflicts with other List implementations.
        java.util.List<WebElement> categoryLinks = categoriesSection.findElements(By.tagName("a"));

        // Loop through each category link and verify it has items
        for (WebElement categoryLink : categoryLinks) {
            // Click on the category link
            categoryLink.click();

            // Find the items section for the category
            WebElement itemsSection = driver.findElement(By.id("tbodyid"));

            // Check if there are any items in the section
            boolean hasItems = itemsSection.findElements(By.tagName("tr")).size() > 0;

            // Assert that the category has items
            Assert.assertTrue(hasItems, "Category " + categoryLink.getText() + " does not have any items.");

            // Go back to the Categories section
            driver.navigate().back();
            categoriesSection = driver.findElement(By.id("cat"));
            categoryLinks = categoriesSection.findElements(By.tagName("a"));
        }
    }
    
    
    @Test(priority = 3)
    public void addRandomItemToCart() { 
    	
        
    	wait = new WebDriverWait(driver, 10);
   
        driver.get("https://www.demoblaze.com/");
        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Laptops')]")));
        WebElement categoryLink = driver.findElement(By.xpath("//a[contains(text(),'Laptops')]"));
        categoryLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("tbodyid")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='hrefch']")));
        WebElement randomItem = driver.findElement(By.xpath("//a[@class='hrefch']"));
        String itemName = randomItem.getText();
        randomItem.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("col-sm-12")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Add to cart')]")));
        WebElement addToCartButton = driver.findElement(By.xpath("//a[contains(text(),'Add to cart')]"));
        addToCartButton.click();
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
        WebElement cartLink = driver.findElement(By.id("cartur"));
        Assert.assertEquals(cartLink.getText(), "Cart");
        cartLink.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'" + itemName + "')]")));
        Assert.assertEquals(driver.findElement(By.xpath("//td[contains(text(),'" + itemName + "')]")).getText(), itemName);
        // TODO Auto-generated method stub
	
}

    @Test(priority = 4)
    public void RemoveItemFromCart() {
		//wait = new WebDriverWait(driver, 500);
    	  //driver.get("https://www.demoblaze.com/cart.html#");
    	  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[contains(text(),'Delete')]"))).click();
          //driver.findElements(By.xpath("//a[contains(text(),'Delete')]")).;
    	  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//a[contains(text(),'Delete')]")));
          List<WebElement> listOfElements = driver.findElements(By.xpath("//a[contains(text(),'Delete')]"));
          Assert.assertEquals( listOfElements.size(),0);   
    	
    }
   
	@Test(priority = 5)
	 public void PlceOrder(){
		addRandomItemToCart();
		
		
		  // Click on the "Place Order" button
		  driver.findElement(By.xpath("//button[contains(text(),'Place Order')]")).click();
		  
		  // Fill in the checkout form
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("name"))).sendKeys("John Doe");
				 
		  driver.findElement(By.id("country")).sendKeys("United States");
		  driver.findElement(By.id("city")).sendKeys("New York");
		  driver.findElement(By.id("card")).sendKeys("1234567890123456");
		  driver.findElement(By.id("month")).sendKeys("12");
		  driver.findElement(By.id("year")).sendKeys("2025");
		  
		  // Click on the "Purchase" button to complete the checkout process
		  driver.findElement(By.xpath("//button[contains(text(),'Purchase')]")).click();
		  

		// Wait for the success message to appear
		  WebDriverWait wait = new WebDriverWait(driver, 10);
		  WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[contains(text(),'Thank you for your purchase!')]")));
		  
		  // Verify that the purchase was successful
		  String successText = successMessage.getText();
		  Assert.assertEquals(successText, "Thank you for your purchase!");
		  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(text(),'OK')]"))).click(); 
	}
	
	@AfterTest
    public void tearDown() {
        // Close the browser
        driver.quit();
    }

}