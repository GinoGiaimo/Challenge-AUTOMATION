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

import java.util.List;

public class DemoBlazeProductoPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(css = "a.btn-success")
    private WebElement botonAgregarAlCarrito;

    @FindBy(tagName = "h2")
    private WebElement tituloProducto;

    @FindBy(tagName = "h3")
    private WebElement precioProducto;

    public DemoBlazeProductoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        PageFactory.initElements(this.driver, this);
    }

    public void agregarAlCarrito() {
        wait.until(ExpectedConditions.visibilityOf(botonAgregarAlCarrito));
        botonAgregarAlCarrito.click();
        waitForPageLoad();
    }

    public String obtenerTituloProducto() {
        wait.until(ExpectedConditions.visibilityOf(tituloProducto));
        return tituloProducto.getText();
    }

    public String obtenerPrecioProducto() {
        wait.until(ExpectedConditions.visibilityOf(precioProducto));
        return precioProducto.getText();
    }

    protected void waitForPageLoad() {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}
