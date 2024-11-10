package testrunner;

import com.github.javafaker.Faker;
import config.Setup;
import config.UserModel;
import org.apache.commons.configuration.ConfigurationException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.RegistrationPage;
import utils.Utils;

import java.io.IOException;
import java.time.Duration;

public class RegistrationTestRunner extends Setup {


    @Test(priority = 1, description = "user registration and assert mail and toast message")
    public void registration() throws IOException, ParseException, InterruptedException, ConfigurationException {
        RegistrationPage userReg = new RegistrationPage(driver);
        Faker faker = new Faker();
        userReg.btnRegister.get(1).click();
        String firstname = faker.name().firstName();
        String lastname = faker.name().lastName();
        String email = "shakhawatopi+220@gmail.com";
        String password = "1234";
        String phoneNumber = "01435"+ Utils.generateRandomNumber(100000,999999);
        String address = faker.address().fullAddress();
        UserModel userModel = new UserModel();
        userModel.setFirstname(firstname);
        userModel.setLastname(lastname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        userModel.setPhonenumber(phoneNumber);
        userModel.setAddress(address);
        userReg.doRegistration(userModel);

        doRegAssertion();




        JSONObject userObj=new JSONObject();
        userObj.put("firstName",firstname);
        userObj.put("lastName",lastname);
        userObj.put("email",email);
        userObj.put("password",password);
        userObj.put("phoneNumber",phoneNumber);
        userObj.put("address",address);
        Utils.saveUserInfo("./src/test/resources/users.json",userObj);

        assertEmail();


    }



    public void doRegAssertion() throws InterruptedException {
        Thread.sleep(2000);
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("Toastify__toast")));
        String successMessageActual= driver.findElement(By.className("Toastify__toast")).getText();
        String successMessageExpected="successfully";
        System.out.println(successMessageActual);
        Assert.assertTrue(successMessageActual.contains(successMessageExpected));
    }

    public void assertEmail() throws ConfigurationException, IOException, InterruptedException {

        Thread.sleep(2000);
        String confirmationEmailActual = Utils.readLatestMail();
        String confirmationEmailExpected = "Dear";
        System.out.println(confirmationEmailActual);
        Assert.assertTrue( confirmationEmailActual.contains(confirmationEmailExpected) );

    }

}
