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

public class DemoBlazeCarritoPage {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(id = "cartur")
    private WebElement botonCarrito;

    @FindBy(css = "button.btn-success")
    private WebElement botonOrdenar;

    @FindBy(id = "name")
    private WebElement inputName;

    @FindBy(id = "country")
    private WebElement inputCountry;

    @FindBy(id = "city")
    private WebElement inputCity;

    @FindBy(id = "card")
    private WebElement inputCard;

    @FindBy(id = "month")
    private WebElement inputMonth;

    @FindBy(id = "year")
    private WebElement inputhYear;

    @FindBy(css = "#orderModal button.btn-primary")
    private WebElement botonPagar;

    @FindBy(id = "orderModalLabel")
    private WebElement h5TituloOrden;

    @FindBy(css = "div.sweet-alert")
    private WebElement alertaPago;

    @FindBy(css = "button.confirm")
    private WebElement botonConfirmar;

    @FindBy(css = "table tbody tr")
    private List<WebElement> listaProductosCarrito;

    @FindBy(id = "totalp")
    private WebElement totalPrecio;

    public DemoBlazeCarritoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, java.time.Duration.ofSeconds(10));
        PageFactory.initElements(this.driver, this);
    }

    public void irAlCarrito() {
        wait.until(ExpectedConditions.visibilityOf(botonCarrito));
        botonCarrito.click();
        waitForPageLoad();
    }

    public void clickRealizarOrden() {
        wait.until(ExpectedConditions.visibilityOf(botonOrdenar));
        botonOrdenar.click();
        waitForPageLoad();
    }

    public void completarFormularioOrden(String nombre, String pais, String ciudad, String tarjeta, String mes, String año) {
        wait.until(ExpectedConditions.visibilityOf(h5TituloOrden));
        inputName.sendKeys(nombre);
        inputCountry.sendKeys(pais);
        inputCity.sendKeys(ciudad);
        inputCard.sendKeys(tarjeta);
        inputMonth.sendKeys(mes);
        inputhYear.sendKeys(año);
    }

    public void pagarOrden() {
        botonPagar.click();
        waitForPageLoad();
    }

    public String obtenerTextoAlertaPago() {
        wait.until(ExpectedConditions.visibilityOf(alertaPago));
        WebElement h2 = alertaPago.findElement(By.cssSelector("h2"));
        return h2.getText();
    }

    public boolean tieneElProductoSeleccionado(String nombreProducto) {
        wait.until(ExpectedConditions.visibilityOfAllElements(listaProductosCarrito));
        for (WebElement fila : listaProductosCarrito) {
            WebElement celdaTitulo = fila.findElement(By.cssSelector("td:nth-child(2)"));
            if (celdaTitulo.getText().trim().equalsIgnoreCase(nombreProducto)) {
                return true;
            }
        }
        return false;
    }

    public String obtenerTotal() {
        wait.until(ExpectedConditions.visibilityOf(totalPrecio));
        return totalPrecio.getText();
    }

    public String obtenerDetallePago() {
        wait.until(ExpectedConditions.visibilityOf(alertaPago));
        WebElement p = alertaPago.findElement(By.cssSelector("p"));
        return p.getText();
    }

    protected void waitForPageLoad() {
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }
}
