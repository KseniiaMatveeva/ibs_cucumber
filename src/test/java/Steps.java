
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.То;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.sql.*;
import java.util.concurrent.TimeUnit;

public class Steps {

    private static ChromeDriver driver;
    private Statement statement;

    @Дано("открыта страница по {string}")
    public void openWebPage(String string) {
        ChromeOptions options = new ChromeOptions();
        String version = "127.0.6533.120";
        options.setBrowserVersion(version);
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get(string);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
    }

    @Дано("осуществлено подключение к БД")
    public void testConnect() throws SQLException, ClassNotFoundException {
        //Class.forName("org.h2.Driver");
        //DriverManager.registerDriver(new org.h2.Driver());
        Connection connection = DriverManager.getConnection(
                "jdbc:h2:tcp://localhost:9092/mem:testdb",
                "user", "pass");
        this.statement = connection.createStatement();
    }

    @Когда("добавляем товар с значениями {string} {string} {string}")
    public void testAdd(String name, String type, String tf) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT MAX(FOOD_ID) FROM FOOD");
        rs.next();
        int maxId = rs.getInt(1);
        String insertStr = "INSERT INTO FOOD (FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC) VALUES(" +
                (maxId + 1) + ", "+ name+", "+type+", "+tf+")";
        statement.executeUpdate(insertStr);
        rs = statement.executeQuery("SELECT TOP 1 * FROM FOOD ORDER BY FOOD_ID DESC");
        rs.next();
        int newMaxId = rs.getInt("FOOD_ID");
        Assertions.assertNotEquals(maxId,newMaxId);
        Assertions.assertEquals(maxId+1, newMaxId);
    }

    @То("удаляем товар, который вставили")
    public void testDelet() throws SQLException {
        statement.executeUpdate("DELETE FROM FOOD WHERE FOOD_ID=(SELECT MAX(FOOD_ID) FROM FOOD)");
        ResultSet rs = statement.executeQuery("SELECT MAX(FOOD_ID) FROM FOOD");
        rs.next();
        int finalMaxId = rs.getInt(1);
    }

    @Когда("нажимаем на кнопку {string}")
    public void clickButton(String button) {
        WebElement buttonAdd = driver.findElement(By.xpath(button));
        buttonAdd.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Когда("вводим {string} в поле {string}")
    public void inputText(String text, String field) {
        WebElement newInput = driver.findElement(By.xpath(field));
        newInput.click();
        newInput.sendKeys(text);
    }

    @Когда("выбираем {string} из списка {string}")
    public void chooseIt(String element, String name){
        WebElement field = driver.findElement(By.xpath(name));
        WebElement selectedType = driver.findElement(By.xpath(element));
        field.sendKeys(selectedType.getText());
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Когда("ставим значение {string} в поле {string}")
    public void setExotic(String info, String field){
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        String normalizedInfo = info.toLowerCase();
        boolean isTrue = normalizedInfo.contains("true");
        if (isTrue) {
            WebElement inputExotic = driver.findElement(By.xpath(field));
            inputExotic.click();
        }
//        WebElement inputExotic = driver.findElement(By.xpath(field));
//        inputExotic.click();
    }

    @То("поле {string} равно {string}")
    public void assertSmth(String record, String string){
        WebElement newRecord = driver.findElement(By.ByCssSelector.cssSelector(record));
        Assertions.assertEquals(string, newRecord.getText());
    }

    @То("закрываем страницу")
    public void closeWebPage(){
        driver.quit();
    }
}
