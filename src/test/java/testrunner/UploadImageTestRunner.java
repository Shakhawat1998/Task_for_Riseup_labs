package testrunner;

import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminDashboardPage;
import pages.UpdateImagePage;
import utils.Utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UploadImageTestRunner extends Setup {

    @Test(priority = 1,description = "login with last registered user , upload profile picture and update phone number")
    public  void profileImgUploadAndUpdatePhoneNumber() throws IOException, ParseException, InterruptedException {
        AdminDashboardPage adminDashboardPage= new AdminDashboardPage(driver);
        driver.get("https://dailyfinance.roadtocareer.net/");
        UpdateImagePage updateImagePage = new UpdateImagePage(driver);

        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email = (String) userObj.get("email");
        String password = (String) userObj.get("password");

        adminDashboardPage.doLogin(email,password);
        updateImagePage.btnProfileIcon.click();
        updateImagePage.btnProfile.click();
        updateImagePage.btnEdit.click();
        String relativePath="\\src\\test\\resources\\house.jpg";
        String absolutePath=System.getProperty("user.dir")+relativePath;
        updateImagePage.chooseFile.sendKeys(absolutePath);
        updateImagePage.txtPhoneNumber.sendKeys(Keys.CONTROL,"a");
        updateImagePage.txtPhoneNumber.sendKeys(Keys.BACK_SPACE);
        updateImagePage.txtPhoneNumber.sendKeys("01990150241");
        updateImagePage.btnUpload.click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();

        Utils.scroll(driver,500);
        updateImagePage.btnUpdate.click();
        Thread.sleep(1000);
        driver.switchTo().alert().accept();

    }


}
