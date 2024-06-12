package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ExcelDataProvider {

    WebDriver driver = null;
    WebDriverWait wait = null;

    @BeforeTest
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test(dataProvider = "testData")
    public void test(String username, String password) {
        System.out.println(username + " | " + password);

        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        WebElement usernameElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@name='username']")));
        usernameElement.sendKeys(username);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();
    }

    @DataProvider(name = "testData")
    public Object[][] getData() {
        String projectPath = System.getProperty("user.dir");
        String excelPath = projectPath + "/excel/data.xlsx";
        String sheetName = "Sheet1";
        return testData(excelPath, sheetName);
    }

    public Object[][] testData(String excelPath,String sheetName) {
        ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
        int rowCount = excel.getRowCount();
        int columnCount = excel.getColumnCount();

        Object[][] data = new Object[rowCount][columnCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                String cellData = excel.getCellData(i, j);
                data[i][j] = cellData;
            }
        }
        return data;
    }
}
