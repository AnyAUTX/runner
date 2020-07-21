package com.AnyAUT;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.InvalidSelectorException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.log4testng.Logger;

import com.google.common.base.Function;

public class LowLevelKeywords {

	// Global Variables
	public WebDriver driver;
	Alert alert;
	int linkcount;
	public String orig_win_handle, parentWindow, childWindow;

	static Logger logger = Logger.getLogger(LowLevelKeywords.class);

	public LowLevelKeywords(WebDriver driver) {

		try {
			this.driver = driver;
		}catch (Exception ex) {
			logger.error("Error occured while passing the webdriver  " + ex);
		}

	}

	/// **********************Reusable function library*****************\\\
	public void openBrowser(String browserType) {

		int browserTimeout = 0;
		String strTimeout = new UICode().returnValues("BrowserTimeOut");
		String osPath = "Browsers/";
		
		if (System.getProperty("os.name").contains("Windows")) {
			osPath = osPath + "Win/";
			} else if (System.getProperty("os.name").contains("Mac")) {
					osPath = osPath + "Mac/";
				} else if (System.getProperty("os.name").contains("Linux")) {
							osPath = osPath + "Linux/";
						}
		
					
		if (strTimeout.length() > 0) {
			browserTimeout = Integer.valueOf(strTimeout);

		}else {
			browserTimeout = 10;
		}
		try {
			switch (browserType.toLowerCase()) {

			case "firefox":
			case "ff":
				System.out.println("Entered Firefox Browser");
				System.setProperty("webdriver.gecko.driver", Utilities.fileAbsolutePath() + osPath + "geckodriver.exe");
				driver = new FirefoxDriver();
				System.out.println(driver);
				break;

			case "chrome":
				System.out.println("Entered Chrome Browser");
				System.setProperty("webdriver.chrome.driver", Utilities.fileAbsolutePath() + osPath + "chromedriver.exe");
				driver = new ChromeDriver();
				break;

			case "ie":
			case "internet explorer":
				System.setProperty("webdriver.ie.driver", Utilities.fileAbsolutePath() + osPath + "IEDriverServer.exe");
				driver = new InternetExplorerDriver();
				break;

			case "edge":
				System.out.println("Entered Microsoft Edge Browser");
				System.setProperty("webdriver.edge.driver", Utilities.fileAbsolutePath() + osPath + "MicrosoftWebDriver.exe");
				driver = new EdgeDriver();
				break;
				/*
				case "safari":
				System.out.println("Entered Safari Edge Browser");
				//System.setProperty("webdriver.safari.driver", Utilities.fileAbsolutePath() + osPath + "SafariWebDriver.exe");
				driver = new SafariDriver();
				break;

				case "PHANTOMJS":
			   System.setProperty("phantomjs.binary.path", Utilities.fileAbsolutePath() + "Browsers/Win/phantomjs.exe");  
			   driver = new PhantomJSDriver();
				break;*/

			default:
				System.out.println("Default Browser - Chrome");
				System.setProperty("webdriver.chrome.driver", Utilities.fileAbsolutePath() + osPath + "chromedriver.exe");
				driver = new ChromeDriver();

			}
		}catch(Exception ex) {
			logger.error("Error occured while initializing the browser drivers " + ex);
		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(browserTimeout, TimeUnit.SECONDS);

	}

	/* Replaced by getElementHighlight for regular keyword calls */
	public 	By getByType(String ElementType,String ElementLocator)
	{   
		By byType;
		switch (ElementType) {
		case "id":
			byType=By.id(ElementLocator);
			break;
		case "className":
			byType=By.className(ElementLocator);
			break;
		case "cssSelector":
			byType = By.cssSelector(ElementLocator);
			break;
		case "name":
			byType = By.name(ElementLocator);
			break;
		case "linkText":
			byType = By.linkText(ElementLocator);
			break;
		case "xpath":
			byType = By.xpath(ElementLocator);
			break;
		case "PartialLinkText":
			byType = By.partialLinkText(ElementLocator);
			break;
		case "tagname":
			byType =By.tagName(ElementLocator);
			break;
		default:
			byType = By.xpath(ElementLocator);
		}
		return byType;
	}
	
	public WebElement getElementHighlight(String ElementType,String ElementLocator)
	{   
		By byType;
		switch (ElementType) {
		case "id":
			byType=By.id(ElementLocator);
			break;
		case "className":
			byType=By.className(ElementLocator);
			break;
		case "cssSelector":
			byType = By.cssSelector(ElementLocator);
			break;
		case "name":
			byType = By.name(ElementLocator);
			break;
		case "linkText":
			byType = By.linkText(ElementLocator);
			break;
		case "xpath":
			byType = By.xpath(ElementLocator);
			break;
		case "PartialLinkText":
			byType = By.partialLinkText(ElementLocator);
			break;
		case "tagname":
			byType =By.tagName(ElementLocator);
			break;
		default:
			byType = By.xpath(ElementLocator);
		}
		try {	
			((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid " + "gold" +"'", driver.findElement(byType));
		//	System.out.println(">>> Element Highlight Passed");
		} catch (Exception e){
		//	System.out.println(">>> Element Highlight Failed");	
		}
		//((JavascriptExecutor)driver).executeScript("arguments[0].style.border=''", driver.findElement(byType));
		return driver.findElement(byType);
	}


	public void navigateBrowser(String URL) {
		driver.navigate().to(URL);
	}

	public void maximizeBrowser() {
		driver.manage().window().maximize();
	}

	public void closeBrowser() {
		driver.close();
	}

	public void quitBrowser() {
		driver.quit();
	}

	/* [KK] REPLACED BY CLICK ELEMENT
	public void clickLink(String EleType,String EleLocator) {
		getElementHighlight(EleType,EleLocator).click();
		//driver.findElement(By.linkText(linkText)).click();
	}

	public void clickButton(String EleType,String EleLocator) {
		getElementHighlight(EleType,EleLocator).click();
	}
	*/

	public void clickElement(String EleType,String EleLocator) {
		getElementHighlight(EleType,EleLocator).click();
	}


	public void typeText(String EleType,String EleLocator, String text) {
		getElementHighlight(EleType,EleLocator).clear();
		getElementHighlight(EleType,EleLocator).sendKeys(text);
	}

	public boolean verifyTitle(String title) {
		if (title.equals(driver.getTitle())) {
			return true;
		} else {
			return false;
		}
	}

	public String verifyText(String EleType, String EleLocator, String testData) {
		testData = testData.trim(); 
		String textToVerify = getElementHighlight(EleType,EleLocator).getText().trim();

		ExecuteTest.verifyOutput = textToVerify;
		System.out.println("Expected Value - Test Data : " + testData );
		System.out.println("Actual Value - from UI: " + textToVerify );

		
		if (testData.equalsIgnoreCase(textToVerify)) {
			return "Pass";
		} else {
			return "Fail";
		}
		
	}
	public String verifyPartialText(String EleType, String EleLocator, String testData) {
		testData = testData.trim(); 
		String textToVerify = getElementHighlight(EleType,EleLocator).getText().trim();

		ExecuteTest.verifyOutput = textToVerify;
		// boolean partialMatch = textToVerify.matches("(?i).*" + testData + ".*");
		boolean partialMatch = textToVerify.contains(testData);
		
		if (partialMatch) {
			return "Pass";
		} else {
			return "Fail";
		}
	}
	
	
	public String verifyTextAnyWhere(String EleType,String EleLocator, String testData) {
		testData = testData.trim(); 
		String textToVerify = getElementHighlight(EleType,EleLocator).getText().trim();

		ExecuteTest.verifyOutput = textToVerify;
		System.out.println("Expected Value - Test Data : " + testData );
		System.out.println("Actual Value - from UI: " + textToVerify );

		if (testData.equalsIgnoreCase(textToVerify)) {
			return "Pass";
		} else {
			return "Fail";
		}
	}

	public String verifyMultilineText(String EleType,String EleLocator, String testData) {
		testData = testData.trim().replaceAll("\n","").replaceAll("\r","").replaceAll("\t", ""); 
		String textToVerify = getElementHighlight(EleType,EleLocator).getText();
		textToVerify = textToVerify.trim().replaceAll("\n","").replaceAll("\r","").replaceAll("\t", "");

		ExecuteTest.verifyOutput = textToVerify;
		System.out.println("Expected Value - Test Data : " + testData );
		System.out.println("Actual Value - from UI: " + textToVerify );

		if (testData.equalsIgnoreCase(textToVerify)) {
			return "Pass";
		} else {
			return "Fail";
		}
	}

	public String verifyValue(String EleType,String EleLocator, String testData) {
		testData = testData.trim();
		String textToVerify = getElementHighlight(EleType,EleLocator).getAttribute("value").trim();

		System.out.println("Expected Value : " + testData );
		System.out.println("Actual Value : " + textToVerify );
		if (testData.equalsIgnoreCase(textToVerify)) {
			return "Pass";
		} else {
			return "Fail";
		}
	}
	public boolean verifyLink(String EleType,String EleLocator) {
		try {
			if (getElementHighlight(EleType,EleLocator).isDisplayed()) {
				return true;
			} 
		}
		catch (Exception ex) { 
			logger.error("Error in isDisplayed method " + ex);
		}
		return false;
	}


	public void enterKeyboard(String EleType,String EleLocator) {
		// Purpose: Simulates ENTER key press of the keyboard
		// IP: Locator of the element
		// OP: N/A
		getElementHighlight(EleType,EleLocator).sendKeys(Keys.ENTER);
	}

	public String readText(String EleType,String EleLocator) {
		// Purpose: Reads a text from an edit field
		// IP: Locator of the element
		// OP: Text of type String
		String text = getElementHighlight(EleType,EleLocator).getAttribute("value");
		return text;
	}
	/// **********************Newly added Reusable function
	/// library*****************\\\

	public void clearText(String EleType,String EleLocator) {
		// Clears the text value of text entry element identified by locator.
		getElementHighlight(EleType,EleLocator).clear();
	}

	public void dragAndDrop(String EleType,String EleLocator, String text) {

		try {
			String[] splitElement = text.split(":");

			WebElement sourceElement = getElementHighlight(EleType,EleLocator);
			WebElement destinationElement = getElementHighlight(splitElement[0],splitElement[1]);

			(new Actions(driver)).dragAndDrop(sourceElement, destinationElement).perform();
		}
		catch (Exception e) {}
	}

	public String getTitle() {
		// get the title of Page
		String title = driver.getTitle();
		System.out.println(title);
		return title;
	}

	public void defaultContent() {
		// Getting back to Default Content from a frame
		driver.switchTo().defaultContent();
	}

	public void switchToFrame(String EleType,String EleLocator) {
		// switch to frame by select frame locator
		WebElement frame = getElementHighlight(EleType,EleLocator);

		driver.switchTo().frame(frame);
	}

	public void selectList(String EleType,String EleLocator, String text) {
		// Purpose: Select an item from a Dropdown List Box
		// IP: Locator of the DDLB, Item from drop down list to be selected
		// OP: N/A
		Select dropDown = new Select(getElementHighlight(EleType,EleLocator));
		dropDown.selectByVisibleText(text);
	}

	public void selectByValue(String EleType,String EleLocator, String value) {
		// Select the given values of multi select list.
		WebElement combobox = getElementHighlight(EleType,EleLocator);
		Select sList = new Select(combobox);
		sList.selectByValue(value);

	}

	//parseInt will throw exception if trying to parse a string of characters instead of string of digits
	// index "100" will pass, but "Hundred" will throw NumberFormatException
	public void selectByIndex(String EleType,String EleLocator, String index) throws NumberFormatException {

		// Select the given index of multi select list.
		int parseIndex = Integer.parseInt(index);
		WebElement dropDown = getElementHighlight(EleType,EleLocator);
		// Pointing to a WE object which is of Type Select
		Select selectList = new Select(dropDown); 
		// By index is pointing to order in the drop-down list
		selectList.selectByIndex(parseIndex); 
	}

	public boolean isAlertPresent() {

		WebDriverWait wait = new WebDriverWait(driver, 15); 

		try {
			wait.until(ExpectedConditions.alertIsPresent());
			return true;
		} catch (TimeoutException eTO) {
			return false;
		}
	}


	public void switchToAlert() {
		// Switch to SimpleAlert/Confirm Alert box/Prompt Alert box
		try {
			alert = driver.switchTo().alert();

		} catch (NoAlertPresentException Ex) {

			System.out.println("Alert dialogue does not yet exist...");

		}
	}


	public String getAlertText() {

		if (isAlertPresent()) {
			alert = driver.switchTo().alert();
			return alert.getText();
		}
		return "";
	}

	public void dismissAlert() {
		if (isAlertPresent()) {
			driver.switchTo().alert();
			alert.dismiss();
		}

		else {
			logger.error("Cannot find alert to dismiss!");
		}
	}

	public void confirmAlert() {

		if (isAlertPresent()) {
			driver.switchTo().alert();
			alert.accept();
		}

		else {
			logger.error("Cannot find alert to dismiss!");
		}
	}



	public void mouseHover(String EleType,String EleLocator) {
		WebElement menu =getElementHighlight(EleType,EleLocator);
		Actions action = new Actions(driver);
		action.moveToElement(menu).build().perform();
	}

	public void contextClick(String EleType,String EleLocator) {
		// You can use this function for doing right click on any Webelement
		WebElement rightClick = getElementHighlight(EleType,EleLocator);
		Actions action = new Actions(driver);
		Action a1 = action.contextClick(rightClick).build();
		a1.perform();
	}

	public void dragAndDropByOffset(String EleType,String EleLocator, String xyoffset) {
		try {
			String[] xysplit = xyoffset.split(",");
			int x = Integer.parseInt(xysplit[0]);
			int y = Integer.parseInt(xysplit[1]);

			WebElement webElement = getElementHighlight(EleType,EleLocator);

			//drag the webElement from x coordinate to y coordinate, and drop at y
			new Actions(driver).dragAndDropBy(webElement, x, y).build().perform();
		}
		catch (Exception e) {}
	}

	public HashMap<String, String> getWindowNames() {
		Set<String> windowHandles=null;
		HashMap<String, String> hmap=null;
		windowHandles = driver.getWindowHandles();
		hmap = new HashMap<String, String>();
		orig_win_handle = driver.getWindowHandle();
		hmap.put("parentWindow", orig_win_handle);
		// window is a string looping thru all the window handles
		for (String window : windowHandles) {
			// when window is not original then switch to the new window and get
			// the title of the new window
			if (!window.equals(orig_win_handle)) {
				driver.switchTo().window(window);
				// hmap.put(window, driver.getTitle());
				hmap.put("childWindow", window);
			}
		}
		// go back to original window get the title
		//driver.switchTo().window(orig_win_handle);
		// hmap.put(orig_win_handle, driver.getTitle());
		
		System.out.println("getWindowNames"+ hmap);
		return hmap;
	}

		
	public void closepopupWindow() {
		driver.close();
	}

	public void switchToWindow(String childWinHandle) {
		
		System.out.println("Child window:" + childWinHandle);
		driver.switchTo().window(childWinHandle);
		
	}
public void switchToParentWindow(String parentWinHandle) {
		
		System.out.println("Parent window:" + parentWinHandle);
		driver.switchTo().window(parentWinHandle);
		
	}

	public void waitForSeconds(String seconds) throws InterruptedException {
		Thread.sleep(Integer.parseInt(seconds)*1000);
	}
	public void waitTillElementIsEnabled(String EleType,String EleLocator, String seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(seconds));
		wait.until(ExpectedConditions.elementToBeClickable(getByType( EleType, EleLocator)));
	}

	public void waitTillElementIsInvisible(String EleType,String EleLocator, String seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(seconds));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(getByType( EleType, EleLocator)));
	}

	public void waitTillElementIsVisible(String EleType,String EleLocator, String seconds) {
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(seconds));
		wait.until(ExpectedConditions.visibilityOfElementLocated(getByType( EleType, EleLocator)));
	}

	public void waitTillElementTextPresent(String EleType,String EleLocator, String text) {
		// Wait till text is present - we know the text displayed but don't know
		// xpath/locator
		WebDriverWait wait = new WebDriverWait(driver, 20);
		wait.until(ExpectedConditions.textToBePresentInElementLocated(getByType( EleType, EleLocator), text));
	}


	public void fluentWait(String EleType,String EleLocator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(20, TimeUnit.SECONDS)
				.pollingEvery(10, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		//The fluent wait will be applied on the below element
		WebElement element = wait.until(new Function<WebDriver, WebElement>() 
		{
			public WebElement apply(WebDriver driver) 
			{
				return getElementHighlight(EleType,EleLocator);
			}
		});
		element = null;
	}

	public void openLinkInNewWindow(String EleType,String EleLocator) {
		WebElement Webelement =getElementHighlight(EleType,EleLocator);
		Actions action = new Actions(driver);
		action.keyDown(Keys.SHIFT).click(Webelement).keyUp(Keys.SHIFT).build().perform();
	}

	public void clickCheckbox(String EleType,String EleLocator, String text) throws InterruptedException {
		WebElement checkbox = getElementHighlight(EleType,EleLocator);
		Thread.sleep(2000);
		if ( checkbox.isSelected() ) {
			checkbox.click();
			Thread.sleep(3000);
		} else if
		(!checkbox.isSelected() ){
			checkbox.click();
		}
	}


	public void tabKeyboard(String EleType,String EleLocator) {
		WebElement webElement = getElementHighlight(EleType,EleLocator);
		webElement.sendKeys(Keys.TAB);
	}

	/*Changed the following keyword capsLockKeyword to 2 keywords, toUppercase and toLowercase after 
	 * discussing with Chandra Sekhar. No need for Shift keys. Jan 30, 2018
	public void capsLockKeyword(String EleType,String EleLocator, String text) { 
		// Purpose:
		// IP:
		// OP:
		Actions builder = new Actions(driver);
		WebElement e = getElementHighlight(EleType,EleLocator);
		Action writeCapital = builder.keyDown(Keys.SHIFT).sendKeys(e, text).keyUp(Keys.SHIFT).build();
		writeCapital.perform();
	}*/


	//toUpperCase( ) method is used to convert all the character in the specified string from Lower case to Upper case.
	public String toUppercase(String text) {
		// Purpose:
		// IP:
		// OP:

		String uppercase = "";

		uppercase = text.toUpperCase();
		System.out.println("Applying uppercase method " + uppercase);// added syso statement to see that it is all in uppercase
		return uppercase;
	}

	//toLowerCase( ) method is used to convert all the character in the specified string from Lower case to Upper case.
	public String toLowercase(String text) {
		// Purpose:
		// IP:
		// OP:

		String lowercase = "";
		lowercase = text.toLowerCase();
		System.out.println("Applying lowercase method " + lowercase); //added syso statement to see that it is all in lowercase
		return lowercase;
	}


	public void implicitWait(int seconds) {
		driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.MILLISECONDS);
	}

	public void doubleClick(String EleType,String EleLocator) {
		Actions action = new Actions(driver);
		WebElement element = getElementHighlight(EleType,EleLocator);
		action.doubleClick(element).perform();
	}

	public String isEnabled(String EleType,String EleLocator) {
		if (getElementHighlight(EleType,EleLocator).isEnabled()) {
			return "Pass";
		} else {
			return "Fail";
		}
	}

	public String isDisabled(String EleType,String EleLocator) {

		if (!(getElementHighlight(EleType,EleLocator)).isEnabled()) {
			return "Pass";
		} else {
			return "Fail";
		}
	}

	public String isDisplayed(String EleType,String EleLocator) throws Exception {
		// Purpose: Checks if element is displayed or not
		if (getElementHighlight(EleType,EleLocator).isDisplayed()){
			return "Pass";
		} else {
			return "Fail";
		}
	}

	public void pageLoadTimeout(String seconds) {
		driver.manage().timeouts().pageLoadTimeout(Integer.parseInt(seconds), TimeUnit.SECONDS);

	}

	// [KK] - ADD TO TEST STEP OUTPUT COLUMN?
	public String getAttributeValue(String EleType,String EleLocator, String attribute)
	{
		String value="";

		try {
			WebElement webElement = getElementHighlight(EleType,EleLocator);
			value = webElement.getAttribute(attribute.toLowerCase());
		}
		catch (Exception e) {
			
		}

		if ((value == "") || (value == null))
		{
			logger.info("Keyword getAttributeValue - Attribute not found!");
		}
		System.out.println("attribute : "+ attribute );
		System.out.println("value "+ value);
		return value;
	}

	public String verifyAttributeValue(String EleType, String EleLocator, String data)
	{
		try {
			String attribute;
			String output[] = data.split(":"); //The splitter in earlier code was '|', which did not work somehow. So changed to ':'
			attribute = output[0];
			data = output[1];

			WebElement webElement = getElementHighlight(EleType,EleLocator);
			if (webElement.getAttribute(attribute.toLowerCase()).equalsIgnoreCase(data))
				return "Pass";
		}
		catch (Exception e) {}

		return "Fail";
	}

	public String getCssValue(String EleType,String EleLocator, String data)
	{
		WebElement webElement = getElementHighlight(EleType,EleLocator);
		String cssValue = webElement.getCssValue(data);

		return cssValue;
	}


	public List<String> getAllLinks(String EleType,String EleLocator) {
		List<WebElement> links = new ArrayList<WebElement>();
		List<String> linkText = new ArrayList<String>();


		links = driver.findElements(getByType(EleType,EleLocator));

		linkcount = links.size();
		for (int i = 0; i < links.size(); i++) {
			linkText.add((links.get(i)).getText());
		}
		System.out.println("inside llk" + linkText);
		return linkText;

	}

	public void goBack() {
		driver.navigate().back();

	}

	public void goForward() {
		driver.navigate().forward();
	}

	public void refresh() {
		driver.navigate().refresh();

	}

	public void scrollDown() {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0, document.body.scrollHeight)");

	}

	public void scrollByElement(String EleType,String EleLocator) {

		WebElement element = getElementHighlight(EleType,EleLocator);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);

	}

	public void selectRadio(String EleType,String EleLocator, String value) {
		List<WebElement> radiobtn = new ArrayList<WebElement>();
		radiobtn=driver.findElements(getByType(EleType,EleLocator));
		for (int i = 0; i < radiobtn.size(); i++) {
			String rdvalue = radiobtn.get(i).getAttribute("value");
			if (rdvalue.equalsIgnoreCase(value)) {
				radiobtn.get(i).click();
				break;
			}
		}
	}

	public String getText(String EleType,String EleLocator) {
		/*
		String text = getElementHighlight(EleType,EleLocator).getText();
		System.out.println(text);
		return text;
		*/
		return getElementHighlight(EleType,EleLocator).getText();

	}

	public boolean isSelected(String EleType,String EleLocator) {
		return getElementHighlight(EleType,EleLocator).isSelected();
	}
	
	public boolean isNotSelected(String EleType,String EleLocator) {
		return (!getElementHighlight(EleType,EleLocator).isSelected());
	}


	//getWebElement keyword name changed to getWebElements
	public List<WebElement> getWebElements(String EleType,String EleLocator) {
		List<WebElement> webelements = new ArrayList<WebElement>();
		webelements = driver.findElements(getByType(EleType,EleLocator));
		return webelements;
	}

	public String takePageScreenshot(String filePath) {
		String scrnSht = "";
		try {
			File srcfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(filePath);
			FileUtils.copyFile(srcfile, new File(filePath));
			scrnSht = destFile.getAbsolutePath();
		} catch (Exception e) {
			System.out.println("Exception in Lowlevelkeywords takepagescreenshot method" + e.getClass().getSimpleName());
		}
		return scrnSht;


	}

	
	 /*...............New BIK by Selenium Dev Group.............................................*/

	// BIK 

	public void deleteAllCookies(){ 
		driver.manage().deleteAllCookies();
	}

	public void keyUpAndDown() {   

		//To Do: Need to add code for Mac
		Actions action = new Actions (driver);
		action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.DELETE).perform();
	}

	public String reverseString (String text){
		int len = text.length();// use a loop, starting from 0
		String reverse = "";
		for (int i=len-1; i>=0; i--) {
			reverse = reverse + text.charAt(i);
		}                
		return reverse;
	}


	public boolean isElementPresent (String EleType, String EleLocator) {

		//Need exception handling
		if(driver.findElements(getByType(EleType, EleLocator)).size() > 0){
			return true;                                                                      
		}

		return false;                                                                                                                                   
	}

	//Look for any text/data in a webpage
	public boolean isTextPresent(String text) {

		if(driver.getPageSource().contains(text)){
			return true; 
		}
		return false;  
	}
	
	public void waitforAlert() { 

		WebDriverWait wait = new WebDriverWait(driver, 15); 
		wait.until(ExpectedConditions.alertIsPresent());
	}

	public void waitforTitle(String text) { 

		//Wait for page title
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.titleContains(text)); 
	}	 


	//how many pixels to scroll up vertically on y axis (x axis/horizontal being 0)
	public void scrollUpByPixel() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,-scrollHeight)"); //(0,-250)
		Thread.sleep(4000);
	}

	//how many pixels to scroll down vertically along y axis (x axis/horizontal is 0)
	public void scrollDownByPixel() throws InterruptedException {
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,scrollHeight)");//(0, 250)
		Thread.sleep(4000);
	}


	//zoom by percentage is not supported by Firefox
	public void zoomByPercentage(String percentage) {

		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName();

		// Script not supported by Firefox
		if (browserName == "firefox") {
			logger.error("The keyword 'zoomByPercentage' is not supported in Firefox browser");
		}
		else {
			try {
				int percent = Integer.parseInt(percentage);
				((JavascriptExecutor) driver).executeScript("document.body.style.zoom="+"'"+percent+"%'");
				//	Thread.sleep(4000);
			} catch (Exception ex) {
				logger.error("Error occured in zoomInByPercentage Keyword " + ex);	
			}
		}
	}

	//This keyword verifies the alert message against a test data
	public String verifyAlertText(String data) {

		if (isAlertPresent()) {
			alert = driver.switchTo().alert();
			if (alert.getText().equalsIgnoreCase(data))
				return "Pass";
		}
		else {
			logger.error("Cannot find alert!");
		}
		return "Fail";
	}

	//send a text to the input field of Prompt Alert
	public void sendTextToAlert(String data) {

		if (isAlertPresent()) {
			alert = driver.switchTo().alert();
			alert.sendKeys(data);
		}
		else {
			logger.error("Cannot find alert!");
		}
	}


	// Select 1st Option present in the dropDownlist 
	public String selectFirstOptionInDropDownList(String EleType,String EleLocator, String Value) 
	{              
		Select selectByXpath = new Select(getElementHighlight(EleType,EleLocator));
		WebElement  selected_firstValue = selectByXpath.getFirstSelectedOption();
		String firstValue = selected_firstValue.getText();
		return firstValue;
	}

	/*
     //click and Hold a source element ,Move to desired target by sepcifing x and y axis and release 
    public void Resizable_clickAndHold_Release (String ElementType,String ElementLocator,String xoffset,String yoffset)
    {    
 	  int xoffset1 = Integer.parseInt(xoffset);
       int yoffset1 = Integer.parseInt(yoffset); 		
         Actions action = new Actions(driver); 
         action.clickAndHold(driver.findElement(getByType(ElementType,ElementLocator)))
        .moveByOffset(xoffset1 , yoffset1)
        .release().build().perform();
     } 
	 */

	//Drag an object Horizontally .Eg scrolling a page left and right
	public void scroll_Horizontally (String ElementType,String ElementLocator,String xoffset)
	{
		int yoffset =0;
		int xoffset1 = Integer.parseInt(xoffset);
		Actions action = new Actions(driver); 
		action.clickAndHold(driver.findElement(getByType(ElementType,ElementLocator)))
		.moveByOffset(xoffset1,yoffset )
		.release().build().perform();
	} 

	//Drag an object Vertically .Eg scrolling a page Up and Down 
	public void scroll_Vertically (String ElementType,String ElementLocator,String yoffset)
	{
		int xoffset=0;
		int yoffset1 = Integer.parseInt(yoffset);
		Actions action = new Actions(driver); 
		action.clickAndHold(driver.findElement(getByType(ElementType,ElementLocator)))
		.moveByOffset(xoffset,yoffset1 )
		.release().build().perform();
	}
	
	// ************************* NEW BUILT-IN-KEYWORDS ********************************
							
	public void switchToPrintWindow(String childWinHandle) {									
							
	    driver.switchTo().window(childWinHandle);								
	 								
	}

	public void closeLatestWindow() {								
					
				driver.close();					
	}								
									
	public void highlightElement(String EleType, String EleLocator, String color) {
	
		try {	
			WebElement element = getElementHighlight(EleType,EleLocator);
			// Draws a border around the found element. Does not set it back anyhow.
			((JavascriptExecutor)driver).executeScript("arguments[0].style.border='3px solid " + color +"'", element);
		} 
		catch (InvalidSelectorException e1) {
			// Do nothing
		}
		catch (Exception e){
			System.out.println("Element Highlight Failed" + e);	
		}
	}


	public void unHighlightElement(String EleType, String EleLocator) {

		try {
			//After the screen shot is taken, the highlighted element should be unhighlighted
			WebElement element = getElementHighlight(EleType,EleLocator);
			((JavascriptExecutor)driver).executeScript("arguments[0].style.border=''", element);
		} catch (Exception e){
			// System.out.println("!@#!@#!@# Element unHighlight Failed" + e);	
		}
	}
}
