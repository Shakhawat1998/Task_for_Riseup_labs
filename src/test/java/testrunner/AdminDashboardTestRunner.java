package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.UpdateImagePage;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminDashboardTestRunner extends Setup {


    @Test(priority = 1, description = "login by admin credentials and check if last registered user is showed in dashboard")
    public void adminLoginAndCheckLastUserRegisteredIsDisplayed() throws IOException, ParseException {

        AdminDashboardPage adminDashboardPage = new AdminDashboardPage(driver);



        if(System.getProperty("username")!=null && System.getProperty("password")!=null){
            adminDashboardPage.doLogin(System.getProperty("username"),System.getProperty("password"));
        }
        else{
            adminDashboardPage.doLogin("admin@test.com","admin123");
        }

        Map<String,String> userData = adminDashboardPage.checkLastRegisteredUserData();
        assertEquals(userData.get("tableFirstName"), userData.get("jsonFirstName"), "First name  match!");
        assertEquals(userData.get("tableEmail"), userData.get("jsonEmail"), "Email  match!");




    }

    @Test(priority = 2, description = "Check uploaded profile picture is showed or not")
    public void checkProfilePictureIsUpdatedFromAdminDashboard() throws IOException, ParseException {
        AdminDashboardPage adminDashboardPage= new AdminDashboardPage(driver);
        driver.get("https://dailyfinance.roadtocareer.net/");
        if(System.getProperty("username")!=null && System.getProperty("password")!=null){
            adminDashboardPage.doLogin(System.getProperty("username"),System.getProperty("password"));
        }
        else{
            adminDashboardPage.doLogin("admin@test.com","admin123");
        }
        adminDashboardPage.btnView.click();

        String src= adminDashboardPage.profileImage.getAttribute("src");
        System.out.println(src);
        assert src != null;
        Assert.assertTrue(src.contains("profileImage"), "profile image was successfully uploaded");

    }

}
