package com.AnyAUT;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class swdTest1 {

	public static void main(String[] args) {
		String vURL = "https://go.itelearn.com/";
		WebDriver myD; // Declaring my browser Driver... 
		
		System.out.println("Print this");
		System.out.println("Start Program");
		
		System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver.exe");
		myD = new ChromeDriver();
		// WebDriver myD = new ChromeDriver();
		
		// 1. Go to AUT
		myD.navigate().to(vURL);
		System.out.println(myD.getTitle());
		
		// 2. Click on all courses
		myD.findElement(By.linkText("All Courses")).click();
		System.out.println(myD.getTitle());
		
		// 3. Type a course in Search

		// 4. Verify a course
		
				
		myD.close();
		System.out.println("End Program");

	}

}
