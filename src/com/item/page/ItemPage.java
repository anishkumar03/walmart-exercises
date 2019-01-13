package com.item.page;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.item.report.ExtentTestManager;

public class ItemPage{

	private By labelLego 	 = By.xpath("//a[@class='brand-link' and contains(text(), 'Google')]");
	private By itemImage 	 = By.xpath("//ul[@class='slides large']//li[@class='flex-active-slide']//img[@class='image']");
	private By itemName	 	 = By.xpath("//h1[@data-analytics-type='productPage-productName']");
	private By itemPrice 	 = By.xpath("//div[@aria-label='pricing']");
	private By itemQuantity  = By.xpath("//input[@title='Quantity']");
	private By addCartButton = By.xpath("//div[@class='add-cart-container']");
	private By cartUpdateMsg = By.xpath("//h3[@id='ac-title']");
	private By continueShopBtn = By.xpath("//a[@class='button-list close']"); 
	private By prodDesc	= By.id("product-desc");
	private By priceAndShipping = By.xpath("//div[contains(@class,'pricing-shipping')]");
	private By prodDescription = By.xpath("//h2[@id='product-description-heading']");
	private By itemSpecifications = By.xpath("//section[@id='product-specs']//h2[contains(@class,'accordion-title')]");
	private By ratingReviews = By.xpath("//h2[@id='ratings-reviews-heading']");
	private By checkOutBtn = By.xpath("//div[@id='ac-ctas']/a[@class='button']");
	private By shopCartCheckoutBtn = By.xpath("//a[@id='cart-aside-checkout-btn']");
	private By shopCartTitle = By.xpath("//div[@data-analytics-value='Shopping Cart']");
	private By paymentOptions = By.xpath("//div[@class='payment-method-inner-wrapper']");
	private By secureCheckOut = By.xpath("//h1[contains(text(),'Secure Checkout')]");
	
	private WebDriver driver;
	JavascriptExecutor js;
	WebElement objElement;

	///Launch browser 
	@BeforeTest
	@Parameters({"url"})
	public void setBaseURL(String url) {
		System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		js = (JavascriptExecutor) driver; 
		driver.get(url);
//		tearDown();
	}
	
	
	public void verifyItemHomePage() {
			String title;
			waitForElementPresence(labelLego);
			if (isElementPresent(labelLego)) {
				title = (String) js.executeScript("return document.title");
				ExtentTestManager.getTest().log(Status.PASS, "Navigated to product home page successfully " +title);
			} else {
				ExtentTestManager.getTest().log(Status.FAIL, "Failed to navigate to product home page");
			}
	}
	
	@Test(priority=1, description="Verify product details in item home page")
	public void verifyProductDetails() {
		try {
		verifyItemHomePage();
		WebElement imgFile = driver.findElement(itemImage);
		Boolean imagePresent = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth "
				+ "!= \"undefined\" && arguments[0].naturalWidth > 0", imgFile);
		if(imagePresent)
		{
			ExtentTestManager.getTest().log(Status.PASS, "The product image is displayed in product details page");
		}else
			ExtentTestManager.getTest().log(Status.FAIL, "Failed to display the item image");
			Iterator<String> elmIterator1 = isElementsPresentNameCheck(prodDesc).iterator();
			if(!isElementsPresentNameCheck(prodDesc).isEmpty())
			{
			while (elmIterator1.hasNext()) {
				ExtentTestManager.getTest().log(Status.PASS, "The following product details information is displayed in product details page: " +"<b>"+elmIterator1.next());
			}
			}else
				ExtentTestManager.getTest().log(Status.FAIL, "The expected product details information is not displayed in item page");
			if(!isElementsPresentNameCheck(priceAndShipping).isEmpty())
			{
			Iterator<String> elmIterator2 = isElementsPresentNameCheck(priceAndShipping).iterator();
			while (elmIterator2.hasNext()) {
				ExtentTestManager.getTest().log(Status.PASS, "The following details related to price and shipping is displayed in product details page: " +"<b>"+elmIterator2.next());
			}
			}else
				ExtentTestManager.getTest().log(Status.FAIL, "The expected price and shipping details information is not displayed in item page");
				scrollToElement(prodDescription);
				if (isElementPresent(prodDescription))
				{
					ExtentTestManager.getTest().log(Status.PASS, "Product description is available");
				}else
					ExtentTestManager.getTest().log(Status.FAIL, "Product description is NOT available");
				scrollToElement(prodDescription);
				if (isElementPresent(itemSpecifications))
				{
					ExtentTestManager.getTest().log(Status.PASS, "Product specification is available");
				}else
					ExtentTestManager.getTest().log(Status.FAIL, "Product specification is NOT available");
				scrollToElement(ratingReviews);
				if (isElementPresent(ratingReviews))
				{
					ExtentTestManager.getTest().log(Status.PASS, "Product rating & reviews is available");
				}else
					ExtentTestManager.getTest().log(Status.PASS, "Product rating & reviews is available");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	@Test(priority=2, description="Verify add to cart functionality")
	public void verifyAddtoCartFunction() throws IOException
	{
		if(isElementPresent(addCartButton))
		{
			try {
				scrollToElement(addCartButton);
				Thread.sleep(1000);
				FuncClick(addCartButton);
				waitForElementPresence(cartUpdateMsg);
				if(driver.findElement(cartUpdateMsg).getText().contains("Successfully updated "))
				{
					ExtentTestManager.getTest().log(Status.PASS, "Item added successfully to the cart");
				}else
					ExtentTestManager.getTest().log(Status.FAIL, "Item added successfully to the cart");
				FuncClick(continueShopBtn);
				waitForElementPresence(addCartButton);
				if(isElementPresent(addCartButton)) {	
					ExtentTestManager.getTest().log(Status.PASS, "Clicked on continue shopping button and navigated back to Item home page");
					Thread.sleep(2000);
				}else
					ExtentTestManager.getTest().log(Status.FAIL, "Your shopping cart page is not displayed");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Test(priority=3, description="Verify cart checkout and payment options")
	public void verifyCartCheckoutAndPayment() throws IOException {
		try {
			waitForElementPresence(addCartButton);
			FuncClick(addCartButton);
			Thread.sleep(2000);
			moveToElementNPerformClick(checkOutBtn);
			waitForElementPresence(shopCartTitle);
			if(isElementPresent(shopCartTitle))
			{
				ExtentTestManager.getTest().log(Status.PASS, "Your shopping cart page is displayed");
			}else
				ExtentTestManager.getTest().log(Status.FAIL, "Your shopping cart page is not displayed");
			FuncClick(shopCartCheckoutBtn);
			waitForElementPresence(secureCheckOut);
			if(isElementPresent(secureCheckOut))
			{
				scrollToElement(paymentOptions);
				if(isElementPresent(paymentOptions))
					{
						ExtentTestManager.getTest().log(Status.PASS, "User navigated to Secure Checkout page with different payment options");
					}else
						ExtentTestManager.getTest().log(Status.FAIL, "Payment options are not available");
				
					}else
						ExtentTestManager.getTest().log(Status.FAIL, "Failed to navigate to Secure Checkout page");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@AfterTest
	public void tearDown() {
		if (driver != null)
			driver.close();
	}

	public void waitForElementPresence(By newElement) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 60);
			wait.until(ExpectedConditions.visibilityOf(driver.findElement(newElement)));
			wait.until(ExpectedConditions.presenceOfElementLocated(newElement));
		} catch (Exception e) {
			System.out.println("Fail: Element " +newElement +" does not exist");
			ExtentTestManager.getTest().log(Status.FAIL, "Element " +newElement +" does not exist");
		}
	}

	protected boolean isElementPresent(By by) {
		// Check to see if there are any elements in the found list
		List<WebElement> elements = driver.findElements(by);
		boolean isPresent = elements.size() != 0 && elements.get(0).isDisplayed() && elements.get(0).isEnabled();
		// Return to the original implicit timeout value
		// Create a Constant long value for implicit wait
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return isPresent;
	}
	
	public void FuncClick(By sElementLocater)throws InterruptedException
	{
		try{
		WebDriverWait wait = new WebDriverWait(driver,60);
		objElement = wait.until(ExpectedConditions.elementToBeClickable(sElementLocater));
		objElement.click();		
		}
		catch (IllegalArgumentException e)
		{
			System.out.println("IllegalArgumentException occured");
			throw e;
		}
		catch(Exception e)
		{
			System.out.println("The element is not present in the current page");
			ExtentTestManager.getTest().log(Status.FAIL, "Element " +sElementLocater +" does not exist");
			throw e;
		}
	}
	
	public boolean scrollToElement(By eleSelector) throws IOException{
		try{
			WebDriverWait wait = new WebDriverWait(driver,60);
			objElement = wait.until(ExpectedConditions.presenceOfElementLocated(eleSelector)); 
			((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",driver.findElement(eleSelector));
			return true;			
			}
			catch (IllegalArgumentException e)
			{
				System.out.println("IllegalArgumentException");
				throw e;					
			}
			catch(Exception e)
			{
				ExtentTestManager.getTest().log(Status.FAIL, "Element " +eleSelector +" does not exist");
				System.out.println("The element is not present in the current page");
				throw e;
			}	
		}
	
	public void moveToElementNPerformClick(By eleSelector) throws InterruptedException, IOException{
		try{
			WebDriverWait wait = new WebDriverWait(driver,60);
			objElement = wait.until(ExpectedConditions.presenceOfElementLocated(eleSelector));
			 
			Actions actions = new Actions(driver);
			WebElement lnkSel = driver.findElement(eleSelector);
			actions.moveToElement(lnkSel);
			Thread.sleep(2000);
			actions.click().perform();
			//actions.perform();			
			}
			catch (IllegalArgumentException e)
			{
				ExtentTestManager.getTest().log(Status.FAIL, "Arguments are not correct, see log for more  details..");
				throw e;			
			}
			catch(Exception e)
			{
				ExtentTestManager.getTest().log(Status.FAIL, "Verification-<b>- "+eleSelector+"</b> not present in current page");
				throw e;
			}
	}
	
	public ArrayList<String> isElementsPresentNameCheck(By by) {
		ArrayList<String> returnArray=new ArrayList<String>();
		returnArray.clear();
		try{		
			List<WebElement> elements = driver.findElements(by);
//			System.out.println("size"+elements.size());
			for(WebElement webElement : elements)
			{
				if(webElement.isDisplayed())
				{
					returnArray.add(webElement.getText());
				}
				else
				{
					//No element case, the return value will be null
					returnArray.clear();
					return returnArray;
				}
			}
			return returnArray;
		}
		catch(Exception e){
			returnArray.clear();
			return returnArray;
		}
	}
	

}
