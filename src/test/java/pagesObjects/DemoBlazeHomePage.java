package pagesObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class DemoBlazeHomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "itemc")
    private List<WebElement> categorias;

    @FindBy(css = "h4.card-title")
    private List<WebElement> titulosProductos;

    public DemoBlazeHomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(this.driver, this);
    }

    public void seleccionarCategoria(String nombreCategoria) {
        for (WebElement c : categorias) {
            if (c.getText().trim().equalsIgnoreCase(nombreCategoria)) {
                c.click();
                waitForPageLoad();
                return;
            }
        }
        throw new RuntimeException("Category not found: " + nombreCategoria);
    }

    public void seleccionarProductoPorNombre(String nombreProducto) {
        wait.until(ExpectedConditions.visibilityOfAllElements(titulosProductos));
        List<WebElement> productos = driver.findElements(By.cssSelector("h4.card-title a"));
        for (WebElement p : productos) {
            if (p.getText().trim().equalsIgnoreCase(nombreProducto)) {
                p.click();
                waitForPageLoad();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + nombreProducto);
    }

    protected void waitForPageLoad() {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}
