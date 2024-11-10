package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddCostPage {
    @FindBy(xpath = "//button[normalize-space()='Add Cost']")
    public WebElement btnAddCost;
    @FindBy(id = "itemName")
    public WebElement txtItemName;
    @FindBy(xpath = "//button[normalize-space()='+']")
    public WebElement btnPlus;
    @FindBy(id= "amount")
    public WebElement txtAmount;
    @FindBy(id = "remarks")
    public WebElement txtRemarks;
    @FindBy(css = "[type=submit]")
    public WebElement btnSubmit;
    @FindBy(id = "purchaseDate")
    public WebElement purchaseDate;
    @FindBy(xpath = "//span[normalize-space()][2]")
    public WebElement totalCostUI;

    public AddCostPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

}
