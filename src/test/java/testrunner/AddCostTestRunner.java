package testrunner;

import config.CostDataset;
import config.Setup;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AddCostPage;
import pages.AdminDashboardPage;
import utils.Utils;

import java.io.FileReader;
import java.io.IOException;

public class AddCostTestRunner extends Setup {
    int csvTotalCost =0;
    String expectedTotalCost;





    @Test(dataProvider = "CostCSVData", dataProviderClass = CostDataset.class, priority = 1, description = "Add 5 items from CSV dataset")
    public void addItemCost(String item, int quantity, String amount, String purchaseDate, String month, String remarks) throws IOException, ParseException, InterruptedException {
        AdminDashboardPage adminDashboardPage = new AdminDashboardPage(driver);
        driver.get("https://dailyfinance.roadtocareer.net/");
        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));
        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email = (String) userObj.get("email");
        String password = (String) userObj.get("password");
        adminDashboardPage.doLogin(email,password);
        AddCostPage addCostPage = new AddCostPage(driver);
        addCostPage.btnAddCost.click();
        addCostPage.txtItemName.sendKeys(item);
        for(int i=1;i<quantity;i++){
            addCostPage.btnPlus.click();
        }

        addCostPage.txtAmount.sendKeys(amount);
        csvTotalCost = csvTotalCost+Integer.parseInt(amount);

        addCostPage.purchaseDate.click();
        addCostPage.purchaseDate.clear();
        addCostPage.purchaseDate.sendKeys(purchaseDate);

        Select dropMonth = new Select(driver.findElement(By.id("month")));
        dropMonth.selectByVisibleText(month);
        Utils.scroll(driver,500);
        addCostPage.txtRemarks.sendKeys(remarks);
        addCostPage.btnSubmit.click();
        Thread.sleep(2000);
        driver.switchTo().alert().accept();

    }
    @Test(priority = 2, description = "calculate total cost from CSV dataset and check whether it matches with total cost from UI")
    public void showExpectedCostAndMatchWithTotalCost() throws IOException, InterruptedException, ParseException {
        AdminDashboardPage adminDashboardPage= new AdminDashboardPage(driver);
        driver.get("https://dailyfinance.roadtocareer.net/");
        JSONParser parser=new JSONParser();
        JSONArray jsonArray= (JSONArray) parser.parse(new FileReader("./src/test/resources/users.json"));

        JSONObject userObj= (JSONObject) jsonArray.get(jsonArray.size()-1);
        String email = (String) userObj.get("email");
        String password = (String) userObj.get("password");

        adminDashboardPage.doLogin(email,password);
        expectedTotalCost = String.valueOf(csvTotalCost);
        System.out.println(expectedTotalCost);

        AddCostPage addCostPage= new AddCostPage(driver);
        Thread.sleep(2000);
        String totalCostFromUI = addCostPage.totalCostUI.getText();
        System.out.println(totalCostFromUI);
        String actualTotalCost = totalCostFromUI.replaceAll("[^0-9]", "");
        System.out.println(actualTotalCost);
        Assert.assertTrue(actualTotalCost.contains(expectedTotalCost), "expected and actual matched");

    }


}
