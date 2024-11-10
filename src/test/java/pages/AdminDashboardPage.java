package pages;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDashboardPage {
    @FindBy(id = "email")
    public WebElement txtEmail;

    @FindBy(id = "password")
    public  WebElement txtPassword;

    @FindBy(css = "[type=submit]")
    public  WebElement btnLogin;

    @FindBy(xpath = "//table//tbody/tr[1]/td")
    public List<WebElement> firstRowTblData;

    @FindBy(xpath = "//tbody/tr[1]/td[8]/button[1]")
    public WebElement btnView;

    @FindBy(className = "profile-image")
    public WebElement profileImage;

    public  AdminDashboardPage(WebDriver driver){
        PageFactory.initElements(driver,this);
    }


    public  void doLogin(String email, String password){
        txtEmail.sendKeys(email);
        txtPassword.sendKeys(password);
        btnLogin.click();
    }



    public Map<String,String> checkLastRegisteredUserData() throws IOException, ParseException {

        Map<String, String> userData = new HashMap<>();

        //first name of last user from table
        String firstname1 = firstRowTblData.get(0).getText();
        //email of last user from table
        String email1 = firstRowTblData.get(2).getText();


        // Storing table data in map
        userData.put("tableFirstName", firstname1);
        userData.put("tableEmail", email1);



        System.out.println(firstname1);
        System.out.println(email1);


        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String firstnameJSONlast = (String) userObj.get("firstName");
        String emailJSONlast = (String) userObj.get("email");


        // Storing JSON data in map
        userData.put("jsonFirstName", firstnameJSONlast);
        userData.put("jsonEmail", emailJSONlast);


        return userData;

    }
}
