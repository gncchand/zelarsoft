package Firefox;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import excel.Shopping_Data;
import pages.Shopping_Page;
import pages.Checkout_Page;


import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;

public class Shopping_Firefox_Test {
	public static WebDriver driver;
	public ExtentReports er;
	public static ExtentTest et;
	public Shopping_Page scp;
	public Checkout_Page ccp;
		
	 @BeforeSuite
	  public void launchApp()
		{
		  er=new ExtentReports(System.getProperty("user.dir")+"\\Shopping_ReportResults_Firefox.html",true);
		    et=er.startTest("shopping website", "cart checkout validaton using Firefox"); 
			 driver=new FirefoxDriver();
			 driver.get("http://automationpractice.com/index.php");
			 driver.manage().window().maximize();
			 driver.manage().timeouts().implicitlyWait(12, TimeUnit.SECONDS);
			 scp=new Shopping_Page(driver);
			 ccp=new Checkout_Page(driver);		 
		}
	 
  @Test(dataProvider = "getexcel_data")  
  public void test_cart(String title, String price, String quantity, String name, String total) throws Exception {
	  Thread.sleep(3000);
	  System.out.println("Title : "+title+" ; Price : "+price+" ; Quantity : "+quantity+" ; Name : "+name+" ; Total : "+total);
	  scp.click_best_Seller();
	  Thread.sleep(3000);
	  WebElement product=  driver.findElement(By.xpath("(//*[contains(text(),'"+name+"')])[3]"));
	  System.out.println("clicking on..."+product.getText());
	  product.click();
	  System.out.println("price ..."+ccp.price.getText());
	  
	  if(ccp.price.getText().equalsIgnoreCase(price))//validate price
	  {
		  et.log(LogStatus.PASS, "Price validation test case passed");
		  Thread.sleep(3000);		  
	  }
	  else
	  {
	   et.log(LogStatus.FAIL, "Price validation test case failed");
	   Thread.sleep(3000);
	  }

	  if(driver.getTitle().equalsIgnoreCase(title))
	  {
		    et.log(LogStatus.PASS, "Dress title test case passed");
		    Thread.sleep(3000);
		    driver.findElement(By.xpath("//i[@class='icon-plus']")).click();
		    Thread.sleep(3000);
		    ccp.checkout.click();
		    Thread.sleep(6000);
		    System.out.println("success text is.."+ccp.success_msg.getText());
		    if(ccp.success_msg.getText().equalsIgnoreCase("Product successfully added to your shopping cart"))
		    {
		    et.log(LogStatus.PASS, "Successfull message test case passed");
		    Thread.sleep(3000);				  
		    }
			else
		    {
		    et.log(LogStatus.FAIL, "Successfull message test case failed");
		    Thread.sleep(3000);
		    }   
		   
		   if(ccp.cart_price.getText().equalsIgnoreCase(total))
		   {
		   et.log(LogStatus.PASS, "Cart price test case passed");
		   Thread.sleep(3000);	  
		   }
		   else
			{
			et.log(LogStatus.FAIL, "Cart price test case failed");
			Thread.sleep(3000);
			}
		   
		    if(ccp.cart_quantity.getText().equalsIgnoreCase(quantity))
		    {
			  et.log(LogStatus.PASS, "Cart quantity test case passed");
			   Thread.sleep(3000);
			  
		    }
		   else
			{
			  et.log(LogStatus.FAIL, "Cart quantity test case failed..expected "+ccp.cart_quantity.getText()+" but found actual "+quantity);
			  take_screenshot();
			  Thread.sleep(3000);
			   
			 }
		  
		    if(ccp.cart_total.getText().equalsIgnoreCase(total))
		    {
			  et.log(LogStatus.PASS, "Cart total test case passed");
			   Thread.sleep(3000);	  
		    }
		    else
			{
			  et.log(LogStatus.FAIL, "Expected is "+ccp.cart_total.getText()+" but Actual is "+total);
			  take_screenshot();	
			   Thread.sleep(3000);
			}
		 
		  ccp.click_cont_shoppin();
	  }
	  else
	  {
	   et.log(LogStatus.FAIL, "Dress title test case failed");
	   Thread.sleep(3000);
	  }	 
  }
 
  	@AfterSuite
  	public void closeBrowser() throws AWTException 
  	{
		er.endTest(et);
	  	er.flush();
	    driver.quit();	  
  	}
  
	 @DataProvider 
	 public String[][] getexcel_data() throws IOException
	 {
	   Shopping_Data excel=new Shopping_Data();
	   return excel.get_data_from_excel("shopping_data.xlsx", "Firefox");
	 }
	 public static void take_screenshot() throws Exception
		{
			File src= ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			SimpleDateFormat sf=new SimpleDateFormat("dd-MM-yy-hh-mm-ss");
			Date d =new Date();
			String fname=sf.format(d)+".png";
			File dest=new File("C:\\Users\\tanvi\\Downloads\\"+fname);
			System.out.println(dest);
			FileHandler.copy(src, dest);
			et.log(LogStatus.FAIL, "<br><img src='"+dest+"' height='500' width='700'/><br>");  
		}
	 
}