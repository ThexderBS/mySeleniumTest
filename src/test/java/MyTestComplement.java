import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyTestComplement {
    WebDriver driver;

    public MyTestComplement(WebDriver wd) {
        driver = wd;
        System.out.println("Hola soy complemento.");
        testComplement();
    }

    public void testComplement(){
        By byInput34 = By.name("txt_34");
        WebElement input34 = driver.findElement(byInput34);
        input34.clear();
        input34.sendKeys("Este es un cambio.");

        By byInput100 = By.name("txt_100");
        WebElement input100 = driver.findElement(byInput100);
        input100.clear();
        input100.sendKeys("Fin de mi prueba.");
    }
}
