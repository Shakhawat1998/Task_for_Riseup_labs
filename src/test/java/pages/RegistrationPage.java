package pages;

import config.UserModel;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class RegistrationPage {
    @FindBy(tagName = "a")
    public List<WebElement> btnRegister;
    @FindBy(id = "firstName")
    public WebElement txtFirstname;
    @FindBy(id = "lastName")
    public WebElement txtLastname;
    @FindBy(id = "email")
    public WebElement txtEmail;
    @FindBy(id = "phoneNumber")
    public WebElement txtPhoneNumber;
    @FindBy(id = "password")
    public WebElement txtPassword;
    @FindBy(id = "address")
    public WebElement txtAddress;
    @FindBy(css = "[type=checkbox]")
    WebElement chkAcceptTerms;
    @FindBy(css = "[type=radio]")
    List<WebElement> rbGender;
    @FindBy(id = "register")
    public WebElement btnSubmitReg;

    public RegistrationPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }

    public void doRegistration(UserModel userModel){
        txtFirstname.sendKeys(userModel.getFirstname()!=null?userModel.getFirstname():"");
        txtLastname.sendKeys(userModel.getLastname()!=null?userModel.getLastname():"");
        txtEmail.sendKeys(userModel.getEmail()!=null?userModel.getEmail():"");
        txtPassword.sendKeys(userModel.getPassword()!=null?userModel.getPassword():"");
        txtPhoneNumber.sendKeys(userModel.getPhonenumber()!=null?userModel.getPhonenumber():"");
        txtAddress.sendKeys(userModel.getAddress()!=null?userModel.getAddress():"");
        rbGender.get(0).click();
        chkAcceptTerms.click();
        btnSubmitReg.click();
    }



}
