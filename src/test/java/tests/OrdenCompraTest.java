package tests;

import driver.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pagesObjects.DemoBlazeCarritoPage;
import pagesObjects.DemoBlazeHomePage;
import pagesObjects.DemoBlazeProductoPage;

public class OrdenCompraTest {

    private WebDriver driver;
    private final String URL_STRING = "https://www.demoblaze.com/";

    private static final Logger log = (Logger) LogManager.getLogger(OrdenCompraTest.class);

    DemoBlazeHomePage homePage;
    DemoBlazeProductoPage productoPage;
    DemoBlazeCarritoPage carritoPage;

    @BeforeTest
    public void setUp() {
        // Configuraciones previas al test, si es necesario
        log.info("Configuración previa al test de orden de compra.");
        String navegadorSuite = null;
        String navegadorString = navegadorSuite != null ? navegadorSuite : "CHROME";

        this.driver = DriverFactory.LevantarBrowser(this.driver, navegadorString, URL_STRING);

        homePage = new DemoBlazeHomePage(driver);
        productoPage = new DemoBlazeProductoPage(driver);
        carritoPage = new DemoBlazeCarritoPage(driver);
    }

    @Test
    public void testCrearOrdenCompra() throws InterruptedException {
        log.info("Iniciando test de creación de orden de compra");

        String nombre = "Gino";
        String pais = "Argentina";
        String ciudad = "Córdoba";
        String tarjeta = "3112222221111111";
        String mes = "11";
        String año = "2025";

        String categoria = "Phones";
        String producto = "Samsung galaxy s6";

        SoftAssert softAssert = new SoftAssert();

        log.info("Navegando a la página principal");
        homePage.seleccionarCategoria(categoria);
        homePage.seleccionarProductoPorNombre(producto);

        log.info("Verificando detalles del producto seleccionado");
        softAssert.assertEquals(productoPage.obtenerTituloProducto(), producto, "El título del producto debería coincidir");

        String precioTexto = productoPage.obtenerPrecioProducto().replaceAll("[^0-9]", "");

        log.info("Agregando producto al carrito");
        productoPage.agregarAlCarrito();
        Thread.sleep(1000);
        String textoAlerta = aceptarAlerta();

        log.info("Verificando alerta de producto agregado");
        softAssert.assertTrue(textoAlerta.toLowerCase().contains("product added"), "La alerta debería indicar que el producto fue agregado");

        log.info("Navegando al carrito de compras");
        carritoPage.irAlCarrito();

        log.info("Verificando que el carrito contiene el producto seleccionado y el precio total");
        softAssert.assertTrue(carritoPage.tieneElProductoSeleccionado(producto));
        softAssert.assertEquals(precioTexto, carritoPage.obtenerTotal(), "El precio total en el carrito debería coincidir con el precio del producto");

        log.info("Realizando la orden de compra");
        carritoPage.clickRealizarOrden();
        log.info("Completando el formulario de orden");
        carritoPage.completarFormularioOrden(nombre, pais, ciudad, tarjeta, mes, año);
        carritoPage.pagarOrden();
        String textoConfirmacion = carritoPage.obtenerTextoAlertaPago();
        String detallePago = carritoPage.obtenerDetallePago();

        log.info("Verificando confirmación de pago");
        softAssert.assertTrue(detallePago.contains(nombre), "El detalle de pago debería contener el nombre");
        softAssert.assertTrue(detallePago.contains(tarjeta), "El detalle de pago debería contener el número de tarjeta");
        softAssert.assertTrue(detallePago.contains(precioTexto), "El detalle de pago debería contener el precio total");
        softAssert.assertTrue(textoConfirmacion.equals("Thank you for your purchase!"), "El texto de confirmación de pago debería coincidir");
        softAssert.assertAll();
    }

    @AfterTest(alwaysRun = true)
    public void cerrarDriver() {
        if (driver != null) driver.quit();
    }

    private String aceptarAlerta() {
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
}
