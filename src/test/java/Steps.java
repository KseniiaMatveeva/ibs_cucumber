
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.То;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

public class Steps {

    private static ChromeDriver driver;

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

    @Когда("нажимаем на кнопку {string}")
    public void clickButton(String button) {
        WebElement buttonAdd = driver.findElement(By.xpath(button));
        buttonAdd.click();
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
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
