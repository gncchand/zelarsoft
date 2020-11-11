package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Shopping_Chrome_Page {
	public WebDriver driver;
	
	//locators - elements
	@FindBy(className="blockbestsellers")
	public WebElement best_seller;
	
	@FindBy(xpath="(//a[contains(text(),\"Printed Chiffon Dress\")])[2]")
	public WebElement dress;
	
	//methods - constructor methods
	public Shopping_Chrome_Page(WebDriver driver)
	{
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	//methods- operational methods
	
	public void click_best_Seller()
	{
		best_seller.click();		
	}	
	
	public void click_on_Dress()
	{
		dress.click();
	}


}
