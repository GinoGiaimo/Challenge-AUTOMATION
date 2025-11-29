package driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.Reporter;

import java.util.HashMap;
import java.util.Map;

public class DriverFactory {
    public enum browsers {
        CHROME, FIREFOX, EDGE
    }

    public static WebDriver LevantarBrowser(WebDriver driver, String browserName, String url) {
        switch (browsers.valueOf(browserName)) {
            case CHROME: {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                options.addArguments("start-maximized");// Descomentar linea al implementar
                options.addArguments("--window-size=1920,1080");// Descomentar linea al implementar
                driver = new ChromeDriver(options);
                break;
            }
            case FIREFOX: {
                System.setProperty("webdriver.gecko.driver", "");
                Reporter.log("Abrir navegador Firefox");
                driver = new FirefoxDriver();
                break;
            }
            case EDGE: {
                System.setProperty("webdriver.edge.driver", "");
                Reporter.log("Abrir navegador Edge");
                driver = new EdgeDriver();
                break;
            }
            default: {
                Reporter.log("No se selecciono un navegador");
                ChromeOptions options = new ChromeOptions();
                options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
                options.addArguments("--headless"); // Activa el modo headless
                System.setProperty("webdriver.chrome.driver", "/jenkins/app/chromedriver");
                Reporter.log("Abrir navegador Chrome!");
                driver = new ChromeDriver(options);
                break;
            }
        }

        driver.manage().window().maximize(); // Comentar linea al implementar

//		driver.get(url);

        int maxRetries = 3; // Número máximo de reintentos
        int attempt = 0;
        boolean isAvailable = false;

        while (attempt < maxRetries) {
            driver.get(url);
            if (!isServiceUnavailable(driver)) {
                isAvailable = true;
                break;
            }
            //log.warn("La aplicación no está disponible (503). Intentando recargar... (Intento " + (attempt + 1) + ")");
            attempt++;
            try {
                Thread.sleep(5000); // Esperar un poco antes de intentar nuevamente
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        if (!isAvailable) {
            //log.error("La aplicación no estuvo disponible después de " + maxRetries + " intentos.");
            driver.quit();
            throw new RuntimeException("La aplicación no está disponible (503).");
        }

        return driver;
    }

    private static boolean isServiceUnavailable(WebDriver driver) {
        String pageSource = driver.getPageSource();
        return pageSource.contains("Application is not available") || driver.getTitle().contains("503");
    }

    private static Map<String, Object> getLogPreferences() {
        Map<String, Object> logPrefs = new HashMap<>();
        logPrefs.put(LogType.BROWSER, "ALL");
        return logPrefs;
    }

    public static void CloseBrowser(WebDriver driver) {
        driver.quit();
    }
}
